package com.example.chatapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class testShowFriend extends AppCompatActivity {
    private Button btn_reload;
    DatabaseReference myRef, myRef2;
    ListView listView;
    ArrayList<String> nameArray = new ArrayList<>();
    ArrayList<String> listKey = new ArrayList<>();

    //    String[] nameArray = {"Octopus","Pig","Sheep","Rabbit","Snake","Spider" };
    ArrayList<String> infoArray = new ArrayList<>();

    Integer[] imageArray = {R.drawable.user,
            R.drawable.user
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_show_friend);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef2 = database.getReference("user");
        myRef = database.getReference("friend");
        btn_reload=(Button) findViewById(R.id.btn_reload);
        btn_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }
    private void queryFriend(){
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
                    String value= (String) data.getValue();

                    System.out.println("AAA"+value);
                    convertName(value);
                    listKey.add(value);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

    }
    public void showdatafriend(){
        CustomListAdapter whatever = new CustomListAdapter(this, nameArray, infoArray, imageArray);
        listView = (ListView) findViewById(R.id.listViewFriend);
        listView.setAdapter(whatever);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(testShowFriend.this, DetailActivity.class);
                String message = nameArray.get(position);
                intent.putExtra("animal", message);
                startActivity(intent);
            }
        });
    }
    private void convertName(String key2){

            Query findname = myRef2.orderByChild("key").equalTo(key2);
            findname.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    DataUser user = dataSnapshot.getValue(DataUser.class);
                    String name = user.getName();
                    nameArray.add(name);
                    infoArray.add("test");
                    System.out.println("AAA"+name);
                    showdatafriend();
                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

