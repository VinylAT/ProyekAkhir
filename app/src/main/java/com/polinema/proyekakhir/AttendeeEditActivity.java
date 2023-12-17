package com.polinema.proyekakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AttendeeEditActivity extends AppCompatActivity {
// Todo : Add declaration, and the edit functions
    private EditText editText_Name, editText_NI;
    private DatabaseReference databaseAttendee;
    private String currentSessionID;
    private ListView listViewAttendee;
    private List<Attendee> listAttendee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_edit);
        editText_Name = findViewById(R.id.nameEdit);
        editText_NI = findViewById(R.id.niEdit);

    }
    @Override
    protected void onStart(){
        super.onStart();
        databaseAttendee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}