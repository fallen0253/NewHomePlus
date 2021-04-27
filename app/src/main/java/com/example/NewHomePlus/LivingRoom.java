package com.example.NewHomePlus;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class LivingRoom extends AppCompatActivity {

    Switch swLED;
    TextView tvLCD1, tvLCD2;
    CheckBox cbDate, cbWeather, cbDust, cbUserInput;
    EditText edtLCDInput;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.living_room);

        swLED=findViewById(R.id.swLED);
        tvLCD1=findViewById(R.id.tvLCD1);
        tvLCD2=findViewById(R.id.tvLCD2);
        cbDate=findViewById(R.id.cbDate);
        cbWeather=findViewById(R.id.cbWeather);
        cbDust=findViewById(R.id.cbDust);
        cbUserInput=findViewById(R.id.cbUserInput);
        edtLCDInput=findViewById(R.id.edtLCDInput);

    }
}

