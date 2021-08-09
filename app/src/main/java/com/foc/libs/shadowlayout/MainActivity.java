package com.foc.libs.shadowlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.foc.libs.shadowLayoutPro.ShadowLayout;

public class MainActivity extends AppCompatActivity {
    boolean b = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShadowLayout sl = findViewById(R.id.sl);


        findViewById(R.id.button_ed).setOnClickListener(v -> {
            if (b) sl.disableShadow();
            else sl.enableShadow();
            b=!b;
        });
    }
}