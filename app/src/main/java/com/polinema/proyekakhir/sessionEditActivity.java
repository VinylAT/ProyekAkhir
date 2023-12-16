package com.polinema.proyekakhir;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class sessionEditActivity extends AppCompatActivity {
//  Todo : Implement Camera here
    private EditText editText_Title, editText_Duration;
    private DatabaseReference databaseSession;
    private DatabaseReference databaseAttendee;
    private String currentSessionID;
    private ListView listViewAttendee;
    private List<Attendee> listAttendee;
    private List<String> selectedAttendee;
    private List<Attendee> attendeeSelectedTrue;
    /*private void displaySelectedAttendeeNames(List<String> selectedAttendee) {
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
    }*/
    private void fetchAttendeesforCurrentSession(Session currentSession){
        databaseAttendee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAttendee.clear();
                //Loop menampilkan semua data dari database
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    //Masuk ke objek biodata
                    Attendee attendee = postSnapshot.getValue(Attendee.class);
                    if (attendee.getSessionPresence()!= null &&
                        attendee.getSessionPresence().containsKey(currentSession.getSession_ID()) &&
                        attendee.getSessionPresence().get(currentSession.getSession_ID())) {
                        listAttendee.add(attendee);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_edit);
        editText_Title = findViewById(R.id.editText_Title);
        editText_Duration = findViewById(R.id.editText_Duration);
        currentSessionID = getIntent().getStringExtra("SessionID");
        databaseSession = FirebaseDatabase.getInstance().getReference("session").child(currentSessionID);
        databaseAttendee = FirebaseDatabase.getInstance().getReference("attendee");
        listViewAttendee = findViewById(R.id.listViewAttendee);
        listAttendee = new ArrayList<>();
        selectedAttendee = new ArrayList<>();
        attendeeSelectedTrue = new ArrayList<>();
        // Method to select the attendee
        /*
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
                //displaySelectedAttendeeNames(selectedAttendee);
            }
        }); */
    }
    @Override
    protected void onStart(){
        super.onStart();
        // This function below is writes into editText for the Session selected before
        databaseSession.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //Masuk ke objek biodata
                    Session session = snapshot.getValue(Session.class);
                    //Cek apakah data kosong
                    if (session != null){
                        //tampilkan semua data dari firebase
                        String title = session.getSession_nama();
                        String duration = session.getDuration();
                        editText_Title.setText(title);
                        editText_Duration.setText(duration);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        databaseSession.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Session session = snapshot.getValue(Session.class);
                listAttendee = session.getAttendeeList();
                listview_attendee attendeelistAdapater = new listview_attendee(sessionEditActivity.this, listAttendee);
                attendeelistAdapater.setSpecSessionID(currentSessionID);
                listViewAttendee.setAdapter(attendeelistAdapater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });
}}