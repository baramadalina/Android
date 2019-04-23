package com.androidtutorialshub.loginregister.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.adapters.InventoryCustomAdapter;
import com.androidtutorialshub.loginregister.model.Equipment;
import com.androidtutorialshub.loginregister.sql.DatabaseHelper;
import com.androidtutorialshub.loginregister.sql.EquipmentSqlCommander;

import java.util.List;

public class InventoryListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListView listView;
    private Button btnAddNew;
    private List<Equipment> inventoryList;
    private InventoryCustomAdapter customAdapter;
    private DatabaseHelper databaseHelper;
    private EquipmentSqlCommander sqlCommander;
    private SearchView searchView;
    private MenuItem searchMenuItem;

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.menu_toolbarsearch);
        searchView = (SearchView) searchMenuItem.getActionView();
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search by name");
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        customAdapter.getFilter().filter(newText);
        return true;
    }
}
