package com.androidtutorialshub.loginregister.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.androidtutorialshub.loginregister.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
                String date = year + "/" + month + "/" + dayOfMonth;
                Log.d(TAG, "Date value: " + date);
                Intent intent = new Intent(CalendarActivity.this, SecondActivity.class);
                // once date is displayed go to ScheduleActivity to choose available interval and not SecondActivity
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }
}*/
public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {
    Calendar calendar;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 9);
        calendar.set(Calendar.YEAR, 2012);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.YEAR, 1);

        calendarView = (CalendarView) findViewById(R.id.calendarView);

        Button btnRange = (Button) findViewById(R.id.btnRange);
        btnRange.setOnClickListener(this);

        Button btnWithoutAnim = (Button) findViewById(R.id.btnWithoutAnim);
        btnWithoutAnim.setOnClickListener(this);

        Button btnWithAnim = (Button) findViewById(R.id.btnWithAnim);
        btnWithAnim.setOnClickListener(this);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                String msg = "Selected date Day: " + i2 + " Month : " + (i1 + 1) + " Year " + i;
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CalendarActivity.this, OpenCalendarActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnWithAnim:
                calendarView.setDate(calendar.getTimeInMillis(), true, true);
                break;

            case R.id.btnWithoutAnim:
                calendar.set(Calendar.DAY_OF_MONTH, 12);
                calendar.set(Calendar.YEAR, 2016);
                calendar.add(Calendar.MONTH, Calendar.MARCH);
                calendarView.setDate(calendar.getTimeInMillis(), false, false);
                break;

            case R.id.btnRange:

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
                long endOfMonth = calendar.getTimeInMillis();
                calendar = Calendar.getInstance();
                calendar.set(Calendar.DATE, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                long startOfMonth = calendar.getTimeInMillis();
                calendarView.setMaxDate(endOfMonth);
                calendarView.setMinDate(startOfMonth);


                String minDateString = new SimpleDateFormat("MM/dd/yyyy").format(new Date(calendarView.getMinDate()));
                String maxDateString = new SimpleDateFormat("MM/dd/yyyy").format(new Date(calendarView.getMaxDate()));

                Toast.makeText(getApplicationContext(), "MMDDYYYY Min date - " + minDateString + " Max Date is " + maxDateString, Toast.LENGTH_LONG).show();

                break;
        }
    }
}


