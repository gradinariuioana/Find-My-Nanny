package com.example.findmynanny.fragments;

import android.content.Intent;
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
import androidx.room.Room;

import com.example.findmynanny.R;
import com.example.findmynanny.activities.MainActivity;
import com.example.findmynanny.database.MyDataBase;
import com.example.findmynanny.database.UserDao;
import com.example.findmynanny.models.User;

public class SignUpFragment extends Fragment {


    private EditText edtFirstName;
    private EditText edtLastName;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btCancel;
    private Button btRegister;

    private UserDao userDao;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_signup, container, false);

        edtFirstName = myview.findViewById(R.id.first_name_input_su);
        edtLastName = myview.findViewById(R.id.last_name_input_su);
        edtEmail = myview.findViewById(R.id.email_input_su);
        edtPassword = myview.findViewById(R.id.password_input_su);
        btCancel = myview.findViewById(R.id.btCancel);
        btRegister = myview.findViewById(R.id.btRegister);

        userDao = Room.databaseBuilder(getContext(), MyDataBase.class, "users-database.db")
                .allowMainThreadQueries()
                .build()
                .getUserDao();

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty())
                {
                    User user = new User(edtFirstName.getText().toString(), edtLastName.getText().toString(),
                            edtEmail.getText().toString(), edtPassword.getText().toString());

                    userDao.insert(user);

                    startActivity(new Intent(getContext(), MainActivity.class));
                } else
                    {
                        Toast.makeText(getContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        return myview;
    }

    private boolean isEmpty() {
        if (TextUtils.isEmpty(edtEmail.getText().toString()) ||
                TextUtils.isEmpty(edtPassword.getText().toString()) ||
                TextUtils.isEmpty(edtFirstName.getText().toString()) ||
                TextUtils.isEmpty(edtLastName.getText().toString())
        )
        {
            return true;
        } else
            {
                return false;
            }
    }
}
