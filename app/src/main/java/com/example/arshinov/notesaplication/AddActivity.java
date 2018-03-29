package com.example.arshinov.notesaplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.ContentValues;

public class AddActivity extends AppCompatActivity {

    EditText etTitle, etNote;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etTitle = (EditText) findViewById(R.id.editText);
        etNote = (EditText) findViewById(R.id.editText3);
        dbHelper = new DBHelper(this);
    }
    public void saveNotes(View view){
        String title = etTitle.getText().toString();
        String notes = etNote.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();


        contentValues.put(DBHelper.KEY_TITLE, title);
        contentValues.put(DBHelper.KEY_NOTE, notes);


        database.insert(DBHelper.TABLE_NOTES, null, contentValues);

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
