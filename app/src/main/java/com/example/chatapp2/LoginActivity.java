package com.example.chatapp2;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends Activity {
    private TextView link;
    private Button btndangnhap;
    private EditText editTextsdt, editTextmatkhau;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences, datauser;
    private CheckBox checkBox;
    private DatabaseReference myRef, reffriend;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        anhxa();
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("dataLogin", MODE_ENABLE_WRITE_AHEAD_LOGGING);

        editTextsdt.setText(sharedPreferences.getString("taikhoan",""));
        editTextmatkhau.setText(sharedPreferences.getString("matkhau",""));
        checkBox.setChecked(sharedPreferences.getBoolean("checked", false));


        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                link.setTextColor(Color.GREEN);
                Toast.makeText(LoginActivity.this, "Đăng ký tài khoản", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, DangkyActivity.class);
                startActivity(intent);
            }
        });

        //dang nhap tu dong

        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangnhap();
            }
        });

    }
    private void anhxa(){
        link=(TextView)findViewById(R.id.textViewlink);
        editTextsdt=(EditText) findViewById(R.id.editTextemail);
        editTextmatkhau=(EditText)findViewById(R.id.editTextmatkhau);
        btndangnhap=(Button)findViewById(R.id.btn_dangnhap);
        checkBox=(CheckBox) findViewById(R.id.checkBoxnho);
    }
    private void dangnhap(){
        String sdt=editTextsdt.getText().toString();
        String password=editTextmatkhau.getText().toString();
        mAuth.signInWithEmailAndPassword(sdt, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, fragment.class);

                            if(checkBox.isChecked()){
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("taikhoan", editTextsdt.getText().toString());
                                editor.putString("matkhau", editTextmatkhau.getText().toString());
                                editor.putBoolean("checked", true);
                                editor.commit();
                            }else{
                                SharedPreferences.Editor  editor = sharedPreferences.edit();
                                editor.remove("taikhoan");
                                editor.remove("matkhau");
                                editor.remove("cheked");

                            }
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
//    private void getdatauser(){
//        String email=editTextsdt.getText().toString();
//        database=FirebaseDatabase.getInstance();
//        myRef=database.getReference("user");
//        Query friend = myRef.orderByChild("email").equalTo(email);
//        friend.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
//                DataUser user = dataSnapshot.getValue(DataUser.class);
//
//                listfriend(user.getKey());
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//    private void listfriend(String keyuser){
//        String fr;
//        reffriend=database.getReference("friend");
//        Query lisfriend=reffriend.orderByChild(keyuser);
//        lisfriend.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot data: dataSnapshot.getChildren()){
//                    String value=(String)data.getValue();
//
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError)
//            {
//                System.out.println("AAA Loi");
//
//            }
//        });
//    }
//    private void convertemail(String x){
//
//        database=FirebaseDatabase.getInstance();
//        myRef=database.getReference("user");
//        Query friend = myRef.orderByChild("key").equalTo(x);
//        friend.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
//                DataUser user = dataSnapshot.getValue(DataUser.class);
//                SharedPreferences.Editor editor=datauser.edit();
//                String email=user.getEmail();
//                editor.putString("friend",email);
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

}
