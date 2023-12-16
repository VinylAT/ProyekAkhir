package com.polinema.proyekakhir;

import java.util.HashMap;
import java.util.Map;

public class Attendee {
    private String attendee_Nama;
    private String attendee_ID;
    private String attendee_NI;
    private Boolean is_Present;
    private Map<String, Boolean> sessionPresence = new HashMap<>();

    public Attendee(String id, String nama, String NI){
        this.attendee_Nama = nama;
        this.attendee_ID = id;
        this.attendee_NI = NI;
    }
    public Attendee(){}
    public void markPresence(String sessionId, boolean isPresent) {
        sessionPresence.put(sessionId, isPresent);
    }
    public boolean isPresentInSession(String sessionId) {
        return sessionPresence!= null && sessionPresence.containsKey(sessionId) && sessionPresence.get(sessionId);
    }
    public Map<String, Boolean> getSessionPresence() {
        return sessionPresence;
    }
    public void setSessionPresence(Map<String, Boolean> sessionPresence) {
        this.sessionPresence = sessionPresence;
    }

    public String getAttendee_ID() {
        return attendee_ID;
    }

    public String getAttendee_Nama() {
        return attendee_Nama;
    }

    public String getAttendee_NI() {
        return attendee_NI;
    }

    public boolean isPresent() {
        return is_Present != null && is_Present.booleanValue(); // Check for null before invoking booleanValue()
    }

    public void setPresent(boolean present) {
        is_Present = present;
    }

    // Todo: Done, Debug it
}
