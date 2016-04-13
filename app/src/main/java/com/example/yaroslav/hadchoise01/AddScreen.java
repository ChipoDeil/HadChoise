package com.example.yaroslav.hadchoise01;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddScreen extends AppCompatActivity {
    Button addTest = null;
    EditText nameText = null;
    EditText item1Text = null;
    EditText item2Text = null;
    EditText item3Text = null;
    DBMain db = null;
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
                    String[] items = {item1Text.getText().toString(), item2Text.getText().toString(), item3Text.getText().toString()};
                    String name = nameText.getText().toString();
                    boolean a = db.insertData(name, items);
                    if(a)
                        Toast.makeText(AddScreen.this, "Тест добавлен!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(AddScreen.this, "Что-то пошло не так!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddScreen.this, "Все поля обязательны к заполнению!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
