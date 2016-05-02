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
    ListView namesList = null;
    DBMain db = null;
    TextView tv = null;
    int i = 0;
    ArrayAdapter<String> namesAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
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

        showNames();
        listeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void listeners(){
        intentAdd = new Intent(this, AddScreen.class);
        intentDel = new Intent(this, DelScreen.class);
        intentInfo = new Intent(this, InfoScreen.class);
        add = (Button) findViewById(R.id.add);
        info = (Button) findViewById(R.id.info);
        delete = (Button) findViewById(R.id.delete);
        animAdd = AnimationUtils.loadAnimation(this, R.anim.scale);
        animDel = AnimationUtils.loadAnimation(this, R.anim.scale);
        animInfo = AnimationUtils.loadAnimation(this, R.anim.scale);
        add.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {add.startAnimation(animAdd);}});
        animAdd.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(intentAdd);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete.startAnimation(animDel);
            }
        });
        animDel.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(intentDel);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info.startAnimation(animInfo);
            }
        });
        animInfo.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showNames();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

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
        while(hasMoreNames){
            names[i] = res.getString(res.getColumnIndex("NAME"));
            hasMoreNames = res.moveToNext();
            i++;
        }
        namesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        namesList.setAdapter(namesAdapter);
        namesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = namesAdapter.getItem(position);
                //Toast.makeText(MainScreen.this, name, Toast.LENGTH_LONG).show();
                intentSelect = new Intent(MainScreen.this, SelectSreen.class);
                intentSelect.putExtra("testName", name);
                startActivity(intentSelect);
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
