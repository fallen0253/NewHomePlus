package com.example.NewHomePlus;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class joinMember extends AppCompatActivity {
    Button btnSingUP;
    TextView tvSingIn;
    EditText edtEmail, edtPw;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_member);
        btnSingUP=findViewById(R.id.btnSingUP);
        edtEmail=findViewById(R.id.edtEmail);
        edtPw=findViewById(R.id.edtPw);
        tvSingIn=findViewById(R.id.tvSignin);

        mAuth = FirebaseAuth.getInstance();
        /*리나 2021-04-24 로그인 상태 확인*/
        if (mAuth.getCurrentUser() != null) {
            //이미 로그인 되었다면 현재 joinMember 액티비티를 종료함
            finish();
            //그리고 MainActivity 액티비티를 연다.
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        btnSingUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        /*리나 2021-04-24 회원가입 페이지에서 로그인 페이지로 이동*/
        tvSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 이미 완료한 사람은 로그인 페이지로 이동
                Intent intent = new Intent(joinMember.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    //2021-04-22 상현 신규 계정 만들기
    private void createAccount() {
        //사용자가 입력하는 email, password를 가져온다.
        String email = edtEmail.getText().toString().trim();
        String password = edtPw.getText().toString().trim();
        //2021-04-22 상현 email과 password가 비었는지 확인한다.
        if(TextUtils.isEmpty(email)){
            showToast("Email을 입력해주세요.");
            return;
        }
        if(TextUtils.isEmpty(password)){
            showToast("Password를 입력해 주세요.");
            return;
        }
        //2021-04-22 상현 계정생성
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("BACK", "로그인 성공!!");
                            setResult(RESULT_OK,intent);
                            finish();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(joinMember.this);
                            builder.setTitle("에러유형");
                            builder.setMessage("-이미 등록된 이메일-\n-암호 최소 6자리 이상-\n-서버에러-");
                            /*리나 2021-04-24 '확인'버튼 클릭 시 이벤트 null로 수정*/
                            builder.setPositiveButton("확인", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            updateUI(null);
                        }
                    }
                });
        //2021-04-22 상현 계정생성 종료
    }
    private void updateUI(FirebaseUser user){

    }
    /*리나 2021-04-24 토스트메세지 */
    void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }
}
