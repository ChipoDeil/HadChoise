package com.example.yaroslav.hadchoise01;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainScreen extends AppCompatActivity {
    Button add = null;
    Button info = null;
    Button delete = null;
    Animation animAdd = null;
    Animation animDel = null;
    Animation animInfo = null;
    Intent intentAdd = null;
    Intent intentDel = null;
    Intent intentInfo = null;
    Intent intentSelect = null;
    Intent intentFinish = null;
    ListView namesList = null;
    TextView textList;
    DBMain db = null;
    TextView tv = null;
    int i = 0;
    String id;
    String status;
    ArrayAdapter<String> namesAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showNames();
        listeners();
    }

    private void listeners(){
        intentAdd = new Intent(this, AddScreen.class);
        intentFinish = new Intent(this, FinishScreen.class);
        intentDel = new Intent(this, DelScreen.class);
        add = (Button) findViewById(R.id.add);
        info = (Button) findViewById(R.id.info);
        delete = (Button) findViewById(R.id.delete);
        animAdd = AnimationUtils.loadAnimation(this, R.anim.scale);
        animDel = AnimationUtils.loadAnimation(this, R.anim.scale);
        animInfo = AnimationUtils.loadAnimation(this, R.anim.scale);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentAdd);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentDel);
            }
        });

    }
    String[] names;
    public void showNames(){
        db = new DBMain(this);
        Cursor res = db.getNames();
        namesList = (ListView)findViewById(R.id.namesList);
        names = new String[db.getCount()];
        i = 0;
        boolean hasMoreNames = res.moveToFirst();
        while (hasMoreNames) {
            names[i] = res.getString(res.getColumnIndex("NAME"));
            hasMoreNames = res.moveToNext();
            i++;
        }
        if (names.length == 0 && db.getCountItems("1") == 0) {
            db.insertData("Мой любимый фрукт", new String[]{"Апельсин", "Банан", "Яблоко", "Гранат", "Слива", "Киви", "Ананас"});
            db.insertData("Мой любимый жанр музыки", new String[]{"Рок", "Реп", "Поп", "Метал", "Классика", "Инди", "Диско", "Джаз"});
            names = new String[db.getCount()];
            i = 0;
            hasMoreNames = res.moveToFirst();
            while (hasMoreNames) {
                names[i] = res.getString(res.getColumnIndex("NAME"));
                hasMoreNames = res.moveToNext();
                i++;
            }
        }else if(names.length == 0 && db.getCountItems("1") != 0){
            textList = (TextView)findViewById(R.id.textList);
            textList.setText("У вас пока нет тестов!");
        }
        namesAdapter = new ArrayAdapter<String>(this, R.layout.my_list_item, names);
        namesList.setAdapter(namesAdapter);
        namesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = namesAdapter.getItem(position);
                //Toast.makeText(MainScreen.this, name, Toast.LENGTH_LONG).show();
                intentSelect = new Intent(MainScreen.this, SelectSreen.class);
                DBMain db = new DBMain(MainScreen.this);
                Cursor res  = db.getNames();
                boolean hasMoreNames = res.moveToFirst();
                String idTest = "";
                while(hasMoreNames){
                    if (name.equals(res.getString(res.getColumnIndex("NAME")))) {
                        idTest = res.getString(res.getColumnIndex("ID"));
                        status = res.getString(res.getColumnIndex("STATUS"));
                    }
                    hasMoreNames = res.moveToNext();
                }
                if(status.equals("DONE")){
                    intentFinish.putExtra("ID", idTest);
                    startActivity(intentFinish);
                }else {
                    intentSelect.putExtra("testName", name);
                    startActivity(intentSelect);
                }
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        showNames();
    }

    @Override
    protected void onPause() {
        super.onPause();
        showNames();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showNames();
    }
}
