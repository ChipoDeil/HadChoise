package com.example.yaroslav.hadchoise01;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;

public class FinishScreen extends AppCompatActivity {
    Items[] items;
    Intent intentSelect;
    ListView lvName;
    ListView lvScore;
    ArrayAdapter<String> adapterName;
    ArrayAdapter<String> adapterScore;
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
        getSupportActionBar().setTitle("HardChoice");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Intent backIntent = new Intent(this, MainScreen.class);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(backIntent);
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
        lvName = (ListView)findViewById(R.id.resultsName);
        lvScore = (ListView)findViewById(R.id.resultsScore);
        int g = 1;
        String[] namesForLv = new String[score.length];
        String[] scoreForLv = new String[score.length];
        for(int j = score.length-1; j != -1; j--){
            namesForLv[g-1] = g + ". " + getName(score[j]);
            scoreForLv[g-1] = "Очков: "+score[j]+"";
            g++;
        }
        adapterName = new ArrayAdapter<String>(this, R.layout.list_item_for_results_right, namesForLv);
        lvName.setAdapter(adapterName);
        adapterScore = new ArrayAdapter<String>(this, R.layout.list_item_for_results_right, scoreForLv);
        lvScore.setAdapter(adapterScore);
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
