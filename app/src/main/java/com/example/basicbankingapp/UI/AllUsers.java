package com.example.basicbankingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import com.example.basicbankingapp.Adapter.UserListAdapter;
import com.example.basicbankingapp.DB.UserContract;
import com.example.basicbankingapp.DB.UserDBHelper;
import com.example.basicbankingapp.Data.User;
import com.example.basicbankingapp.R;

import java.util.ArrayList;

public class AllUsers extends AppCompatActivity {


    // RecyclerView
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<User> userArrayList;

    // Database
    private UserDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        // Create ArrayList of Users
        userArrayList = new ArrayList<User>();

        // Create Table in the Database
        dbHelper = new UserDBHelper(AllUsers.this);

        // Read Data from DataBase
        displayDatabaseInfo();

        // Show list of items
        recyclerView = findViewById(R.id.all_users_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new UserListAdapter(this, userArrayList);
        recyclerView.setAdapter(myAdapter);
    }
    private void displayDatabaseInfo() {
        userArrayList.clear();

        Cursor cursor = new UserDBHelper(this).readAllData();

        int phoneNoColumnIndex = cursor.getColumnIndex(UserContract.UserEntryData.COLUMN_USER_PHONE_NO);
        int emailColumnIndex = cursor.getColumnIndex(UserContract.UserEntryData.COLUMN_USER_EMAIL);
        int accountNumberColumnIndex = cursor.getColumnIndex(UserContract.UserEntryData.COLUMN_USER_ACCOUNT_NUMBER);
        int nameColumnIndex = cursor.getColumnIndex(UserContract.UserEntryData.COLUMN_USER_NAME);
        int accountBalanceColumnIndex = cursor.getColumnIndex(UserContract.UserEntryData.COLUMN_USER_ACCOUNT_BALANCE);

        while (cursor.moveToNext()) {
            String currentName = cursor.getString(nameColumnIndex);
            int accountNumber = cursor.getInt(accountNumberColumnIndex);
            String email = cursor.getString(emailColumnIndex);
            String phoneNumber = cursor.getString(phoneNoColumnIndex);
            int accountBalance = cursor.getInt(accountBalanceColumnIndex);

            // Display the values from each column of the current row in the cursor in the TextView
            userArrayList.add(new User(currentName, accountNumber, phoneNumber, accountBalance, email));
        }
    }
}