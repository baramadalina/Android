package com.androidtutorialshub.loginregister.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.model.Inventory;
import com.androidtutorialshub.loginregister.sql.DBHandler;

import java.util.List;

public class InventoryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);


        DBHandler db = new DBHandler(this);


        // Inserting Shop/Rows
        Log.d("Insert: ", "Inserting ..");
        db.addEquipemnt(new Inventory("Anaesthesia Machine"," OT"));
        db.addEquipemnt(new Inventory("X-ray Machine","X-ray Lab"));
        db.addEquipemnt(new Inventory("Blood Chemistry Analyser", "Pathology Lab"));
        db.addEquipemnt(new Inventory("ECG/EKG", "ECG Room"));
        db.addEquipemnt(new Inventory("Largyngoscope","OT"));
        db.addEquipemnt(new Inventory("Magnifying Glasses","Surgery Cabinet"));
        db.addEquipemnt(new Inventory("Bio-hazard bags","Store Room #1"));
        db.addEquipemnt(new Inventory("Antibacterial Wipes","Store Room #2"));
        db.addEquipemnt(new Inventory("Hand Sanitizer","Store Room #3"));

        // Reading all shops
        Log.d("Reading: ", "Reading all equipments..");
        List<Inventory> inv = db.getAllShops();


        for(Inventory i: inv){
            String log ="Id"+ i.getId() + "" +
                    " ,Name: "+i.getName() + " ,Room " +
                    i.getRoom();

            // Writing shops to log
            Log.d("Inventory::",log);
        }

    }
}







