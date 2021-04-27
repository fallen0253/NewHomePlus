package com.example.NewHomePlus;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class Room extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    SeekBar seekRED, seekGREEN, seekBLUE;
    LinearLayout rgbColor;
    int redValue, greenValue, blueValue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room);

        seekRED=findViewById(R.id.seekRED);
        seekRED.setOnSeekBarChangeListener(this);
        seekBLUE=findViewById(R.id.seekBLUE);
        seekBLUE.setOnSeekBarChangeListener(this);
        seekGREEN=findViewById(R.id.seekGREEN);
        seekGREEN.setOnSeekBarChangeListener(this);
        rgbColor=findViewById(R.id.rgbColor);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        /*리나 2021-04-26 RGB 색상 보여주기*/
        switch (seekBar.getId()){
            case R.id.seekRED:
                redValue=progress;
                break;
            case R.id.seekGREEN:
                greenValue=progress;
                break;
            case R.id.seekBLUE:
                blueValue=progress;
                break;
        }
        rgbColor.setBackgroundColor(0xff000000+redValue*0x10000+greenValue*0x100+blueValue);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

