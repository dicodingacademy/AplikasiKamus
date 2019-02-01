package com.dicoding.picodiploma.kamus.database;

import android.provider.BaseColumns;

class DatabaseContract {

    static final String TABLE_INDO = "table_indo";
    static final String TABLE_ENGLISH = "table_kata";

    static final class KataColumns implements BaseColumns {
        //kata awal
        static final String WORD = "kata";
        //deskripsi kata
        static final String DESCRIPTION = "deskripsi";
    }

}
