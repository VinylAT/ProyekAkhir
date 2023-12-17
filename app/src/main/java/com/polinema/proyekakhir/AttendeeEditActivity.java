package com.polinema.proyekakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AttendeeEditActivity extends AppCompatActivity {
// Todo : Add declaration, and the edit functions
    private EditText editText_Name, editText_NI;
    private DatabaseReference databaseAttendee;
    private String currentAttendeeID;
    private ListView listViewAttendee;
    private List<Attendee> listAttendee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_edit);
        editText_Name = findViewById(R.id.nameEdit);
        editText_NI = findViewById(R.id.niEdit);
        currentAttendeeID = getIntent().getStringExtra("AttendeeID");
        databaseAttendee = FirebaseDatabase.getInstance().getReference("attendee").child(currentAttendeeID);
    }
    @Override
    protected void onStart(){
        super.onStart();
        databaseAttendee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Attendee attendee = snapshot.getValue(Attendee.class);
                if (attendee != null){
                    String nama = attendee.getAttendee_Nama();
                    String NI = attendee.getAttendee_NI();
                    editText_Name.setText(nama);
                    editText_NI.setText(NI);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void buttonupdateAttendee(View view) {
        String nama = editText_Name.getText().toString();
        String ni = editText_NI.getText().toString();
        if (!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(ni)){
            Attendee attendee = new Attendee(currentAttendeeID, nama, ni);
            databaseAttendee.setValue(attendee).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    // Toast popup
                    Toast.makeText(AttendeeEditActivity.this, "Attendee data updated", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        } else {
            Toast.makeText(this, "Please fill all forms", Toast.LENGTH_LONG).show();
        }
    }

    public void buttondeleteAttendee(View view) {
        databaseAttendee.removeValue().addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AttendeeEditActivity.this, "Attendee removed", Toast.LENGTH_LONG).show();
            }
        });
    }
}