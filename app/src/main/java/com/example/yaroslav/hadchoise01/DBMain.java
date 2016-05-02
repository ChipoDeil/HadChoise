package com.example.yaroslav.hadchoise01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yaroslav on 29.03.2016.
 */
public class DBMain extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Main.db";
    public static final String MAIN_TABLE = "MAIN";
    public static final String COL_MAIN_1 = "ID";
    public static final String COL_MAIN_2 = "NAME";
    public static final String COL_MAIN_3 = "STATUS";
    public static final String SEC_TABLE = "SEC";
    public static final String COL_SEC_1 = "ID";
    public static final String COL_SEC_2 = "NAME";
    public static final String COL_SEC_3 = "ASSOC_ID";
    public static final String COL_SEC_4 = "SCORE";

    public DBMain(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table " + MAIN_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, STATUS TEXT)");
        db.execSQL("CREATE table " + SEC_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, ASSOC_ID INTEGER, SCORE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MAIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SEC_TABLE);
        onCreate(db);
    }

    public boolean insertData(String name, String[] items){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_MAIN_2, name);
        contentValues.put(COL_MAIN_3, "WAITING");
        long a  = db.insert(MAIN_TABLE, null, contentValues);
        if (a == -1)
            return false;
        contentValues.clear();
        for (int i = 0; i < items.length; i++){
            contentValues.put(COL_SEC_2, items[i]);
            contentValues.put(COL_SEC_3, a);
            contentValues.put(COL_SEC_4, 0);
            long b  = db.insert(SEC_TABLE, null, contentValues);
            if(b == -1)
                return false;
            contentValues.clear();
        }
        return true;
    }
    public Cursor getNames(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + MAIN_TABLE, null);
        return res;
    }
    public int getCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + MAIN_TABLE, null);
        boolean hasMoreNames = res.moveToFirst();
        int i = 0;
        while(hasMoreNames){
            i++;
            hasMoreNames = res.moveToNext();
        }
        return i;
    }

    public Integer deleteData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        //int i = (Integer)(db.execSQL("select id from "+MAIN_TABLE+" where name = " + name));
        return db.delete(MAIN_TABLE, "ID = ?", new String[] { name });
    }
    Cursor getItems(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + SEC_TABLE + " WHERE ASSOC_ID = " + id, null);
        return res;
    }
    public int getCountItems(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + SEC_TABLE + " WHERE ASSOC_ID = " + id, null);
        boolean hasMoreNames = res.moveToFirst();
        int i = 0;
        while (hasMoreNames) {
            i++;
            hasMoreNames = res.moveToNext();
        }
        return i;
    }
    public boolean updateScore(String name, String id, int score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SEC_3, id);
        contentValues.put(COL_SEC_2, name);
        contentValues.put(COL_SEC_4, score);
        db.update(SEC_TABLE, contentValues, "ASSOC_ID =  " + id + " AND NAME = " + name, null);
        return true;
    }

}
