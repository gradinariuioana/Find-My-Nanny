package com.example.findmynanny.fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.findmynanny.NannyReceiver;
import com.example.findmynanny.R;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class AlarmFragment extends Fragment {
    private Button time_btn;
    private Button date_btn;
    private Button alarm_btn;
    private TimePickerDialog timePicker;
    private DatePickerDialog datePicker;
    private TextView time_view;
    private TextView date_view;

    private static final int NOTIFICATION_ID = 0;
    private FragmentManager fragmentManager;
    private String nanny_fName;
    private String nanny_lName;
    private String nanny_phoneN;

    AlarmFragment(String fName, String lName, String phoneN){
        this.nanny_fName = fName;
        this.nanny_lName = lName;
        this.nanny_phoneN = phoneN;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        time_btn = view.findViewById(R.id.btn_time);
        date_btn = view.findViewById(R.id.btn_date);
        alarm_btn = view.findViewById(R.id.btn_alarm);
        time_view = view.findViewById(R.id.txtv_time);
        date_view = view.findViewById(R.id.txtv_date);
        fragmentManager = getFragmentManager();

        time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int sHour, int sMinute) {
                                String selectedHour;
                                if (sHour < 10)
                                    selectedHour = "0" + sHour;
                                else
                                    selectedHour = "" + sHour;
                                String selectedMinute;
                                if (sMinute < 10)
                                    selectedMinute = "0" + sMinute;
                                else
                                    selectedMinute = "" + sMinute;
                                time_view.setText("Selected time: "+ selectedHour + ":" + selectedMinute);
                            }
                        }, hour, minutes, true);
                timePicker.show();
            }
        });

        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                String selectedDay, selectedMonth;
                                monthOfYear = monthOfYear + 1;
                                if (monthOfYear < 10)
                                    selectedMonth = "0" + monthOfYear;
                                else
                                    selectedMonth = "" + monthOfYear;
                                if (dayOfMonth < 10)
                                    selectedDay = "0" + dayOfMonth;
                                else
                                    selectedDay = "" + dayOfMonth;
                                date_view.setText("Selected date: " + selectedDay + "/" + selectedMonth + "/" + year);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });

        alarm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();

                calendar.setTimeInMillis(System.currentTimeMillis());

                int hour = 0, minute = 0, dayOfMonth = 0, monthOfYear = 0, year = 0;

                try {
                    hour = Integer.parseInt(time_view.getText().toString().substring(15, 17));
                    minute = Integer.parseInt(time_view.getText().toString().substring(18));
                    dayOfMonth = Integer.parseInt(date_view.getText().toString().substring(15, 17));
                    monthOfYear = Integer.parseInt(date_view.getText().toString().substring(18, 20)) - 1;
                    year = Integer.parseInt(date_view.getText().toString().substring(21));
                }   catch (Exception e)
                {
                    Toast.makeText(getContext(), "Please pick a time and a date", Toast.LENGTH_SHORT).show();
                    return;
                }

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.SECOND, 0);

                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(getContext(), NannyReceiver.class);
                intent.putExtra("fname", nanny_fName);
                intent.putExtra("lname", nanny_lName);
                intent.putExtra("phone", nanny_phoneN);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExact(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

                Toast.makeText(getContext(), "Alarm created", Toast.LENGTH_LONG).show();

                fragmentManager.popBackStackImmediate();
            }
        });

        return view;
    }


}
