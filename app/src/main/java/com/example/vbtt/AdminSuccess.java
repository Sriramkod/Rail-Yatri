package com.example.vbtt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_success);
    }

    public void AddNew(View view) {
        Intent intent = new Intent(AdminSuccess.this,AddData.class);
        startActivity(intent);
    }

    public void Update(View view) {
        Intent intent = new Intent(AdminSuccess.this,Retrieve.class);
        startActivity(intent);
    }
}