package com.example.chatapp2;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    private EditText editTexttext;
    private Button btn_gui;
    private ListView listView;
    private String name, email;
    ArrayAdapter<String> adapter;
    private DatabaseReference mDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        anhxa();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            name = user.getDisplayName();
            email = user.getEmail();
            boolean emailVerified = user.isEmailVerified();
        } else {


        }
        //doc data=================
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Kết nối tới node có tên là message
        myRef = database.getReference("message");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
            //vòng lặp để lấy dữ liệu khi có sự thay đổi trên Firebase
                for (DataSnapshot data: dataSnapshot.getChildren())
                {
                //lấy key của dữ liệu
                    String key=data.getKey();
                        //lấy giá trị của key (nội dung)
                    String value=data.getValue().toString();
                    adapter.add(value+"\n");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
        //======================================


        btn_gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=name+":"+editTexttext.getText().toString();
                myRef.push().setValue(text);
                editTexttext.setText("");
                        }
        });

    }
    private void anhxa(){
        editTexttext=(EditText) findViewById(R.id.editTexttext);
        btn_gui=(Button)findViewById(R.id.btn_gui);
        listView=(ListView) findViewById(R.id.listtext);
    }


    }


