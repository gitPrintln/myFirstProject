package edu.java.abc;

import static edu.java.abc.OracleJdbc.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleDriver;

public class SQLAccessTest {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            DriverManager.registerDriver(new OracleDriver());
            System.out.println("Oracle Driver 등록 성공");
            
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Oracle DB 접속 성공");
            
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
        }

    }

}
