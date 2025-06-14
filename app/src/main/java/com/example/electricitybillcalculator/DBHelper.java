package com.example.electricitybillcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "electricity_bills.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "bills";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "month TEXT, " +
                "units INTEGER, " +
                "rebate INTEGER, " +
                "total REAL, " +
                "final REAL)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String month, int units, int rebate, double total, double finalCost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("month", month);
        cv.put("units", units);
        cv.put("rebate", rebate);
        cv.put("total", total);
        cv.put("final", finalCost);
        long result = db.insert(TABLE_NAME, null, cv);
        return result != -1;
    }

    public ArrayList<Bill> getAllBills() {
        ArrayList<Bill> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Bill bill = new Bill(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getDouble(4),
                        cursor.getDouble(5)
                );
                list.add(bill);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public Bill getBillById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            Bill bill = new Bill(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getDouble(4),
                    cursor.getDouble(5)
            );
            cursor.close();
            return bill;
        }
        cursor.close();
        return null;
    }
}