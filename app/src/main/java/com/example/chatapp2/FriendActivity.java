package com.example.chatapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class FriendActivity extends AppCompatActivity {
    private ListView listViewbanbe;
    private Button btn_themban, btn_tinnhan, btn_thongtin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        anhxa();
        btn_themban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timbanbe();
            }
        });
    }
    private void anhxa(){
        listViewbanbe=(ListView) findViewById(R.id.lisViewBanbe);
        btn_themban=(Button)findViewById(R.id.btn_themban);
        btn_tinnhan=(Button)findViewById(R.id.btn_tinnhan);
        btn_thongtin=(Button) findViewById(R.id.btn_thongtin);
    }
    private void timbanbe(){
        
    }
}
