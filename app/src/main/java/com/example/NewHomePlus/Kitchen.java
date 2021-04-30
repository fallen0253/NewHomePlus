package com.example.NewHomePlus;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;


public class Kitchen extends AppCompatActivity {

    ImageView ivSecurity;
    TextView tvFireOn, tvTemperature;
    Animation anim;
    Button btn119;

    Thread wokerThread=null;
    String strDelimiter="\n";
    char charDelimiter='\n';
    byte readBuffer[];
    int readBufferPosition;
    Intent intent = getIntent();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kitchen);
        /*상현 2021-04-30 가스값이 담긴 tempValue를 Int tempValue로 담는다.*/
        int tempValue = Integer.parseInt(intent.getStringExtra("tempValue"));

        /*상현 2021-04-28 액션바 생성*/
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Home Plus");
        bar.setDisplayHomeAsUpEnabled(true);

        /*리나 2021-04-29*/
        ivSecurity=findViewById(R.id.ivSecurity);
        tvFireOn=findViewById(R.id.tvFireOn);
        tvTemperature=findViewById(R.id.tvTemperature);
        btn119=findViewById(R.id.btn119);

        /*리나 2021-04-29 버튼 클릭 시 119에 전화걸기*/
        btn119.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel="119";
                Uri uri=Uri.parse("tel:" + tel);
                Intent intent=new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
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
    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /*리나 2021-04-29 아두이노 수신 데이터 처리*/
    //데이터 수신 준비 및 처리
    void beginListenForData(){
        final Handler handler=new Handler();
        readBuffer=new byte[1024]; //아두이노에서 받는 수신버퍼 크기
        readBufferPosition=0; //버퍼 내 수신 문자 저장 위치
        //문자열 수신 쓰레드
        wokerThread=new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()){ //쓰레드가 중단된 상태가 아닐 경우
                    try{
                        int byteAvailalbe=InputOutput.inputStream.available(); //수신데이터가 있는지 확인
                        if(byteAvailalbe>0){
                            //아두이노에서 보낸 데이터가 있음
                            byte[] packetBytes=new byte[byteAvailalbe];
                            InputOutput.inputStream.read(packetBytes);
                            for(int i=0; i<byteAvailalbe; i++){
                                byte b=packetBytes[i];
                                if(b==charDelimiter){ //마지막에 '\n'이 들어 올 경우
                                    byte[] encodeByte=new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodeByte, 0, encodeByte.length);
                                    final String tempValueS=new String(encodeByte, "US-ASCII");
                                    readBufferPosition=0;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            //온도 값
                                            String temp=tempValueS.substring(0,1);
                                            String value=tempValueS.substring(1);
                                            int tempValue=Integer.getInteger(value);

                                            if(temp.equals("F")){
                                                tvTemperature.setText("현재 온도 : " + value);
                                                //온도가 올라가면 문구 깜빡거리기
                                                if(tempValue>20){
                                                    ivSecurity.setImageResource(R.drawable.shield_on);
                                                    tvFireOn.startAnimation(anim);
                                                }else{
                                                    tvFireOn.clearAnimation();
                                                }
                                                anim = new AlphaAnimation(0.0f,1.0f); //투명도 조절
                                                anim.setDuration(300); //지속시간
                                                anim.setStartOffset(20); //대기시간
                                                anim.setRepeatMode(Animation.REVERSE); //반복
                                                anim.setRepeatCount(Animation.INFINITE); //반복 횟수 지정
                                            } else{
                                                tvTemperature.setText("온도 측정 불가");
                                            }
                                         }
                                    });
                                }else{
                                    readBuffer[readBufferPosition++]=b;
                                }
                            }
                        }
                    }catch (IOException e){
                        showToast("데이터 수신 중 오류가 발생했습니다.");
                    }
                }
            }
        });
        wokerThread.start();
    }


}

