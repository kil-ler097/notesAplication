package com.example.arshinov.notesaplication;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by arshinov on 24.03.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "notesDB";
    public static final String TABLE_NOTES = "notes";


    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_NOTE = "note";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NOTES + "(" + KEY_ID
                + " integer primary key," + KEY_TITLE + " text," + KEY_NOTE + " text" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_NOTES);
        onCreate(db);
    }

    public void deleteField(SQLiteDatabase db,String id){
        db.execSQL("delete from " + TABLE_NOTES + "where" + KEY_ID + "="+id);
    }
}
