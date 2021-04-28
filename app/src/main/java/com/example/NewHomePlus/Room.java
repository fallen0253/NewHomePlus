package com.example.NewHomePlus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    FirebaseAuth mAuth;
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
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Home Plus");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.membermenuback,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logOut:
                mAuth.signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                Log.i("Test_result : ", "로그아웃.");
                break;
            case R.id.deleteMember:
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Room.this);
                alert_confirm.setMessage("정말 계정을 삭제 할까요?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                showToast("계정이 삭제 되었습니다.");
                                                finish();
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            }
                                        });
                            }
                        }
                );
                alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showToast("회원탈퇴를 취소하셨습니다.");
                    }
                });
                alert_confirm.show();
                break;
            case R.id.goBack:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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

