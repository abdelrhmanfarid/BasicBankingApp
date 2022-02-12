package com.example.basicbankingapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.basicbankingapp.DB.TransactionContract.TransactionEntryData;

public class TransactionHelper extends SQLiteOpenHelper {
    /** Name of the database file */
    private static final String DATABASE_NAME = "transaction.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.*/
    private static final int DATABASE_VERSION = 1;

    public TransactionHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_TRANSACTION_TABLE =  "CREATE TABLE " + TransactionEntryData.TABLE_NAME + " ("
                + TransactionEntryData.COLUMN_FROM_NAME + " VARCHAR, "
                + TransactionEntryData.COLUMN_TO_NAME + " VARCHAR, "
                + TransactionEntryData.COLUMN_AMOUNT + " INTEGER, "
                + TransactionEntryData.COLUMN_STATUS + " INTEGER);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TransactionEntryData.TABLE_NAME);
            onCreate(db);
        }
    }

    public boolean insertTransferData (String fromName, String toName, String amount, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TransactionEntryData.COLUMN_FROM_NAME, fromName);
        contentValues.put(TransactionEntryData.COLUMN_TO_NAME, toName);
        contentValues.put(TransactionEntryData.COLUMN_AMOUNT, amount);
        contentValues.put(TransactionEntryData.COLUMN_STATUS, status);
        Long result = db.insert(TransactionEntryData.TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
}
