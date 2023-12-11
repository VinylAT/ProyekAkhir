package com.polinema.proyekakhir;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class listview_attendee extends ArrayAdapter {
    // ToDo : Done, Debug this
    private Activity context;
    private String SessionIDtoCheck;
    List<Attendee> list_attendee;
    public listview_attendee(Activity context, List<Attendee> attendeeArray){
        super(context, R.layout.layout_listview_attendee, attendeeArray);
        this.context = context;
        this.list_attendee = attendeeArray;
    }
    public void setSpecSessionID(String SessionID){
        this.SessionIDtoCheck = SessionID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_listview_attendee, null, true);

        TextView TextViewNama = listViewItem.findViewById(R.id.textViewNama);
        TextView TextViewNI = listViewItem.findViewById(R.id.textViewUmur);
        TextView TextViewisPresent = listViewItem.findViewById(R.id.textViewJK);
        Attendee attendee = list_attendee.get(position);
        TextViewNama.setText(attendee.getAttendee_Nama());
        TextViewNI.setText(attendee.getAttendee_NI());
        boolean isPresent = attendee.isPresentInSession(SessionIDtoCheck);
        if (isPresent) {
            TextViewisPresent.setText("Hadir");
        } else {
            TextViewisPresent.setText(" ");
        }

        return listViewItem;
    }
}

