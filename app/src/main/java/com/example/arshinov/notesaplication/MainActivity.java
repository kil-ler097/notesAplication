package com.example.arshinov.notesaplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.ViewDebug;
import android.widget.LinearLayout;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    private LinearLayout llt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        Button btn = new Button(MainActivity.this);
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NOTES, null, null, null, null, null, null);
        llt = (LinearLayout) findViewById(R.id.container2);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (cursor.moveToFirst()) {
            final int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int titleIndex = cursor.getColumnIndex(DBHelper.KEY_TITLE);
            int noteIndex = cursor.getColumnIndex(DBHelper.KEY_NOTE);
            int i;
            i = 0;
            do {
                final String ids;
                ids = cursor.getString(idIndex);
                btn = new Button(MainActivity.this);
                btn.setText(cursor.getString(titleIndex));
                btn.setId(idIndex);
                btn.setWidth(600);
                btn.setHeight(50);
                if (i >0 ) {
                    btn.setY(-95*i);
                }
                btn.setX(0);
                btn.setPadding(0, 0, 250, 0);
                btn.setLayoutParams(layoutParams);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, AddActivity.class);
                        intent.putExtra("note_id",ids);
                        startActivity(intent);
                    }
                });

                Log.d("mLog", " title = " + cursor.getString(titleIndex) + ", note = " + cursor.getString(noteIndex));
                llt.addView(btn);

//                DELETE BUTTON BEGIN
                btn = new Button(MainActivity.this);
                btn.setText("X");
                btn.setWidth(50);
                btn.setHeight(50);
                btn.setX(605);
                if (i == 0) {
                    btn.setY(-95);
                }
                if (i >0) {
                    btn.setY(-95*(i+1));
                }
                btn.setLayoutParams(layoutParams);
                btn.setId(idIndex);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database.execSQL("delete from notes where _id="+ids);
                    }
                });
                llt.addView(btn);
                i++;
            } while (cursor.moveToNext());
        } else
            Log.d("mLog", "0 rows");
        cursor.close();
    }

    public void addNotes(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("note_id","new");

        startActivity(intent);
    }

    public void EditNote(String id){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
}

