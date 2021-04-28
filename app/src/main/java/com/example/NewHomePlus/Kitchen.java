package com.example.NewHomePlus;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class Kitchen extends AppCompatActivity {

    ImageView ivSecurity;
    TextView tvFireOn, tvTemperature;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kitchen);
    }
}

