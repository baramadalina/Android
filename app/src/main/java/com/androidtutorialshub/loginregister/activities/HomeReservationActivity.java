package com.androidtutorialshub.loginregister.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.activities.util.PreferencesManager;
import com.androidtutorialshub.loginregister.activities.util.RetrieveNewEventsService;


public class HomeReservationActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_home);

        ImageButton ibNewEvent = (ImageButton) findViewById(R.id.ibNewEvent);
        if (ibNewEvent != null) {
            ibNewEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent currentIntent = getIntent();
                    String authenticatedEmail = currentIntent.getStringExtra("EMAIL");
                    Intent intent = new Intent(HomeReservationActivity.this, AddReservationEventActivity.class);
                    intent.putExtra("EMAIL", authenticatedEmail);
                    startActivity(intent);
                }
            });
        }

        // start service that fetches new events and notifies user
        this.StartBackgroundService();

    }

    private void StartBackgroundService() {
        Log.d("HomeActivity: ", "service is running: " + RetrieveNewEventsService.isRunning);
        if (!RetrieveNewEventsService.isRunning) {
            Intent serviceIntent = new Intent(this, RetrieveNewEventsService.class);
            startService(serviceIntent);
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferencesManager preferencesManager = new PreferencesManager(this, null);
        if (!preferencesManager.getRememberLogin()) {
            // stop service and clear preferences
            preferencesManager.logout();
        }
    }
}
