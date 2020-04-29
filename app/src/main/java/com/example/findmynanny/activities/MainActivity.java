package com.example.findmynanny.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.findmynanny.database.MyDataBase;
import com.example.findmynanny.R;
import com.example.findmynanny.fragments.ListFragment;

public class MainActivity extends AppCompatActivity {

    private MyDataBase myDataset;
    private Button button;
    private TextView hello;
    private Button login;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hello = findViewById(R.id.hellotxt);
        login = findViewById(R.id.login);
        logout = findViewById(R.id.logout);

        Intent i = getIntent();
        if (i.getExtras() != null)
        {
            String fname = i.getExtras().getString("FIRST_NAME");
            String lname = i.getExtras().getString("LAST_NAME");

            if (fname != null && lname != null) {
                hello.setText("Hello " + fname + " " + lname + "!");
                login.setVisibility(View.INVISIBLE);
                logout.setVisibility(View.VISIBLE);
            }
            else {
                hello.setText("Hello!");
                login.setVisibility(View.VISIBLE);
                logout.setVisibility(View.INVISIBLE);
            }

        }
        else
        {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
            String first_name = sharedPreferences.getString(getString(R.string.saved_first_name), null);
            String last_name = sharedPreferences.getString(getString(R.string.saved_last_name), null);

            if(first_name != null && last_name != null) {
                hello.setText("Hello " + first_name + " " + last_name + "!");
                login.setVisibility(View.INVISIBLE);
                logout.setVisibility(View.VISIBLE);
            }
            else {
                hello.setText("Hello!");
                login.setVisibility(View.VISIBLE);
                logout.setVisibility(View.INVISIBLE);
            }
        }

        ListFragment listFragment = new ListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, listFragment, "list_fragment");
        fragmentTransaction.commit();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                sharedPreferences.edit().remove(getString(R.string.saved_first_name)).remove(getString(R.string.saved_last_name)).apply();

                hello.setText("Hello!");
                login.setVisibility(View.VISIBLE);
                logout.setVisibility(View.INVISIBLE);

                }
        });
    }
}
