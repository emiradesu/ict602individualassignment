package com.example.electricitybillcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.*;
import android.content.Intent;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText edtUnits;
    Spinner spinnerMonth;
    TextView txtTotal, txtFinal;
    Button btnCalculate, btnHistory, btnAbout;

    RadioGroup radioGroupRebate;
    RadioButton radio0, radio1, radio2, radio3, radio4, radio5;

    DBHelper dbHelper;
    int rebatePercent = 0;
    double totalCharge, finalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.customToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Electricity Bill Calculator");
        }

        edtUnits = findViewById(R.id.edtUnits);
        spinnerMonth = findViewById(R.id.spinnerMonth);
        txtTotal = findViewById(R.id.txtTotal);
        txtFinal = findViewById(R.id.txtFinal);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnHistory = findViewById(R.id.btnHistory);
        btnAbout = findViewById(R.id.btnAbout);
        radioGroupRebate = findViewById(R.id.radioGroupRebate);
        radio0 = findViewById(R.id.radio0);
        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);
        radio3 = findViewById(R.id.radio3);
        radio4 = findViewById(R.id.radio4);
        radio5 = findViewById(R.id.radio5);

        dbHelper = new DBHelper(this);

        // Set Spinner values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapter);

        btnCalculate.setOnClickListener(v -> {
            String unitStr = edtUnits.getText().toString().trim();
            if (unitStr.isEmpty()) {
                edtUnits.setError("Enter electricity units used");

                return;
            }

            int units = Integer.parseInt(unitStr);

            String selected = "0";

            if (radio1.isChecked()) {
                selected = "1";
            } else if (radio2.isChecked()) {
                selected = "2";
            } else if (radio3.isChecked()) {
                selected = "3";
            }
            else if (radio4.isChecked()) {
                selected = "4";
            }
            else if (radio5.isChecked()) {
                selected = "5";
            }


            rebatePercent = Integer.parseInt(selected);

            totalCharge = calculateCharge(units);
            finalCost = totalCharge - (totalCharge * rebatePercent / 100.0);
            DecimalFormat df = new DecimalFormat("0.00");

            txtTotal.setText("Total Charges: RM " + df.format(totalCharge));
            txtFinal.setText("Final Cost: RM " + df.format(finalCost));

            String month = spinnerMonth.getSelectedItem().toString();

            boolean isInserted = dbHelper.insertData(month, units, rebatePercent, totalCharge, finalCost);
            if (isInserted) {
                Toast.makeText(this, "Record successfully saved!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to save.", Toast.LENGTH_SHORT).show();
            }
        });

        btnHistory.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, HistoryActivity.class)));

        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }

    private double calculateCharge(int units) {
        double cost = 0;

        if (units <= 200) {
            cost = units * 0.218;
        } else if (units <= 300) {
            cost = 200 * 0.218 + (units - 200) * 0.334;
        } else if (units <= 600) {
            cost = 200 * 0.218 + 100 * 0.334 + (units - 300) * 0.516;
        } else {
            cost = 200 * 0.218 + 100 * 0.334 + 300 * 0.516 + (units - 600) * 0.546;
        }

        return cost;
    }
}