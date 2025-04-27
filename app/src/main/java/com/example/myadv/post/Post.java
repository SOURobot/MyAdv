package com.example.myadv.post;

import java.io.Serializable;

public class Post implements Serializable {
    private final int id;
    private final String username;
    private final String date;
    private final String header;
    private final String info;
    private final String contact;
    private final String category;

    public Post (int id, String username, String date, String header, String info, String contact, String category) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.header = header;
        this.info = info;
        this.contact = contact;
        this.category = category;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getDate() { return date; }
    public String getHeader() { return header; }
    public String getInfo() { return info; }
    public String getContact() { return contact; }
    public String getCategory() { return category; }
}