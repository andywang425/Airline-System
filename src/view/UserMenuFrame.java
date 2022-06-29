package view;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import util.*;
import entity.*;

public class UserMenuFrame extends JFrame implements ActionListener {
    User USER;
    JButton contactInfoManagementBtn = new JButton("常用联系人信息管理"),
            myOrderBtn = new JButton("我的订单"),
            ticketQueryBtn = new JButton("机票查询与购买"),
            modInfoBtn = new JButton("个人信息修改"),
            logoutBtn = new JButton("退出登录");
    ArrayList<JFrame> frames = new ArrayList<JFrame>();

    private class TopPanel extends JPanel {
        public TopPanel() {
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createTitledBorder("个人中心"));
            contactInfoManagementBtn.setFont(new Font(null, Font.PLAIN, 20));
            myOrderBtn.setFont(new Font(null, Font.PLAIN, 20));
            modInfoBtn.setFont(new Font(null, Font.PLAIN, 20));
            logoutBtn.setFont(new Font(null, Font.PLAIN, 20));
            ticketQueryBtn.setFont(new Font(null, Font.PLAIN, 20));
            contactInfoManagementBtn.addActionListener(UserMenuFrame.this);
            myOrderBtn.addActionListener(UserMenuFrame.this);
            modInfoBtn.addActionListener(UserMenuFrame.this);
            logoutBtn.addActionListener(UserMenuFrame.this);
            ticketQueryBtn.addActionListener(UserMenuFrame.this);
            add(contactInfoManagementBtn,
                    new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
            add(myOrderBtn, new GBC(0, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
            add(ticketQueryBtn, new GBC(0, 2).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
            add(modInfoBtn, new GBC(0, 3).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
            add(logoutBtn, new GBC(0, 4).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
        }
    }

    public UserMenuFrame(User USER) {
        this.USER = USER;
        setLayout(new GridBagLayout());
        add(new TopPanel(), new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                for (JFrame frame : frames) {
                    frame.dispose();
                }
                dispose();
            }
        });
        setTitle("航空订票管理系统");
        setIconImage(new ImageIcon("assets/pic/frameIcon.png").getImage());
        setSize(400, 600);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == contactInfoManagementBtn) {
            frames.add(new ContactInfoFrame(USER));
        } else if (e.getSource() == myOrderBtn) {
            frames.add(new MyOrderFrame(USER));
        } else if (e.getSource() == ticketQueryBtn) {
            frames.add(new TicketQueryFrame(USER));
        } else if (e.getSource() == modInfoBtn) {
            frames.add(new ModUserInfoFrame(USER, new Runnable() {
                @Override
                public void run() {
                    for (JFrame frame : frames) {
                        frame.dispose();
                        dispose();
                    }
                }
            }));
        } else if (e.getSource() == logoutBtn) {
            int choice = JOptionPane.showConfirmDialog(this, "确定退出登录？", "提示", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                for (JFrame frame : frames) {
                    frame.dispose();
                }
                dispose();
                new LoginFrame();
            }
        }
    }

    public static void main(String[] args) {
        new UserMenuFrame(new User(1, "Zhang", "张三", "女", "123456", 0, "410223197203281185", "13195656565"));
    }
}
