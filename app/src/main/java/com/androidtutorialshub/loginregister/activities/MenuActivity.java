package com.androidtutorialshub.loginregister.activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.activities.util.PreferencesManager;

/**
 * Created by Madalina Bara on 6/27/2019.
 */
public class MenuActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu1_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        else if (id == R.id.action_logout) {
            // stop service and clear preferences
            PreferencesManager preferencesManager = new PreferencesManager(this, null);
            preferencesManager.logout();

            // redirect to login activity and clear other activities
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
