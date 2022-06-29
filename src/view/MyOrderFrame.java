package view;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import dao.*;
import util.*;
import entity.*;

public class MyOrderFrame extends JFrame implements ActionListener {
    User USER;
    JButton refundButton = new JButton("退票"),
            rebookButton = new JButton("改签");
    ArrayList<JFrame> frames = new ArrayList<JFrame>();
    String colums[] = new String[] { "航空公司", "机型", "航班号", "起飞时间", "抵达时间", "出发地", "目的地", "座位号", "舱位", "购买价格", "下单时间" };
    String rows[][] = new String[][] {};
    JTable table = new JTable();
    String orderRows[][];
    String airlineRows[][];

    private class TablePanel extends JPanel {
        private static void setTableWeight(JTable t) {
            t.getColumnModel().getColumn(0).setMaxWidth(120);
            t.getColumnModel().getColumn(0).setMinWidth(120);
            t.getColumnModel().getColumn(1).setMaxWidth(50);
            t.getColumnModel().getColumn(1).setMinWidth(50);
            t.getColumnModel().getColumn(2).setMaxWidth(80);
            t.getColumnModel().getColumn(2).setMinWidth(80);
            t.getColumnModel().getColumn(3).setMinWidth(200);
            t.getColumnModel().getColumn(3).setMaxWidth(200);
            t.getColumnModel().getColumn(4).setMinWidth(200);
            t.getColumnModel().getColumn(4).setMaxWidth(200);
            t.getColumnModel().getColumn(5).setMinWidth(80);
            t.getColumnModel().getColumn(5).setMaxWidth(80);
            t.getColumnModel().getColumn(6).setMinWidth(80);
            t.getColumnModel().getColumn(6).setMaxWidth(80);
            t.getColumnModel().getColumn(7).setMinWidth(60);
            t.getColumnModel().getColumn(7).setMaxWidth(60);
            t.getColumnModel().getColumn(8).setMinWidth(80);
            t.getColumnModel().getColumn(8).setMaxWidth(80);
            t.getColumnModel().getColumn(10).setMinWidth(200);
            t.getColumnModel().getColumn(10).setMaxWidth(200);
        }

