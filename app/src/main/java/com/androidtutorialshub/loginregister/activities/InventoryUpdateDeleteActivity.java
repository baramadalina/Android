package com.androidtutorialshub.loginregister.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.model.Equipment;
import com.androidtutorialshub.loginregister.sql.DatabaseHelper;
import com.androidtutorialshub.loginregister.sql.EquipmentSqlCommander;

public class InventoryUpdateDeleteActivity extends AppCompatActivity {

    private Equipment equipment;
    private EditText etname, etroom, etDescription, etTask,
            etManufacturer, etModel, etPreventiveMaintenanceRequired,
            etWarranty, etYearOfManufacture, etQuantityAvailable;
    private Button btnupdate, btndelete;
    private DatabaseHelper databaseHelper;
    private EquipmentSqlCommander sqlCommander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_update_delete);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        equipment = intent.getParcelableExtra("equipment");
        Log.d("Selected equipment : {}", equipment.toString());

        databaseHelper = new DatabaseHelper(this);
        sqlCommander = new EquipmentSqlCommander(databaseHelper);

        etname = (EditText) findViewById(R.id.etname);
        etroom = (EditText) findViewById(R.id.etroom);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etTask = (EditText) findViewById(R.id.etTask);
        etManufacturer = (EditText) findViewById(R.id.etManufacturer);
        etModel = (EditText) findViewById(R.id.etModel);
        etPreventiveMaintenanceRequired = (EditText) findViewById(R.id.etPreventiveMaintenanceRequired);
        etWarranty = (EditText) findViewById(R.id.etWarranty);
        etYearOfManufacture = (EditText) findViewById(R.id.etYearOfManufacture);
        etQuantityAvailable = (EditText) findViewById(R.id.etQuantityAvailable);

        btndelete = (Button) findViewById(R.id.btndelete);
        btnupdate = (Button) findViewById(R.id.btnupdate);

        etname.setText(equipment.getName());
        etroom.setText(equipment.getRoom());
        etDescription.setText(equipment.getDescription());
        etTask.setText(equipment.getTask());
        etManufacturer.setText(equipment.getManufacturer());
        etModel.setText(equipment.getModel());
        etPreventiveMaintenanceRequired.setText(equipment.getPreventiveMaintenanceRequired());
        etWarranty.setText(equipment.getWarranty());
        etYearOfManufacture.setText(equipment.getYearOfManufacture());
        etQuantityAvailable.setText(equipment.getQuantityAvailable());

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlCommander.updateEquipment(new Equipment(equipment.getId(), etname.getText().toString(), etroom.getText().toString(),
                        etDescription.getText().toString(), etTask.getText().toString(), etManufacturer.getText().toString(),
                        etModel.getText().toString(), etPreventiveMaintenanceRequired.getText().toString(),
                        etWarranty.getText().toString(), etYearOfManufacture.getText().toString(),
                        etQuantityAvailable.getText().toString()));

                Toast.makeText(InventoryUpdateDeleteActivity.this, "Update an existing equipment!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InventoryUpdateDeleteActivity.this, InventoryListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlCommander.deleteEquipment(equipment);
                Toast.makeText(InventoryUpdateDeleteActivity.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InventoryUpdateDeleteActivity.this, InventoryListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}