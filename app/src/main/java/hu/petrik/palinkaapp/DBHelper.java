package hu.petrik.palinkaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "italok.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "palinka";
    private static final String COL_ID = "id";
    private static final String COL_FOZO = "fozo";
    private static final String COL_GYUM = "gyumolcs";
    private static final String COL_ALKT = "alkoholtart";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COL_FOZO + " TEXT NOT NULL, " +
                COL_GYUM + " TEXT NOT NULL, " +
                COL_ALKT + " INTEGER NOT NULL, " +
                "UNIQUE(" + COL_FOZO + ", " + COL_GYUM + ") " +
                ");";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public Cursor listazas() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor eredmeny = db.query(TABLE_NAME, new String[]{COL_ID, COL_FOZO, COL_GYUM, COL_ALKT},
                null, null, null, null, null);
        return eredmeny;
    }

    public boolean rogzites(String fozo, String gyum, int alkTart) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(COL_FOZO, fozo);
        value.put(COL_GYUM, gyum);
        value.put(COL_ALKT, alkTart);
        return db.insert(TABLE_NAME, null, value) != -1;
    }

    public Cursor kereses(String fozo, String gyum) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT " + COL_ALKT.trim() + " FROM " + TABLE_NAME + " WHERE " + COL_FOZO + " LIKE ? AND " + COL_GYUM + " LIKE ?";
        Cursor eredmeny = db.rawQuery(sql, new String[]{fozo, gyum});
        return eredmeny;
    }

    public int torles(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int eredmeny = db.delete(TABLE_NAME, COL_ID + "=?", new String[]{id});
        return eredmeny;
    }

    public int modositas(String id, String fozo, String gyum, String alkTart) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(COL_FOZO, fozo);
        value.put(COL_GYUM, gyum);
        value.put(COL_ALKT, alkTart);
        return db.update(TABLE_NAME, value, COL_ID + "=?", new String[]{id});
    }
}
