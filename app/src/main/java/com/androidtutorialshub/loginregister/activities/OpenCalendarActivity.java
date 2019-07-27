package com.androidtutorialshub.loginregister.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.androidtutorialshub.loginregister.R;
import com.sch.calendar.CalendarView;
import com.sch.calendar.adapter.SampleVagueAdapter;
import com.sch.calendar.annotation.DayOfMonth;
import com.sch.calendar.annotation.Month;
import com.sch.calendar.entity.Date;
import com.sch.calendar.listener.OnDateClickedListener;
import com.sch.calendar.listener.OnMonthChangedListener;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpenCalendarActivity extends AppCompatActivity {

    @BindView(R.id.calendar_view)
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_calendar);
        ButterKnife.bind(this);

        initCalendarView();
    }

    @OnClick(R.id.btn_goto_checkin)
    public void gotoCheckin() {
        startActivity(new Intent(this, ReportsActivity.class));
    }

    @OnClick(R.id.btn_look_action)
    public void lookAction() {
        startActivity(new Intent(this, UsersListActivity.class));
    }

    // Initialize view for calendar
    private void initCalendarView() {
        calendarView.setCanDrag(true); // can't change month by slide
        calendarView.setScaleEnable(false); // can't auto scale calendar when month changed.
        calendarView.setShowOverflowDate(true); // hide overflow date of showing month.
        calendarView.setCanFling(true);
        calendarView.setTitleFormat("yyyy-MM", Locale.ENGLISH);
        // Set a listener，callback when month changed.
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(Date date) {
            }
        });
        // Set a listener，callback when one of date be clicked.
        calendarView.setOnDateClickedListener(new OnDateClickedListener() {
            @Override
            public void onDateClicked(View itemView, int year, @Month int month, @DayOfMonth int dayOfMonth) {
                Toast.makeText(OpenCalendarActivity.this, String.format("%sYear%sMonth%sDay", year, month, dayOfMonth), Toast.LENGTH_SHORT).show();
            }
        });
        // using SampleVagueAdapter
        calendarView.setVagueAdapter(new SampleVagueAdapter());
    }

}
