package com.example.electricitybillcalculator;

import static com.example.electricitybillcalculator.R.id.customToolbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView txtInfo, txtUrl;
    Button btnCustomize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(customToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("About");

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            txtInfo = findViewById(R.id.txtInfo);
            txtUrl = findViewById(R.id.txtUrl);

            String info = "Name: Faizmin Emira binti Mohd Faizul\n" +
                    "Student ID: 2023197491 \n" +
                    "Course: ICT602-Mobile Technology and Development \n" +
                    "Presented for: Dr Ahmad Iqbal Hakim bin Suhaimi \n" +
                    "© 2025 Faizmin Emira. All Rights Reserved";

            txtInfo.setText(info);

            txtUrl.setText("GitHub: https://github.com/yourusername/electricity-bill-app");
            txtUrl.setMovementMethod(LinkMovementMethod.getInstance());

        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // go back
        return true;
    }
}
