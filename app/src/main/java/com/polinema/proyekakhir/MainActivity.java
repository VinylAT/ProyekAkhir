package com.polinema.proyekakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button bAdmin;
    //Button to Intent Admin Main & Admin User is declared

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Introduce each button to XML equivalent
        bAdmin = findViewById(R.id.Admin);
    }
    //Set button handler for each button to intent
    //Handles going to admin

    public void HandleAdmin(View view) {
        Intent toAdmin = new Intent(this, userMainActivity.class);
        startActivity(toAdmin);
    }
}