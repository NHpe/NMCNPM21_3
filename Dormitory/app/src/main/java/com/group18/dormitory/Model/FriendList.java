package com.group18.dormitory.Model;

import java.util.ArrayList;

public class FriendList {
    private String userId;
    private ArrayList<String> friendsId;
    private ArrayList<String> friendRequestId;

    public FriendList() {
        friendsId = new ArrayList<>();
        friendRequestId = new ArrayList<>();
    }

    public FriendList(String userId) {
        this.userId = userId;
        friendsId = new ArrayList<>();
        friendRequestId = new ArrayList<>();
    }

    public ArrayList<String> getFriendRequestId() {
        return friendRequestId;
    }

    public void setFriendRequestId(ArrayList<String> friendRequestId) {
        this.friendRequestId = friendRequestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getFriendsId() {
        return friendsId;
    }

    public void setFriendsId(ArrayList<String> friendsId) {
        this.friendsId = friendsId;
    }
}
