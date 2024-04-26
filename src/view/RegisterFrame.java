package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import dao.*;
import entity.*;
import util.*;

public class RegisterFrame extends JFrame implements ActionListener {
    JLabel usernameLabel = new JLabel("用户名"),
            realNameLabel = new JLabel("真实姓名"),
            sexLabel = new JLabel("性别"),
            idNumberLabel = new JLabel("身份证号"),
            phoneLabel = new JLabel("联系电话"),
            passwordLabel = new JLabel("密码"),
            confirmPasswordLabel = new JLabel("确认密码");
    JTextField usernameField = new JTextField(),
            realNameField = new JTextField(),
            idNumberField = new JTextField(),
            phoneField = new JTextField();
    JPasswordField passwordField = new JPasswordField(),
            confirmPasswordField = new JPasswordField();
    JRadioButton maleRadioButton = new JRadioButton("男"),
            femaleRadioButton = new JRadioButton("女");
    ButtonGroup sexButtonGroup = new ButtonGroup();
    Box sexBox = Box.createHorizontalBox();
    JButton registerBtn = new JButton("注册");

    public RegisterFrame() {
        setLayout(new GridBagLayout());
        usernameLabel.setFont(new Font(null, Font.BOLD, 20));
        realNameLabel.setFont(new Font(null, Font.BOLD, 20));
        sexLabel.setFont(new Font(null, Font.BOLD, 20));
        idNumberLabel.setFont(new Font(null, Font.BOLD, 20));
        phoneLabel.setFont(new Font(null, Font.BOLD, 20));
        passwordLabel.setFont(new Font(null, Font.BOLD, 20));
        confirmPasswordLabel.setFont(new Font(null, Font.BOLD, 20));
        usernameField.setFont(new Font(null, Font.PLAIN, 20));
        realNameField.setFont(new Font(null, Font.PLAIN, 20));
        idNumberField.setFont(new Font(null, Font.PLAIN, 20));
        phoneField.setFont(new Font(null, Font.PLAIN, 20));
        passwordField.setFont(new Font(null, Font.PLAIN, 20));
        confirmPasswordField.setFont(new Font(null, Font.PLAIN, 20));
        maleRadioButton.setFont(new Font(null, Font.PLAIN, 20));
        femaleRadioButton.setFont(new Font(null, Font.PLAIN, 20));
        registerBtn.setFont(new Font(null, Font.PLAIN, 20));
        sexButtonGroup.add(maleRadioButton);
        sexButtonGroup.add(femaleRadioButton);
        maleRadioButton.setSelected(true);
        sexBox.add(maleRadioButton);
        sexBox.add(Box.createHorizontalStrut(30));
        sexBox.add(femaleRadioButton);
        registerBtn.addActionListener(this);
        add(usernameLabel, new GBC(0, 0).setWeight(100, 100).setInsets(10, 10, 10, 10));
        add(usernameField, new GBC(1, 0, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10));
        add(realNameLabel, new GBC(0, 1).setWeight(100, 100).setInsets(10, 10, 10, 10));
        add(realNameField, new GBC(1, 1, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10));
        add(sexLabel, new GBC(0, 2).setWeight(100, 100).setInsets(10, 10, 10, 10));
        add(sexBox, new GBC(1, 2, 2, 1).setWeight(100, 100).setInsets(10, 0, 10, 10));
        add(idNumberLabel, new GBC(0, 3).setWeight(100, 100).setInsets(10, 10, 10, 10));
        add(idNumberField, new GBC(1, 3, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10));
        add(phoneLabel, new GBC(0, 4).setWeight(100, 100).setInsets(10, 10, 10, 10));
        add(phoneField, new GBC(1, 4, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10));
        add(passwordLabel, new GBC(0, 5).setWeight(100, 100).setInsets(10, 10, 10, 10));
        add(passwordField, new GBC(1, 5, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10));
        add(confirmPasswordLabel, new GBC(0, 6).setWeight(100, 100).setInsets(10, 10, 10, 10));
        add(confirmPasswordField, new GBC(1, 6, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10));
        add(registerBtn,
                new GBC(0, 7, 3, 1).setFill(GBC.VERTICAL).setWeight(100, 50).setInsets(10, 0, 10, 0).setIpad(80, 0));
        setTitle("注册");
        setIconImage(new ImageIcon("assets/pic/frameIcon.png").getImage());
        setSize(600, 800);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerBtn) {
            String username = usernameField.getText();
            String realName = realNameField.getText();
            String sex = maleRadioButton.isSelected() ? "男" : "女";
            String idNumber = idNumberField.getText();
            String phone = phoneField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            int result = Validate.isUsername(username);
            switch (result) {
                case 1:
                    JOptionPane.showMessageDialog(null, "用户名不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                case 2:
                    JOptionPane.showMessageDialog(null, "用户名长度不能大于20", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                case 3:
                    JOptionPane.showMessageDialog(null, "用户名已存在", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                case -1:
                    JOptionPane.showMessageDialog(null, "数据库错误，请联系管理员", "错误", JOptionPane.ERROR_MESSAGE);
            }
            result = Validate.isRealname(realName);
            switch (result) {
                case 1:
                    JOptionPane.showMessageDialog(null, "真实姓名不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                case 2:
                    JOptionPane.showMessageDialog(null, "真实姓名长度不能大于10", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
            }
            if (!Validate.isID(idNumber)) {
                JOptionPane.showMessageDialog(null, "身份证号码不合法", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (UserDao.isIdNumberExist(idNumber)) {
                JOptionPane.showMessageDialog(null, "身份证号码已存在，请勿重复注册", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!Validate.isPhone(phone)) {
                JOptionPane.showMessageDialog(null, "联系电话不合法", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            result = Validate.isPassword(password);
            switch (result) {
                case 1:
                    JOptionPane.showMessageDialog(null, "密码长度不能小于6", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                case 2:
                    JOptionPane.showMessageDialog(null, "密码长度不能大于20", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
            }
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "两次输入的密码不一致", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            var newUser = new User(username, realName, sex, password, 0, idNumber, phone);
            if (UserDao.addNewUser(newUser) == 1) {
                JOptionPane.showMessageDialog(null, "注册成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
            else
                JOptionPane.showMessageDialog(null, "注册失败，请联系管理员！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new RegisterFrame();
    }
}
