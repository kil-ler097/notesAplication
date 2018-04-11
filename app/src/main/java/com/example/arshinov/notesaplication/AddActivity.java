package com.example.arshinov.notesaplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.content.ContentValues;

public class AddActivity extends AppCompatActivity {

    EditText etTitle, etNote, etID;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etTitle = (EditText) findViewById(R.id.editText);
        etNote = (EditText) findViewById(R.id.editText3);
        etID = (EditText) findViewById(R.id.editText2);
        dbHelper = new DBHelper(this);
        String id = "";
        id = getIntent().getStringExtra("note_id");

        final SQLiteDatabase database = dbHelper.getWritableDatabase();

        etID.setText(id);
        Log.d("mLog NOTE ID", id);

        if (id.equals("new")) {}else{
            Cursor cursor = database.query(DBHelper.TABLE_NOTES, null, "_id=" + id, null, null, null, null);
            if (cursor.moveToFirst()) {
                int titleIndex = cursor.getColumnIndex(DBHelper.KEY_TITLE);
                int noteIndex = cursor.getColumnIndex(DBHelper.KEY_NOTE);
                do {
                    etTitle.setText(cursor.getString(titleIndex));
                    etNote.setText(cursor.getString(noteIndex));
                } while (cursor.moveToNext());
            }
        }
    }

    public void saveNotes(View view) {
        String title = etTitle.getText().toString();
        String notes = etNote.getText().toString();
        String id = etID.getText().toString();
        String sql;
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Log.d("mLog", "INSERT "+id+" BLALAL");

        ContentValues contentValues = new ContentValues();
        if (id.equals("new")) {
            contentValues.put(DBHelper.KEY_TITLE, title);
            contentValues.put(DBHelper.KEY_NOTE, notes);
            database.insert(DBHelper.TABLE_NOTES, null, contentValues);
        } else {
            sql = "update notes set note='" + notes + "',title='" + title + "' where _id=" + id;
            Log.d("mLog", sql);
            database.execSQL(sql);
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
