package com.polinema.proyekakhir;

import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private String session_ID;
    private String session_nama;
    private String duration;
    private boolean isPresent;
    private List<Attendee> attendeeList;
    public Session(){}
    public Session(String ID, String title, String duration){
        this.session_nama = title;
        this.session_ID = ID;
        this.duration = duration;
        this.attendeeList = new ArrayList<>();
    }
    public void addAttendee(Attendee attendee){
        // This function adds the session_ID for this session to the Attendee registered.
        attendeeList.add(attendee);
        attendee.markPresence(session_ID, false);
        // Also by default set the attendance isPresent status to false
    }
    public void addAttendee(List<Attendee> attendees){
        for (Attendee attendee : attendees){
            attendeeList.add(attendee);
            attendee.markPresence(session_ID, false);
        }
    }
    public void editAttendee(List<Attendee> attendees){
        for (Attendee attendee : attendees){
            attendeeList.add(attendee);
        }
    }
    public List<Attendee> getAttendeeList() {
        return attendeeList;
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
