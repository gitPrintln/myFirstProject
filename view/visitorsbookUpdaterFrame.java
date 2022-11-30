package edu.java.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import edu.java.controller.VisitorsDaoImpl;
import edu.java.model.VisitorsBook;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class visitorsbookUpdaterFrame extends JFrame {

    @FunctionalInterface
    public interface postUpdateListener{
        void postUpdateNotify();
    }
    private postUpdateListener litener;
    
    private VisitorsDaoImpl dao;
    private Component parent;
    private JPanel contentPane;
    private JTextField textTitle;
    private JTextField textPostNum;
    private JTextField textFromId;
    private JTextArea textContent;
    private String id; // 로그인한 아이디
    private String friendId; // 친구 방명록 아이디
    private Integer postNum;
    
    /**
     * Launch the application.
     */
    public static void postUpdate(Component parent, String id, postUpdateListener listener, Integer postNum, String friendId) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    visitorsbookUpdaterFrame frame = new visitorsbookUpdaterFrame(parent, id, listener, postNum, friendId);
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
    public visitorsbookUpdaterFrame(Component parent, String id, postUpdateListener listener, Integer postNum, String friendId) {
        this.parent = parent;
        this.id = id;
        this.friendId = friendId;
        this.litener = listener;
        this.postNum = postNum;
        dao = VisitorsDaoImpl.getInstance();
        initialize();
        initializeDetailRead(friendId, postNum);
    }
    
    private void initializeDetailRead(String friendId, Integer postNum) {
        VisitorsBook postRead = dao.select(friendId, postNum);
        
        if (postRead != null) {
            textFromId.setText(postRead.getUserFromId());
            textPostNum.setText(postRead.getPostNum().toString());
            textTitle.setText(postRead.getTitle());
            textContent.setText(postRead.getContent());
        }
    }
    
    public void initialize() {
        setTitle("글 수정하기");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();        
        setBounds(x, y, 591, 624);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblTitle = new JLabel("제목");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("D2Coding", Font.PLAIN, 20));
        lblTitle.setBounds(12, 48, 69, 56);
        contentPane.add(lblTitle);
        
        textTitle = new JTextField();
        textTitle.setBounds(87, 48, 467, 56);
        contentPane.add(textTitle);
        textTitle.setColumns(10);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 114, 542, 378);
        contentPane.add(scrollPane);
        
        textContent = new JTextArea();
        scrollPane.setViewportView(textContent);
        
        textPostNum = new JTextField();
        textPostNum.setFont(new Font("D2Coding", Font.PLAIN, 20));
        textPostNum.setEditable(false);
        textPostNum.setColumns(10);
        textPostNum.setBounds(77, 5, 69, 36);
        contentPane.add(textPostNum);
        
        JLabel lblId = new JLabel("ID");
        lblId.setHorizontalAlignment(SwingConstants.RIGHT);
        lblId.setFont(new Font("D2Coding", Font.PLAIN, 20));
        lblId.setBounds(158, 5, 31, 36);
        contentPane.add(lblId);
        
        textFromId = new JTextField();
        textFromId.setFont(new Font("D2Coding", Font.PLAIN, 20));
        textFromId.setEditable(false);
        textFromId.setColumns(10);
        textFromId.setBounds(201, 5, 116, 36);
        contentPane.add(textFromId);
        
        JLabel lblPostNum = new JLabel("글번호");
        lblPostNum.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPostNum.setFont(new Font("D2Coding", Font.PLAIN, 20));
        lblPostNum.setBounds(12, 5, 60, 36);
        contentPane.add(lblPostNum);
        
        JButton btnEnter = new JButton("수정하기");
        btnEnter.setBackground(SystemColor.inactiveCaptionBorder);
        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postUpdate(friendId, postNum);
            }
        });
        btnEnter.setFont(new Font("D2Coding", Font.PLAIN, 20));
        btnEnter.setBounds(287, 504, 128, 71);
        contentPane.add(btnEnter);
        
        JButton btnBack = new JButton("뒤로가기");
        btnBack.setBackground(SystemColor.inactiveCaptionBorder);
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnBack.setFont(new Font("D2Coding", Font.PLAIN, 20));
        btnBack.setBounds(426, 504, 128, 71);
        contentPane.add(btnBack);
    }

    private void postUpdate(String friendId, Integer postNum) {
        String title = textTitle.getText();
        String content = textContent.getText();
        VisitorsBook newPost = new VisitorsBook(null, title, content, friendId, id, null, null);
        if (title.equals("")) {
            JOptionPane.showMessageDialog(this, "제목을 반드시 입력하세요.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int result = dao.update(newPost, postNum);
        dispose();
        
        if (result == 1) {
            JOptionPane.showMessageDialog(parent, "방명록 수정 성공", "Information", JOptionPane.INFORMATION_MESSAGE);
            litener.postUpdateNotify();
        } else {
            JOptionPane.showMessageDialog(parent, "방명록 수정 실패", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
