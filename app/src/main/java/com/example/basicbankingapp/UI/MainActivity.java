package com.example.basicbankingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.basicbankingapp.R;

public class MainActivity extends AppCompatActivity {


    Button viewUsers,viewTransactions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewUsers = findViewById(R.id.view_all_users);
        viewTransactions = findViewById(R.id.view_all_transactions);

        viewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AllUsers.class);
                startActivity(intent);
            }
        });

        viewTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AllTransactions.class);
                startActivity(intent);
            }
        });

    }
}