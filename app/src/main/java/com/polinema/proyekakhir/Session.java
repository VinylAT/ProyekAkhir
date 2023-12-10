package com.polinema.proyekakhir;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private String session_ID;
    private String session_nama;
    private int session_duration;
    private boolean isPresent;
    private List<Attendee> attendeeList;
    public Session(){}
    public Session(String ID, String title, int duration){
        this.session_nama = title;
        this.session_ID = ID;
        this.session_duration = duration;
        this.attendeeList = new ArrayList<>();
    }
    public void addAttendee(Attendee attendee){
        // This function adds the session_ID for this session to the Attendee registered.
        attendeeList.add(attendee);
        attendee.markPresence(session_ID, false);
        // Also by default set the attendance isPresent status to false
    }

    public List<Attendee> getAttendeeList() {
        return attendeeList;
    }
    public boolean isPresent() {
        return isPresent;
    }
    public void setPresent(boolean present) {
        isPresent = present;
    }
    public String getSession_nama() {
        return session_nama;
    }

    public int getDuration() {
        return session_duration;
    }

    public String getSession_ID() {
        return session_ID;
    }

    // Todo: done for now, Debug left
}
