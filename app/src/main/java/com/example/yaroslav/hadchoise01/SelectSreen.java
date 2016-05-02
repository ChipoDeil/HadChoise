package com.example.yaroslav.hadchoise01;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SelectSreen extends AppCompatActivity {
    int i = 0;
    Button startTest;
    ArrayAdapter<String> itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Intent intent = getIntent();
        String name  = intent.getExtras().getString("testName");
        TextView tv = (TextView)findViewById(R.id.nameTest);
        tv.setText(name);
        String id = "";
        DBMain db = new DBMain(this);
        Cursor res  = db.getNames();
        //getting id
        boolean hasMoreNames = res.moveToFirst();
        while(hasMoreNames){
            if (name.equals(res.getString(res.getColumnIndex("NAME"))))
                id = res.getString(res.getColumnIndex("ID"));
            hasMoreNames = res.moveToNext();
        }
        String[] itemsName = new String[db.getCountItems(id)];
        String[] itemsScore = new String[db.getCountItems(id)];
        Cursor resItems = db.getItems(id);
        boolean hasMoreItems = resItems.moveToFirst();
        i = 0;
        while(hasMoreItems){
            itemsName[i] = resItems.getString(resItems.getColumnIndex("NAME"));
            itemsScore[i] = resItems.getString(resItems.getColumnIndex("SCORE"));
            hasMoreItems = resItems.moveToNext();
            i++;
        }
        ListView itemsList = (ListView)findViewById(R.id.itemsList);
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemsName);
        itemsList.setAdapter(itemsAdapter);
        startTest = (Button)findViewById(R.id.startTest);
        final Intent processItent = new Intent(this, ProcessScreen.class);
        processItent.putExtra("id", id);
        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(processItent);
            }
        });

    }

}
