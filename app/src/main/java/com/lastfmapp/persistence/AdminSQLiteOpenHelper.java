package com.lastfmapp.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context) {
        super(context, TablesHelper.DATABASE_NAME, null, TablesHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TablesHelper.CREATE_ARTIST);
        db.execSQL(TablesHelper.CREATE_ALBUM);
        db.execSQL(TablesHelper.CREATE_TRACK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TablesHelper.DROP_ARTIST);
        db.execSQL(TablesHelper.CREATE_ARTIST);

        db.execSQL(TablesHelper.DROP_ALBUM);
        db.execSQL(TablesHelper.CREATE_ALBUM);

        db.execSQL(TablesHelper.DROP_TRACK);
        db.execSQL(TablesHelper.CREATE_TRACK);
    }
}
