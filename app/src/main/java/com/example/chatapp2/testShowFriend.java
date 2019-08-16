package com.example.chatapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class testShowFriend extends AppCompatActivity {
    private Button btn_showFriend;
    private ListView listViewFriend;
    ArrayAdapter<String> adapter;
    private DatabaseReference Database;
    private DatabaseReference myRef;
    private TextView link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_show_friend);
        anhxa();
        showmain();
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                link.setTextColor(Color.GREEN);
                Toast.makeText(testShowFriend.this, "Đăng ký tài khoản", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void anhxa(){
        btn_showFriend=(Button)findViewById(R.id.btn_showFriend);
        listViewFriend=(ListView)findViewById(R.id.listViewFriend);
    }
    private void showmain(){
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        listViewFriend.setAdapter(adapter);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Kết nối tới node có tên là message
        myRef = database.getReference("friend");
        Query friend = myRef.child("-LmKl_MZy97jB8YFSjl8");

        friend.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //vòng lặp để lấy dữ liệu khi có sự thay đổi trên Firebase
                for (DataSnapshot data: dataSnapshot.getChildren())
                {
                    //lấy key của dữ liệu
                    String key=data.getKey();
                    //lấy giá trị của key (nội dung)
                    String value=data.getValue().toString();

                    adapter.add(value+"\n");
                    adapter.add(String.valueOf(link));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

}
