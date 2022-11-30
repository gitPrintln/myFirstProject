package edu.java.model;

import java.time.LocalDateTime;


public class VisitorsBook {

    public interface Entity {
        String TBL_ADMINPOST = "ADMINPOST";
        String COL_POSTNUM = "POSTNUM";
        String COL_TITLE = "TITLE";
        String COL_CONTENT = "CONTENT";
        String COL_FROM_ID = "FROM_ID";
        String COL_CREATED_TIME = "CREATED_TIME";
        String COL_MODIFIED_TIME = "MODIFIED_TIME";
        String COL_EDITOR_ID = "WRITER";
    }
    
    private Integer postNum;
    private String title;
    private String content;
    private String userId;
    private String userFromId;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    
   
    
    public VisitorsBook(Integer postNum, String title, String content, String userId, String userFromId,
            LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.postNum = postNum;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.userFromId = userFromId;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }


    public Integer getPostNum() {
        return postNum;
    }



    public String getTitle() {
        return title;
    }



    public String getContent() {
        return content;
    }



    public String getUserId() {
        return userId;
    }



    public String getUserFromId() {
        return userFromId;
    }



    public LocalDateTime getCreatedTime() {
        return createdTime;
    }



    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }



    @Override
    public String toString() {
        return String.format("VisitorsBook(postNum=%d, title=%s, content=%s, fromID=%s, CreatedTime=%s, ModifiedTime=%s)", 
                        postNum, title, content, userFromId, createdTime, modifiedTime);
    }
    
}
