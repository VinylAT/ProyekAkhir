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

public class listview_session extends ArrayAdapter {
    //Todo : Done, Debug it
    private Activity context;
    private String SessionIDtoCheck;
    List<Session> list_Session;
    public listview_session(Activity context, List<Session> SessionArray){
        super(context, R.layout.layout_listview_session, SessionArray);
        this.context = context;
        this.list_Session = SessionArray;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_listview_session, null, true);

        TextView TextViewTitle = listViewItem.findViewById(R.id.textViewTitle);
        TextView TextViewDuration = listViewItem.findViewById(R.id.textViewDuration);
        Session session = list_Session.get(position);
        TextViewTitle.setText(session.getSession_nama());
        TextViewDuration.setText(session.getDuration());
        return listViewItem;
    }
}

