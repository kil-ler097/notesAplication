package com.example.arshinov.notesaplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.ViewDebug;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    private LinearLayout llt;
    public Integer indx = 0;
    EditText etTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NOTES, null, null, null, null, null, null);
        Button btn = new Button(MainActivity.this);
        llt = (LinearLayout) findViewById(R.id.container2);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (cursor.moveToFirst()) {

            final int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int titleIndex = cursor.getColumnIndex(DBHelper.KEY_TITLE);
            int noteIndex = cursor.getColumnIndex(DBHelper.KEY_NOTE);
            int i;
            i = 0;
            indx = 0;

            do {
                indx++;
                final String ids;
                ids = cursor.getString(idIndex);
                btn = new Button(MainActivity.this);
                btn.setText(cursor.getString(titleIndex));
                btn.setId(indx);
                Log.d("CREATE LOG", "True__" + indx);

                btn.setWidth(600);
                btn.setHeight(50);
                if (i > 0) {
                    btn.setY(-95 * i);
                }
                btn.setX(0);
                btn.setPadding(0, 0, 250, 0);
                btn.setLayoutParams(layoutParams);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, AddActivity.class);
                        intent.putExtra("note_id", ids);
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
                if (i > 0) {
                    btn.setY(-95 * (i + 1));
                }
                final int btnid;
                btnid = indx;
                btn.setLayoutParams(layoutParams);
                btn.setId(indx * 1000);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database.execSQL("delete from notes where _id=" + ids);
                        Button button = findViewById(btnid);
                        button.setVisibility(View.INVISIBLE);
                        Button butn = findViewById(btnid * 1000);
                        butn.setVisibility(View.INVISIBLE);
                        indx = indx - 1;
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
        intent.putExtra("note_id", "new");
        startActivity(intent);
    }

    public void searchNote(View view) {
        etTitle = (EditText) findViewById(R.id.editText4);
        String title = etTitle.getText().toString();
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NOTES, null, "title like '%" + title + "%'", null, null, null, null);
        if (cursor.moveToFirst()) {
            for (int i = 1; i < indx + 1; i++) {
                Button button = findViewById(i);
                String btntext = String.valueOf(button.getText());
                if (btntext != title && title.length() >0) {
                    button.setVisibility(View.INVISIBLE);
                    Button butn = findViewById(i * 1000);
                    butn.setVisibility(View.INVISIBLE);
                }else{
                    button.setVisibility(View.VISIBLE);
                    Button butn = findViewById(i * 1000);
                    butn.setVisibility(View.VISIBLE);
                }

            }
        } else {
            Log.d("SEARCH LOG", "False");
        }
    }
}

