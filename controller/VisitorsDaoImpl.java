package edu.java.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import edu.java.model.FriendManagement;
import edu.java.model.UserInfo;
import edu.java.model.VisitorsBook;
import edu.java.view.visitorsbookMain;
import edu.java.view.visitorsbookPost;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.proxy.annotation.Pre;

import static edu.java.abc.OracleJdbc.*;

import static edu.java.controller.JdbcSql.*;
import static edu.java.model.VisitorsBook.Entity.*;
import static edu.java.model.UserInfo.Entity.*;
import static edu.java.model.FriendManagement.Entity.*;
public class VisitorsDaoImpl implements VisitorsDao{

    // singleton 이용
    private static VisitorsDaoImpl instance = null;
    private VisitorsDaoImpl() {}
    public static VisitorsDaoImpl getInstance() {
        if (instance == null) {
            instance = new VisitorsDaoImpl();
        }
        return instance;
    }
    
    // 접속 메서드
    private Connection getConnection () throws SQLException {
        DriverManager.registerDriver(new OracleDriver());
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    // 닫아주는 메서드
    private void closeResources (Connection conn, Statement stmt) throws SQLException {
        stmt.close();
        conn.close();
    }
    
    private void closeResources (Connection conn, Statement stmt, ResultSet rs) throws SQLException {
        rs.close();
        closeResources(conn, stmt);
    }
    
    @Override
    public int login(String userId, String password) {
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(LOG_IN);
            stmt.setString(1, userId);
            stmt.setString(2, password);
            
            rs = stmt.executeQuery();
            if (rs.isBeforeFirst() == true) {
                result = 1;
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    @Override
    public int insert(UserInfo userInfo) {
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            
            stmt = conn.prepareStatement(INSERT_USERINFO);
            
            stmt.setString(1, userInfo.getUserId());
            stmt.setString(2, userInfo.getPassword());
            stmt.setString(3, userInfo.getName());
            stmt.setString(4, userInfo.getPhoneNum());
            stmt.setString(5, userInfo.getEmail());
            result = stmt.executeUpdate();            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    @Override
    public List<VisitorsBook> select(String id) {
        List<VisitorsBook> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(LIST_ID_BOOK);
            
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Integer postNum = rs.getInt(COL_POSTNUM);
                String title = rs.getString(COL_TITLE);
                String content = rs.getString(COL_CONTENT);
                String FromId = rs.getString(COL_FROM_ID);
                LocalDateTime createdTime = rs.getTimestamp(COL_CREATED_TIME).toLocalDateTime();
                LocalDateTime modifiedTime = rs.getTimestamp(COL_MODIFIED_TIME).toLocalDateTime();
                
                VisitorsBook visitorsBook = new VisitorsBook(postNum, title, content, id, FromId, createdTime, modifiedTime);
                list.add(visitorsBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return list;
    }

    @Override
    public VisitorsBook select(String id, Integer postNum) {
        VisitorsBook list = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECTED_LOGIN_ID_BOOK_POST);
            
            stmt.setString(1, id);
            stmt.setInt(2, postNum);        
            rs = stmt.executeQuery();
            if (rs.next()) {
                String FromId = rs.getString(COL_FROM_ID);
                Integer postNo = rs.getInt(COL_POSTNUM);
                String Title = rs.getString(COL_TITLE);
                String Content = rs.getString(COL_CONTENT);
                LocalDateTime CreatedT = rs.getTimestamp(COL_CREATED_TIME).toLocalDateTime();
                LocalDateTime ModifiedT = rs.getTimestamp(COL_MODIFIED_TIME).toLocalDateTime();
                
                list = new VisitorsBook(postNo, Title, Content, id, FromId, CreatedT, ModifiedT);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return list;
    }

    @Override
    public int insert(VisitorsBook visitorsBook) {
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(NEW_POST_WRITE);
            stmt.setString(1, visitorsBook.getTitle());
            stmt.setString(2, visitorsBook.getContent());
            stmt.setString(3, visitorsBook.getUserId());
            stmt.setString(4, visitorsBook.getUserFromId());
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        
        
        return result;
    }

    @Override
    public int update(VisitorsBook visitorsBook, Integer postNum) {
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(POST_UPDATE);
            
            stmt.setString(1, visitorsBook.getTitle());
            stmt.setString(2, visitorsBook.getContent());
            stmt.setString(3, visitorsBook.getUserId());
            stmt.setInt(4, postNum);

            result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }

    @Override
    public int delete(String id, Integer postNum) {
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(POST_DELETE);
            
            stmt.setString(1, id);
            stmt.setInt(2, postNum);
            
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }
    
    @Override
    public List<UserInfo> selectFriend(int type, String keyword) {
        List<UserInfo> list = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
                        
            switch(type) {
            case 0:
                stmt = conn.prepareStatement(SEARCH_FRIEND_ID);
                stmt.setString(1, "%" + keyword.toLowerCase() + "%");
                break;
            case 1:
                stmt = conn.prepareStatement(SEARCH_FRIEND_NAME);
                stmt.setString(1, "%" + keyword.toLowerCase() + "%");
                break;
            default:
            }
           rs = stmt.executeQuery();
           while (rs.next()) {
               String userId = rs.getString(COL_ID);
               String name = rs.getString(COL_NAME);
               
               UserInfo users = new UserInfo(userId, null, name, null, null);
               list.add(users);
           } 
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return list;
    }
    
    @Override
    public List<VisitorsBook> selectPost(int type, String keyword, String id) {
        List<VisitorsBook> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            
            switch(type) {
            case 0:
                conn = getConnection();
                stmt = conn.prepareStatement(SEARCH_POST_TITLE);
                stmt.setString(1, "%" + keyword.toLowerCase() + "%");
                stmt.setString(2, id);
                break;
            case 1:
                conn = getConnection();
                stmt = conn.prepareStatement(SEARCH_POST_CONTENT);
                stmt.setString(1, "%" + keyword.toLowerCase() + "%");
                stmt.setString(2, id);
                break;
            case 2:
                conn = getConnection();
                stmt = conn.prepareStatement(SEARCH_POST_TITLE_OR_CONTENT);
                stmt.setString(1, "%" + keyword.toLowerCase() + "%");
                stmt.setString(2, "%" + keyword.toLowerCase() + "%");
                stmt.setString(3, id);
                break;
            case 3:
                conn = getConnection();
                stmt = conn.prepareStatement(SEARCH_POST_USERID);
                stmt.setString(1, "%" + keyword.toLowerCase() + "%");
                stmt.setString(2, id);
                break;
            default:
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                Integer postNum = rs.getInt(COL_POSTNUM);
                String title = rs.getString(COL_TITLE);
                String content = rs.getString(COL_CONTENT);
                String userId = rs.getString(COL_ID);
                String userFromId = rs.getString(COL_FROM_ID);
                LocalDateTime createdTime = rs.getTimestamp(COL_CREATED_TIME).toLocalDateTime();
                LocalDateTime modifiedTime = rs.getTimestamp(COL_MODIFIED_TIME).toLocalDateTime();
                
                VisitorsBook visitorsPostInfo = new VisitorsBook(postNum, title, content, userId, userFromId, createdTime, modifiedTime);
                list.add(visitorsPostInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return list;
        
    }
    
    
    @Override
    public List<UserInfo> selectMyFriendInfo(String id) {
        List<UserInfo> list = new ArrayList<>();
        List<UserInfo> idList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECT_MY_FRIEND_LIST);
            
            stmt.setString(1, id);
            
            rs = stmt.executeQuery();   
            while (rs.next()) {
                String userId = rs.getString(COL_FRIEND_ID);
                UserInfo infoList = new UserInfo(userId, null);
                idList.add(infoList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (UserInfo U : idList) {
            try {
                conn = getConnection();
                stmt = conn.prepareStatement(SELECT_MY_FRIEND_INFO);
                
                stmt.setString(1, U.getUserId());
                
                rs = stmt.executeQuery();
                if (rs.next()) {
                    String FriendName = rs.getString(COL_NAME);
                    
                    UserInfo friendInfo = new UserInfo(U.getUserId(), FriendName);
                    list.add(friendInfo);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    closeResources(conn, stmt, rs);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }   
        }
        
        return list;
    }
    @Override
    public int add(String id, String addId) {
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(ADD_FRIEND);
            
            stmt.setString(1, id);
            stmt.setString(2, addId);
            
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }
    @Override
    public int selectValidityCheck(String id, String addId) {
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECT_ADD_FRIEND_VIOLATED);
            
            stmt.setString(1, id);
            stmt.setString(2, addId);
            rs = stmt.executeQuery();
            if (rs.isBeforeFirst() == false) {
                result = 1;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }
    
    public boolean idValidityCheck(String id, String editorId) {
        boolean result = false;
        if (id.equals(editorId)) {
            result = true;
        } 
        return result;
    }
    
    @Override
    public int idDuplicationCheck(String userId) {
        int result = 0;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();

            stmt = conn.prepareStatement(SELECT_ID_DUPLICATION);
            
            stmt.setString(1, userId);
            rs = stmt.executeQuery();
            
            if (rs.isBeforeFirst() == false) {
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }
    @Override
    public int delete(String id, String deleteId) {
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(DELETE_FRIEND);
            
            stmt.setString(1, id);
            stmt.setString(2, deleteId);
            
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }

}
