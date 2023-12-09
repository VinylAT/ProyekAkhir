package com.polinema.proyekakhir;

public class Student {
    private String student_Nama;
    private String student_ID;
    private String student_NIM;
    private Boolean isPresent;

    public void Student(){}
    public void Student (String nama, String ID, String NIM){
        this.student_Nama = nama;
        this.student_ID = ID;
        this.student_NIM = NIM;
    }

    public String getStudent_ID() {
        return student_ID;
    }

    public String getStudent_Nama() {
        return student_Nama;
    }

    public String getStudent_NIM() {
        return student_NIM;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
}
