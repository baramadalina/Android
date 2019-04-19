package com.androidtutorialshub.loginregister.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.adapters.InventoryCustomAdapter;
import com.androidtutorialshub.loginregister.model.Equipment;
import com.androidtutorialshub.loginregister.sql.DatabaseHelper;
import com.androidtutorialshub.loginregister.sql.EquipmentSqlCommander;

import java.util.List;

public class InventoryListActivity extends AppCompatActivity {

    private ListView listView;
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InventoryListActivity.this, InventoryUpdateDeleteActivity.class);
                intent.putExtra("equipment", inventoryList.get(position));
                startActivity(intent);
            }
        });
    }
}

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        DatabaseHelper db = new DatabaseHelper(this);
        EquipmentSqlCommander equipmentDb = new EquipmentSqlCommander(db);

        // Inserting Shop/Rows
        Log.d("Insert: ", "Inserting ..");
        equipmentDb.addEquipment(new Equipment("Equipmentt","SpitalulMilitar"));

        // Reading all shops
        Log.d("Reading: ", "Reading all equipments..");
        List<Equipment> inv = equipmentDb.getAllEquipments();

        for (Equipment i : inv) {
            String log = "Id" + i.getId() + "" + " ,Name: " + i.getName() + " ,Room " + i.getRoom();
            // Writing shops to log
            Log.d("Equipment::", log);
        }
    }
}*/







