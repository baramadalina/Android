package com.androidtutorialshub.loginregister.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.activities.util.Event;
import com.androidtutorialshub.loginregister.model.Equipment;
import com.androidtutorialshub.loginregister.sql.DatabaseHelper;
import com.androidtutorialshub.loginregister.sql.EquipmentSqlCommander;


public class ReservationEventDetailsActivity extends MenuActivity {

    private Event mEvent;
    private TextView tvTitle, tvDate, tvTime,
            tvDuration, tvLocation, tvBody,
            tvReservedByUser, tvReservedEquipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_reservation_event_details);

        tvTitle = (TextView) findViewById(R.id.tvEventdetailsTitle);
        tvDate = (TextView) findViewById(R.id.tvEventdetailsDate);
        tvTime = (TextView) findViewById(R.id.tvEventdetailsTime);
        tvDuration = (TextView) findViewById(R.id.tvEventdetailsDuration);
        tvLocation = (TextView) findViewById(R.id.tvEventdetailsLocation);
        tvBody = (TextView) findViewById(R.id.tvEventdetailsBody);
        tvReservedByUser = (TextView) findViewById(R.id.tvEventdetailsReservedBy);
        tvReservedEquipment = (TextView) findViewById(R.id.tvEventdetailsEquipmentName);

        Intent intent = getIntent();
        mEvent = intent.getParcelableExtra("event");
        this.setEventDetails();
    }

    private void setEventDetails() {
        tvTitle.setText(this.mEvent.getTitle());
        tvDate.setText(this.mEvent.getDateString());
        tvTime.setText(this.mEvent.getTimeString());
        tvDuration.setText(this.mEvent.getDuration() + " Minutes");
        tvLocation.setText(this.mEvent.getLocation());
        tvBody.setText(this.mEvent.getDetails());
        tvReservedByUser.setText(this.mEvent.getUserEmail());
        int equipmentId = this.mEvent.getEquipmentId();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        EquipmentSqlCommander sqlCommander = new EquipmentSqlCommander(databaseHelper);
        Equipment equipment = sqlCommander.getInventory(equipmentId);
        if (equipment != null) {
            tvReservedEquipment.setText(equipment.getName());
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_event_details, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public Event getEvent() {
        return mEvent;
    }
}
