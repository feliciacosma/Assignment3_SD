package com.example.demo.model;

import java.util.Observable;

public class StudentUpdate extends Observable {

    private Integer studentId;
    private String username;
    private String password;

    public Student buildStudent() {
        return Student.builder()
                .studentId(studentId)
                .username(username)
                .password(password)
                .build();
    }

    public void setStudentId(Integer id) {
        this.studentId = id;
        fireChange();
    }

    public void fireChange() {
        setChanged();
        System.out.println("Student updated");
        notifyObservers(buildStudent());
    }
}