package com.example.findmynanny.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.example.findmynanny.R;
import com.example.findmynanny.activities.MainActivity;
import com.example.findmynanny.database.MyDataBase;
import com.example.findmynanny.database.UserDao;
import com.example.findmynanny.models.User;

public class LoginFragment extends Fragment {

    private Button btSignIn;
    private Button btSignUp;
    private EditText edtEmail;
    private EditText edtPassword;
    private MyDataBase database;

    private UserDao userDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_login, container, false);

        btSignIn = myview.findViewById(R.id.btSignIn);
        btSignUp = myview.findViewById(R.id.btSignUp);
        edtEmail = myview.findViewById(R.id.email_input_li);
        edtPassword = myview.findViewById(R.id.password_input_li);

        database = Room.databaseBuilder(getContext(), MyDataBase.class, "users-database.db")
                .allowMainThreadQueries()
                .build();

        userDao = database.getUserDao();

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment signUpFragment = new SignUpFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                if (fragmentManager.findFragmentByTag("login_fragment") != null)
                    fragmentTransaction.replace(R.id.auth_container, signUpFragment, "signup_fragment").addToBackStack("login_fragment");
                fragmentTransaction.commit();
            }
        });

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emptyValidation())
                {
                   User user = userDao.getUser(edtEmail.getText().toString(), edtPassword.getText().toString());
                    if(user!=null)
                    {
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(getString(R.string.saved_first_name), user.getFirstName());
                        editor.putString(getString(R.string.saved_last_name), user.getLastName());
                        editor.apply();

                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("FIRST_NAME", user.getFirstName());
                        intent.putExtra("LAST_NAME", user.getLastName());
                        startActivity(intent);
                    } else
                        {
                            Toast.makeText(getContext(), "Unregistered user, or incorrect", Toast.LENGTH_LONG).show();
                        }
                } else
                    {
                        Toast.makeText(getContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        return myview;
    }

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(edtEmail.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString())) {
            return true;
        } else
            {
                return false;
            }
    }
}
