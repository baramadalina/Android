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

public class InventoryUpdateDeleteActivity extends AppCompatActivity {

    private Equipment equipment;
    private EditText etname, etroom;
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

        databaseHelper = new DatabaseHelper(this);
        sqlCommander = new EquipmentSqlCommander(databaseHelper);

        etname = (EditText) findViewById(R.id.etname);
        etroom = (EditText) findViewById(R.id.etroom);
        btndelete = (Button) findViewById(R.id.btndelete);
        btnupdate = (Button) findViewById(R.id.btnupdate);

        etname.setText(equipment.getName());
        etroom.setText(equipment.getRoom());

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlCommander.updateEquipment(new Equipment(equipment.getId(), etname.getText().toString(), etroom.getText().toString()));
                Toast.makeText(InventoryUpdateDeleteActivity.this, "Add a new equipment!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InventoryUpdateDeleteActivity.this, InventoryMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlCommander.deleteEquipment(equipment);
                Toast.makeText(InventoryUpdateDeleteActivity.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InventoryUpdateDeleteActivity.this, InventoryMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}