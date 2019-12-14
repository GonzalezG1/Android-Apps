package com.example.inspirationrewards;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
//all the attributes needed
    //this should be done!
    private String username = "";
    private String password = "";
    private String firstname = "";
    private String lastname = "";
    private String department = "";
    private String position = "";
    private String story = "";
    private boolean admin = false;
    private int points = 0;
    private List<Reward> rewardList;
    private byte[] photo;
    private String location;

    public User(String u, String p, String f, String l, String d, String pos, String s, boolean a, int pts, byte[] ph, String loc, List<Reward> rl
    ){
        username = u;
        password = p;
        firstname = f;
        lastname = l;
        department = d;
        position = pos;
        story = s;
        admin = a;
        points = pts;
        photo = ph;
        location = loc;
        rewardList = rl;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public String getStory() {
        return story;
    }

    public boolean isAdmin(){
        return admin;
    }

    public int getPoints(){
        return points;
    }

    public List<Reward> getRewardList(){
        return rewardList;
    }

    public byte[] getPhoto(){
        return photo;
    }

    public String getLocation() {
        return location;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public void setAdmin(boolean admin){
        this.admin = admin;
    }

    public void setPoints(int points){
        this.points = points;
    }

    public void setPhoto(byte[] photo){
        this.photo = photo;
    }

    public void setRewardList(List<Reward> rewardList) {
        this.rewardList = rewardList;
    }

    public void setLocation(String location){
        this.location = location;
    }
}
