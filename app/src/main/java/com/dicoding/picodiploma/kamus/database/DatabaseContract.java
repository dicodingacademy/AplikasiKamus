package com.dicoding.picodiploma.kamus.database;

import android.provider.BaseColumns;

class DatabaseContract {

    static final String TABLE_INDONESIA = "tb_indonesia";
    static final String TABLE_ENGLISH = "tb_english";

    static final class KataColumns implements BaseColumns {
        //kata awal
        static final String WORD = "word";
        //deskripsi kata
        static final String DESCRIPTION = "description";
    }

}
