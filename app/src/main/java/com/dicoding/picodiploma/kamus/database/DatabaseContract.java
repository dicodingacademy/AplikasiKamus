package com.dicoding.picodiploma.kamus.database;

import android.os.Parcelable;
import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_INDO = "table_indo";
    static String TABLE_ENGLISH = "table_kata";

    static final class KataColumns implements BaseColumns {
        static String WORD = "kata";
        static String DESCRIPTION = "deskripsi";
    }

}
