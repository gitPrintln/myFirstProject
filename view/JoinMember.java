package edu.java.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.java.controller.VisitorsDaoImpl;
import edu.java.model.UserInfo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.SystemColor;
import java.awt.Color;

public class JoinMember extends JFrame {

    @FunctionalInterface
    public interface onJoinMemberListener {
        void onJoinMemberNotify();
    }
    
    private onJoinMemberListener listener;
    private Component parent;
    private VisitorsDaoImpl dao;
    
    private JPanel contentPane;
    private JTextField textUserId;
    private JTextField textName;
    private JTextField textPhoneNum;
    private JTextField textEmail;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public static void newJoinMemberFrame(Component parent, onJoinMemberListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JoinMember frame = new JoinMember(parent, listener);
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
    public JoinMember(Component parent, onJoinMemberListener listener) {
        this.parent = parent;
        this.listener = listener;
        this.dao = VisitorsDaoImpl.getInstance();
        initialize();
    }
    
    public void initialize() {
        setTitle("회원 가입");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX(); // 부모 창의 x 좌표
        int y = parent.getY(); // 부모 창의 y 좌표
        
        setBounds(x, y, 422, 475);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblTitle = new JLabel("회원가입");
        lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitle.setFont(new Font("D2Coding", Font.BOLD, 20));
        lblTitle.setBounds(12, 41, 98, 30);
        contentPane.add(lblTitle);
        
        JLabel lblUserId = new JLabel("ID");
        lblUserId.setHorizontalAlignment(SwingConstants.LEFT);
        lblUserId.setFont(new Font("D2Coding", Font.PLAIN, 20));
        lblUserId.setBounds(12, 81, 98, 30);
        contentPane.add(lblUserId);
        
        JLabel lblPassword = new JLabel("PW");
        lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
        lblPassword.setFont(new Font("D2Coding", Font.PLAIN, 20));
        lblPassword.setBounds(12, 121, 98, 30);
        contentPane.add(lblPassword);
        
        JLabel lblName = new JLabel("이름");
        lblName.setHorizontalAlignment(SwingConstants.LEFT);
        lblName.setFont(new Font("D2Coding", Font.PLAIN, 20));
        lblName.setBounds(12, 161, 98, 30);
        contentPane.add(lblName);
        
        JLabel lblPhoneNum = new JLabel("전화번호");
        lblPhoneNum.setHorizontalAlignment(SwingConstants.LEFT);
        lblPhoneNum.setFont(new Font("D2Coding", Font.PLAIN, 20));
        lblPhoneNum.setBounds(12, 201, 98, 30);
        contentPane.add(lblPhoneNum);
        
        JLabel lblEmail = new JLabel("이메일");
        lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
        lblEmail.setFont(new Font("D2Coding", Font.PLAIN, 20));
        lblEmail.setBounds(12, 241, 98, 30);
        contentPane.add(lblEmail);
        
        textUserId = new JTextField();
        textUserId.setFont(new Font("D2Coding", Font.PLAIN, 20));
        textUserId.setColumns(10);
        textUserId.setBounds(116, 81, 154, 30);
        contentPane.add(textUserId);
        
        textName = new JTextField();
        textName.setFont(new Font("D2Coding", Font.PLAIN, 20));
        textName.setColumns(10);
        textName.setBounds(116, 161, 154, 30);
        contentPane.add(textName);
        
        textPhoneNum = new JTextField();
        textPhoneNum.setFont(new Font("D2Coding", Font.PLAIN, 20));
        textPhoneNum.setColumns(10);
        textPhoneNum.setBounds(116, 201, 266, 30);
        contentPane.add(textPhoneNum);
        
        textEmail = new JTextField();
        textEmail.setFont(new Font("D2Coding", Font.PLAIN, 20));
        textEmail.setColumns(10);
        textEmail.setBounds(116, 241, 266, 30);
        contentPane.add(textEmail);
        
        JButton btnEnter = new JButton("가입완료");
        btnEnter.setBackground(new Color(248, 248, 255));
        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinNewMember();
            }
        });
        btnEnter.setFont(new Font("D2Coding", Font.PLAIN, 18));
        btnEnter.setBounds(254, 386, 128, 40);
        contentPane.add(btnEnter);
        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("굴림", Font.PLAIN, 20));
        passwordField.setBounds(116, 121, 154, 30);
        contentPane.add(passwordField);
        
        JButton btnCheck = new JButton("중복확인");
        btnCheck.setBackground(SystemColor.inactiveCaptionBorder);
        btnCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String checkId = textUserId.getText();
                idOverlapCheck(checkId);
            }
        });
        btnCheck.setFont(new Font("D2Coding", Font.PLAIN, 18));
        btnCheck.setBounds(278, 81, 116, 30);
        contentPane.add(btnCheck);
    }

    private void idOverlapCheck(String checkId) {
        int result = dao.idDuplicationCheck(checkId);
        if (result == 1) {
            if (checkId.equals("")) {
                JOptionPane.showMessageDialog(this, "아이디를 입력해 주세요.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "사용 가능한 아이디입니다.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            JOptionPane.showMessageDialog(this, "이미 사용중인 아이디입니다.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }

    private void joinNewMember() {
        String userId = textUserId.getText();
        char[] pwChars = passwordField.getPassword();
        StringBuilder builder = new StringBuilder();
        for (char c : pwChars) {
            builder.append(c);
        }
        String password = builder.toString();
        
        String name = textName.getText();
        String phoneNum = textPhoneNum.getText();
        String email = textEmail.getText();
        
        if (userId.equals("")) {
            JOptionPane.showMessageDialog(this, "ID를 입력하시오.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (password.equals("")) {
            JOptionPane.showMessageDialog(this, "PW를 입력하시오.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (name.equals("")) {
            JOptionPane.showMessageDialog(this, "이름을 입력하시오.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (phoneNum.equals("")) {
            JOptionPane.showMessageDialog(this, "전화 번호를 입력하시오.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        UserInfo newUser = new UserInfo(userId, password, name, phoneNum, email);
        dispose();
        int result = dao.insert(newUser);
        if (result == 1) {
            JOptionPane.showMessageDialog(parent, "회원가입 성공했습니다.", "information", JOptionPane.INFORMATION_MESSAGE);
            listener.onJoinMemberNotify();
        } else {
            JOptionPane.showMessageDialog(parent, "회원가입 실패했습니다.", "information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
