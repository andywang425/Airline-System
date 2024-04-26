package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import dao.*;
import entity.*;
import util.*;

public class ModUserInfoFrame extends JFrame implements ActionListener {
    User USER;
    Runnable runnable;
    JLabel usernameLabel = new JLabel("用户名"),
            phoneLabel = new JLabel("联系电话"),
            tipsLabel = new JLabel("若不修改密码，以下信息不需要填写"),
            oldPasswordLabel = new JLabel("原密码"),
            passwordLabel = new JLabel("新密码"),
            confirmPasswordLabel = new JLabel("确认密码");
    JTextField usernameField = new JTextField(),
            phoneField = new JTextField();
    JPasswordField oldPasswordField = new JPasswordField(),
            passwordField = new JPasswordField(),
            confirmPasswordField = new JPasswordField();
    JButton confirmBtn = new JButton("确认");

    private class TopPanel extends JPanel {
        public TopPanel() {
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createTitledBorder("基础信息"));
            usernameLabel.setFont(new Font(null, Font.BOLD, 20));
            phoneLabel.setFont(new Font(null, Font.BOLD, 20));
            passwordLabel.setFont(new Font(null, Font.BOLD, 20));
            confirmPasswordLabel.setFont(new Font(null, Font.BOLD, 20));
            usernameField.setFont(new Font(null, Font.PLAIN, 20));
            phoneField.setFont(new Font(null, Font.PLAIN, 20));
            usernameField.setText(USER.getUsername());
            phoneField.setText(USER.getPhone());
            add(usernameLabel, new GBC(0, 0).setWeight(100, 100).setInsets(10, 10, 10, 0));
            add(usernameField,
                    new GBC(1, 0, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10).setIpad(200, 0));
            add(phoneLabel, new GBC(0, 1).setWeight(100, 100).setInsets(10, 10, 10, 0));
            add(phoneField, new GBC(1, 1, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10));
        }
    }

    private class BottomPanel extends JPanel {
        public BottomPanel() {
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createTitledBorder("修改密码（若不修改密码，以下信息不需要填写）"));
            passwordField.setFont(new Font(null, Font.PLAIN, 20));
            confirmPasswordField.setFont(new Font(null, Font.PLAIN, 20));
            oldPasswordLabel.setFont(new Font(null, Font.BOLD, 20));
            oldPasswordField.setFont(new Font(null, Font.PLAIN, 20));
            confirmBtn.setFont(new Font(null, Font.PLAIN, 20));
            confirmBtn.addActionListener(ModUserInfoFrame.this);

            add(oldPasswordLabel, new GBC(0, 0).setWeight(100, 100).setInsets(10, 10, 10, 0));
            add(oldPasswordField,
                    new GBC(1, 0, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10).setIpad(200, 0));
            add(passwordLabel, new GBC(0, 1).setWeight(100, 100).setInsets(10, 10, 10, 0));
            add(passwordField, new GBC(1, 1, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10));
            add(confirmPasswordLabel, new GBC(0, 2).setWeight(100, 100).setInsets(10, 10, 10, 0));
            add(confirmPasswordField,
                    new GBC(1, 2, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10));
        }
    }

    public ModUserInfoFrame(User USER, Runnable runnable) {
        this.USER = USER;
        this.runnable = runnable;
        setLayout(new GridBagLayout());
        add(new TopPanel(), new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
        add(new BottomPanel(), new GBC(0, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
        add(confirmBtn,
                new GBC(0, 2).setFill(GBC.VERTICAL).setWeight(100, 15).setInsets(20, 0, 20, 0).setIpad(90,
                        0));
        setTitle("个人信息修改");
        setIconImage(new ImageIcon("assets/pic/frameIcon.png").getImage());
        setSize(400, 600);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmBtn) {
            String username = usernameField.getText();
            String phone = phoneField.getText();
            String oldPassword = new String(oldPasswordField.getPassword());
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            Boolean passwordIsModified = false;
            int result = Validate.isUsername(username);
            switch (result) {
                case 1:
                    JOptionPane.showMessageDialog(null, "用户名不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                case 2:
                    JOptionPane.showMessageDialog(null, "用户名长度不能大于20", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                case 3:
                    if (!USER.getUsername().equals(username)) {
                        JOptionPane.showMessageDialog(null, "用户名已存在", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        break;
                    }
                case -1:
                    JOptionPane.showMessageDialog(null, "数据库错误，请联系管理员", "错误", JOptionPane.ERROR_MESSAGE);
            }
            if (!Validate.isPhone(phone)) {
                JOptionPane.showMessageDialog(null, "联系电话不合法", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (oldPassword.length() != 0 || password.length() != 0 || confirmPassword.length() != 0) {
                passwordIsModified = true;
                if (oldPassword.length() == 0) {
                    JOptionPane.showMessageDialog(null, "请输入原密码", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!oldPassword.equals(USER.getPassword())) {
                    JOptionPane.showMessageDialog(null, "原密码错误", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                result = Validate.isPassword(password);
                switch (result) {
                    case 1:
                        JOptionPane.showMessageDialog(null, "新密码长度不能小于6", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    case 2:
                        JOptionPane.showMessageDialog(null, "新密码长度不能大于20", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                }
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "两次输入的密码不一致", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (USER.getUsername().equals(username) && USER.getPhone().equals(phone)
                    && (!passwordIsModified || USER.getPassword().equals(password))) {
                JOptionPane.showMessageDialog(null, "您没有修改任何信息", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            var newUser = new User(USER.getID(), username, USER.getRealName(), USER.getSex(),
                    passwordIsModified ? password : USER.getPassword(),
                    USER.getPoints(),
                    USER.getIDNumber(), phone);
            if (UserDao.updateUser(USER.getID(), newUser) == 1) {
                JOptionPane.showMessageDialog(null, "修改个人信息成功，请重新登录", "提示", JOptionPane.INFORMATION_MESSAGE);
                runnable.run();
                new LoginFrame();
            } else
                JOptionPane.showMessageDialog(null, "修改个人信息失败，请联系管理员", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new ModUserInfoFrame(new User(1, "Zhang", "张三", "女", "123456", 0, "410223197203281185", "13195656565"),
                new Runnable() {
                    @Override
                    public void run() {
                        // NOTING
                    }
                });
    }
}
