package edu.java.model;

public class FriendManagement {

    public interface Entity{
        String TBL_MANAGEDFRIEND = "MANAGEDFRIEND";
        String COL_FRIEND_ID = "FRIEND_ID";
    }
    
    private String userId;
    private String friendId;
    
    public FriendManagement(String userId, String friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public String getUserId() {
        return userId;
    }

    public String getFriendId() {
        return friendId;
    }
    
    @Override
    public String toString() {
        return String.format("FriendManagement(userId=%s, friendId=%s", userId, friendId);
    }
    
}
