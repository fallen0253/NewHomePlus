package com.example.NewHomePlus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class FindPwd extends AppCompatActivity {

    private static final String TAG = "FindPwdActivity";

    //define view objects
    private EditText edtFindPWEmail;
    private Button btnFindPW;
    private ProgressDialog progressDialog;
    //define firebase object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);

        edtFindPWEmail = (EditText) findViewById(R.id.edtFindPWEmail);
        btnFindPW = (Button) findViewById(R.id.btnFindPW);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        /*리나 2021-04-24 비밀번호 재설정(해당 이메일로 비밀번호 재설정 링크 메일 전송*/
        btnFindPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("처리중입니다. 잠시 기다려 주세요...");
                progressDialog.show();
                //비밀번호 재설정 이메일 보내기
                String emailAddress = edtFindPWEmail.getText().toString().trim();
                firebaseAuth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    showToast("등록된 이메일로 메일을 전송했습니다.");
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                } else {
                                    showToast("이메일 전송을 실패하였습니다.");
                                }
                                progressDialog.dismiss();
                            }
                        });
            }
        });

    }
    void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }
}