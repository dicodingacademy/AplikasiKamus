package com.dicoding.picodiploma.kamus.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.dicoding.picodiploma.kamus.database.DatabaseContract.KataColumns.DESCRIPTION;
import static com.dicoding.picodiploma.kamus.database.DatabaseContract.KataColumns.WORD;
import static com.dicoding.picodiploma.kamus.database.DatabaseContract.TABLE_ENGLISH;
import static com.dicoding.picodiploma.kamus.database.DatabaseContract.TABLE_INDO;

class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbkata";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_INDO = "create table " + TABLE_INDO +
            " (" + _ID + " integer primary key autoincrement, " +
            WORD + " text not null, " +
            DESCRIPTION + " text not null);";

    private static final String CREATE_TABLE_ENGLISH = "create table " + TABLE_ENGLISH +
            " (" + _ID + " integer primary key autoincrement, " +
            WORD + " text not null, " +
            DESCRIPTION + " text not null);";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_INDO);
        db.execSQL(CREATE_TABLE_ENGLISH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INDO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENGLISH);
        onCreate(db);
    }
}
