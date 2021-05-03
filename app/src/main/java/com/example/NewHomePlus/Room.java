package com.example.NewHomePlus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Room extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    SeekBar seekRED, seekGREEN, seekBLUE;
    LinearLayout rgbColor;
    int redValue, greenValue, blueValue;
    EditText edtRed, edtGreen, edtBlue;
    FirebaseAuth mAuth;
    Button btnSend;

    String strDelimiter="\n";
    String str;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room);

        /*seekRED=findViewById(R.id.seekRED);
        seekRED.setOnSeekBarChangeListener(this);
        seekBLUE=findViewById(R.id.seekBLUE);
        seekBLUE.setOnSeekBarChangeListener(this);
        seekGREEN=findViewById(R.id.seekGREEN);
        seekGREEN.setOnSeekBarChangeListener(this);*/
        edtRed=findViewById(R.id.edtRed);
        edtGreen=findViewById(R.id.edtGreen);
        edtBlue=findViewById(R.id.edtBlue);
        btnSend=findViewById(R.id.btnSend);

        rgbColor=findViewById(R.id.rgbColor);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Home Plus");
        bar.setDisplayHomeAsUpEnabled(true);



        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redValue = Integer.parseInt(edtRed.getText().toString());
                greenValue = Integer.parseInt(edtGreen.getText().toString());
                blueValue = Integer.parseInt(edtBlue.getText().toString());
                if (edtRed == null || edtGreen == null || edtBlue == null) {
                    redValue = 0;
                    greenValue = 0;
                    blueValue = 0;
                }
                if (redValue >= 0 && redValue < 255) {
                    str = "R" + redValue;
                    if (InputOutput.outputStream != null) {
                        sendData(str);
                    }
                } else {
                    edtRed.setText("");
                }

                if (greenValue >= 0 && greenValue < 255) {
                    str = "G" + greenValue;
                    if (InputOutput.outputStream != null) {
                        sendData(str);
                    }
                } else {
                    edtRed.setText("");
                }

                if (blueValue >= 0 && blueValue < 255) {
                    str = "B" + blueValue;
                    if (InputOutput.outputStream != null) {
                        sendData(str);
                    }
                } else {
                    edtRed.setText("");
                }
                rgbColor.setBackgroundColor(0xff000000 + redValue * 0x10000 + greenValue * 0x100 + blueValue);


            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        /*리나 2021-04-26 RGB 색상 보여주기*/
       /* switch (seekBar.getId()){
            case R.id.seekRED:
                redValue=progress;
                str="R"+String.valueOf(progress);
                Log.i("slskfl", "RGB: "+ str);
                break;
            case R.id.seekGREEN:
                greenValue=progress;
                str="G"+String.valueOf(progress);
                Log.i("slskfl", "RGB: "+ str);
                break;
            case R.id.seekBLUE:
                blueValue=progress;
                str="B"+String.valueOf(progress);
                Log.i("slskfl", "RGB: "+ str);
                break;
        }
        if(InputOutput.outputStream!=null) {
            sendData(str);
        }
        rgbColor.setBackgroundColor(0xff000000+redValue*0x10000+greenValue*0x100+blueValue);*/

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void sendData(String msg) {
        msg+=strDelimiter;
        try{
            Log.i("slskfl", "Room sendData : " +msg);
            InputOutput.outputStream.write(msg.getBytes()); //문자열 전송
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "문자열 전송 도중에 오류가 발생했습니다.",  Toast.LENGTH_SHORT).show();
        }
    }
    void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}

