package com.ujang.medical.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db_travel";
    public static final String TABLE_USER = "tb_user";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_NAME = "name";
    public static final String TABLE_BOOK = "tb_book";
    public static final String COL_ID_BOOK = "id_book";
    public static final String COL_RMHSK = "rmhsk";
    public static final String COL_KELAS = "kelas";
    public static final String COL_TANGGAL = "tanggal";
    public static final String COL_ORANG = "orang";
    public static final String COL_HARI = "hari";
    public static final String TABLE_HARGA = "tb_harga";
    public static final String COL_HARGA_ORANG = "harga_orang";
    public static final String COL_HARGA_HARI = "harga_hari";
    public static final String COL_HARGA_TOTAL = "harga_total";

    public static final String TABLE_BOOKI = "tb_booki";
    public static final String COL_ID_BOOKI = "id_booki";
    public static final String COL_DKTR = "dktr";
    public static final String COL_JAM = "jam";
    public static final String COL_TANGGALI = "tanggali";


    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("create table " + TABLE_USER + " (" + COL_USERNAME + " TEXT PRIMARY KEY, " + COL_PASSWORD +
                " TEXT, " + COL_NAME + " TEXT)");
        db.execSQL("create table " + TABLE_BOOK + " (" + COL_ID_BOOK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RMHSK + " TEXT, " + COL_KELAS + " TEXT" + ", " + COL_TANGGAL + " TEXT, " + COL_ORANG + " TEXT, "
                + COL_HARI + " TEXT)");
        db.execSQL("create table " + TABLE_HARGA + " (" + COL_USERNAME + " TEXT, " + COL_ID_BOOK + " INTEGER, " +
                COL_HARGA_ORANG + " TEXT, " + COL_HARGA_HARI + " TEXT, " + COL_HARGA_TOTAL +
                " TEXT, FOREIGN KEY(" + COL_USERNAME + ") REFERENCES " + TABLE_USER
                + ", FOREIGN KEY(" + COL_ID_BOOK + ") REFERENCES " + TABLE_BOOK + ")");
        db.execSQL("create table " + TABLE_BOOKI + " (" + COL_ID_BOOKI + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DKTR + " TEXT, " + COL_JAM + " TEXT" + ", " + COL_TANGGALI + " TEXT)");
                db.execSQL("insert into " + TABLE_USER + " values ('azhar@gmail.com','azhar','Azhar Rivaldi');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public boolean Register(String username, String password, String name) throws SQLException {

        @SuppressLint("Recycle") Cursor mCursor = db.rawQuery("INSERT INTO " + TABLE_USER + "(" + COL_USERNAME + ", " + COL_PASSWORD + ", " + COL_NAME + ") VALUES (?,?,?)", new String[]{username, password, name});
        if (mCursor != null) {
            return mCursor.getCount() > 0;
        }
        return false;
    }

    public boolean Login(String username, String password) throws SQLException {
        @SuppressLint("Recycle") Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?", new String[]{username, password});
        if (mCursor != null) {
            return mCursor.getCount() > 0;
        }
        return false;
    }

}