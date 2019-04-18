package com.androidtutorialshub.loginregister.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.androidtutorialshub.loginregister.R;

public class SecondActivity extends AppCompatActivity {

    private Button btn_inventory;
    private Button btn_tasks;
    private Button btn_reports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btn_inventory=(Button)findViewById(R.id.ButtonInventory);
        btn_tasks=(Button)findViewById(R.id.ButtonTasks);
        btn_reports=(Button)findViewById(R.id.ButtonReports);

        btn_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inventoryIntent=new Intent(SecondActivity.this,InventoryActivity.class);
                SecondActivity.this.startActivity(inventoryIntent);
            }
        });

        btn_tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tasksIntent=new Intent(SecondActivity.this,TasksActivity.class);
                SecondActivity.this.startActivity(tasksIntent);
            }
        });

        btn_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reportsIntent=new Intent(SecondActivity.this,ReportsActivity.class);
                SecondActivity.this.startActivity(reportsIntent);

            }
        });


    }
}
