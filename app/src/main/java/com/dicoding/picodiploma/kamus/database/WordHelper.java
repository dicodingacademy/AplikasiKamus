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
import static com.dicoding.picodiploma.kamus.database.DatabaseContract.TABLE_INDO;

public class WordHelper {

    private DatabaseHelper databaseHelper;
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

    public ArrayList<WordModel> getAllData() {
        Cursor cursor = database.query(TABLE_INDO, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<WordModel> arrayList = new ArrayList<>();
        WordModel wordModel;
        if (cursor.getCount() > 0) {
            do {
                wordModel = new WordModel();
                wordModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                wordModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                wordModel.setDescrition(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));


                arrayList.add(wordModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

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
                wordModel.setDescrition(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));


                arrayList.add(wordModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void insertTransaction(WordModel wordModel) {
        String sql = "INSERT INTO " + TABLE_INDO + " (" + WORD + ", " + DESCRIPTION
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, wordModel.getWord());
        stmt.bindString(2, wordModel.getDescrition());
        stmt.execute();
        stmt.clearBindings();
    }

    public void insertTransactionENG(WordModel wordModel) {
        String sql = "INSERT INTO " + TABLE_ENGLISH + " (" + WORD + ", " + DESCRIPTION
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, wordModel.getWord());
        stmt.bindString(2, wordModel.getDescrition());
        stmt.execute();
        stmt.clearBindings();
    }
}
