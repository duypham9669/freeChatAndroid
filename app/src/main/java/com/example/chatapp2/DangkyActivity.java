package com.example.chatapp2;
import android.app.Activity;
import android.util.Log;
import android.view.Window;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import static android.content.ContentValues.TAG;

public class DangkyActivity extends Activity {
    private TextView linkdangnhap, textViewthongbao;
    private EditText textViewemail,textViewmatkhau, textViewmatkhau2,
            textViewname;
    private Button btn_dangky;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dangky);
        anhxa();
        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
        linkdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkdangnhap.setTextColor(Color.GREEN);
                Toast.makeText(DangkyActivity.this, "Đăng nhập", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DangkyActivity.this, LoginActivity.class);
                startActivity(intent);            }
        });
        dangky();

    }
    private void anhxa(){
        linkdangnhap=(TextView)findViewById(R.id.textViewlink);
        textViewemail=(EditText) findViewById(R.id.editTextemail);
        textViewmatkhau=(EditText) findViewById(R.id.editTextmatkhau);
        textViewmatkhau2=(EditText)findViewById(R.id.editTextmatkhau2);
        textViewname=(EditText) findViewById(R.id.editTextname);
        btn_dangky=(Button) findViewById(R.id.btn_dangnhap);
        textViewthongbao=(TextView) findViewById(R.id.textViewthongbao);
    }
    private void settext(String conten){
        textViewthongbao.setText(conten);
    }



    private void dangky(){
        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((textViewemail.getText().toString()).equals("")||(textViewmatkhau.getText().toString()).equals("")||(textViewmatkhau2.getText().toString()).equals("")
                        ||(textViewname.getText().toString()).equals("")){
                    settext("vui lòng điền đủ thông tin");
                }
                else if((textViewmatkhau.getText().toString()).length()>6){
                    settext("Mật khẩu ít nhất 6 ký tự");
                }
                else  if(!(textViewmatkhau.getText().toString()).equals(textViewmatkhau2.getText().toString()))
                {
                    settext("Mật khẩu không khớp");
                }
                else{
                    pushdangky();
                }
            }
        });
    }
    private void pushdangky(){
        String email=textViewemail.getText().toString();
        String password=textViewmatkhau.getText().toString();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            settext("Tài khoản đã tồn tại");
                            Toast.makeText(DangkyActivity.this, "Tài khoản đã tồn tại",  Toast.LENGTH_SHORT).show();
                        }
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(DangkyActivity.this, "Thành công, mời đăng nhập", Toast.LENGTH_SHORT).show();
                            settext("Đăng ký thành công, mời đăng nhập");

//                            FirebaseUser user = mAuth.getCurrentUser();
                            setnameuser();
                        }

                        else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(DangkyActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void setnameuser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(textViewname.getText().toString())
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }
}
