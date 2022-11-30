package edu.java.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.java.controller.VisitorsDaoImpl;
import edu.java.model.VisitorsBook;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class visitorsbookReaderFrame extends JFrame {

    
    private Component parent;
    private VisitorsDaoImpl dao;
    private Integer postNum;
    private String id; // 로그인 한 아이디
    private String friendId; // 친구 방명록 아이디
    
    private JPanel contentPane;
    private JTextField textTitle;
    private JTextField textPostNum;
    private JTextField textFromId;
    private JTextField textCreatedT;
    private JTextField textModifiedT;
    private JTextArea textContent;
    
    
    /**
     * Launch the application.
     */
    public static void newReader(Component parent, String id, Integer postNum, String friendId) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    visitorsbookReaderFrame frame = new visitorsbookReaderFrame(parent, id, postNum, friendId);
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
    public visitorsbookReaderFrame(Component parent, String id, Integer postNum, String friendId) {
        this.parent = parent;
        this.dao = VisitorsDaoImpl.getInstance();
        this.friendId = friendId;
        this.postNum = postNum;
        this.id = id;
        this.friendId = friendId;
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
            textCreatedT.setText(postRead.getCreatedTime().toString());
            textModifiedT.setText(postRead.getModifiedTime().toString());
        }
    }

    public void initialize() {
        setTitle("글 읽기");
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
        textTitle.setEditable(false);
        textTitle.setBounds(87, 48, 467, 56);
        contentPane.add(textTitle);
        textTitle.setColumns(10);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 114, 542, 378);
        contentPane.add(scrollPane);
        
        textContent = new JTextArea();
        textContent.setEditable(false);
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
        
        textCreatedT = new JTextField();
        textCreatedT.setFont(new Font("D2Coding", Font.PLAIN, 20));
        textCreatedT.setEditable(false);
        textCreatedT.setColumns(10);
        textCreatedT.setBounds(114, 502, 300, 36);
        contentPane.add(textCreatedT);
        
        JLabel lblModifiedT = new JLabel("수정시간");
        lblModifiedT.setHorizontalAlignment(SwingConstants.RIGHT);
        lblModifiedT.setFont(new Font("D2Coding", Font.PLAIN, 20));
        lblModifiedT.setBounds(12, 539, 80, 36);
        contentPane.add(lblModifiedT);
        
        JLabel lblCreatedT = new JLabel("작성시간");
        lblCreatedT.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCreatedT.setFont(new Font("D2Coding", Font.PLAIN, 20));
        lblCreatedT.setBounds(12, 502, 80, 36);
        contentPane.add(lblCreatedT);
        
        textModifiedT = new JTextField();
        textModifiedT.setFont(new Font("D2Coding", Font.PLAIN, 20));
        textModifiedT.setEditable(false);
        textModifiedT.setColumns(10);
        textModifiedT.setBounds(114, 539, 300, 36);
        contentPane.add(textModifiedT);
    }
}
