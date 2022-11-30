package edu.java.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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

public class visitorsbookWriterFrame extends JFrame {

    @FunctionalInterface
    public interface newPostListener{
        void newPostNotify();
    }
    private newPostListener litener;
    
    private VisitorsDaoImpl dao;
    private Component parent;
    private JPanel contentPane;
    private JTextField textTitle;
    private JTextField textPostNum;
    private JTextField textId;
    private JTextArea textContent;
    private String id;
    private String friendId;
    /**
     * Launch the application.
     */
    public static void newWriter(Component parent, String id, newPostListener listener, String friendId) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    visitorsbookWriterFrame frame = new visitorsbookWriterFrame(parent, id, listener, friendId);
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
    public visitorsbookWriterFrame(Component parent, String id, newPostListener listener, String friendId) {
        this.parent = parent;
        this.id = id;
        this.litener = listener;
        this.friendId = friendId;
        dao = VisitorsDaoImpl.getInstance();
        initialize();
    }
    
    public void initialize() {
        setTitle("새 글 작성하기");
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
        
        textId = new JTextField();
        textId.setFont(new Font("D2Coding", Font.PLAIN, 20));
        textId.setEditable(false);
        textId.setColumns(10);
        textId.setBounds(201, 5, 116, 36);
        contentPane.add(textId);
        
        JLabel lblPostNum = new JLabel("글번호");
        lblPostNum.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPostNum.setFont(new Font("D2Coding", Font.PLAIN, 20));
        lblPostNum.setBounds(12, 5, 60, 36);
        contentPane.add(lblPostNum);
        
        JButton btnEnter = new JButton("작성하기");
        btnEnter.setBackground(SystemColor.inactiveCaptionBorder);
        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newPostWrite(friendId, id);
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

    private void newPostWrite(String id, String fromId) {
        String title = textTitle.getText();
        String content = textContent.getText();
        VisitorsBook newPost = new VisitorsBook(null, title, content, id, fromId, null, null);
        if (title.equals("")) {
            JOptionPane.showMessageDialog(this, "제목을 반드시 입력하세요.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int result = dao.insert(newPost);
        dispose();
        
        if (result == 1) {
            JOptionPane.showMessageDialog(parent, "새 방명록 등록 성공", "Information", JOptionPane.INFORMATION_MESSAGE);
            litener.newPostNotify();
        } else {
            JOptionPane.showMessageDialog(parent, "새 방명록 등록 실패", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
