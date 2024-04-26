package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import dao.*;
import entity.*;
import util.*;

public class AddContactFrame extends JFrame implements ActionListener {
    User USER;
    Runnable runnable;
    JLabel nameLabel = new JLabel("姓名"),
            phoneLabel = new JLabel("电话");
    JTextField nameField = new JTextField(),
            phoneField = new JTextField();
    JButton addBtn = new JButton("添加");

    public AddContactFrame(User USER, Runnable runnable) {
        this.USER = USER;
        this.runnable = runnable;
        setLayout(new GridBagLayout());
        nameLabel.setFont(new Font(null, Font.BOLD, 20));
        phoneLabel.setFont(new Font(null, Font.BOLD, 20));
        nameField.setFont(new Font(null, Font.PLAIN, 20));
        phoneField.setFont(new Font(null, Font.PLAIN, 20));
        addBtn.setFont(new Font(null, Font.PLAIN, 20));
        addBtn.addActionListener(this);
        add(nameLabel, new GBC(0, 0).setWeight(100, 100).setInsets(10, 10, 10, 0));
        add(nameField,
                new GBC(1, 0, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10).setIpad(150, 0));
        add(phoneLabel, new GBC(0, 1).setWeight(100, 100).setInsets(10, 10, 10, 0));
        add(phoneField,
                new GBC(1, 1, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 10).setIpad(150, 0));
        add(addBtn, new GBC(0, 2, 3, 1).setInsets(10, 10, 10, 10).setIpad(100, 30));
        setTitle("添加常用联系人");
        setIconImage(new ImageIcon("assets/pic/frameIcon.png").getImage());
        setSize(400, 300);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            int result = Validate.isRealname(name);
            switch (result) {
                case 1:
                    JOptionPane.showMessageDialog(this, "姓名不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                case 2:
                    JOptionPane.showMessageDialog(this, "姓名长度不能大于10", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
            }
            result = UserDao.isContactExist(USER.getID(), name);
            switch (result) {
                case 1:
                    JOptionPane.showMessageDialog(this, "该联系人已存在", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                case -1:
                    JOptionPane.showMessageDialog(this, "数据库错误，请联系管理员", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
            }
            if (!Validate.isPhone(phone)) {
                JOptionPane.showMessageDialog(null, "电话号码不合法", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (UserDao.addContactInfo(USER.getID(), name, phone) == 1) {
                JOptionPane.showMessageDialog(this, "添加联系人" + name + "成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                runnable.run();
            }
            else
                JOptionPane.showMessageDialog(this, "添加联系人" + name + "失败，请联系管理员", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AddContactFrame(new User(1, "Zhang", "张三", "女", "123456", 0, "410223197203281185", "13195656565"), new Runnable() {
            public void run() {
                // NOTING
            }
        });
    }
}
