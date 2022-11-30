package edu.java.view;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.java.controller.VisitorsDaoImpl;
import edu.java.view.JoinMember.onJoinMemberListener;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.SystemColor;

public class visitorsbookMain implements onJoinMemberListener {

    private VisitorsDaoImpl dao;
    
    private JFrame frame;
    private JTextField textUserId;
    private JPasswordField textPassword;
    
    /**
     * Launch the application.
     */
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    visitorsbookMain window = new visitorsbookMain();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public visitorsbookMain() {
        initialize();
        dao = VisitorsDaoImpl.getInstance();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("방명록 프로그램");
        frame.setBounds(100, 100, 469, 454);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JLabel lbluserId = new JLabel("ID");
        lbluserId.setFont(new Font("D2Coding", Font.PLAIN, 20));
        lbluserId.setBounds(50, 170, 40, 53);
        frame.getContentPane().add(lbluserId);
        
        textUserId = new JTextField();
        textUserId.setBackground(Color.WHITE);
        textUserId.setBorder(new LineBorder(new Color(192, 192, 192), 2, true));
        textUserId.setFont(new Font("D2Coding", Font.PLAIN, 20));
        textUserId.setBounds(114, 171, 215, 53);
        frame.getContentPane().add(textUserId);
        textUserId.setColumns(10);
        
        JLabel lblpassword = new JLabel("PW");
        lblpassword.setFont(new Font("D2Coding", Font.PLAIN, 20));
        lblpassword.setBounds(50, 233, 40, 53);
        frame.getContentPane().add(lblpassword);
        
        JButton btnEnter = new JButton("Enter");
        btnEnter.setBackground(SystemColor.inactiveCaptionBorder);
        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginOrFailed(frame);
            }
        });
        btnEnter.setFont(new Font("D2Coding", Font.PLAIN, 20));
        btnEnter.setBounds(341, 233, 93, 53);
        frame.getContentPane().add(btnEnter);
        
        JLabel lblTitle = new JLabel("Visitorsbook Program");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("D2Coding", Font.PLAIN, 30));
        lblTitle.setBounds(50, 42, 341, 63);
        frame.getContentPane().add(lblTitle);
        
        JButton btnJoin = new JButton("회원가입");
        btnJoin.setBackground(new Color(248, 248, 255));
        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JoinMember.newJoinMemberFrame(frame, visitorsbookMain.this);
            }
        });
        btnJoin.setFont(new Font("D2Coding", Font.PLAIN, 20));
        btnJoin.setBounds(114, 311, 133, 43);
        frame.getContentPane().add(btnJoin);
         
        textPassword = new JPasswordField();
        textPassword.setBackground(Color.WHITE);
        textPassword.setBorder(new LineBorder(new Color(192, 192, 192), 2, true));
        textPassword.setBounds(114, 234, 215, 52);
        frame.getContentPane().add(textPassword);
    }

    private void loginOrFailed(Component parent) {
        String id = textUserId.getText();
        char[] pwChars = textPassword.getPassword();
        StringBuilder builder = new StringBuilder();
        for (char c : pwChars) {
            builder.append(c);
        }
        String pw = builder.toString();
        
        if (dao.login(id, pw) == 1) {
            textUserId.setText("");
            textPassword.setText("");
            frame.setVisible(false);
            visitorsbookPost.visitorsPostList(id, frame);
            
        } else {
            JOptionPane.showMessageDialog(frame, 
                                        "ID와 비밀번호가 일치하지 않습니다.", 
                                        "Warning", 
                                        JOptionPane.WARNING_MESSAGE);
            return; 
        }
    }

    
    
    @Override
    public void onJoinMemberNotify() {
    }

}
