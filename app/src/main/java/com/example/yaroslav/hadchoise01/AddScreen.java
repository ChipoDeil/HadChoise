package com.example.yaroslav.hadchoise01;

import android.database.Cursor;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class AddScreen extends AppCompatActivity {
    Button addTest = null;
    EditText nameText = null;
    EditText item1Text = null;
    EditText item2Text = null;
    EditText item3Text = null;
    DBMain db = null;
    boolean a;
    int i;
    boolean isTheSame;
    String[] names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = new EditText(AddScreen.this);
                LinearLayout answ = (LinearLayout)findViewById(R.id.vars);
                answ.addView(et);
            }
        });
        addTest();
    }
    private void addTest(){
        db = new DBMain(this);
        addTest = (Button) findViewById(R.id.addTest);
        nameText = (EditText) findViewById(R.id.nameText);
        item1Text = (EditText) findViewById(R.id.item1Text);
        item2Text = (EditText) findViewById(R.id.item2Text);
        item3Text = (EditText) findViewById(R.id.item3Text);
        addTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameText.getText().length() != 0 &&item1Text.getText().length() != 0&&item2Text.getText().length() != 0&&item3Text.getText().length() != 0){
                    db = new DBMain(AddScreen.this);
                    Cursor res = db.getNames();
                    isTheSame = false;
                    names = new String[db.getCount()];
                    i = 0;
                    boolean hasMoreNames = res.moveToFirst();
                    while(hasMoreNames){
                        names[i] = res.getString(res.getColumnIndex("NAME"));
                        hasMoreNames = res.moveToNext();
                        i++;
                    }
                    String name = nameText.getText().toString();
                    for(int i = 0; i < names.length; i++){
                        if(names[i].equals(name)) {
                            Toast.makeText(AddScreen.this, "Тест с таким названием уже существует!", Toast.LENGTH_SHORT).show();
                            isTheSame = true;
                            break;
                        }
                    }
                    if(!isTheSame) {
                        boolean isEmpty = false;
                        LinearLayout answ = (LinearLayout) findViewById(R.id.vars);
                        String[] getItems = new String[answ.getChildCount()];
                        for (int i = 0; i < answ.getChildCount(); i++) {
                            EditText et = (EditText) answ.getChildAt(i);
                            if (et.getEditableText().toString().length() != 0) {
                                getItems[i] = et.getEditableText().toString();
                            } else {
                                Toast.makeText(AddScreen.this, "У вас имеется пустое поле!", Toast.LENGTH_LONG).show();
                                isEmpty = true;
                                break;
                            }
                        }
                        if (!isEmpty) {
                            a = db.insertData(name, getItems);
                        }
                        if (a)
                            Toast.makeText(AddScreen.this, "Тест добавлен!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(AddScreen.this, "Что-то пошло не так!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AddScreen.this, "Все поля обязательны к заполнению!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
