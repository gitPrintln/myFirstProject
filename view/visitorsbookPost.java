package edu.java.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import edu.java.controller.VisitorsDaoImpl;
import edu.java.model.VisitorsBook;
import edu.java.view.MyFriendFrame.logoutListener;
import edu.java.view.visitorsbookUpdaterFrame.postUpdateListener;
import edu.java.view.visitorsbookWriterFrame.newPostListener;

import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.List;

import javax.swing.JTable;
import static edu.java.model.VisitorsBook.Entity.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class visitorsbookPost extends JFrame implements newPostListener, postUpdateListener, logoutListener {
    
    private static final String[] COLUMN_NAMES = {
            COL_POSTNUM, COL_TITLE, COL_EDITOR_ID, COL_MODIFIED_TIME
    };
    
    
    private Component parent;
    
    
    private JComboBox<String> comboBox; 
    private VisitorsDaoImpl dao;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private JTextField textSearch;
    private JTextField textWelcome;
        
    private String id;
    private JTextField textAnonymousPost;
    private int cnt; // 글 번호 카운트
    /**
     * Launch the application.
     */
    public static void visitorsPostList(String id, Component parent) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    visitorsbookPost frame = new visitorsbookPost(id, parent);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public visitorsbookPost(String id, Component parent) {
        this.id = id;
        this.parent = parent;
        dao = VisitorsDaoImpl.getInstance();
        initialize(id);
        initializeTable(id);
    }
   
    private void initializeTable(String id) {
        model = new DefaultTableModel(null, COLUMN_NAMES);
        table.setModel(model);
        
        List<VisitorsBook> tableList = dao.select(id);
        int cnt = tableList.size();
        for (VisitorsBook v : tableList) {
        Object[] row = {
                v.getPostNum(), v.getTitle(), v.getUserFromId(), v.getModifiedTime()
        };
//        cnt--;
        model.addRow(row);
        }    
    }
    
    
    private void initializeSearchingTable(int type, String keyword, String id) {
        List<VisitorsBook> searchList = dao.selectPost(type, keyword, id);
        model = new DefaultTableModel(null, COLUMN_NAMES);
        table.setModel(model);
        for (VisitorsBook v : searchList) {
            Object[] list = {
                    v.getPostNum(), v.getTitle(), v.getUserFromId(), v.getModifiedTime()
            };
        model.addRow(list);
        }
    }
    
    public void initialize(String id) {
        setTitle("방명록 프로그램");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();
        setBounds(x, y, 624, 689);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBounds(442, 64, 154, 576);
        contentPane.add(btnPanel);
        btnPanel.setLayout(null);
        
        JButton btnCreate = new JButton("글 남기기");
        btnCreate.setBackground(new Color(230, 230, 250));
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                visitorsbookWriterFrame.newWriter(visitorsbookPost.this, id, visitorsbookPost.this, id);
            }
        });
        btnCreate.setBounds(31, 56, 112, 36);
        btnCreate.setFont(new Font("D2Coding", Font.PLAIN, 17));
        btnPanel.add(btnCreate);
        
        JButton btnUpdate = new JButton("수정하기");
        btnUpdate.setBackground(new Color(230, 230, 250));
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bringPostData(id);
            }
        });
        btnUpdate.setBounds(31, 108, 112, 29);
        btnUpdate.setFont(new Font("D2Coding", Font.PLAIN, 17));
        btnPanel.add(btnUpdate);
        
        JButton btnDelete = new JButton("삭제");
        btnDelete.setBackground(new Color(230, 230, 250));
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postDelete();
            }
        });
        btnDelete.setFont(new Font("D2Coding", Font.PLAIN, 17));
        btnDelete.setBounds(31, 147, 112, 29);
        btnPanel.add(btnDelete);
        
        JButton btnLogOut = new JButton("로그아웃");
        btnLogOut.setBackground(new Color(211, 211, 211));
        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logoutOngoing();
            }
        });
        btnLogOut.setFont(new Font("D2Coding", Font.PLAIN, 17));
        btnLogOut.setBounds(42, 530, 112, 36);
        btnPanel.add(btnLogOut);
        
        JButton btnDetail = new JButton("글 읽기");
        btnDetail.setBackground(new Color(230, 230, 250));
        btnDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visitorsbookPostRead(id);            
            }
        });
        btnDetail.setFont(new Font("D2Coding", Font.PLAIN, 17));
        btnDetail.setBounds(31, 10, 112, 36);
        btnPanel.add(btnDetail);
        
        JButton btnFriend = new JButton("친구 목록");
        btnFriend.setBackground(new Color(250, 235, 215));
        btnFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyFriendFrame.ManagingFriends(id, visitorsbookPost.this, parent, visitorsbookPost.this);
            }
        });
        btnFriend.setFont(new Font("D2Coding", Font.PLAIN, 17));
        btnFriend.setBounds(31, 381, 112, 36);
        btnPanel.add(btnFriend);
        
        JButton btnReadAll = new JButton("글 목록");
        btnReadAll.setBackground(new Color(250, 235, 215));
        btnReadAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initializeTable(id);
            }
        });
        btnReadAll.setFont(new Font("D2Coding", Font.PLAIN, 17));
        btnReadAll.setBounds(31, 335, 112, 36);
        btnPanel.add(btnReadAll);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        scrollPane.setBounds(12, 65, 424, 532);
        contentPane.add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        
        JButton btnWriteSearch = new JButton("검색");
        btnWriteSearch.setBackground(SystemColor.inactiveCaptionBorder);
        btnWriteSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchingPost();
            }
        });
        btnWriteSearch.setBounds(355, 607, 75, 33);
        contentPane.add(btnWriteSearch);
        btnWriteSearch.setFont(new Font("D2Coding", Font.PLAIN, 17));
        
        textSearch = new JTextField();
        textSearch.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        textSearch.setBounds(163, 607, 180, 33);
        contentPane.add(textSearch);
        textSearch.setColumns(10);
        
        comboBox = new JComboBox();
        comboBox.setBackground(SystemColor.inactiveCaptionBorder);
        comboBox.setFont(new Font("D2Coding", Font.PLAIN, 18));
        String[] comboBoxContents = {"제목", "내용", "제목+내용", "ID"};
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(comboBoxContents);
        comboBox.setBounds(38, 607, 113, 33);
        comboBox.setModel(comboBoxModel);
        comboBox.setSelectedIndex(1);
        contentPane.add(comboBox);
        
        textWelcome = new JTextField();
        textWelcome.setBorder(null);
        textWelcome.setForeground(Color.BLACK);
        textWelcome.setBackground(new Color(240, 240, 240));
        textWelcome.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textWelcome.setSelectedTextColor(Color.WHITE);
        textWelcome.setSelectionColor(Color.WHITE);
        textWelcome.setEditable(false);
        textWelcome.setBounds(422, 0, 174, 33);
        contentPane.add(textWelcome);
        String welText = String.format("%s님, 환영합니다!", id);
        textWelcome.setText(welText);
        textWelcome.setColumns(10);
        
        textAnonymousPost = new JTextField();
        textAnonymousPost.setBorder(null);
        textAnonymousPost.setFont(new Font("D2Coding", Font.BOLD, 20));
        textAnonymousPost.setSelectionColor(Color.WHITE);
        textAnonymousPost.setSelectedTextColor(Color.WHITE);
        textAnonymousPost.setEditable(false);
        textAnonymousPost.setBounds(12, 10, 232, 45);
        contentPane.add(textAnonymousPost);
        String anonText = String.format("%s님의 방명록", id);
        textAnonymousPost.setText(anonText);
        textAnonymousPost.setColumns(10);
        
    }


    private void logoutOngoing() {
        int confirm = JOptionPane.showConfirmDialog(this, "정말 로그아웃 하시겠습니까?", "Information", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            parent.setVisible(true);
        }
    }

    private void searchingPost() {
        int type = comboBox.getSelectedIndex();
        String keyword = textSearch.getText();
        if (keyword.equals("")) {
            JOptionPane.showMessageDialog(this, "검색어를 입력해주세요", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        initializeSearchingTable(type, keyword, id);
    }

    private void postDelete() {
       int row = table.getSelectedRow();
       
       if (row == -1) {
           JOptionPane.showMessageDialog(this, "삭제할 글을 먼저 선택해주세요.", "Warning", JOptionPane.WARNING_MESSAGE);
           return;
       }
       Integer postNum = (Integer) model.getValueAt(row, 0);
       String editorId = (String) model.getValueAt(row, 2);
       if (dao.idValidityCheck(id, editorId)) {
           int confirm = JOptionPane.showConfirmDialog(this, postNum + " 번 글을 정말 삭제하시겠습니까? ", "?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
           if (confirm == JOptionPane.YES_OPTION) {
               dao.delete(id, postNum);
               JOptionPane.showMessageDialog(this, postNum + " 번 글을 삭제했습니다.", "Informatin", JOptionPane.INFORMATION_MESSAGE);
               model.removeRow(row);
           } 
       } else {
           JOptionPane.showMessageDialog(this, "내가 작성한 글만 삭제할 수 있습니다.", "Error", JOptionPane.ERROR_MESSAGE);
           return;
       }
       
    }

    private void bringPostData(String id) {
        int row = table.getSelectedRow(); 
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "수정할 글을 먼저 선택해주세요.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Integer postNum = (Integer) model.getValueAt(row, 0);
        String editorId = (String) model.getValueAt(row, 2);
        if (dao.idValidityCheck(id, editorId)) {
            visitorsbookUpdaterFrame.postUpdate(visitorsbookPost.this, id, visitorsbookPost.this, postNum, id);
        } else {
            JOptionPane.showMessageDialog(this, "내가 작성한 글만 수정할 수 있습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void visitorsbookPostRead(String id) {
        // 행을 선택해야 읽을 수 있도록
        int row = table.getSelectedRow(); 
        
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "방명록 글을 먼저 선택해주세요.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Integer postNum = (Integer) model.getValueAt(row, 0);
        visitorsbookReaderFrame.newReader(visitorsbookPost.this, id, postNum, id);
    }

    @Override
    public void newPostNotify() {
        initializeTable(id);        
    }

    @Override
    public void postUpdateNotify() {
        initializeTable(id); 
    }

    @Override
    public void onLogoutNotify() {
            dispose();  
    }
}
