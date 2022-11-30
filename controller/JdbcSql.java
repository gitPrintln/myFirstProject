package edu.java.controller;


import static edu.java.model.UserInfo.Entity.*;
import static edu.java.model.VisitorsBook.Entity.*;
import static edu.java.model.FriendManagement.Entity.*;
public interface JdbcSql {

    // 회원가입 유저 정보 저장하기
    String INSERT_USERINFO = String.format("insert into %s (%s, %s, %s, %s, %s) values (?, ?, ?, ?, ?)", 
                                TBL_MEMBER, COL_ID, COL_PASSWORD, COL_NAME, COL_PHONE_NUM, COL_EMAIL);
    
    // 로그인 할 때 ID/PW 일치하면 로그인, 아니면 실패
    String LOG_IN = String.format("select %s, %s from %s where %s = ? and %s = ?", 
                        COL_ID, COL_PASSWORD, TBL_MEMBER, COL_ID, COL_PASSWORD);
    
    // 아이디가 가진 방명록 글 리스트 가져오기
    String LIST_ID_BOOK = String.format("select * from %s where %s = ? order by %s desc", TBL_ADMINPOST, COL_ID, COL_POSTNUM);
    
    // 로그인 한 아이디가 가진 방명록 글 중에 선택된 글 전체 정보 읽기
    String SELECTED_LOGIN_ID_BOOK_POST = String.format("select * from %s where %s = ? and %s = ? order by %s desc", TBL_ADMINPOST, COL_ID, COL_POSTNUM, COL_POSTNUM);
    
    // 로그인 한 아이디로 내 방명록에 글 남기기
    String NEW_POST_WRITE = String.format("insert into %s (%s, %s, %s, %s) values (?, ?, ?, ?)", TBL_ADMINPOST, COL_TITLE, COL_CONTENT, COL_ID, COL_FROM_ID);
    
    // 로그인 한 아이디로 방명록 글 수정하기
    String POST_UPDATE = String.format("update %s set %s = ?, %s = ?, %s = sysdate where %s = ? and %s = ?", TBL_ADMINPOST, COL_TITLE, COL_CONTENT, COL_MODIFIED_TIME, COL_ID, COL_POSTNUM);
    
    // 로그인 한 아이디로 방명록 글 삭제하기
    String POST_DELETE = String.format("delete from %s where %s = ? and %s = ?", TBL_ADMINPOST, COL_ID, COL_POSTNUM);
    
    // 친구 검색:조건 검색:ID로 검색하기
    String SEARCH_FRIEND_ID = String.format("select * from %s where lower(%s) like ? order by %s", TBL_MEMBER, COL_ID, COL_ID);
    
    // 친구 검색:조건 검색:이름으로 검색하기
    String SEARCH_FRIEND_NAME = String.format("select * from %s where lower(%s) like ? order by %s", TBL_MEMBER, COL_NAME, COL_NAME);
    
    // TODO: 그 방명록에 해당하는 id에 해당하는 글만 검색되도록
    // 글 검색:조건 검색:제목으로 검색하기
    String SEARCH_POST_TITLE = String.format("select * from %s where lower(%s) like ? and %s = ? order by %s", TBL_ADMINPOST, COL_TITLE, COL_ID, COL_POSTNUM);

    // 글 검색:조건 검색:내용으로 검색하기
    String SEARCH_POST_CONTENT = String.format("select * from %s where lower(%s) like ? and %s = ? order by %s", TBL_ADMINPOST, COL_CONTENT, COL_ID, COL_POSTNUM);
    
    // 글 검색:조건 검색:제목 + 내용으로 검색하기
    String SEARCH_POST_TITLE_OR_CONTENT = String.format("select * from %s where (lower(%s) like ? or lower(%s) like ?) and %s = ? order by %s", TBL_ADMINPOST, COL_TITLE, COL_CONTENT, COL_ID, COL_POSTNUM);
    
    // 글 검색:조건 검색:ID로 검색하기
    String SEARCH_POST_USERID = String.format("select * from %s where lower(%s) like ? and %s = ? order by %s", TBL_ADMINPOST, COL_FROM_ID, COL_ID, COL_POSTNUM);
    // TODO
    
    // UserInfo와 FriendManagement 테이블에서 친구 목록 불러오기
    String SELECT_MY_FRIEND_LIST = String.format("select * from %s where %s = ?", TBL_MANAGEDFRIEND, COL_ID);
    String SELECT_MY_FRIEND_INFO = String.format("select %s from %s where %s = ?", COL_NAME, TBL_MEMBER, COL_ID);
    
    // 로그인 한 아이디로 친구 추가하기
    String ADD_FRIEND = String.format("insert into %s (%s, %s) values(?, ?)", TBL_MANAGEDFRIEND, COL_ID, COL_FRIEND_ID);
    
    // 친구 추가 유효성 체크(중복되는 친구 추가인지 아닌지)
    String SELECT_ADD_FRIEND_VIOLATED = String.format("select * from %s where %s = ? and %s = ?", TBL_MANAGEDFRIEND, COL_ID, COL_FRIEND_ID);
    
    // 회원 가입 아이디 유효성 체크(중복되는 아이디인지 아닌지)
    String SELECT_ID_DUPLICATION = String.format("select * from %s where %s = ?", TBL_MEMBER, COL_ID);
    
    // 로그인 한 아이디로 친구 삭제하기
    String DELETE_FRIEND = String.format("delete from %s where %s = ? and %s = ?", TBL_MANAGEDFRIEND, COL_ID, COL_FRIEND_ID);
    
}
