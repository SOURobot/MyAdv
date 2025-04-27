package com.example.myadv.post;

public class Post {
    private int id;
    private String username;
    private String date;
    private String header;
    private String info;
    private String contact;
    private String category;

    public Post(int id, String username, String date, String header, String info, String contact, String category) {
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