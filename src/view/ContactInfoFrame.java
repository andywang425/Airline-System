package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import dao.*;
import entity.*;
import util.*;

public class ContactInfoFrame extends JFrame implements ActionListener {
    User USER;
    String rows[][];
    String colums[] = { "姓名", "联系电话" };
    JTable table;
    JButton addBtn = new JButton("添加联系人"),
            deleteBtn = new JButton("删除联系人");

    private class BottomPanel extends JPanel {
        public BottomPanel() {
            setLayout(new GridBagLayout());
            addBtn.setFont(new Font(null, Font.PLAIN, 20));
            deleteBtn.setFont(new Font(null, Font.PLAIN, 20));
            deleteBtn.setEnabled(false);
            addBtn.addActionListener(ContactInfoFrame.this);
            deleteBtn.addActionListener(ContactInfoFrame.this);
            add(addBtn,
                    new GBC(0, 0).setFill(GBC.HORIZONTAL).setWeight(100, 100).setInsets(0, 10, 0, 10).setIpad(0, 10));
            add(deleteBtn,
                    new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(100, 100).setInsets(0, 10, 0, 10).setIpad(0, 10));
        }
    }

    public ContactInfoFrame(User USER) {
        this.USER = USER;
        setLayout(new GridBagLayout());
        rows = UserDao.getContactInfoByID(USER.getID());
        table = new JTable(rows, colums) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setFont(new Font(null, Font.PLAIN, 20));
        table.setRowHeight(40);
        table.getSelectionModel().addListSelectionListener(x -> {
            if (table.getSelectedRow() != -1) {
                deleteBtn.setEnabled(true);
            } else {
                deleteBtn.setEnabled(false);
            }
        });
        table.getTableHeader().setReorderingAllowed(false);
        add(new JScrollPane(table), new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 2000).setInsets(10, 10, 0, 10));
        add(new BottomPanel(), new GBC(0, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
        setTitle("航空订票管理系统");
        setIconImage(new ImageIcon("assets/pic/frameIcon.png").getImage());
        setSize(600, 400);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            new AddContactFrame(USER, new Runnable() {
                @Override
                public void run() {
                    rows = UserDao.getContactInfoByID(USER.getID());
                    table.setModel(new DefaultTableModel(rows, colums));
                }
            });
        }
        if (e.getSource() == deleteBtn) {
            int row = table.getSelectedRow();
            String name = table.getValueAt(row, 0).toString();
            // 确认
            int result = JOptionPane.showConfirmDialog(this, "确定删除联系人" + name + "吗？", "删除联系人",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                if (UserDao.deleteContactInfoByID(USER.getID(), name) == 1) {
                    JOptionPane.showMessageDialog(this, "删除联系人" + name + "成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                    rows = UserDao.getContactInfoByID(USER.getID());
                    table.setModel(new DefaultTableModel(rows, colums));
                } else
                    JOptionPane.showMessageDialog(this, "删除联系人" + name + "失败，请联系管理员", "失败", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public static void main(String[] args) {
        new ContactInfoFrame(new User(1, "Zhang", "张三", "女", "123456", 0, "410223197203281185", "13195656565"));
    }
}
