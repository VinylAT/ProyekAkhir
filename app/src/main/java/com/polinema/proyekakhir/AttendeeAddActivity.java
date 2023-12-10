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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AttendeeAddActivity extends AppCompatActivity {
    private EditText editText_nama, editText_NI;
    private DatabaseReference databaseAtendee;
    private ListView listViewAttendee;
    private List<Attendee> listAttendee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_add);
        editText_nama = findViewById(R.id.editTextNama);
        editText_NI = findViewById(R.id.editTextNI);
        databaseAtendee = FirebaseDatabase.getInstance().getReference("attendee");
        listViewAttendee = findViewById(R.id.listView_attendee);
        listAttendee = new ArrayList<>();
        listViewAttendee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Mengambil biodata dari listview yang diklik
                Attendee attendee = listAttendee.get(position);
                //Ambil ID
                String attendeeID = attendee.getAttendee_ID();
                //Kirim ID melalui intent ke MainActivity2
                Intent intent = new Intent(AttendeeAddActivity.this, AttendeeEditActivity.class);
                intent.putExtra("AttendeeID", attendeeID);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        databaseAtendee.addValueEventListener(new ValueEventListener() {
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
                listview_attendee biodatalistAdapater = new listview_attendee(AttendeeAddActivity.this, listAttendee);
                listViewAttendee.setAdapter(biodatalistAdapater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addAttendee(View view) {
        String nama = editText_nama.getText().toString();
        String NI = editText_NI.getText().toString();

        if(!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(NI)){
            //Generate ID
            String id = databaseAtendee.push().getKey();
            // Buat objek biodata dengan semua parameternya
            Attendee biodata = new Attendee(id, nama, NI);
            //Tambahkan biodata ke database
            databaseAtendee.child(id).setValue(biodata)
                    .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Jika proses penambahan data sukses maka dikosongkan
                            editText_nama.setText("");
                            editText_NI.setText("");
                            //Toast popup
                            Toast.makeText(AttendeeAddActivity.this, "Attendee Added",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Please Fill all forms", Toast.LENGTH_LONG).show();
        }
    }
    // ToDo : Done, debug imminent
}
