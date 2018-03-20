package com.tcs.remindmeapplication.beans;

import java.io.Serializable;

/**
 * Created by 977975 on 10/20/2015.
 */
public class RM_FriendBean implements Serializable {

    int friend_id;
    int user_id;
    String friend_name;
    String nickname;
    String phoneNumber;
    String email;
    String address;
    String friend_image;


    public RM_FriendBean() {
        this.friend_name = "Name";
    }

    public RM_FriendBean(String address, String nickname, String name, String friend_image, String email, String phoneNumber, int user_id) {
        this.address = address;
        this.nickname = nickname;
        this.friend_name = name;
        this.friend_image = friend_image;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.user_id = user_id;
        //
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public String getFriend_image() {
        return friend_image;
    }



    public void setFriend_image(String friend_image) {
        this.friend_image = friend_image;
    }

    public String getName() {
        return friend_name;
    }

    public void setName(String name) {
        this.friend_name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
