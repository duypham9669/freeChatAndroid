package com.example.chatapp2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class frag2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private Button btn_themban;
    private View view;
    private EditText txtname;
    private TextView viewname, viewemail;
    private FirebaseAuth mAuth;
    private String myemail="";
    private String mykey="";
    DatabaseReference myRef, myRef2;
    private ListView listView;
    private ArrayList<String> nameArray = new ArrayList<>();
    private ArrayList<String> listKey = new ArrayList<>();

    //    String[] nameArray = {"Octopus","Pig","Sheep","Rabbit","Snake","Spider" };
    private ArrayList<String> infoArray = new ArrayList<>();

    Integer[] imageArray = {R.drawable.user,
            R.drawable.user
    };

    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view =inflater.inflate(R.layout.fragment2, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.reset);
        swipeRefreshLayout.setOnRefreshListener(this);

        btn_themban=(Button)view.findViewById(R.id.btn_thembann);

        btn_themban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef2 = database.getReference("user");
        myRef = database.getReference("friend");
        mAuth = FirebaseAuth.getInstance();
        getMyEmail();

        return view;
    }
    private void getMyEmail(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            myemail = user.getEmail();
        }
        final Query getkey = myRef2.orderByChild("email").equalTo(myemail);
        getkey.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                DataUser user = dataSnapshot.getValue(DataUser.class);

                queryFriend(user.getKey());
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
                System.out.println("AAA ok");
                if(dataSnapshot.getValue()==null){
                    alert("Không tìm thấy");
                }else{
                    findfinish(email);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                System.out.println("AAA Loi");
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
                // push len 2 node cua user+friend

                refFriend.child(c).push().setValue(keyfriend);
                refFriend.child(keyfriend).push().setValue(c);
                cleardata();
                reload();
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

    private void queryFriend(String key){
        Query friend = myRef.child(key);
        friend.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //vòng lặp để lấy dữ liệu khi có sự thay đổi trên Firebase
                for (DataSnapshot data: dataSnapshot.getChildren())
                {
                    //lấy key của dữ liệu
                    String key=data.getKey();
                    //lấy giá trị của key (nội dung)
                    String value=(String)data.getValue();
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
    private void showdatafriend(){
        CustomListAdapter whatever = new CustomListAdapter(getActivity(), nameArray, infoArray, imageArray);
        listView = (ListView)view.findViewById(R.id.listViewFriend);
        listView.setAdapter(whatever);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                String message = nameArray.get(position);
                intent.putExtra("email", message);
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

    private void reload() {
            Intent intent = getActivity().getIntent();
            startActivity(intent);
    }
    private void cleardata(){
        listKey.clear();
        nameArray.clear();
    }

    public void onRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        refreshList();
    }
    private void refreshList(){
        //do processing to get new data and set your listview's adapter, maybe  reinitialise the loaders you may be using or so
        //when your data has finished loading, cset the refresh state of the view to false
        swipeRefreshLayout.setRefreshing(false);

    }
}
