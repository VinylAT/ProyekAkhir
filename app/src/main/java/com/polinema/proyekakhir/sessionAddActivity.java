package com.polinema.proyekakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class sessionAddActivity extends AppCompatActivity {
/* Todo : Debug*/
private EditText editText_Title, editText_Duration;
    private DatabaseReference databaseSession, databaseAttendee;
    private ListView listViewAttendee;
    private List<Attendee> listAttendee;
    private List<String> selectedAttendee;
    private List<Attendee> attendeeSelectedTrue;
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
        setContentView(R.layout.activity_session_add);
        editText_Title = findViewById(R.id.editTitle);
        editText_Duration = findViewById(R.id.editDuration);
        databaseSession = FirebaseDatabase.getInstance().getReference("session");
        databaseAttendee = FirebaseDatabase.getInstance().getReference("attendee");
        listViewAttendee = findViewById(R.id.listView_attendee);
        listAttendee = new ArrayList<>();
        selectedAttendee = new ArrayList<>();
        attendeeSelectedTrue = new ArrayList<>();
        // Method to select the attendee
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
                listview_attendee attendeelistAdapater = new listview_attendee(sessionAddActivity.this, listAttendee);
                listViewAttendee.setAdapter(attendeelistAdapater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // The entire add Session function below adds the session to firebase
    // Todo : This entire section is wack and I need to resolve it? I guess
    public void addSession(View view) {
        String title = editText_Title.getText().toString();
        String duration = editText_Duration.getText().toString();

        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(duration)){
            //Generate ID untuk session
            String id = databaseSession.push().getKey();
            // Buat objek biodata dengan semua parameternya
            Session session = new Session(id, title, duration);
            session.addAttendee(attendeeSelectedTrue);
            /* Todo: addAttendee with a list should make sense but something feels off
                Try to recheck
            */
            //Tambahkan biodata ke database
            databaseSession.child(id).setValue(session)
                    .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Jika proses penambahan data sukses maka dikosongkan
                            editText_Title.setText("");
                            editText_Duration.setText("");
                            //Toast popup
                            Toast.makeText(sessionAddActivity.this, "Event added!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Please Fill all forms", Toast.LENGTH_LONG).show();
        }
    }
}