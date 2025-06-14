package com.example.electricitybillcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Bill> billList;
    ArrayList<String> displayList;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.customToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("History");

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            listView = findViewById(R.id.listView);
            dbHelper = new DBHelper(this);

            billList = dbHelper.getAllBills();

            if (billList == null) {
                Toast.makeText(this, "No bills found.", Toast.LENGTH_SHORT).show();
                return;
            }

            displayList = new ArrayList<>();
            for (Bill b : billList) {
                displayList.add(b.getMonth() + " - RM " + String.format("%.2f", b.getFinalCost()));
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    displayList
            );

            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view, position, id) -> {
                Bill selected = billList.get(position);
                Intent intent = new Intent(HistoryActivity.this, DetailActivity.class);
                intent.putExtra("billId", selected.getId()); // <- make sure this exists
                startActivity(intent);
            });
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // go back
        return true;
    }
}