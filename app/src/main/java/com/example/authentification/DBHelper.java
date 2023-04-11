package com.example.authentification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper  extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "register.db", null, 1);
    }

    @Override
    public void onCreate( SQLiteDatabase db) {
db.execSQL(" create Table users(email Text primary key, name Text, password Text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("drop Table if exists users");
    }


    public Boolean insertdata (String email, String name, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("password", password);

        long result = db.insert( "users", null, contentValues);

        if (result==-1) {
            return false;
        }
        else {
            return true;
        }

}
    public Boolean checkemail (String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where email = ?", new String[] {email});

        if(cursor.getCount()>0) {
            return true;
        }
        else {
            return false;
        }
    }
    public String getuser (String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where email = ?", new String[] {email});
cursor.moveToFirst();

        String password = cursor.getString(2);
        String mail = cursor.getString(0);
        String infos = "Your email is "+mail+"\n"+"Your password is " +password;
        return  infos;

    }
    public Boolean checkusernamepassword(String email, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {

            return true;
        } else {

            return false;
        }

    }












    }