package edu.java.controller;

import java.util.List;

import edu.java.model.UserInfo;
import edu.java.model.VisitorsBook;

public interface VisitorsDao {

    /**
     * 로그인 한 id로 친구 삭제하기
     * @param id 삭제할 친구 id
     * @param deleteId
     * @return 친구 삭제에 성공했으면 1, 그렇지 않으면 0.
     */
    int delete(String id, String deleteId);
    
    /**
     * 회원 가입 하고자 하는 아이디가 DB에 있는 아이디인지 체크해주는 기능
     * @param userId 체크할 아이디
     * @return 새로 회원 가입이 가능한 id면 1, 그렇지 않으면 0.
     */
    int idDuplicationCheck(String userId);
    
    /**
     * 로그인 한 id가 이미 추가되어 있는 친구인지 확인해주는 기능
     * @param id 로그인한 id
     * @param addId 추가할 id
     * @return 친구 추가에 성공하면 1, 실패하면 0.
     */
    int selectValidityCheck(String id, String addId);
    
    /**
     * 로그인 한 id로 친구 추가하기
     * @param id 추가할 친구 id
     * @return 친구 추가에 성공했으면 1, 그렇지 않으면 0.
     */
    int add(String id, String addId);
    
    /**
     * 로그인한 아이디가 추가한 아이디와 이름을 불러오는 기능
     * @param id 불러올 정보에 대한 로그인 한 ID
     * @return 로그인한 id가 추가한 친구 ID와 이름 리스트
     */
    List<UserInfo> selectMyFriendInfo(String id);
    
    /**
     * 방명록 리스트를 통해서 글을 검색할 수 있는 기능.
     * @param type 검색하고 싶은 방식에 대한 타입
     * @param keyword 제목, 내용, 제목 + 내용, 작성한 ID로 검색할 것인지의 keyword
     * @return 검색할 글에 대한 정보 리스트
     */
    List<VisitorsBook> selectPost(int type, String keyword, String id);
    
    /**
     * 친구 목록창을 통해서 친구를 검색하고 추가하고 친구 방명록을 방문할 수 있는 기능. 
     * @param type 검색하고 싶은 방식에 대한 타입
     * @param keyword ID로 검색할 것인지, 이름으로 검색할 것인지의 keyword
     * @return 검색할 ID에 대한 정보 리스트
     */
    List<UserInfo> selectFriend(int type, String keyword);
    
    /**
     * SQL DB에 있는 ID와 PW가 일치할 때 로그인
     * @param userId 로그인할 ID
     * @param password 로그인할 PW
     * @return 로그인에 성공하면 1, 실패하면 0을 리턴.
     */
    int login(String userId, String password);
    
    /**
     * 회원가입 정보들을 추가.
     * @param userInfo 유저 정보.
     * @return 관련 정보를 입력했을 때 성공하면 1, 그렇지 않으면 0.
     */
    int insert(UserInfo userInfo);
    
    /**
     * 방명록 게시글 안에 있는 정보를 보여줌.
     * @param id 로그인한 id 정보.
     * @return 게시글 정보가 들어 있는 리스트.
     */
    List<VisitorsBook> select(String id);
    
    /**
     * 글을 클릭했을 때 전체 내용들을 보기.
     * @param userId 회원 id가 가지고 있는 글만 가져 오기 위한 id
     * @param postNum 글 전체 내용을 가져오기 위한 글 번호.
     * @return 해당하는 위치에 null이 아닌 정보가 있을 경우 그 정보를 불러옴.
     */
    VisitorsBook select(String id, Integer postNum);
    
    /**
     * 제목, 내용, 작성자, 작성 시간 등이 리스트에 추가됨.
     * @param visitorsBook 추가하고자 하는 글 관련 정보.
     * @return 관련 정보를 입력했을 때 성공하면 1, 그렇지 않으면 0.
     */
    int insert(VisitorsBook visitorsBook);
    
    /**
     * 제목, 내용, 작성자, 수정 시간 등이 리스트에 업데이트됨.
     * @param visitorsBook 수정하고자 하는 글 관련 정보.
     * @return 관련 정보를 수정했을 때 성공하면 1, 그렇지 않으면 0.
     */
    int update(VisitorsBook visitorsBook, Integer postNum);
    
    /**
     * 제목, 내용, 및 작성자 등 삭제하고자 하는 글을 삭제.
     * @param userId 로그인한 아이디에 해당하는 글만 삭제하도록 설정.
     * @param postNum 삭제하고자 하는 글 번호.
     * @return 관련 정보를 삭제했을 때 성공하면 1, 그렇지 않으면 0.
     */
    int delete(String id, Integer postNum);

    
}
