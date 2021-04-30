package com.example.NewHomePlus;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;


public class LivingRoom extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Switch swLCD;
    TextView tvLCD1, tvLCD2;
    CheckBox cbDate, cbWeather, cbDust, cbUserInput;
    EditText edtLCDInput;
    String strDelimiter = "\n";
    String LEDstr;
    String datestr;
    long now;
    Date today = new Date(now);
    String edtText;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    int count = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.living_room);

        tvLCD1 = findViewById(R.id.tvLCD1);
        tvLCD2 = findViewById(R.id.tvLCD2);
        cbDate = findViewById(R.id.cbDate);
        cbWeather = findViewById(R.id.cbWeather);
        cbDust = findViewById(R.id.cbDust);
        cbUserInput = findViewById(R.id.cbUserInput);
        edtLCDInput = findViewById(R.id.edtLCDInput);
        /*상현 2021-04-28 액션바 생성*/
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Home Plus");
        bar.setDisplayHomeAsUpEnabled(true);
        swLCD = findViewById(R.id.swLCD);
        datestr = format.format(today);
        Log.i("test", "datestr 값 " + datestr);
        /*상현 2021-04-30 count 2미만일때만 체크가능*/
        //if(count<2){
        Log.i("test", "if시작" + count);

            cbDate.setOnCheckedChangeListener(this);
            cbWeather.setOnCheckedChangeListener(this);
            cbDust.setOnCheckedChangeListener(this);
            cbUserInput.setOnCheckedChangeListener(this);

        edtText = edtLCDInput.getText().toString();
        /*상현 2021-04-29 날짜 보여주기 */

        swLCD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /*상현 2021-04-29 LCD전원 키기*/
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (InputOutput.outputStream != null) {
                    if (isChecked) {
                        LEDstr = "1";
                    } else {
                        LEDstr = "0";
                    }
                    sendData(LEDstr);
                }
            }
        });


    }

    /*상현 2021-04-28 옵션메뉴 선택시 발생 이벤트*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendData(String msg) {
        msg += strDelimiter;
        try {
            InputOutput.outputStream.write(msg.getBytes()); //문자열 전송
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "문자열 전송 도중에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (cbDate.isChecked()) {
            Log.i("test", "cbDate안에 :" + datestr);
            tvLCD1.setText(datestr);
            count++;
            Log.i("test", "cdDate안에" + count);
            cbWeather.setEnabled(false);
        } else {
            tvLCD1.setText("");
            cbWeather.setEnabled(true);
        }
        sendData(datestr);
        if (cbWeather.isChecked()) {
            if (isChecked) {
                tvLCD1.setText("오늘 날씨:");
                count++;
                cbDate.setEnabled(false);
            } else {
                tvLCD1.setText("");
                cbDate.setEnabled(true);
            }
        }
        if (cbDust.isChecked()) {
            if (isChecked) {
                tvLCD2.setText("미세 먼지: ");
                count++;
                cbUserInput.setEnabled(false);
            } else {
                tvLCD2.setText("");
                cbUserInput.setEnabled(true);
            }
        }
        if (cbUserInput.isChecked()) {
            if (isChecked) {

                tvLCD2.setText(edtText);
                cbDate.setEnabled(false);
            }else {
                cbDate.setEnabled(true);
            }
        }
    }
}



