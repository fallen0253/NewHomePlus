package com.example.NewHomePlus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    /*상현 2021-04-18 회원가입 요청코드*/
    public static final int REQUEST_CODE_JOINMEBER = 101;
    /*상현 2021-04-18 로그인 요청코드*/
    public static final int REQUEST_CODE_HOMEPLUS = 102;
    Button btnJoinMember, btnHomePlus;
    FirebaseAuth mAuth;
    EditText edtUserName, edtPassWord;
    TextView tvFindPW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnJoinMember=findViewById(R.id.btnJoinMember);
        btnHomePlus=findViewById(R.id.btnHomePlus);
        edtUserName=findViewById(R.id.edtUserName);
        edtPassWord=findViewById(R.id.edtPassWord);
        tvFindPW=findViewById(R.id.tvFindPW);

        mAuth = FirebaseAuth.getInstance();
        /*리나 2021-04-24 로그인 상태 확인*/
        if(mAuth.getCurrentUser() != null){
            //이미 로그인 되었다면 이 액티비티를 종료함
            finish();
            //그리고 homePlusMain 액티비티를 연다.
            Intent intent = new Intent(MainActivity.this,homePlusMain.class);
            startActivity(intent);
            //startActivityForResult(intent,REQUEST_CODE_HOMEPLUS);
        }
        /*상현 2021-04-18 회원가입 액티비티 호출 */
        btnJoinMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,joinMember.class);
                startActivity(intent);
                //startActivityForResult(intent, REQUEST_CODE_JOINMEBER);
            }
        });
        /*상현 2021-04-18 홈플러스 메인 액티비티 호출*/
        btnHomePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        /*리나 2021-04-24 비밀번호 찾기 페이지 FindPwd로 이동*/
        tvFindPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , FindPwd.class);
                startActivity(intent);
                //startActivity(intent);
            }
        });
    }
    /*상현 2021-04-18 액티비티 응답 구별 메서드*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_JOINMEBER){
            showToast("onActivityResult 메서드 호출됨. 요청 코드 : "
                    + requestCode + ", 결과 코드 : " + resultCode);
            if(resultCode == RESULT_OK){
                String BACK = data.getStringExtra("BACK");
                showToast("응답으로 전달된 BACK : \" + BACK");
            }
        }
    }
    /*상현 2021-04-23 로그인 메서드 추가*/
    private void Login(){

        String email = edtUserName.getText().toString().trim();
        String password = edtPassWord.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Email을 입력해주세요.",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Password를 입력해 주세요.",Toast.LENGTH_LONG).show();
            return;
        }
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //FirebaseUser user = mAuth.getCurrentUser();
                            /* 리나 2021-04-24 로그인이 완료되면 페이지 이동후 로그인 페이지는 종료해야함
                             * 그래야지 뒤돌아가기 버튼을 이용해서 로그인 페이지로 돌아올 수 없음*/
                            finish();
                            Intent intent = new Intent(MainActivity.this,homePlusMain.class);
                            startActivity(intent);
                            //startActivityForResult(intent,REQUEST_CODE_HOMEPLUS);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        // [END sign_in_with_email]

    }
    void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }

    /* 리나 2021-04-24 주석처리
    이 부분은 삭제하고 위에 onCreate에 처음부터 시작하도록 했어요.
    그래야지 로그인 상태에서 또 로그인페이지로 안 넘어가도록!

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }else{
            Toast.makeText(getApplicationContext(),"현재 접속중이 아닙니다.",Toast.LENGTH_LONG).show();
        }
    }*/
    /* 리나 2021-04-24 주석처리
    이 부분은 어떤 용도로 사용되는지 설명 부탁드립니다.
    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }
    */
}