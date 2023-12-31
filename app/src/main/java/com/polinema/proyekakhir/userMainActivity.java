package com.polinema.proyekakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class userMainActivity extends AppCompatActivity {
    private ListView listViewSession;
    private DatabaseReference databaseSession;
    private List<Session> listSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        listViewSession = findViewById(R.id.listview_session);
        databaseSession = FirebaseDatabase.getInstance().getReference("session");
        listSession = new ArrayList<>();
        listViewSession.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Session session = listSession.get(position);
                String sessionID = session.getSession_ID();
                Intent intent = new Intent(userMainActivity.this, sessionEditActivity.class);
                intent.putExtra("SessionID", sessionID);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        databaseSession.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listSession.clear();
                //Loop menampilkan semua data dari database
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    //Masuk ke objek biodata
                    Session session = postSnapshot.getValue(Session.class);
                    //Simpan ke list
                    listSession.add(session);
                }
                listview_session sessionlistAdapater = new listview_session(userMainActivity.this, listSession);
                listViewSession.setAdapter(sessionlistAdapater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void toAttendeeList(View view) {
        Intent intent = new Intent(userMainActivity.this, AttendeeAddActivity.class);
        startActivity(intent);
    }

    public void addSession(View view) {
        Intent intent = new Intent(userMainActivity.this, sessionAddActivity.class);
        startActivity(intent);
    }
    // Todo : Done? debugging
}