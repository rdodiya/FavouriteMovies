package com.example.favouritemovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper  extends SQLiteOpenHelper {
    public  DatabaseHelper(Context context){
        super(context,"movies.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table movies(name TEXT primary key,actor TEXT,actress TEXT,director TEXT" +
                ",releaseDate TEXT,language TEXT,country TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists movies");
    }

    protected boolean insertData(String name,String actor,String actress,String director,String releaseDate,
                                 String language,String country){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("actor",actor);
        contentValues.put("actress",actress);
        contentValues.put("director",director);
        contentValues.put("releaseDate",releaseDate);
        contentValues.put("language",language);
        contentValues.put("country",country);

        long result = sqLiteDatabase.insert("movies",null,contentValues);
        if(result==1){
            return false;
        }else {
            return true;
        }
    }

    protected boolean updateData(String name,String actor,String actress,String director,String releaseDate,
                                 String language,String country){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("actor",actor);
        contentValues.put("actress",actress);
        contentValues.put("director",director);
        contentValues.put("releaseDate",releaseDate);
        contentValues.put("language",language);
        contentValues.put("country",country);
        Cursor cursor=sqLiteDatabase.rawQuery("Select * from movies where name=?",new String[] {name});
        if(cursor.getCount()>0){
            long result = sqLiteDatabase.update("movies",contentValues,"name=?",new String[]{name});
            if(result==1){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    protected boolean deleteData(String name){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        Cursor cursor=sqLiteDatabase.rawQuery("Select * from movies where name=?",new String[]{name});
        if(cursor.getCount()>0){
            long result = sqLiteDatabase.delete("movies","name=?",new String[]{name});
            if(result==1){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
    protected Cursor getData(String name){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
//        ContentValues contentValues=new ContentValues();
        Cursor cursor=sqLiteDatabase.rawQuery("Select * from movies where name=?",new String[]{name});
        return cursor;
    }
    protected Cursor getData(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
//        ContentValues contentValues=new ContentValues();
        Cursor cursor=sqLiteDatabase.rawQuery("Select * from movies",null);
        return cursor;
    }
}
