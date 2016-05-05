package com.example.yaroslav.hadchoise01;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class FinishScreen extends AppCompatActivity {
    Items[] items;
    Intent intentSelect;
    TextView tv;
    String id;
    Button back;
    Button again;
    Intent intentBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent intent = getIntent();
        id = intent.getExtras().getString("ID");
        DBMain db = new DBMain(this);
        Integer[] score = new Integer[db.getCountItems(id)];
        items = new Items[db.getCountItems(id)];
        Cursor res = db.getItems(id);
        boolean hasMoreItems = res.moveToFirst();
        int i = 0;
        while(hasMoreItems){
            score[i] = Integer.parseInt(res.getString(res.getColumnIndex("SCORE")));
            items[i] = new Items(res.getString(res.getColumnIndex("NAME")), Integer.parseInt(res.getString(res.getColumnIndex("SCORE"))));
            hasMoreItems = res.moveToNext();
            i++;
        }
        intentSelect = new Intent(this, SelectSreen.class);
        intentBack = new Intent(this, MainScreen.class);
        Arrays.sort(score);
        tv = (TextView)findViewById(R.id.results);
        int g = 1;
        for(int j = score.length-1; j != -1; j--){
            if(j != score.length-1)
            tv.setText(tv.getText() + "" + g + ". " + getName(score[j]) + "  Очков: " + score[j] + "\n");
            else
            tv.setText("" + g + ". " + getName(score[j]) + "  Очков: " + score[j] + "\n");
            g++;
        }
        again = (Button)findViewById(R.id.again);
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBMain db = new DBMain(FinishScreen.this);
                int i = 1;
                db.again(id);
                db.updateStatus(id, 1);
                Cursor res = db.getNames();
                String name = "";
                boolean hasMoreItems = res.moveToFirst();
                while(hasMoreItems){
                    if(id.equals(res.getString(res.getColumnIndex("ID")))){
                        name = res.getString(res.getColumnIndex("NAME"));
                    }
                    hasMoreItems = res.moveToNext();
                }
                intentSelect.putExtra("testName", name);
                startActivity(intentSelect);
            }
        });
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentBack);
            }
        });

    }

    public String getName(int score){
        String name = "";
        for (int i =0; i<items.length; i++){
            if(score == items[i].getScore()){
                name = items[i].getName();
            }
        }
        return name;
    }

}
