package io.github.mpao.popmovies.models.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String NAME = "App.db";

    public DbHelper(Context context){
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AppContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no rules for saving data on upgrade, reset the db
        db.execSQL(AppContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
