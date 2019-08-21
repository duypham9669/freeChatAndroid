package com.example.chatapp2.test;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp2.R;
import com.example.chatapp2.querryNameUser.querryName;

public class test extends AppCompatActivity {
private TextView testname;
    String a="";
    String b="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        testname=(TextView) findViewById(R.id.testname);
            querryName name = new querryName("-LmKl_MZy97jB8YFSjl8");
           a= name.setName();
           int x=0;
           do {
               if(a==null){
                   a= name.setName();
           }else{
                   System.out.println("AAA return name:" +a);
                   testname.setText(a);
               x=1;
           }
           }while (x==0);


//           if("".equals(a)){
//               System.out.println("AAA return name:" +a);
//
//           }else{
//               testname.setText(a);
//           }
    }
}
