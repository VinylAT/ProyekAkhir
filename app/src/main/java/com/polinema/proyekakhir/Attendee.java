package com.polinema.proyekakhir;

import java.util.HashMap;
import java.util.Map;

public class Attendee {
    private String attendee_Nama;
    private String attendee_ID;
    private String attendee_NI;
    private Boolean isPresent;
    private Map<String, Boolean> sessionPresence;

    public Attendee(String id, String nama, String NI){
        this.attendee_Nama = nama;
        this.attendee_ID = id;
        this.attendee_NI = NI;
        this.sessionPresence = new HashMap<>();
    }
    public Attendee(){}
    public void markPresence(String sessionId, boolean isPresent) {
        sessionPresence.put(sessionId, isPresent);
    }
    public boolean isPresentInSession(String sessionId) {
        return sessionPresence.containsKey(sessionId) ? sessionPresence.get(sessionId) : false;
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
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    // Todo: Done, Debug it
}
