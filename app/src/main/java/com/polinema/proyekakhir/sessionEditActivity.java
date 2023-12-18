package com.polinema.proyekakhir;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class sessionEditActivity extends AppCompatActivity {
//  Todo : Implement Camera here and finish the manage Attendee button
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
   /* private void fetchAttendeesforCurrentSession(Session currentSession){
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
    }*/
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

        listViewAttendee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Mengambil biodata dari listview yang diklik
                Attendee selected = (Attendee) parent.getItemAtPosition(position);
                String name = selected.getAttendee_Nama();
                if (selected.isPresent()){
                    selected.setPresent(false);
                    Toast.makeText(getApplicationContext(), name+" is now not present", Toast.LENGTH_SHORT).show();
                } else {
                    selected.setPresent(true);
                    Toast.makeText(getApplicationContext(), name+" is present", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        databaseSession.child("attendeeList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAttendee.clear();
                //Session session = snapshot.getValue(Session.class);
                //listAttendee = session.getAttendeeList();
                for (DataSnapshot postsnap : snapshot.getChildren()){
                    Attendee attendee = postsnap.getValue(Attendee.class);
                    listAttendee.add(attendee);
                }
                /* ToDo : Changed but sus*/
                listview_attendee attendeelistAdapater = new listview_attendee(sessionEditActivity.this, listAttendee);
                listViewAttendee.setAdapter(attendeelistAdapater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });
}

    public void buttonupdateEvent(View view) {
        String title = editText_Title.getText().toString();
        String duration = editText_Duration.getText().toString();
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(duration)){
            Session session = new Session(currentSessionID, title, duration);
            session.editAttendee(listAttendee);
            databaseSession.setValue(session).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Toast popup
                    Toast.makeText(sessionEditActivity.this, "Event updated", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        } else {
            Toast.makeText(this, "Please fill all forms", Toast.LENGTH_LONG).show();
        }
    }

    public void buttondeleteEvent(View view) {
        databaseSession.removeValue().addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(sessionEditActivity.this, "Event removed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void toPresence(View view) {
    }
}