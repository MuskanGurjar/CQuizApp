package com.example.cquizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
public class DBhelper extends SQLiteOpenHelper {
    static String dbName = "Javaques.sqlite";
  //  static String dbName = "Quiz.sqlite";

    String dbPath = "";

    SQLiteDatabase mainDb;

    public DBhelper(Context context) {
        super(context, dbName, null, 1);
        dbPath = "/data/data/"+context.getPackageName()+"/databases";
    }

    public static synchronized DBhelper getDb(Context context){
        return new DBhelper(context);
    }

    public boolean checkDb(){
        SQLiteDatabase sqLiteDatabase;
        try {
            sqLiteDatabase = SQLiteDatabase.openDatabase(dbPath + "/" + dbName, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e){
            sqLiteDatabase = null;
        }

        return sqLiteDatabase != null;
    }

    public void createDB(Context context){
        this.getReadableDatabase();
        this.close();
        try {
            InputStream fin = context.getAssets().open(dbName);

            String outputFilePath = dbPath+"/"+dbName;

            FileOutputStream fos = new FileOutputStream(outputFilePath);

            byte[] bytes = new byte[1024];
            int length;

            while((length=fin.read(bytes))>0){
                fos.write(bytes, 0, length);
            }

            fin.close();
            fos.flush();
            fos.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Suitcase> getQues(){
        ArrayList<Suitcase> arrQues = new ArrayList<>();

        Cursor cursor = mainDb.rawQuery("select * from 'Question'", null);


        while(cursor.moveToNext()){
            Suitcase quesSuitcase = new Suitcase();
            quesSuitcase.Ques = cursor.getString(1);
            quesSuitcase.OptA = cursor.getString(2);
            quesSuitcase.OptB = cursor.getString(3);
            quesSuitcase.OptC = cursor.getString(4);
            quesSuitcase.OptD = cursor.getString(5);
            quesSuitcase.Ans = cursor.getString(6);

            arrQues.add(quesSuitcase);
        }

        return arrQues;

    }

    public void openDb(){
        mainDb = SQLiteDatabase.openDatabase(dbPath+"/"+dbName, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void insertQues(Suitcase suitcase){


        ContentValues cv = new ContentValues();
        cv.put("Ques", suitcase.Ques);
        cv.put("OptA", suitcase.OptA);
        cv.put("OptB", suitcase.OptB);
        cv.put("OptC", suitcase.OptC);
        cv.put("OptD", suitcase.OptD);
        cv.put("Ans", suitcase.Ans);

        mainDb.insert("Question", null, cv);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
