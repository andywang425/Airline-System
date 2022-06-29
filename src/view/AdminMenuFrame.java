package view;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import util.*;
import entity.*;

public class AdminMenuFrame extends JFrame implements ActionListener {
    Admin ADMIN;
    JButton airlineButton = new JButton("航线与机票管理"),
            orderButton = new JButton("订单查询与统计"),
            passwordButton = new JButton("密码修改"),
            logoutButton = new JButton("退出登录");
    ArrayList<JFrame> frameList = new ArrayList<JFrame>();

    private class TopPanel extends JPanel {
        public TopPanel() {
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createTitledBorder("控制面板"));
            airlineButton.setFont(new Font(null, Font.PLAIN, 20));
            orderButton.setFont(new Font(null, Font.PLAIN, 20));
            passwordButton.setFont(new Font(null, Font.PLAIN, 20));
            logoutButton.setFont(new Font(null, Font.PLAIN, 20));
            airlineButton.addActionListener(AdminMenuFrame.this);
            orderButton.addActionListener(AdminMenuFrame.this);
            passwordButton.addActionListener(AdminMenuFrame.this);
            logoutButton.addActionListener(AdminMenuFrame.this);
            add(airlineButton,
                    new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
            add(orderButton, new GBC(0, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
            add(passwordButton, new GBC(0, 2).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
            add(logoutButton, new GBC(0, 3).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
        }
    }

    public AdminMenuFrame(Admin ADMIN) {
        this.ADMIN = ADMIN;
        setLayout(new GridBagLayout());
        add(new TopPanel(), new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
        setTitle("航空订票管理系统");
        setIconImage(new ImageIcon("assets/pic/frameIcon.png").getImage());
        setSize(400, 600);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == airlineButton) {
            frameList.add(new AirlineAndTicketManageFrame(ADMIN));
        } else if (e.getSource() == orderButton) {
            frameList.add(new OrderQueryAndStatFrame(ADMIN));
        } else if (e.getSource() == passwordButton) {
            frameList.add(new ModAdminPasswordFrame(ADMIN, new Runnable() {
                @Override
                public void run() {
                    for (JFrame frame : frameList) {
                        frame.dispose();
                        dispose();
                    }
                }
            }));
        } else if (e.getSource() == logoutButton) {
            int choice = JOptionPane.showConfirmDialog(this, "确定退出登录？", "提示", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                for (JFrame frame : frameList) {
                    frame.dispose();
                }
                dispose();
                new LoginFrame();
            }
        }
    }

    public static void main(String[] args) {
        new AdminMenuFrame(new Admin(1, "admin", "admin"));
    }
}
