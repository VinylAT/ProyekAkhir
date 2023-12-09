package com.polinema.proyekakhir;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private String session_ID;
    private String session_nama;
    private String session_owner;
    private int duration;
    private List<Student> students;
    public Session(String title, int duration){
        this.session_nama = title;
        this.duration = duration;
        this.students = new ArrayList<>();
    }

}
