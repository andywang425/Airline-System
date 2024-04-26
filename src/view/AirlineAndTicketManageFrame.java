package view;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import dao.*;
import entity.*;
import util.*;

public class AirlineAndTicketManageFrame extends JFrame implements ActionListener {
    Admin ADMIN;
    JTable table = new JTable();
    String colums[] = new String[] { "航空公司", "机型", "航班号", "起飞时间", "抵达时间", "出发地", "目的地", "经济舱票价", "经济舱折扣", "经济舱剩余座位数",
            "公务舱票价", "公务舱折扣", "公务舱剩余座位数", "头等舱票价", "头等舱折扣", "头等舱剩余座位数" };
    String rows[][] = new String[][] {};
    JButton addButton = new JButton("添加"),
            deleteButton = new JButton("删除"),
            updateButton = new JButton("修改");
    String airlineRows[][];
    String ticketRows[][];
    ArrayList<JFrame> frames = new ArrayList<JFrame>();

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
            t.getColumnModel().getColumn(7).setMinWidth(80);
            t.getColumnModel().getColumn(8).setMinWidth(60);
            t.getColumnModel().getColumn(10).setMinWidth(80);
            t.getColumnModel().getColumn(11).setMinWidth(60);
            t.getColumnModel().getColumn(14).setMinWidth(60);
            t.getColumnModel().getColumn(15).setMinWidth(80);
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
                    updateButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                } else {
                    updateButton.setEnabled(false);
                    deleteButton.setEnabled(false);
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
            addButton.setFont(new Font(null, Font.PLAIN, 20));
            updateButton.setFont(new Font(null, Font.PLAIN, 20));
            deleteButton.setFont(new Font(null, Font.PLAIN, 20));
            addButton.addActionListener(AirlineAndTicketManageFrame.this);
            updateButton.addActionListener(AirlineAndTicketManageFrame.this);
            deleteButton.addActionListener(AirlineAndTicketManageFrame.this);
            updateButton.setEnabled(false);
            deleteButton.setEnabled(false);
            add(addButton,
                    new GBC(0, 0).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(10, 10, 10, 10).setIpad(0, 40));
            add(updateButton,
                    new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(10, 10, 10, 10).setIpad(0, 40));
            add(deleteButton,
                    new GBC(2, 0).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(10, 10, 10, 10).setIpad(0, 40));
        }
    }

    public AirlineAndTicketManageFrame(Admin ADMIN) {
        this.ADMIN = ADMIN;
        setLayout(new GridBagLayout());
        add(new TablePanel(), new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 2000).setInsets(10, 10, 0, 10));
        add(new ButtonPanel(), new GBC(0, 1).setFill(GBC.BOTH).setWeight(100, 0).setInsets(10, 10, 10, 10));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                for (JFrame frame : frames) {
                    frame.dispose();
                }
                dispose();
            }
        });
        setTitle("航线与机票管理");
        setIconImage(new ImageIcon("assets/pic/frameIcon.png").getImage());
        setSize(1800, 600);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void getTableData() {
        airlineRows = AdminDao.getAllAirlineInfo();
        ticketRows = AdminDao.getAllTicketInfo();
        rows = Utils.combineAirlineTickets(airlineRows, ticketRows);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            frames.add(new AddAirlineAndTicketsFrame(ADMIN, () -> {
                getTableData();
                table.setModel(new DefaultTableModel(rows, colums));
                TablePanel.setTableWeight(table);
            }));
        } else if (e.getSource() == updateButton) {
            int row = table.getSelectedRow();
            int fid = Integer.parseInt(airlineRows[row][0]);
            var selectedAirline = new Airline(fid, airlineRows[row][1], airlineRows[row][2],
                    airlineRows[row][3], airlineRows[row][4], airlineRows[row][5], airlineRows[row][6],
                    airlineRows[row][7]);
            int economySeatNum = Integer.parseInt(ticketRows[row][1]);
            int economyPrice = Integer.parseInt(ticketRows[row][2]);
            float economyDiscount = Float.parseFloat(ticketRows[row][3]);
            int businessSeatNum = Integer.parseInt(ticketRows[row][4]);
            int businessPrice = Integer.parseInt(ticketRows[row][5]);
            float businessDiscount = Float.parseFloat(ticketRows[row][6]);
            int firstSeatNum = Integer.parseInt(ticketRows[row][7]);
            int firstPrice = Integer.parseInt(ticketRows[row][8]);
            float firstDiscount = Float.parseFloat(ticketRows[row][9]);
            var selectedTicket = new Ticket(fid, economySeatNum, economyPrice, economyDiscount,
                    businessSeatNum, businessPrice, businessDiscount, firstSeatNum, firstPrice, firstDiscount);
            frames.add(new UpdateAirlineAndTicketsFrame(ADMIN, selectedAirline, selectedTicket, () -> {
                getTableData();
                table.setModel(new DefaultTableModel(rows, colums));
                TablePanel.setTableWeight(table);
            }));
        } else if (e.getSource() == deleteButton) {
            int row = table.getSelectedRow();
            int fid = Integer.parseInt(airlineRows[row][0]);
            int result = JOptionPane.showConfirmDialog(this, "确定删除该航线及其机票信息吗？", "删除确认", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                if (AdminDao.delAirline(fid) && AdminDao.delTicket(fid)) {
                    JOptionPane.showMessageDialog(this, "删除成功");
                    getTableData();
                    table.setModel(new DefaultTableModel(rows, colums));
                    TablePanel.setTableWeight(table);
                } else {
                    JOptionPane.showMessageDialog(this, "删除失败");
                }
            }
        }
    }

    public static void main(String[] args) {
        new AirlineAndTicketManageFrame(new Admin());

    }
}
