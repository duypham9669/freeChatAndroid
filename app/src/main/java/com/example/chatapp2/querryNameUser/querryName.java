package com.example.chatapp2.querryNameUser;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chatapp2.DataUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class querryName {
    private DatabaseReference myRef;
    private String key;
    private String name;
    String a;

    public querryName(String key) {
        this.key = key;

    }

    public String setName() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user");
        Query findname = myRef.orderByChild("key").equalTo(key);
        findname.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                DataUser user = dataSnapshot.getValue(DataUser.class);
                a = user.getName();

                System.out.println("AAA"+name);
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
        return getName(a);
    }

    private String getName(String name2) {
        return name2;
    }
}

