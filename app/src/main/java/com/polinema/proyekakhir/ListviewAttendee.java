package com.polinema.proyekakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ListviewAttendee extends AppCompatActivity {
    private ListView listViewAttendee;
    private List<Attendee> listAttendee;
    private DatabaseReference databaseAttendee;
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
        setContentView(R.layout.activity_listview_attendee);
        listViewAttendee = findViewById(R.id.soloListview);

    }
}