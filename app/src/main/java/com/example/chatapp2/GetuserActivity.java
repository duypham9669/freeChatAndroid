package com.example.chatapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class GetuserActivity extends AppCompatActivity {
    private TextView showname, showemail;
    private Button btn_gui, btn_timtaikhoan;
    private EditText editTextTimtaikhoan, namenode, txtname;
    private DatabaseReference myRef;
    private String TAG;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getuser);
        showname=(TextView)findViewById(R.id.showname);
        showemail=(TextView) findViewById(R.id.showemail);
        editTextTimtaikhoan=(EditText) findViewById(R.id.editTextTimtaikhoan);
        btn_gui=(Button) findViewById(R.id.btn_gui);
        btn_timtaikhoan=(Button) findViewById(R.id.btn_timtaikhoan);
        namenode=(EditText) findViewById(R.id.namenode);

        String email="duy4@gmail.com";
        btn_gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatnode();

            }
        });
        btn_timtaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();


            }
        });
        btn_gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                testdialog();
                getdatauser();
            }
        });
    }
    private void creatnode(){
        String nameNode= namenode.getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Kết nối tới node có tên là message (node này do ta định
        myRef = database.getReference("user");

//        myRef.push().child("test 2 tầng").setValue(pe);
        String key="abc";
        String email="test email";
        String name="test name";
        DataUser user=new DataUser(key, email, name);
        myRef.child(email).setValue(user);
        Toast.makeText(this, "da gui", Toast.LENGTH_SHORT).show();

    }
//    private void writeNewUser(String userId, String name, String email) {
//
//        DataUser user = new DataUser(name, email);
//        myRef.child("users").child(userId).setValue(user);
//    }

    private void getdatauser(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("user");

        Query email = ref.orderByChild("email").equalTo("test");
        email.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                DataUser newPost = dataSnapshot.getValue(DataUser.class);
                System.out.println("Author: " + newPost.name);
                System.out.println("Title: " + newPost.email);
                System.out.println("Previous Post ID: " + prevChildKey);
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

    private void testdialog(){
        dialog = new Dialog(GetuserActivity.this);
        dialog.setTitle("test dialog");
        dialog.setContentView(R.layout.timbanbe);
        dialog.show();
    }

    public void alertDialog(){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        LayoutInflater inflater = GetuserActivity.this.getLayoutInflater();
        View mView = inflater.inflate(R.layout.timbanbe, null);
        txtname=(EditText) mView.findViewById(R.id.txtusername);

        alertDialog.setTitle("tim ban be");
        alertDialog.setView(mView);
        alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try{
                    test( txtname.getText().toString());
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

    final void test(String x){
        showname.setText(x);
    }
    }



