package com.androidtutorialshub.loginregister.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.model.Equipment;
import com.androidtutorialshub.loginregister.sql.DatabaseHelper;
import com.androidtutorialshub.loginregister.sql.EquipmentSqlCommander;

import java.util.ArrayList;
import java.util.List;

public class InventoryCommentsActivity extends AppCompatActivity {

    private Spinner spinnerEquipmentsList;
    private DatabaseHelper databaseHelper;
    private EquipmentSqlCommander equipmentSqlCommander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_comments_home);

        spinnerEquipmentsList = findViewById(R.id.spinner_Equipments);
        databaseHelper = new DatabaseHelper(this);
        equipmentSqlCommander = new EquipmentSqlCommander(databaseHelper);
        final List<Equipment> inventoryList = equipmentSqlCommander.getAllEquipments();
        List<String> equipmentNamesList = getAllEquipmentsDetails(inventoryList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_all_items, equipmentNamesList);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_selected_item);
        spinnerEquipmentsList.setAdapter(adapter);
    }

    private List<String> getAllEquipmentsDetails(List<Equipment> inventoryList) {
        List<String> equipmentNamesList = new ArrayList<>(inventoryList.size());
        for (Equipment equipment : inventoryList) {
            equipmentNamesList.add(equipment.getName());
        }
        return equipmentNamesList;
    }

    public int getSelectedEquipmentId() {
        return (int) spinnerEquipmentsList.getSelectedItemId() + 1;
    }
}
