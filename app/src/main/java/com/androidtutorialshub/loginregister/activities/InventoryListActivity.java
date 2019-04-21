package com.androidtutorialshub.loginregister.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.adapters.InventoryCustomAdapter;
import com.androidtutorialshub.loginregister.model.Equipment;
import com.androidtutorialshub.loginregister.sql.DatabaseHelper;
import com.androidtutorialshub.loginregister.sql.EquipmentSqlCommander;

import java.util.List;

public class InventoryListActivity extends AppCompatActivity {

    private ListView listView;
    private Button btnAddNew;
    private List<Equipment> inventoryList;
    private InventoryCustomAdapter customAdapter;
    private DatabaseHelper databaseHelper;
    private EquipmentSqlCommander sqlCommander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_get_all);

        listView = (ListView) findViewById(R.id.lv_equipments);
        databaseHelper = new DatabaseHelper(this);
        sqlCommander = new EquipmentSqlCommander(databaseHelper);
        inventoryList = sqlCommander.getAllEquipments();
        customAdapter = new InventoryCustomAdapter(this, inventoryList);
        listView.setAdapter(customAdapter);
        btnAddNew = (Button) findViewById(R.id.btnAddNew);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InventoryListActivity.this, InventoryUpdateDeleteActivity.class);
                intent.putExtra("equipment", inventoryList.get(position));
                startActivity(intent);
            }
        });

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventoryListActivity.this, InventoryMainActivity.class);
                startActivity(intent);
            }
        });
    }
}
