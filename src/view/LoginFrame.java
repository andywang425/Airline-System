package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import dao.*;
import util.*;

public class LoginFrame extends JFrame implements ActionListener, MouseListener {
    JLabel iconLabel = new JLabel(new ImageIcon("assets/pic/airplane.png")),
            titleLabel = new JLabel("欢迎使用航空订票管理系统"),
            usernameLabel = new JLabel("用户名"),
            passwordLabel = new JLabel("密码"),
            registerLabel = new JLabel("<html><u>立即注册</u></html>");
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginBtn = new JButton("登录");

    private class TopPanel extends JPanel {
        public TopPanel() {
            setLayout(new GridBagLayout());
            titleLabel.setFont(new Font(null, Font.PLAIN, 40));
            add(iconLabel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 100));
            add(titleLabel, new GBC(1, 0).setFill(GBC.BOTH).setWeight(100, 100));
        }
    }

    private class MidPanel extends JPanel {
        public MidPanel() {
            setLayout(new GridBagLayout());
            usernameLabel.setFont(new Font(null, Font.BOLD, 20));
            passwordLabel.setFont(new Font(null, Font.BOLD, 20));
            usernameField.setFont(new Font(null, Font.PLAIN, 20));
            passwordField.setFont(new Font(null, Font.PLAIN, 20));
            usernameField.setHorizontalAlignment(JTextField.CENTER);
            passwordField.setHorizontalAlignment(JPasswordField.CENTER);
            add(usernameLabel, new GBC(0, 0).setWeight(20, 20).setInsets(10, 10, 10, 10));
            add(usernameField, new GBC(1, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10));
            add(passwordLabel, new GBC(0, 1).setWeight(20, 20).setInsets(10, 10, 10, 10));
            add(passwordField, new GBC(1, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10));
        }
    }

    private class BottomPanel extends JPanel {
        public BottomPanel() {
            setLayout(new GridBagLayout());
            loginBtn.setFont(new Font(null, Font.PLAIN, 20));
            registerLabel.setFont(new Font(null, Font.PLAIN, 20));
            registerLabel.setForeground(new Color(0, 120, 255));
            registerLabel.addMouseListener(LoginFrame.this);
            loginBtn.addActionListener(LoginFrame.this);
            add(loginBtn,
                    new GBC(0, 0).setFill(GBC.VERTICAL).setWeight(100, 50).setInsets(10, 90, 10, 0).setIpad(100, 0));
            add(registerLabel, new GBC(1, 0).setAnchor(GBC.SOUTHEAST));
        }
    }

    public LoginFrame() {
        setLayout(new GridBagLayout());

        add(new TopPanel(), new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
        add(new MidPanel(), new GBC(0, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
        add(new BottomPanel(), new GBC(0, 2).setFill(GBC.BOTH).setWeight(100, 50).setInsets(10, 10, 10, 10));

        setTitle("航空订票管理系统");
        setIconImage(new ImageIcon("assets/pic/frameIcon.png").getImage());
        setSize(800, 600);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            int result = Validate.login(username, password);
            switch (result) {
                case 0:
                    JOptionPane.showMessageDialog(this, "用户名或密码错误", "错误", JOptionPane.ERROR_MESSAGE);
                    break;
                case 1:
                    JOptionPane.showMessageDialog(this, "欢迎，用户" + username, "成功", JOptionPane.INFORMATION_MESSAGE);
                    new UserMenuFrame(UserDao.getUserInfobyName(username));
                    dispose();
                    break;
                case 2:
                    JOptionPane.showMessageDialog(this, "欢迎，管理员" + username, "成功", JOptionPane.INFORMATION_MESSAGE);
                    new AdminMenuFrame(AdminDao.getAdminInfobyName(username));
                    dispose();
                    break;
                case -1:
                    JOptionPane.showMessageDialog(this, "数据库内部错误，请联系管理员", "错误", JOptionPane.ERROR_MESSAGE);
                    break;
                case -2:
                    JOptionPane.showMessageDialog(this, "用户名或密码为空", "错误", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == registerLabel) {
            new RegisterFrame();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // NOTING TO DO

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // NOTING TO DO

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == registerLabel) {
            registerLabel.setForeground(new Color(0, 0, 255));
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == registerLabel) {
            registerLabel.setForeground(new Color(0, 120, 255));
        }

    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
