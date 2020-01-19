package com.wbin.komapresence;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {

    ImageView ivHome, ivHistorry, ivScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ivHistorry = findViewById(R.id.iv_home_history);
        ivHome = findViewById(R.id.iv_home_home);
        ivScan = findViewById(R.id.iv_home_scan);

        final HomeFragment homeFragment = new HomeFragment();
        final ScanFragment scanFragment = new ScanFragment();
        final HistoryFragment historyFragment= new HistoryFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container_home,homeFragment).commit();

        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_home,scanFragment).commit();
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_home,homeFragment).commit();
            }
        });

        ivHistorry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_home,historyFragment).commit();
            }
        });
    }
}
