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
import android.widget.Toast;

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

public class frag2 extends Fragment{
    private Button btn_themban;
    private View view;
    private EditText txtname;
    private TextView viewname, viewemail;
    private FirebaseAuth mAuth;
    private String myemail="";
    private String mykey="";
    DatabaseReference reffriend, refuser;
    private ListView listView;
    FirebaseDatabase database;
    private ArrayList<String> nameArray = new ArrayList<>();
    private ArrayList<String> emailArray=new ArrayList<>();
    private ArrayList<String> listKey = new ArrayList<>();
    private ArrayList<String> infoArray = new ArrayList<>();
    private Integer[] imageArray = {R.drawable.user,
            R.drawable.user
    };
    private CustomListAdapter everdata;


    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view =inflater.inflate(R.layout.fragment2, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.reset);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "on reset", Toast.LENGTH_SHORT).show();
                onReset();
            }
        });

        btn_themban=(Button)view.findViewById(R.id.btn_thembann);

        btn_themban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();
            }
        });
        database = FirebaseDatabase.getInstance();
        refuser = database.getReference("user");
        reffriend = database.getReference("friend");
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            myemail = user.getEmail();
        }else{
            alert("Có lỗi");
        }
        System.out.println("AAA bắt đầu frag2");
        cleardata();
        getMyEmail();
        return view;
    }
    private void getMyEmail(){

        final Query getkey = refuser.orderByChild("email").equalTo(myemail);
        getkey.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                DataUser user = dataSnapshot.getValue(DataUser.class);
                mykey=user.getKey();
                queryFriend(mykey);
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
    private void queryFriend(String mykey){
        Query friend = reffriend.child(mykey);
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
//                    convertName(value);
                    listKey.add(value);
                }
                readListKey();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    private void readListKey(){
        System.out.println("AAA listkey:"+listKey);
        for (String key:listKey) {
            convertName(key);
        }
    }
    private void convertName(String key2){
        System.out.println("AAA"+"convert name");
        System.out.println("AAA key2:"+key2);
        Query findname = refuser.child(key2);
        findname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("AAA co chay vao day khong");
                //vòng lặp để lấy dữ liệu khi có sự thay đổi trên Firebase
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    System.out.println("AAA data"+data);
                    if(data.getKey().equals("name")){
                        System.out.println("AAA kavlue:"+data.getValue());

                    }
//                    DataUser user = data.getValue(DataUser.class);
//                    String name = user.getName();
//                    String emailfriend = user.getEmail();
//                    emailArray.add(emailfriend);
//                    nameArray.add(name);
//                    infoArray.add("test");
//                    System.out.println("AAA" + name);
//                    showdatafriend();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }

    private void showdatafriend(){
        everdata = new CustomListAdapter(getActivity(), nameArray, infoArray, imageArray);
        listView = (ListView)view.findViewById(R.id.listViewFriend);
        listView.setAdapter(everdata);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                String message = emailArray.get(position);
                intent.putExtra("email", message);
                startActivity(intent);
            }

        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                final String message = listKey.get(position);
                String name=nameArray.get(position);
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Xóa "+name+"/n"+message+" khỏi danh sách bạn bè?");
                alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deletefriend(message);
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
                return true;
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

    final void findfriend(final String email, final String name, final String key){
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
                checkfriend(email, key);

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

    private void cleardata(){
        listKey.clear();
        nameArray.clear();
        emailArray.clear();
        infoArray.clear();
    }
    private void checkfriend(String email, String key){
        int i=0;
        System.out.println("AAA email friend:" +email);
        for (String x:emailArray) {
            System.out.println("AAA emailArray:"+x);
            if(email.equals(x)){
                i++;
            }
        }
        if(i!=0){
            alert("Đã có trong danh sách bạn bè");
        }
        else if(email.equals(myemail)){
            Toast.makeText(getActivity(), "Không thể tự kết bạn với bản thân", Toast.LENGTH_SHORT).show();
        }else{
            addfriend(key);
        }
    }
    private void addfriend(final String keyfriend){
        String myemail="";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            myemail = user.getEmail();
        }else{
            alert("Có lỗi");
        }
        // chọn đến node friend
        // push len 2 node cua user+friend
        reffriend.child(mykey).push().setValue(keyfriend);
        //push len node cua friend
        psuhFriend(keyfriend);
        System.out.println("AAA ket ban xong, reset");
        cleardata();
        getMyEmail();
    }
    private void psuhFriend(final String keyfriend) {

        Query pusfriend = reffriend.child(keyfriend).orderByChild(mykey);
        pusfriend.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int b = 0;
                System.out.println("AAA data:" + dataSnapshot);
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String a = (String) data.getValue();
                    if (a.equals(mykey)) {
                        b++;
                    }
                }
                if (b == 0) {
                    reffriend.child(keyfriend).push().setValue(mykey);
                    onReset();
                } else {
                    System.out.println("AAA"+"Đã có trong list bạn bè");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void deletefriend(final String keydelete){
        System.out.println("AAA key delete: "+keydelete);
        Query delete = reffriend.child(mykey).orderByChild(keydelete);
        delete.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("AAA data:" +dataSnapshot);
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    String a= (String) data.getValue();
                    if(a.equals(keydelete)){
                        data.getRef().removeValue();
                        Toast.makeText(getActivity(), "Đã xóa khỏi danh sách bạn bè", Toast.LENGTH_SHORT).show();

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        onReset();
    }

    private void onReset(){
        swipeRefreshLayout.setRefreshing(false);
//                Intent intent = new Intent(getActivity(), getContext().getClass());
//                startActivity(intent);
        cleardata();
        getMyEmail();
    }
}
