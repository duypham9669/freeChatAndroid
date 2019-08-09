package com.example.chatapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class GetuserActivity extends AppCompatActivity {
    private TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getuser);
        name=(TextView)findViewById(R.id.testname);

        UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
        
        // See the UserRecord reference doc for the contents of userRecord.

        System.out.println("Successfully fetched user data: " + userRecord.getEmail());
    }
}
