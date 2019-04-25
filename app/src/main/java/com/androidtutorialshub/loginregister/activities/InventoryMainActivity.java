package com.androidtutorialshub.loginregister.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.model.Equipment;
import com.androidtutorialshub.loginregister.sql.DatabaseHelper;
import com.androidtutorialshub.loginregister.sql.EquipmentSqlCommander;

public class InventoryMainActivity extends AppCompatActivity {

    private Button btnStore;
    private EditText etname, etroom, etDescription, etTask,
            etManufacturer, etModel, etPreventiveMaintenanceRequired,
            etWarranty, etYearOfManufacture, etQuantityAvailable;

    private DatabaseHelper databaseHelper;
    private EquipmentSqlCommander sqlCommander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_main);
        getSupportActionBar().hide();

        databaseHelper = new DatabaseHelper(this);
        sqlCommander = new EquipmentSqlCommander(databaseHelper);

        btnStore = (Button) findViewById(R.id.btnstore);
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

        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sqlCommander.addEquipment(new Equipment(etname.getText().toString(), etroom.getText().toString(),
                        etDescription.getText().toString(), etTask.getText().toString(), etManufacturer.getText().toString(),
                        etModel.getText().toString(), etPreventiveMaintenanceRequired.getText().toString(),
                        etWarranty.getText().toString(), etYearOfManufacture.getText().toString(),
                        etQuantityAvailable.getText().toString()));
                etname.setText("");
                etroom.setText("");
                etDescription.setText("");
                etTask.setText("");
                etManufacturer.setText("");
                etModel.setText("");
                etPreventiveMaintenanceRequired.setText("");
                etWarranty.setText("");
                etYearOfManufacture.setText("");
                etQuantityAvailable.setText("");
                Toast.makeText(InventoryMainActivity.this, "Stored Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InventoryMainActivity.this, InventoryListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
