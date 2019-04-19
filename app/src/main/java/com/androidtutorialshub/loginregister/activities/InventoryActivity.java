package com.androidtutorialshub.loginregister.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.model.Equipment;
import com.androidtutorialshub.loginregister.model.User;
import com.androidtutorialshub.loginregister.sql.DatabaseHelper;
import com.androidtutorialshub.loginregister.sql.EquipmentSqlCommander;

import java.util.List;

public class InventoryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        DatabaseHelper db = new DatabaseHelper(this);
        EquipmentSqlCommander equipmentDb = new EquipmentSqlCommander(db);

        // Inserting Shop/Rows
        Log.d("Insert: ", "Inserting ..");
        equipmentDb.addEquipment(new Equipment("Equipmentt","SpitalulMilitar"));
        //equipmentDb.addEquipment(new Equipment("X-ray Machine", "X-ray Lab"));
        //equipmentDb.addEquipment(new Equipment("Blood Chemistry Analyser", "Pathology Lab"));
        //equipmentDb.addEquipment(new Equipment("ECG/EKG", "ECG Room"));
        //equipmentDb.addEquipment(new Equipment("Largyngoscope", "OT"));
        //equipmentDb.addEquipment(new Equipment("Magnifying Glasses", "Surgery Cabinet"));
        //equipmentDb.addEquipment(new Equipment("Bio-hazard bags", "Store Room #1"));
        //equipmentDb.addEquipment(new Equipment("Antibacterial Wipes", "Store Room #2"));
        //equipmentDb.addEquipment(new Equipment("Hand Sanitizer", "Store Room #3"));

        // Reading all shops
        Log.d("Reading: ", "Reading all equipments..");
        List<Equipment> inv = equipmentDb.getAllEquipments();

        for (Equipment i : inv) {
            String log = "Id" + i.getId() + "" + " ,Name: " + i.getName() + " ,Room " + i.getRoom();
            // Writing shops to log
            Log.d("Equipment::", log);
        }
    }
}







