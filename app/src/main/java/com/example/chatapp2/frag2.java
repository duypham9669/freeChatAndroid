package com.example.chatapp2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class frag2 extends Fragment {
    private Button btn_themban;
    private View view;
    private EditText txtname;
    private TextView viewname, viewemail;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view =inflater.inflate(R.layout.fragment2, container, false);
        try{
            btn_themban=(Button)view.findViewById(R.id.btn_thembann);
        }catch (Exception ex){
            System.out.println("AAA"+ex);
        }
        btn_themban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("friend");
        mAuth = FirebaseAuth.getInstance();
        return view;
    }
    private void alertDialog(){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = frag2.this.getLayoutInflater();
        View mView = inflater.inflate(R.layout.timbanbe, null);
        txtname=(EditText) mView.findViewById(R.id.txtusername);
        alertDialog.setTitle("tim ban be");
        alertDialog.setView(mView);
        alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try{
                    getdatauser( txtname.getText().toString());
                }catch (Exception ex){
                    System.out.println("AAA"+ex);
                }
            }
        });
        alertDialog.setPositiveButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.create();
        alertDialog.show();
    }
    private void getdatauser(String x){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("user");
        final Query email = ref.orderByChild("email").equalTo(x);
        email.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataUser newPost = dataSnapshot.getValue(DataUser.class);
                System.out.println("AAA ok");
                    try{
                        findfinish(email);
                    }catch (Exception ex){
                        alert("loi");
                    }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                alert("AAA Loi");
            }
        });
    }
    private void findfinish(Query email){
        email.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                DataUser newPost = dataSnapshot.getValue(DataUser.class);
                String a=newPost.email;
                String b=newPost.name;
                String c=newPost.key;
                findfriend(a,b,c);
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

    final void findfriend(final String name, final String email, final String key){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = frag2.this.getLayoutInflater();
        View mView = inflater.inflate(R.layout.timthayban, null);
        viewname=(TextView) mView.findViewById(R.id.textViewName);
        viewemail=(TextView) mView.findViewById(R.id.textViewEmail);

        viewname.setText(name);
        viewemail.setText(email);
        alertDialog.setTitle("Kết bạn");
        alertDialog.setView(mView);
        alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addfiend(email, key);
            }
        });
        alertDialog.setPositiveButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.create();
        alertDialog.show();
    }
    private void alert(String x){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = frag2.this.getLayoutInflater();
        alertDialog.setTitle(x);
        alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.setPositiveButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.create();
        alertDialog.show();
    }
    private void addfiend(String emailFiend, final String keyfriend){
        String myemail="";

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            myemail = user.getEmail();
        }
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("user");

        final Query email = ref.orderByChild("email").equalTo(myemail);
        email.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                DataUser newPost = dataSnapshot.getValue(DataUser.class);

                String c=newPost.key;
                // chọn đến node friend
                DatabaseReference refFriend = database.getReference("friend");
                refFriend.child(c).push().setValue(keyfriend);
                refFriend.child(keyfriend).push().setValue(c);
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
    private void showListFriend(){

    }
}