        public TablePanel() {
            setLayout(new GridBagLayout());
            getTableData();
            table = new JTable(rows, colums) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table.setFont(new Font(null, Font.PLAIN, 20));
            table.setRowHeight(30);
            table.getSelectionModel().addListSelectionListener(x -> {
                if (table.getSelectedRow() != -1) {
                    refundButton.setEnabled(true);
                    rebookButton.setEnabled(true);
                } else {
                    refundButton.setEnabled(false);
                    rebookButton.setEnabled(false);
                }
            });
            table.getTableHeader().setReorderingAllowed(false);
            setTableWeight(table);
            add(new JScrollPane(table), new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 1000).setInsets(10, 10, 10, 10));
        }
    }

    private class ButtonPanel extends JPanel {
        public ButtonPanel() {
            setLayout(new GridBagLayout());
            refundButton.setFont(new Font(null, Font.PLAIN, 20));
            rebookButton.setFont(new Font(null, Font.PLAIN, 20));
            refundButton.addActionListener(MyOrderFrame.this);
            rebookButton.addActionListener(MyOrderFrame.this);
            refundButton.setEnabled(false);
            rebookButton.setEnabled(false);
            add(refundButton,
                    new GBC(0, 0).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(0, 10, 10, 10).setIpad(0, 50));
            add(rebookButton,
                    new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(0, 10, 10, 10).setIpad(0, 50));
        }
    }

    public MyOrderFrame(User USER) {
        this.USER = UserDao.getUserInfoByID(USER.getID());
        setLayout(new GridBagLayout());
        add(new TablePanel(), new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 2000).setInsets(10, 10, 10, 10));
        add(new ButtonPanel(), new GBC(0, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(0, 10, 10, 10));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                for (JFrame frame : frames) {
                    frame.dispose();
                }
                dispose();
            }
        });
        setTitle("我的订单");
        setIconImage(new ImageIcon("assets/pic/frameIcon.png").getImage());
        setSize(1350, 600);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == refundButton) {
            int row = table.getSelectedRow();
            int fid = Integer.parseInt(orderRows[row][0]);
            String seat = orderRows[row][1];
            String classType = orderRows[row][2];
            float price = Float.parseFloat(orderRows[row][3]);
            String flight = table.getValueAt(row, 2).toString();
            String priceStr = String.valueOf(price);
            String time = table.getValueAt(row, 10).toString();
            // 退票确认
            int result = JOptionPane.showConfirmDialog(null,
                    "是否要将您在" + time + "花费" + priceStr + "元购买的" + flight + "航班的机票退票？\n（会扣除1.2倍购买价格数量的积分作为惩罚）", "退票确认",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                if (UserDao.refundTicket(UserDao.getUserInfoByID(USER.getID()), fid, classType, seat, price)) {
                    JOptionPane.showMessageDialog(this, "退票成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    getTableData();
                    table.setModel(new DefaultTableModel(rows, colums));
                    TablePanel.setTableWeight(table);
                } else {
                    JOptionPane.showMessageDialog(this, "退票失败！", "失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == rebookButton) {
            int row = table.getSelectedRow();
            int fid = Integer.parseInt(orderRows[row][0]);
            float price = Float.parseFloat(orderRows[row][3]);
            String seat = orderRows[row][1];
            String classType = orderRows[row][2];
            String departure = airlineRows[row][5];
            String destination = airlineRows[row][6];
            String rebook[][] = UserDao.getRebookInfo(fid, departure, destination);
            if (rebook.length == 0) {
                JOptionPane.showMessageDialog(this, "没有可以改签的航班！", "失败", JOptionPane.ERROR_MESSAGE);
            } else {
                for (int i = 0; i < rebook.length; i++) {
                    int rebookFid = Integer.parseInt(rebook[i][0]);
                    Ticket newTicket = UserDao.getTicketInfoByFid(rebookFid);
                    if ((newTicket.getEconomyNum() == 0 && newTicket.getBusinessNum() == 0
                            && newTicket.getFirstNum() == 0)
                            ||
                            UserDao.checkIfBoughtTicket(USER.getID(), rebookFid)) {
                        rebook[i] = null;
                        continue;
                    }
                }
                ArrayList<String[]> newRebook = new ArrayList<String[]>();
                for (int i = 0; i < rebook.length; i++) {
                    if (rebook[i] != null) {
                        newRebook.add(rebook[i]);
                    }
                }
                if (newRebook.size() == 0) {
                    JOptionPane.showMessageDialog(this, "没有可以改签的航班！", "失败", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String[][] newRebookRows = new String[newRebook.size()][];
                for (int i = 0; i < newRebook.size(); i++) {
                    newRebookRows[i] = newRebook.get(i);
                }
                frames.add(new RebookFrame(USER, rebook, fid, classType, seat, price, () -> {
                    getTableData();
                    table.setModel(new DefaultTableModel(rows, colums));
                    TablePanel.setTableWeight(table);
                }));
            }
        }
    }

    private void getTableData() {
        orderRows = UserDao.getUserOrder(USER.getID()); // 5列
        airlineRows = new String[orderRows.length][7]; // 7列

        for (int i = 0; i < orderRows.length; i++) {
            airlineRows[i] = UserDao.getAirlineInfoByFid(Integer.parseInt(orderRows[i][0]));
        }
        rows = Utils.combineAirlineInfoOrder(airlineRows, orderRows);
    }

    public static void main(String[] args) {
        new MyOrderFrame(new User(1, "Zhang", "张三", "女", "123456", 0, "410223197203281185", "13195656565"));
    }
}
