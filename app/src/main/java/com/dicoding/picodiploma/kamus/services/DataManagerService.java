package com.dicoding.picodiploma.kamus.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.dicoding.picodiploma.kamus.database.WordHelper;
import com.dicoding.picodiploma.kamus.prefs.AppPreference;

public class DataManagerService extends Service {
    public static final int PREPARATION_MESSAGE = 0;
    public static final int UPDATE_MESSAGE = 1;
    public static final int SUCCESS_MESSAGE = 2;
    public static final int FAILED_MESSAGE = 3;
    public static final int CANCEL_MESSAGE = 4;
    public static final String ACTIVITY_HANDLER = "activity_handler";

    private LoadDataAsync loadData;
    private Messenger mActivityMessenger;

    @Override
    public void onCreate() {
        super.onCreate();

        WordHelper wordHelper = WordHelper.getInstance(getApplicationContext());
        AppPreference appPreference = new AppPreference(getApplicationContext());

        loadData = new LoadDataAsync(wordHelper, appPreference, myCallback, getResources());
    }

    /***
     * Ketika semua ikatan sudah di lepas maka ondestroy akan secara otomatis dipanggil
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        loadData.cancel(true);
    }

    /***
     * Method yang akan dipanggil ketika service diikatkan ke activity
     * @param intent digunakan untuk getPareclableExtra
     * @return menerima IBinder
     */
    @Override
    public IBinder onBind(Intent intent) {

        mActivityMessenger = intent.getParcelableExtra(ACTIVITY_HANDLER);

        loadData.execute();
        return mActivityMessenger.getBinder();
    }

    /***
     * Method yang akan dipanggil ketika service dilepas dari activity
     * @param intent digunakan sebagai argumen dalam method onUbind
     * @return melepas service
     */
    @Override
    public boolean onUnbind(Intent intent) {
        loadData.cancel(true);
        return super.onUnbind(intent);
    }

    /***
     * Method yang akan dipanggil ketika service diikatkan kembali
     * @param intent digunakan sebagai argumen dalam method onRebind
     */
    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    private final LoadDataCallback myCallback = new LoadDataCallback() {
        @Override
        public void onPreLoad() {
            Message message = Message.obtain(null, PREPARATION_MESSAGE);
            try {
                mActivityMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onLoadCancel() {
            Message message = Message.obtain(null, CANCEL_MESSAGE);
            try {
                mActivityMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onProgressUpdate(long progress) {
            try {
                Message message = Message.obtain(null, UPDATE_MESSAGE);
                Bundle bundle = new Bundle();
                bundle.putLong("KEY_PROGRESS", progress);
                message.setData(bundle);
                mActivityMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onLoadSuccess() {
            Message message = Message.obtain(null, SUCCESS_MESSAGE);
            try {
                mActivityMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onLoadFailed() {
            Message message = Message.obtain(null, FAILED_MESSAGE);
            try {
                mActivityMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
}
