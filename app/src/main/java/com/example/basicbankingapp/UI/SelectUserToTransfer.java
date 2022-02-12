package com.example.basicbankingapp.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.example.basicbankingapp.Adapter.SelectUserAdapter;
import com.example.basicbankingapp.DB.TransactionContract;
import com.example.basicbankingapp.DB.TransactionHelper;
import com.example.basicbankingapp.DB.UserContract;
import com.example.basicbankingapp.DB.UserDBHelper;
import com.example.basicbankingapp.Data.User;
import com.example.basicbankingapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SelectUserToTransfer extends AppCompatActivity implements SelectUserAdapter.OnUserListener  {

    // RecyclerView
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<User> userArrayList;

    // Database
    private UserDBHelper dbHelper;

    String date=null, time=null;
    int fromUserAccountNo, toUserAccountNo, toUserAccountBalance;
    String fromUserAccountName, fromUserAccountBalance, transferAmount, toUserAccountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_to_transfer);


        // Get time instance
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a");
        String date_and_time = simpleDateFormat.format(calendar.getTime());

        // Get Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fromUserAccountName = bundle.getString("FROM_USER_NAME");
            fromUserAccountNo = bundle.getInt("FROM_USER_ACCOUNT_NO");
            fromUserAccountBalance = bundle.getString("FROM_USER_ACCOUNT_BALANCE");
            transferAmount = bundle.getString("TRANSFER_AMOUNT");
        }

        // Create ArrayList of Users
        userArrayList = new ArrayList<User>();

        // Create Table in the Database
        dbHelper = new UserDBHelper(this);

        // Show list of items
        recyclerView = findViewById(R.id.send_to_user_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new SelectUserAdapter(userArrayList, (SelectUserAdapter.OnUserListener) this);
        recyclerView.setAdapter(myAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }


    @Override
    public void onUserClick(int position) {
        // Insert data into transactions table
        toUserAccountNo = userArrayList.get(position).getAccountNumber();
        toUserAccountName = userArrayList.get(position).getName();
        toUserAccountBalance = userArrayList.get(position).getBalance();

        calculateAmount();

        new TransactionHelper(this).insertTransferData(fromUserAccountName, toUserAccountName, transferAmount, 1);
        Toast.makeText(this, "Transaction Successful!!", Toast.LENGTH_LONG).show();

        startActivity(new Intent(SelectUserToTransfer.this, MainActivity.class));
        finish();
    }
    private void calculateAmount() {
        Integer currentAmount = Integer.parseInt(fromUserAccountBalance);
        Integer transferAmountInt = Integer.parseInt(transferAmount);
        Integer remainingAmount = currentAmount - transferAmountInt;
        Integer increasedAmount = transferAmountInt + toUserAccountBalance;

        // Update amount in the dataBase
        new UserDBHelper(this).updateAmount(fromUserAccountNo, remainingAmount);
        new UserDBHelper(this).updateAmount(toUserAccountNo, increasedAmount);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder_exitButton = new AlertDialog.Builder(SelectUserToTransfer.this);
        builder_exitButton.setTitle("Do you want to cancel the transaction?").setCancelable(false)
                .setPositiveButton ("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        // Transactions Cancelled
                        TransactionHelper dbHelper = new TransactionHelper(getApplicationContext());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();

                        values.put(TransactionContract.TransactionEntryData.COLUMN_FROM_NAME, fromUserAccountName);
                        values.put(TransactionContract.TransactionEntryData.COLUMN_TO_NAME, toUserAccountName);
                        values.put(TransactionContract.TransactionEntryData.COLUMN_STATUS, 0);
                        values.put(TransactionContract.TransactionEntryData.COLUMN_AMOUNT, transferAmount);

                        db.insert(TransactionContract.TransactionEntryData.TABLE_NAME, null, values);

                        Toast.makeText(SelectUserToTransfer.this, "Transaction Cancelled!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SelectUserToTransfer.this, AllUsers.class));
                        finish();
                    }
                }).setNegativeButton("No", null);
        AlertDialog alertExit = builder_exitButton.create();
        alertExit.show();
    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                UserContract.UserEntryData.COLUMN_USER_NAME,
                UserContract.UserEntryData.COLUMN_USER_ACCOUNT_BALANCE,
                UserContract.UserEntryData.COLUMN_USER_ACCOUNT_NUMBER,
                UserContract.UserEntryData.COLUMN_USER_PHONE_NO,
                UserContract.UserEntryData.COLUMN_USER_EMAIL,
        };

        Cursor cursor = db.query(
                UserContract.UserEntryData.TABLE_NAME,   // The table to query
                projection,                          // The columns to return
                null,                        // The columns for the WHERE clause
                null,                     // The values for the WHERE clause
                null,                        // Don't group the rows
                null,                         // Don't filter by row groups
                null);                       // The sort order

        try {
            // Figure out the index of each column
            int phoneNoColumnIndex = cursor.getColumnIndex(UserContract.UserEntryData.COLUMN_USER_PHONE_NO);
            int emailColumnIndex = cursor.getColumnIndex(UserContract.UserEntryData.COLUMN_USER_EMAIL);
            int accountNumberColumnIndex = cursor.getColumnIndex(UserContract.UserEntryData.COLUMN_USER_ACCOUNT_NUMBER);
            int nameColumnIndex = cursor.getColumnIndex(UserContract.UserEntryData.COLUMN_USER_NAME);
            int accountBalanceColumnIndex = cursor.getColumnIndex(UserContract.UserEntryData.COLUMN_USER_ACCOUNT_BALANCE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                String currentName = cursor.getString(nameColumnIndex);
                int accountNumber = cursor.getInt(accountNumberColumnIndex);
                String email = cursor.getString(emailColumnIndex);
                String phoneNumber = cursor.getString(phoneNoColumnIndex);
                int accountBalance = cursor.getInt(accountBalanceColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                userArrayList.add(new User(currentName, accountNumber, phoneNumber, accountBalance, email));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

}