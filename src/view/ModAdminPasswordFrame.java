package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import dao.*;
import entity.*;
import util.*;

public class ModAdminPasswordFrame extends JFrame implements ActionListener {
    Admin ADMIN;
    Runnable runnable;
    JLabel oldPasswordLabel = new JLabel("原密码"),
            passwordLabel = new JLabel("新密码"),
            confirmPasswordLabel = new JLabel("确认密码");
    JPasswordField oldPasswordField = new JPasswordField(),
            passwordField = new JPasswordField(),
            confirmPasswordField = new JPasswordField();
    JButton confirmBtn = new JButton("确认");

    public ModAdminPasswordFrame(Admin ADMIN, Runnable runnable) {
        this.ADMIN = ADMIN;
        this.runnable = runnable;
        setLayout(new GridBagLayout());
        oldPasswordLabel.setFont(new Font(null, Font.BOLD, 20));
        passwordLabel.setFont(new Font(null, Font.BOLD, 20));
        confirmPasswordLabel.setFont(new Font(null, Font.BOLD, 20));
        oldPasswordField.setFont(new Font(null, Font.PLAIN, 20));
        passwordField.setFont(new Font(null, Font.PLAIN, 20));
        confirmPasswordField.setFont(new Font(null, Font.PLAIN, 20));
        confirmBtn.setFont(new Font(null, Font.PLAIN, 20));
        confirmBtn.addActionListener(this);
        add(oldPasswordLabel, new GBC(0, 0).setWeight(100, 100).setInsets(10, 10, 10, 0));
        add(oldPasswordField,
                new GBC(1, 0, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10).setIpad(200, 0));
        add(passwordLabel, new GBC(0, 1).setWeight(100, 100).setInsets(10, 10, 10, 0));
        add(passwordField,
                new GBC(1, 1, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10).setIpad(200, 0));
        add(confirmPasswordLabel, new GBC(0, 2).setWeight(100, 100).setInsets(10, 10, 10, 0));
        add(confirmPasswordField,
                new GBC(1, 2, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10).setIpad(200, 0));
        add(confirmBtn,
                new GBC(0, 3, 3, 1).setFill(GBC.VERTICAL).setWeight(100, 25).setInsets(10, 0, 10, 0).setIpad(100, 0));
        setTitle("密码修改");
        setIconImage(new ImageIcon("assets/pic/frameIcon.png").getImage());
        setSize(400, 400);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmBtn) {
            String oldPassword = new String(oldPasswordField.getPassword());
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            if (oldPassword.length() == 0) {
                JOptionPane.showMessageDialog(null, "请输入原密码", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!oldPassword.equals(ADMIN.getPassword())) {
                JOptionPane.showMessageDialog(null, "原密码错误", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "两次输入的密码不一致", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            var newAdmin = new Admin(ADMIN.getAid(), ADMIN.getName(), password);
            if (AdminDao.updateAdmin(newAdmin) == 1) {
                JOptionPane.showMessageDialog(null, "修改密码成功，请重新登录", "提示", JOptionPane.INFORMATION_MESSAGE);
                runnable.run();
                new LoginFrame();
            } else {
                JOptionPane.showMessageDialog(null, "修改失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public static void main(String[] args) {
        new ModAdminPasswordFrame(new Admin(1, "admin", "admin"), () -> {
            // NOTHING
        });
    }

}
