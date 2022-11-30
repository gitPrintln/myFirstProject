package edu.java.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import edu.java.controller.VisitorsDaoImpl;
import edu.java.model.UserInfo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTable;
import static edu.java.model.UserInfo.Entity.*;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.SystemColor;
import java.awt.Color;


public class MyFriendFrame extends JFrame{

    private static final String[] COLUMN_NAMES = {
            COL_ID, COL_NAME
    };
    
    public interface logoutListener{
        void onLogoutNotify();
    }
    
    private logoutListener listener;
    private Component parent;
    private Component loginFrame;
    private VisitorsDaoImpl dao;
    private JPanel contentPane;
    private JTextField textSearch;
    private JTable table;
    private DefaultTableModel model;
    private String id;
    private JComboBox<String> comboBox;
    
    /**
     * Launch the application.
     */
    public static void ManagingFriends(String id, Component parent, Component loginFrame, logoutListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MyFriendFrame frame = new MyFriendFrame(id, parent, loginFrame, listener);
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
    public MyFriendFrame(String id, Component parent, Component loginFrame, logoutListener listener) {
        this.id = id;
        this.parent = parent;
        this.loginFrame = loginFrame;
        this.listener = listener;
        dao = VisitorsDaoImpl.getInstance();
        initialize();
        myFriendTable(id);
    }
    
    public void myFriendTable(String id) {
        model = new DefaultTableModel(null, COLUMN_NAMES);
        table.setModel(model);
        List<UserInfo> initializeTableList = dao.selectMyFriendInfo(id);
        for (UserInfo f : initializeTableList) {            
        Object[] rowList = {
                f.getUserId(), f.getName() 
        };
        model.addRow(rowList);
        }
    }
    
    public void searchTable(int type, String keyword) {
        model = new DefaultTableModel(null, COLUMN_NAMES);
        table.setModel(model);
        List<UserInfo> tableList = dao.selectFriend(type, keyword);
        for (UserInfo u : tableList) {
        Object[] rowList = { 
            u.getUserId(), u.getName()
        };
        model.addRow(rowList);
        } 
    }
    
    
    public void initialize() {
        setTitle("친구창 관리");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();
        setBounds(x, y, 492, 504);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblTitle = new JLabel("친구 목록");
        lblTitle.setFont(new Font("D2Coding", Font.BOLD, 20));
        lblTitle.setBounds(12, 10, 97, 46);
        contentPane.add(lblTitle);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        scrollPane.setBounds(12, 118, 333, 340);
        contentPane.add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        
        textSearch = new JTextField();
        textSearch.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        textSearch.setFont(new Font("D2Coding", Font.PLAIN, 20));
        textSearch.setBounds(92, 66, 166, 42);
        contentPane.add(textSearch);
        textSearch.setColumns(10);
        
        JButton btnEnter = new JButton("Enter");
        btnEnter.setBackground(SystemColor.inactiveCaptionBorder);
        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchingFriend();
            }
        });
        btnEnter.setFont(new Font("D2Coding", Font.PLAIN, 15));
        btnEnter.setBounds(270, 66, 75, 42);
        contentPane.add(btnEnter);
        
        JButton btnAddFriend = new JButton("친구추가");
        btnAddFriend.setBackground(new Color(230, 230, 250));
        btnAddFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMyFriend(id);
            }
        });
        btnAddFriend.setFont(new Font("D2Coding", Font.PLAIN, 17));
        btnAddFriend.setBounds(357, 131, 105, 46);
        contentPane.add(btnAddFriend);
        
        JButton btnVisitFriend = new JButton("친구방문");
        btnVisitFriend.setBackground(new Color(250, 235, 215));
        btnVisitFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VisitFriendPost();
            }
        });
        btnVisitFriend.setFont(new Font("D2Coding", Font.PLAIN, 17));
        btnVisitFriend.setBounds(357, 387, 105, 46);
        contentPane.add(btnVisitFriend);
        
        comboBox = new JComboBox();
        comboBox.setBackground(SystemColor.inactiveCaptionBorder);
        String[] comboBoxContents = {"ID", "이름"};
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(comboBoxContents);
        comboBox.setFont(new Font("D2Coding", Font.PLAIN, 16));
        comboBox.setBounds(12, 66, 68, 42);
        comboBox.setModel(comboBoxModel);
        contentPane.add(comboBox);
        
        JButton btnFriendList = new JButton("친구목록");
        btnFriendList.setBackground(new Color(250, 235, 215));
        btnFriendList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFriendTable(id);
            }
        });
        btnFriendList.setFont(new Font("D2Coding", Font.PLAIN, 17));
        btnFriendList.setBounds(357, 331, 105, 46);
        contentPane.add(btnFriendList);
        
        JButton btnDeleteFriend = new JButton("친구삭제");
        btnDeleteFriend.setBackground(new Color(230, 230, 250));
        btnDeleteFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMyfriend(id);
            }
        });
        btnDeleteFriend.setFont(new Font("D2Coding", Font.PLAIN, 17));
        btnDeleteFriend.setBounds(357, 187, 105, 46);
        contentPane.add(btnDeleteFriend);
    }

    private void deleteMyfriend(String id) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "삭제할 아이디를 선택해 주세요.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String deleteId = (String) model.getValueAt(row, 0);
        
        int result = dao.selectValidityCheck(id, deleteId);
        
        
        if (result == 0) {
            
            int confirm = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "Information", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "친구 삭제에 성공했습니다.", "Information", JOptionPane.INFORMATION_MESSAGE);
                dao.delete(id, deleteId);
                myFriendTable(id);
            }
        } else {
            JOptionPane.showMessageDialog(this, "등록되지 않은 친구입니다.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void VisitFriendPost() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "방문할 ID를 선택해 주세요", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String friendId = (String) model.getValueAt(row, 0);
        visitorsbookPostFriend.visitorsPostList(id, friendId, parent, loginFrame);
        dispose();
        listener.onLogoutNotify();
    }

    private void addMyFriend(String id) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "추가할 아이디를 선택해 주세요.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String addId = (String) model.getValueAt(row, 0);
        
        if (id.equals(addId)) {
            JOptionPane.showMessageDialog(this, "자신은 추가할 수 없습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int result = dao.selectValidityCheck(id, addId);
        if (result == 1) {
            JOptionPane.showMessageDialog(this, "친구 추가에 성공했습니다.", "Information", JOptionPane.INFORMATION_MESSAGE);
            dao.add(id, addId);
            myFriendTable(id);
        } else {
            JOptionPane.showMessageDialog(this, "이미 추가된 친구입니다.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        
    }

    private void searchingFriend() {
        String keyword = textSearch.getText();
        if (keyword.equals("")) {
            JOptionPane.showMessageDialog(this, "검색어를 입력하세요", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int type = comboBox.getSelectedIndex();
        searchTable(type, keyword);
    }
}
