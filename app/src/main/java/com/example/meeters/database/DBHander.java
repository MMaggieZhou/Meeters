package com.example.meeters.database;

/**
 * Created by fox on 2014/11/3.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.meeters.model.domain.User;
import com.example.meeters.utils.JSONUtils;


public class DBHander extends SQLiteOpenHelper
{
    private static final String TAG = DBHander.class.getSimpleName();

    public DBHander(Context context, String name, CursorFactory factory, int version)
    {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_USERINFO_TABLE = "CREATE TABLE users (id INTEGER PRIMARY KEY,nickname TEXT,email TEXT,gender TEXT,phone TEXT)";
        db.execSQL(CREATE_USERINFO_TABLE);
        Log.i(TAG, "Successfully initialized the Sqlite database!");
    }

   /* public void onCreateUser(SQLiteDatabase db)
    {
        String CREATE_USERINFO_TABLE = "CREATE TABLE " + DB.TABLE.USER + "(" + DB.COLUMN._ID + " INTEGER PRIMARY KEY,"
                + DB.COLUMN.NICKNAME + " TEXT," + DB.COLUMN.EMAIL + " TEXT," + DB.COLUMN.GENDER + " TEXT,"
                + DB.COLUMN.PHONE + " TEXT)";
        db.execSQL(CREATE_USERINFO_TABLE);
        Log.d(TAG, CREATE_USERINFO_TABLE);
        Log.i(TAG, "Successfully created a User Table in Sqlite");

    }*/

    /*
     * (non-Javadoc)
     *
     * @see
     * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
     * .SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
    {
        // TODO Auto-generated method stub

    }

    public long addUser(User user)
    {
        Log.e(TAG, "I'm here!!");
        if (user == null)
        {
            Log.e(TAG, "Cannot add a Null value to User table!!");
            return -1;
        }
        ContentValues values = new ContentValues();
        values.put("nickname", user.getNickname());
        values.put("email", user.getEmail());
        values.put("gender", user.getGender());
        values.put("phone", user.getPhone());

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert("users", null, values);
        db.close();
        Log.i(TAG, "Successfully added a User in Sqlite User table: " + JSONUtils.toJson(user));
        return result;
    }

    public User findUser(String email)
    {
        String query = "Select * FROM user WHERE email =  \"" + email + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        User user = new User();

        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();
            // user.set_id(Integer.parseInt(cursor.getString(0)));
            user.setNickname(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setGender(cursor.getString(3));
            user.setPhone(cursor.getString(4));
            cursor.close();
        }
        else
        {
            user = null;
        }
        db.close();
        return user;
    }

    public int updateUser(User user)
    {
        int result = 0;

        String query = "Select * FROM users WHERE email =  \"" + user.getEmail()
                + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor cursor = db.rawQuery(query, null);
        ContentValues values = new ContentValues();
        values.put("nickname", user.getNickname());
        // values.put(DB.COLUMN.EMAIL, user.getEmail());
        values.put("gender", user.getGender());
        values.put("phone", user.getPhone());

        //
        // if (cursor.moveToFirst()) {
        // user.set_id(Integer.parseInt(cursor.getString(0)));
        // db.delete(DB.TABLE.USER, DB.COLUMN._ID + " = ?",
        // new String[] { String.valueOf(user.get_id()) });
        // cursor.close();
        // result = true;
        // }

        result = db.update("users", values, query, null);
        db.close();
        return result;
    }

    @Deprecated
    public boolean deleteUser(String email)
    {
        boolean result = false;

        String query = "Select * FROM users WHERE email =  \"" + email + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // User user = new User();

        if (cursor.moveToFirst())
        {
            // user.set_id(Integer.parseInt(cursor.getString(0)));
            // db.delete(DB.TABLE.USER, DB.COLUMN._ID + " = ?",
            // new String[] { String.valueOf(user.get_id()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public static void main(String[] args)
    {
        Log.i("db test", "OK");
    }

}
