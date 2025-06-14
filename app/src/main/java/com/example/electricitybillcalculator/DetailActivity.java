package com.example.electricitybillcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView txtMonth, txtUnits, txtRebate, txtTotal, txtFinal;
    DBHelper dbHelper;
    int billId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.customToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Details");

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            txtMonth = findViewById(R.id.txtMonth);
            txtUnits = findViewById(R.id.txtUnits);
            txtRebate = findViewById(R.id.txtRebate);
            txtTotal = findViewById(R.id.txtTotal);
            txtFinal = findViewById(R.id.txtFinal);
            dbHelper = new DBHelper(this);

            billId = getIntent().getIntExtra("billId", -1);
            if (billId != -1) {
                Bill bill = dbHelper.getBillById(billId);
                if (bill != null) {
                    txtMonth.setText("Month: " + bill.getMonth());
                    txtUnits.setText("Units: " + bill.getUnits());
                    txtRebate.setText("Rebate: " + bill.getRebate() + "%");
                    txtTotal.setText("Total Charges: RM " + String.format("%.2f", bill.getTotal()));
                    txtFinal.setText("Final Cost: RM " + String.format("%.2f", bill.getFinalCost()));
                }
            }
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // go back
        return true;
    }
}