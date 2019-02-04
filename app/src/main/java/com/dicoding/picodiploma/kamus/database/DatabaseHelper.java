package com.dicoding.picodiploma.kamus.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.dicoding.picodiploma.kamus.database.DatabaseContract.KataColumns.DESCRIPTION;
import static com.dicoding.picodiploma.kamus.database.DatabaseContract.KataColumns.WORD;
import static com.dicoding.picodiploma.kamus.database.DatabaseContract.TABLE_ENGLISH;
import static com.dicoding.picodiploma.kamus.database.DatabaseContract.TABLE_INDONESIA;

class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_dictionary";
    private static final int DATABASE_VERSION = 2;

    private static final String CREATE_TABLE_INDO = "create table " + TABLE_INDONESIA +
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

    /***
     *  Method onUpgrade akan di panggil ketika terjadi perbedaan versi
     *  Gunakan method onUpgrade untuk melakukan proses migrasi data
     * @param db = merupakan database yang sudah di generate
     * @param oldVersion = merupakan versi dari database yang lama
     * @param newVersion = merupakan versi dari database yang baru
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        Drop table tidak dianjurkan ketika proses migrasi terjadi dikarenakan data user akan hilang,
         */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INDONESIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENGLISH);
        onCreate(db);
    }
}
