package com.tcs.remindmeapplication.beans;

/**
 * Created by 977975 on 10/20/2015.
 */
public class RM_LoginBean {

    int user_id;
    String username;
    String profileImage;
    String password;
    String dob;
    String email;

    public RM_LoginBean(String password, String profileImage, String username, String dob, String email) {
        this.password = password;
        this.profileImage = profileImage;
        this.username = username;
        this.dob = dob;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RM_LoginBean()
    {
        this.profileImage = "profileImage";
        this.username = "User name";
    }

    public RM_LoginBean( String password, String profileImage, String username , String dob) {
        this.password = password;
        this.profileImage = profileImage;
        this.username = username;
        this.dob = dob;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
