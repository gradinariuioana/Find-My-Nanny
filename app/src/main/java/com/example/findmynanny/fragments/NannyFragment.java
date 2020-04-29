package com.example.findmynanny.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.toolbox.NetworkImageView;
import com.example.findmynanny.MySingleton;
import com.example.findmynanny.R;
import com.example.findmynanny.models.Nanny;

public class NannyFragment extends Fragment {

    private NetworkImageView img;
    private Nanny nanny;
    private TextView firstName;
    private TextView lastName;
    private TextView address;
    private TextView phoneN;
    private TextView age;
    private Button alarmbtn;

    NannyFragment(Nanny nanny)
    {
        this.nanny = nanny;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_nanny, container, false);

        img = myview.findViewById(R.id.nanny_info_photo);
        img.setImageUrl(nanny.getPhotoUrl(), MySingleton.getInstance(getContext()).getImageLoader());

        firstName = myview.findViewById(R.id.nanny_first_name_tv);
        lastName = myview.findViewById(R.id.nanny_last_name_tv);
        age = myview.findViewById(R.id.nanny_age_tv);
        phoneN = myview.findViewById(R.id.nanny_phone_number_tv);
        address = myview.findViewById(R.id.nanny_address_tv);
        alarmbtn = myview.findViewById(R.id.alarm_btn);

        firstName.setText("First name: " + nanny.getFirstName());
        lastName.setText("Last name: " + nanny.getLastName());
        age.setText("Age: " + Integer.toString(nanny.getAge()));
        address.setText("Address: " + nanny.getAddress());
        phoneN.setText("Phone number: " + nanny.getPhoneNumber());

        alarmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmFragment alarmFragment = new AlarmFragment(nanny.getFirstName(), nanny.getLastName(), nanny.getPhoneNumber());
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (fragmentManager.findFragmentByTag("nanny_fragment") != null)
                    fragmentTransaction.replace(R.id.main_container, alarmFragment, "alarm_fragment").addToBackStack("nanny_fragment");
                fragmentTransaction.commit();
            }
        });

        return myview;
    }
}
