package com.example.inspirationrewards;

import java.io.Serializable;

public class Reward implements Serializable {

    //this should be done
    //do not touch
    private String username = "";
    private String senderName = "";
    private String comment = "";
    private String date = "";
    private int amount = 0;

    public Reward( int a, String u, String s, String c, String d) {

        username = u;
        senderName = s;
        comment = c;
        date = d;
        amount = a;
    }
    public int getAmount() {
        return amount;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getComment() {
        return comment;
    }

    public String getDate(){
        return date;
    }

    public String getUsername() {
        return username;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    //done
}
