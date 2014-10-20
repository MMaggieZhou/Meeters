package com.example.meeters.activities;

/**
 * Created by fox on 10/18/2014.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
public class DatabaseHelper {
    private static final String TAG = "DBDemo_DBHelper";// 调试标签

    private static final String DATABASE_NAME = "Meeters_local1.db";// 数据库名
    SQLiteDatabase db;
    Context context;//应用环境上下文   Activity 是其子类

    DatabaseHelper(Context _context) {
        context = _context;
        //开启数据库

        db = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
        CreateTable();
        Log.v(TAG, "db path=" + db.getPath());
    }

    public void CreateTable() {
        try {
            db.execSQL("CREATE TABLE t_user (" +
                    "_ID INTEGER PRIMARY KEY autoincrement,"
                    + "NAME TEXT,"
                    + "email text,"
                    + "password text"
                    + ");");
            Log.v(TAG, "Create Table t_user ok");
        } catch (Exception e) {
            Log.v(TAG, "Create Table t_user err,table exists.");
        }
    }

    public boolean insert(String username, String email, String password){
        String sql="";
        try{
            sql="insert into t_user values(null,'"+username+"', '"+email+"', '"+password+"')";
            db.execSQL(sql);
            Log.v(TAG,"insert Table t_user ok");
            return true;

        }catch(Exception e){
            Log.v(TAG,"insert Table t_user err ,sql: "+sql);
            return false;
        }
    }

    public Cursor loadAll(){

        Cursor cur=db.query("t_user", new String[]{"_ID","NAME","email","password"}, null,null, null, null, null);

        return cur;
    }
    public void close(){
        db.close();
    }
}
