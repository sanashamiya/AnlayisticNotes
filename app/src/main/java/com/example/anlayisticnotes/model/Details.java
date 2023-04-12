package com.example.anlayisticnotes.model;

public class Details {
    String id;
    String note;
    String details;

    private Details(){}
    public Details(String id, String note, String details) {
        this.id = id;
        this.note = note;
        this.details = details;


    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return note;

    }
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
