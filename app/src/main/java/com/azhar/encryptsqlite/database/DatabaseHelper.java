package com.azhar.encryptsqlite.database;

/*
 * Created by Azhar Rivaldi on 05-12-2023
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;

import com.azhar.encryptsqlite.model.ModelMain;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "EncryptDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_DATA = "biodata";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TELP = "phone";
    private static DatabaseHelper instance;

    //password database
    public static final String PASS_CODE = "Azhar2023";

    private static final String CREATE_TABLE_BIODATA = "CREATE TABLE "
            + TABLE_DATA + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT, " + KEY_TELP + " TEXT );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BIODATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_DATA + "'");
        onCreate(db);
    }

    public long addUserDetail(String name, String tlp) {
        SQLiteDatabase db = this.getWritableDatabase(PASS_CODE);

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_TELP, tlp);
        long insert = db.insert(TABLE_DATA, null, values);

        return insert;
    }

    @SuppressLint("Range")
    public ArrayList<ModelMain> getAllUsers() {
        ArrayList<ModelMain> modelMainArrayList = new ArrayList<ModelMain>();

        String selectQuery = "SELECT * FROM " + TABLE_DATA;
        SQLiteDatabase db = this.getReadableDatabase(PASS_CODE);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                ModelMain modelMain = new ModelMain();
                modelMain.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                modelMain.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                modelMain.setTlp(c.getString(c.getColumnIndex(KEY_TELP)));
                modelMainArrayList.add(modelMain);
            } while (c.moveToNext());
        }
        return modelMainArrayList;
    }

    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase(PASS_CODE);
        db.delete(TABLE_DATA, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public int updateUser(int id, String name, String tlp) {
        SQLiteDatabase db = this.getWritableDatabase(PASS_CODE);

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_TELP, tlp);

        return db.update(TABLE_DATA, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

}
