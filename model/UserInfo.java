package edu.java.model;

public class UserInfo {

    public interface Entity{
        String TBL_MEMBER = "MEMBER";
        String COL_ID = "ID";
        String COL_PASSWORD = "PASSWORD";
        String COL_NAME = "NAME";
        String COL_PHONE_NUM = "PHONE_NUM";
        String COL_EMAIL = "EMAIL";
    }
    
    // field
    private String userId;
    private String password;
    private String name;
    private String phoneNum;
    private String email;
    
    public UserInfo(String userId, String password, String name, String phoneNum, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    public UserInfo(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }
    
    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getEmail() {
        return email;
    }
    
    @Override
    public String toString() {
          return String.format("userInfo(userId=%s, password=%s, name=%s, phone=%s, email=%s)", 
                  userId, password, name, phoneNum, email);
    }
    
}
