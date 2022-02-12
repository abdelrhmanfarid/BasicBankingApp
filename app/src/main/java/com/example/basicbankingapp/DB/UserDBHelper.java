package com.example.basicbankingapp.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class UserDBHelper extends SQLiteOpenHelper {

    String TABLE_NAME = UserContract.UserEntryData.TABLE_NAME;

    /** Name of the database file */
    private static final String DATABASE_NAME = "User.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.*/
    private static final int DATABASE_VERSION = 1;

    public UserDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_USER_TABLE =  "CREATE TABLE " + UserContract.UserEntryData.TABLE_NAME + " ("
                + UserContract.UserEntryData.COLUMN_USER_ACCOUNT_NUMBER + " INTEGER, "
                + UserContract.UserEntryData.COLUMN_USER_NAME + " VARCHAR, "
                + UserContract.UserEntryData.COLUMN_USER_EMAIL + " VARCHAR, "
                + UserContract.UserEntryData.COLUMN_USER_PHONE_NO + " VARCHAR, "
                + UserContract.UserEntryData.COLUMN_USER_ACCOUNT_BALANCE + " INTEGER NOT NULL);";

        // Execute the SQL statement
        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);

        // Insert Into Table
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(5896,'abdelrhman ahmed', 'abdelrhman@gmail.com','012547896', 15000)");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(6598,'kahled nabil', 'kahled@gmail.com','011447888', 5000)");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(2365,'ahmed osama', 'ahemd@gmail.com','012589963', 1000)");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(2548,'moahmed khaled', 'moahmed@gmail.com','01225889', 8000)");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(5698,'ahemd ali', 'ahemdali@gmail.com','01147789', 7500)");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(7896,'abdo goda', 'abdi@gmail.com','012365978', 6500)");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(4567,'marawan mohamed', 'marawan@gmail.com','01236597180', 4500)");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(4587,'osama nabil', 'osama@gmail.com','01225998870', 2500)");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(8523,'mahmoud osama', 'mahmoud@gmail.com','0102569876', 10500)");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(2586,'mohamed ahmed', 'moahemd@gmail.com','01114778896', 9900)");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(1598,'moataz gamal', 'moataz@gmail.com','012015448963', 9800)");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(1236,'ali alaa', 'alialaa@gmail.com','010125478963', 11000)");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(7532,'hussien ali', 'hussien@gmail.com','01201254896', 5800)");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(8523,'ali ahmed', 'ali@gmail.com','012229793', 3500)");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(2569,'mohamed mahmoud', 'mohamedmahmoud@gmail.com','012369958745',  1010)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor readAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + UserContract.UserEntryData.TABLE_NAME, null);
        return cursor;
    }

    public Cursor readParticularData (int accountNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + UserContract.UserEntryData.TABLE_NAME + " where " +
                UserContract.UserEntryData.COLUMN_USER_ACCOUNT_NUMBER + " = " + accountNo, null);
        return cursor;
    }

    public void updateAmount(int accountNo, int amount) {
        Log.d ("TAG", "update Amount");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update " + UserContract.UserEntryData.TABLE_NAME + " set " + UserContract.UserEntryData.COLUMN_USER_ACCOUNT_BALANCE + " = " + amount + " where " +
                UserContract.UserEntryData.COLUMN_USER_ACCOUNT_NUMBER + " = " + accountNo);
    }
}
