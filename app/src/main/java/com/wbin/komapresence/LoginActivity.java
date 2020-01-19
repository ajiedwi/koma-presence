package com.wbin.komapresence;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    Spinner spEvent, spSesi;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        spEvent = findViewById(R.id.spinner_event);
        spSesi= findViewById(R.id.spinner_sesi);
        btnLogin = findViewById(R.id.btn_login);

        ArrayAdapter<CharSequence> adapterEvent = ArrayAdapter.createFromResource(this,
                R.array.event_name, R.layout.item_spinner);
        adapterEvent.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spEvent.setAdapter(adapterEvent);

        ArrayAdapter<CharSequence> adapterSesi = ArrayAdapter.createFromResource(this,
                R.array.event_sesi, R.layout.item_spinner);
        adapterSesi.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spSesi.setAdapter(adapterSesi);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
        });
    }
}
