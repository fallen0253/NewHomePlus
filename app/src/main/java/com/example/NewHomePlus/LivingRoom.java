package com.example.NewHomePlus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
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


public class LivingRoom extends AppCompatActivity {

    Switch swLED;
    TextView tvLCD1, tvLCD2;
    CheckBox cbDate, cbWeather, cbDust, cbUserInput;
    EditText edtLCDInput;
    FirebaseAuth mAuth;
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
        /*상현 2021-04-28 액션바 생성*/
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Home Plus");

    }
    /*상현 2021-04-28 옵션메뉴 생성 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.membermenuback,menu);
        return true;
    }
    /*상현 2021-04-28 옵션메뉴 선택시 발생 이벤트*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goBack:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}

