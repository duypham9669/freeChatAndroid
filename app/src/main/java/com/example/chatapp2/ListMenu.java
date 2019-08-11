package com.example.chatapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ListMenu extends AppCompatActivity {
    private ListView listViewMenu;
    private Button btn_tinnhan, btn_banbe, btn_thongtin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_menu);
        anhxa();;
        btn_banbe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void anhxa(){
        listViewMenu=(ListView) findViewById(R.id.lisViewMenu);
        btn_tinnhan=(Button) findViewById(R.id.btn_tinnhan);
        btn_banbe=(Button) findViewById(R.id.btn_banbe);
        btn_thongtin=(Button) findViewById(R.id.btn_thongtin);

    }
    private void timbanbe(){
        
    }
}
