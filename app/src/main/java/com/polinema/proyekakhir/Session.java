package com.polinema.proyekakhir;

import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session {
    private String session_ID;
    private String session_nama;
    private String duration;
    private boolean isPresent;
    private Map<String, Attendee> attendeeList = new HashMap<>();
    private List<Attendee> listAttendee;
    public Session(){}
    public Session(String ID, String title, String duration){
        this.session_nama = title;
        this.session_ID = ID;
        this.duration = duration;
    }
    public void addAttendee(Attendee attendee){
        // This function adds the session_ID for this session to the Attendee registered.
        String ids = attendee.getAttendee_ID();
        attendeeList.put(ids, attendee);
        attendee.markPresence(session_ID, false);
        // Also by default set the attendance isPresent status to false
    }
    public void addAttendee(List<Attendee> attendees){
        for (Attendee attendee : attendees){
            String ids = attendee.getAttendee_ID();
            attendeeList.put(ids, attendee);
            attendee.markPresence(session_ID, false);
        }
    }
    public void markAttendeePresence(Attendee attendee){
        if (attendee.isPresentInSession(session_ID) && attendee.isPresent()){
            attendee.markPresence(session_ID, false);
            attendee.setPresent(false);
        } else {
            attendee.markPresence(session_ID, true);
            attendee.setPresent(true);
        }
    }
    public List<Attendee> getAttendeeList() {
        listAttendee = new ArrayList<>(attendeeList.values());
        return listAttendee;
    }
/*    public boolean isPresent() {
        return isPresent;
    }
    public void setPresent(boolean present) {
        isPresent = present;
    } */
    public String getSession_nama() {
        return session_nama;
    }

    public String getDuration() {
        return duration;
    }

    public String getSession_ID() {
        return session_ID;
    }

    // Todo: done for now, Debug left
}
