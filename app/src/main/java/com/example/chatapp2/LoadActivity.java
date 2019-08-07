package com.example.chatapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.webkit.WebView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoadActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
//


        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("dataLogin", MODE_ENABLE_WRITE_AHEAD_LOGGING);
            String checkemail=sharedPreferences.getString("taikhoan","");
            String checkpass=sharedPreferences.getString("matkhau","");
            if(checkpass.equals("")){
                Intent intent = new Intent(LoadActivity.this, LoginActivity.class);
                startActivity(intent);
            }else{
                mAuth.signInWithEmailAndPassword(checkemail, checkpass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
//                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoadActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();


                                }else{
                                    Intent intent = new Intent(LoadActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        }
    }

