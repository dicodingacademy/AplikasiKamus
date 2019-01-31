package com.dicoding.picodiploma.kamus.services;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.dicoding.picodiploma.kamus.R;
import com.dicoding.picodiploma.kamus.database.WordHelper;
import com.dicoding.picodiploma.kamus.model.WordModel;
import com.dicoding.picodiploma.kamus.prefs.AppPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

class LoadDataAsync extends AsyncTask<Void,Integer, Boolean> {

    private final String TAG = LoadDataAsync.class.getSimpleName();
    private final WordHelper wordHelper;
    private final AppPreference appPreference;
    private final WeakReference<LoadDataCallback> weakCallback;
    private final WeakReference<Resources> weakResources;
    private double progress;
    private double progressEng;
    private final double maxprogress = 100;

    public LoadDataAsync(WordHelper wordHelper, AppPreference appPreference,LoadDataCallback callback, Resources resources) {
        this.wordHelper = wordHelper;
        this.appPreference = appPreference;
        this.weakCallback = new WeakReference<>(callback);
        this.weakResources = new WeakReference<>(resources);
    }

    @Override
    protected void onPreExecute() {
        weakCallback.get().onPreLoad();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Boolean firstRun = appPreference.getFirstRun();
        if (firstRun){

            ArrayList<WordModel> wordModels = preLoadRaw();
            ArrayList<WordModel> wordModelsEng = preLoadRawEng();

            wordHelper.open();
            progress = 0;
            progressEng = 0;
            publishProgress((int) progress);
            publishProgress((int) progressEng);
            Double progressMaxInsert = 100.0;
            Double progressDiff = (progressMaxInsert - progress) / wordModels.size();
            Double progressDiffEng = (progressMaxInsert - progressEng) / wordModelsEng.size();

            boolean isInsertSuccess;
            try {

                wordHelper.beginTransaction();

                for (WordModel model : wordModels) {
                    if (isCancelled()) {
                        break;
                    } else {
                        wordHelper. insertTransaction(model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                }

                if (isCancelled()) {
                    isInsertSuccess = false;
                    appPreference.setFirstRun(true);
                    weakCallback.get().onLoadCancel();
                } else {
                    // Jika proses insert indo telah selesai, selanjut nya insert english data
                    for (WordModel model : wordModelsEng) {
                        if (isCancelled()) {
                            break;
                        } else {
                            wordHelper.insertTransactionENG(model);
                            progressEng += progressDiffEng;
                            publishProgress((int) progressEng);
                        }
                    }
                    wordHelper.setTransactionSuccess();
                    isInsertSuccess = true;

                    /*
                     Set preference first run ke false
                     Agar proses preload tidak dijalankan untuk kedua kalinya
                     */
                    appPreference.setFirstRun(false);
                }
            } catch (Exception e) {
                // Jika gagal maka do nothing
                Log.e(TAG, "doInBackground: Exception");
                isInsertSuccess = false;

            } finally {
                // Transaction
                wordHelper.endTransaction();
            }
            wordHelper.close();

            publishProgress((int) maxprogress);

            return isInsertSuccess;

        } else {
            try {
                synchronized (this) {
                    this.wait(2000);

                    publishProgress(50);

                    this.wait(2000);
                    publishProgress((int) maxprogress);

                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        weakCallback.get().onProgressUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            weakCallback.get().onLoadSuccess();
        } else {
            weakCallback.get().onLoadFailed();
        }

    }

    private ArrayList<WordModel> preLoadRaw() {
        ArrayList<WordModel> wordModels = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = weakResources.get();
            InputStream raw_dict = res.openRawResource(R.raw.indonesia_english);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                WordModel wordModel;

                wordModel = new WordModel(splitstr[0], splitstr[1]);
                wordModels.add(wordModel);
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordModels;
    }

    private ArrayList<WordModel> preLoadRawEng() {
        ArrayList<WordModel> wordModels = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = weakResources.get();
            InputStream raw_dict = res.openRawResource(R.raw.english_indonesia);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                WordModel wordModel;

                wordModel = new WordModel(splitstr[0], splitstr[1]);
                wordModels.add(wordModel);
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordModels;
    }


}
