package com.example.findmynanny.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.example.findmynanny.R;
import com.example.findmynanny.database.MyDataBase;
import com.example.findmynanny.database.UserDao;
import com.example.findmynanny.fragments.ListFragment;
import com.example.findmynanny.fragments.LoginFragment;
import com.example.findmynanny.fragments.SignUpFragment;
import com.example.findmynanny.models.User;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.auth_container, loginFragment, "login_fragment");
        fragmentTransaction.commit();

    }

}
