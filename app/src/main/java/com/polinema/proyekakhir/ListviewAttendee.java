package com.polinema.proyekakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListviewAttendee extends AppCompatActivity {
    private ListView listViewAttendee;
    private List<Attendee> listAttendee;
    private DatabaseReference databaseSession, databaseAttendee;
    private List<String> selectedAttendee;
    private List<Attendee> attendeeSelectedTrue, ownedAttendee, unownedAttendee;
    private String currentSessionID, currentTitle, currentDuration;
    private void displaySelectedAttendeeNames(List<String> selectedAttendee) {
        // Construct a message with all selected attendee names
        StringBuilder message = new StringBuilder("Selected Attendees: ");
        for (String name : selectedAttendee) {
            message.append(name).append(", ");
        }

        // Remove the trailing comma and space from the message
        if (!selectedAttendee.isEmpty()) {
            message.delete(message.length() - 2, message.length());
        }

        // Show the Toast message with the selected attendee names
        Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_attendee);
        listViewAttendee = findViewById(R.id.soloListview);
        listAttendee = new ArrayList<>();
        selectedAttendee = new ArrayList<>();
        attendeeSelectedTrue = new ArrayList<>();
        unownedAttendee = new ArrayList<>();
        ownedAttendee = new ArrayList<>();
        currentSessionID = getIntent().getStringExtra("SessionID");
        currentTitle = getIntent().getStringExtra("SessionTitle");
        currentDuration = getIntent().getStringExtra("SessionDuration");
        databaseAttendee = FirebaseDatabase.getInstance().getReference("attendee");
        databaseSession = FirebaseDatabase.getInstance().getReference("session").child(currentSessionID);
        listViewAttendee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Mengambil biodata dari listview yang diklik
                Attendee selected = (Attendee) parent.getItemAtPosition(position);
                //Ambil
                // Retrieve the name of the selected attendee
                String selectedAttendeeName = selected.getAttendee_Nama(); // Replace getName() with your method to get attendee name

                // Check if the attendee is already selected or not
                if (selectedAttendee.contains(selectedAttendeeName)) {
                    // If already selected, remove it from the list
                    selectedAttendee.remove(selectedAttendeeName);
                    attendeeSelectedTrue.remove(selected);
                } else {
                    // If not selected, add it to the list
                    selectedAttendee.add(selectedAttendeeName);
                    attendeeSelectedTrue.add(selected);
                }
                displaySelectedAttendeeNames(selectedAttendee);
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
/*        databaseSession.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Masuk ke objek biodata
                Session session = snapshot.getValue(Session.class);
                //Cek apakah data kosong
                if (session != null){
                    currentTitle = session.getSession_nama();
                    currentDuration = session.getDuration();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });*/
        databaseSession.child("attendeeList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Masuk ke objek biodata
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    //Masuk ke objek biodata
                    Attendee attendee = postSnapshot.getValue(Attendee.class);
                    //Simpan ke list
                    ownedAttendee.add(attendee);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // This function below is to show the list of attendees in this layout
        databaseAttendee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAttendee.clear();
                //Loop menampilkan semua data dari database
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    //Masuk ke objek biodata
                    Attendee attendee = postSnapshot.getValue(Attendee.class);
                    //Simpan ke list
                    listAttendee.add(attendee);
                }

                for (Attendee attendee: listAttendee){
                    if (ownedAttendee.contains(attendee)){
                        unownedAttendee.add(attendee);
                    }
                }
                listview_attendee attendeelistAdapater = new listview_attendee(ListviewAttendee.this, listAttendee);
                listViewAttendee.setAdapter(attendeelistAdapater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onStop(){
        super.onStop();
        Session session = new Session(currentSessionID, currentTitle,currentDuration);
        session.addAttendee(attendeeSelectedTrue);
        for (Attendee attendee : attendeeSelectedTrue) {
            if (ownedAttendee.contains(attendee)){
                ownedAttendee.remove(attendee);
            }
        }
        session.addAttendee(ownedAttendee);
        databaseSession.setValue(session);
    }

}