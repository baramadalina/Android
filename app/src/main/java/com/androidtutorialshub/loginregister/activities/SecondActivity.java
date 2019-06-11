package com.androidtutorialshub.loginregister.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.androidtutorialshub.loginregister.R;

public class SecondActivity extends AppCompatActivity {

    private Button btn_inventory;
    private Button btn_calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btn_inventory = (Button) findViewById(R.id.ButtonInventory);
        btn_calendar = (Button) findViewById(R.id.ButtonCalendar);

        btn_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inventoryIntent = new Intent(SecondActivity.this, InventoryListActivity.class);
                SecondActivity.this.startActivity(inventoryIntent);
            }
        });

        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

    }
}
