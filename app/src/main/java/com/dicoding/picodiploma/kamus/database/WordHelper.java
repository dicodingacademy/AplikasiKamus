package com.dicoding.picodiploma.kamus.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.dicoding.picodiploma.kamus.model.WordModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dicoding.picodiploma.kamus.database.DatabaseContract.KataColumns.DESCRIPTION;
import static com.dicoding.picodiploma.kamus.database.DatabaseContract.KataColumns.WORD;
import static com.dicoding.picodiploma.kamus.database.DatabaseContract.TABLE_ENGLISH;
import static com.dicoding.picodiploma.kamus.database.DatabaseContract.TABLE_INDONESIA;

public class WordHelper {

    private final DatabaseHelper databaseHelper;
    private static WordHelper INSTANCE;
    private SQLiteDatabase database;

    public WordHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static WordHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WordHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    /***
     * Gunakan method ini untuk mendapatkan semua data kata indo
     *
     * @return hasil query word model di dalam arraylist
     */
    public ArrayList<WordModel> getAllData() {
        Cursor cursor = database.query(TABLE_INDONESIA, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<WordModel> arrayList = new ArrayList<>();
        WordModel wordModel;
        if (cursor.getCount() > 0) {
            do {
                wordModel = new WordModel();
                wordModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                wordModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                wordModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));


                arrayList.add(wordModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    /***
     * Gunakan method ini untuk mendapatkan semua data kata english
     *
     * @return hasil query word model di dalam arraylist
     */
    public ArrayList<WordModel> getAllDataEng() {
        Cursor cursor = database.query(TABLE_ENGLISH, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<WordModel> arrayList = new ArrayList<>();
        WordModel wordModel;
        if (cursor.getCount() > 0) {
            do {
                wordModel = new WordModel();
                wordModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                wordModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                wordModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));


                arrayList.add(wordModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    /***
     * Gunakan method ini untuk memulai sesi query transaction
     */
    public void beginTransaction() {
        database.beginTransaction();
    }

    /***
     * Gunakan method ini jika query transaction berhasil, jika error jangan panggil method ini
     */
    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    /***
     * Gunakan method ini untuk mengakhiri sesi query transaction
     */
    public void endTransaction() {
        database.endTransaction();
    }

    /***
     * Gunakan method ini untuk query insert IndoWord di dalam transaction
     *
     * @param wordModel inputan wordmodel
     */
    public void insertTransaction(WordModel wordModel) {
        String sql = "INSERT INTO " + TABLE_INDONESIA + " (" + WORD + ", " + DESCRIPTION
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, wordModel.getWord());
        stmt.bindString(2, wordModel.getDescription());
        stmt.execute();
        stmt.clearBindings();
    }

    /***
     * Gunakan method ini untuk query insert EnglishWord di dalam transaction
     *
     * @param wordModel inputan wordmodel
     */
    public void insertTransactionENG(WordModel wordModel) {
        String sql = "INSERT INTO " + TABLE_ENGLISH + " (" + WORD + ", " + DESCRIPTION
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, wordModel.getWord());
        stmt.bindString(2, wordModel.getDescription());
        stmt.execute();
        stmt.clearBindings();
    }
}
