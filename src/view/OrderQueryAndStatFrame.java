package view;

import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import dao.*;
import util.*;
import entity.*;

public class OrderQueryAndStatFrame extends JFrame implements ActionListener, DocumentListener {
    Admin ADMIN;
    JButton searchButton = new JButton("查询"),
            statButton = new JButton("统计该时段内销售情况");
    ArrayList<JFrame> frames = new ArrayList<JFrame>();
    String colums[] = new String[] { "用户ID", "航空公司", "机型", "航班号", "起飞时间", "抵达时间", "出发地", "目的地", "座位号", "舱位", "购买价格",
            "使用积分", "下单时间" };
    String rows[][] = new String[][] {};
    JTable table = new JTable();
    String orderRows[][];
    String airlineRows[][];
    JLabel startYearLabel = new JLabel("年"),
            startMonthLabel = new JLabel("月"),
            startDayLabel = new JLabel("日"),
            toLabel = new JLabel("~"),
            endYearLabel = new JLabel("年"),
            endMonthLabel = new JLabel("月"),
            endDayLabel = new JLabel("日");
    JTextField startYearText = new JTextField(),
            startMonthText = new JTextField(),
            startDayText = new JTextField(),
            endYearText = new JTextField(),
            endMonthText = new JTextField(),
            endDayText = new JTextField();

    private class TopPanel extends JPanel {
        private void setInitDate() {
            LocalDate localDate = LocalDate.now();
            endYearText.setText(String.valueOf(localDate.getYear()));
            endMonthText.setText(String.valueOf(localDate.getMonthValue()));
            endDayText.setText(String.valueOf(localDate.getDayOfMonth()));
            // 1970年1月1日
            LocalDate start = LocalDate.of(1970, 1, 1);
            startYearText.setText(String.valueOf(start.getYear()));
            startMonthText.setText(String.valueOf(start.getMonthValue()));
            startDayText.setText(String.valueOf(start.getDayOfMonth()));
        }

        private void setMyFonts() {
            startYearText.setFont(new Font(null, Font.PLAIN, 20));
            startMonthText.setFont(new Font(null, Font.PLAIN, 20));
            startDayText.setFont(new Font(null, Font.PLAIN, 20));
            endYearText.setFont(new Font(null, Font.PLAIN, 20));
            endMonthText.setFont(new Font(null, Font.PLAIN, 20));
            endDayText.setFont(new Font(null, Font.PLAIN, 20));
            startYearLabel.setFont(new Font(null, Font.BOLD, 20));
            startMonthLabel.setFont(new Font(null, Font.BOLD, 20));
            startDayLabel.setFont(new Font(null, Font.BOLD, 20));
            toLabel.setFont(new Font(null, Font.BOLD, 30));
            endYearLabel.setFont(new Font(null, Font.BOLD, 20));
            endMonthLabel.setFont(new Font(null, Font.BOLD, 20));
            endDayLabel.setFont(new Font(null, Font.BOLD, 20));
        }

        private void addMyListeners() {
            startYearText.getDocument().addDocumentListener(OrderQueryAndStatFrame.this);
            startMonthText.getDocument().addDocumentListener(OrderQueryAndStatFrame.this);
            startDayText.getDocument().addDocumentListener(OrderQueryAndStatFrame.this);
            endYearText.getDocument().addDocumentListener(OrderQueryAndStatFrame.this);
            endMonthText.getDocument().addDocumentListener(OrderQueryAndStatFrame.this);
            endDayText.getDocument().addDocumentListener(OrderQueryAndStatFrame.this);
        }

        public TopPanel() {
            setLayout(new GridBagLayout());
            setMyFonts();
            setInitDate();
            addMyListeners();
            setBorder(BorderFactory.createTitledBorder("下单时间查询范围"));
            add(startYearText, new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10).setIpad(10, 0));
            add(startYearLabel, new GBC(1, 0).setInsets(10));
            add(startMonthText, new GBC(2, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10).setIpad(10, 0));
            add(startMonthLabel, new GBC(3, 0).setInsets(10));
            add(startDayText, new GBC(4, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10).setIpad(10, 0));
            add(startDayLabel, new GBC(5, 0).setInsets(10));
            add(toLabel, new GBC(6, 0).setInsets(10));
            add(endYearText, new GBC(7, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10).setIpad(10, 0));
            add(endYearLabel, new GBC(8, 0).setInsets(10));
            add(endMonthText, new GBC(9, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10).setIpad(10, 0));
            add(endMonthLabel, new GBC(10, 0).setInsets(10));
            add(endDayText, new GBC(11, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10).setIpad(10, 0));
            add(endDayLabel, new GBC(12, 0).setInsets(10));
        }
    }

    private class TablePanel extends JPanel {
        private static void setTableWeight(JTable t) {
            t.getColumnModel().getColumn(0).setMinWidth(50);
            t.getColumnModel().getColumn(1).setMaxWidth(120);
            t.getColumnModel().getColumn(1).setMinWidth(120);
            t.getColumnModel().getColumn(2).setMaxWidth(50);
            t.getColumnModel().getColumn(2).setMinWidth(50);
            t.getColumnModel().getColumn(3).setMaxWidth(80);
            t.getColumnModel().getColumn(3).setMinWidth(80);
            t.getColumnModel().getColumn(4).setMinWidth(200);
            t.getColumnModel().getColumn(4).setMaxWidth(200);
            t.getColumnModel().getColumn(5).setMinWidth(200);
            t.getColumnModel().getColumn(5).setMaxWidth(200);
            t.getColumnModel().getColumn(6).setMinWidth(80);
            t.getColumnModel().getColumn(6).setMaxWidth(80);
            t.getColumnModel().getColumn(7).setMinWidth(80);
            t.getColumnModel().getColumn(7).setMaxWidth(80);
            t.getColumnModel().getColumn(8).setMinWidth(60);
            t.getColumnModel().getColumn(8).setMaxWidth(60);
            t.getColumnModel().getColumn(9).setMinWidth(80);
            t.getColumnModel().getColumn(9).setMaxWidth(80);
            t.getColumnModel().getColumn(10).setMinWidth(80);
            t.getColumnModel().getColumn(10).setMaxWidth(80);
            t.getColumnModel().getColumn(12).setMinWidth(200);
            t.getColumnModel().getColumn(12).setMaxWidth(200);
        }

        public TablePanel() {
            setLayout(new GridBagLayout());
            table = new JTable(rows, colums) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table.setFont(new Font(null, Font.PLAIN, 20));
            table.setRowHeight(30);
            table.getTableHeader().setReorderingAllowed(false);
            setTableWeight(table);
            add(new JScrollPane(table), new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 1000).setInsets(10, 10, 10, 10));
        }
    }

    private class ButtonPanel extends JPanel {
        public ButtonPanel() {
            setLayout(new GridBagLayout());
            searchButton.setFont(new Font(null, Font.PLAIN, 20));
            statButton.setFont(new Font(null, Font.PLAIN, 20));
            searchButton.addActionListener(OrderQueryAndStatFrame.this);
            statButton.addActionListener(OrderQueryAndStatFrame.this);
            statButton.setEnabled(false);
            add(searchButton,
                    new GBC(0, 0).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(0, 10, 10, 10).setIpad(0, 50));
            add(statButton,
                    new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(0, 10, 10, 10).setIpad(0, 50));
        }
    }

    public OrderQueryAndStatFrame(Admin ADMIN) {
        this.ADMIN = ADMIN;
        setLayout(new GridBagLayout());
        add(new TopPanel(), new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 20, 0, 20));
        add(new TablePanel(), new GBC(0, 1).setFill(GBC.BOTH).setWeight(100, 2000).setInsets(10, 10, 10, 10));
        add(new ButtonPanel(), new GBC(0, 2).setFill(GBC.BOTH).setWeight(100, 100).setInsets(0, 10, 10, 10));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                for (JFrame frame : frames) {
                    frame.dispose();
                }
                dispose();
            }
        });
        setTitle("订单查询与统计");
        setIconImage(new ImageIcon("assets/pic/frameIcon.png").getImage());
        setSize(1500, 800);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String startDate = startYearText.getText() + "-" + startMonthText.getText() + "-" + startDayText.getText();
            String endDate = endYearText.getText() + "-" + endMonthText.getText() + "-" + endDayText.getText()
                    + " 23:59:59";
            rows = AdminDao.getSoldTicketsAndAirlineByTime(startDate, endDate);
            for (int j = 0; j < rows.length; j++) {
                rows[j][9] = Utils.getChineseClassName(rows[j][9]);
            }
            table.setModel(new DefaultTableModel(rows, colums));
            TablePanel.setTableWeight(table);
            if (table.getRowCount() > 0) {
                statButton.setEnabled(true);
            } else {
                statButton.setEnabled(false);
            }
        } else if (e.getSource() == statButton) {
            String startDate = startYearText.getText() + "-" + startMonthText.getText() + "-" + startDayText.getText();
            String endDate = endYearText.getText() + "-" + endMonthText.getText() + "-" + endDayText.getText();
            float income = 0;
            for (int i = 0; i < table.getRowCount(); i++) {
                income += Float.parseFloat(table.getValueAt(i, 10).toString());
            }
            String str = "<html><div style=\"font-size:20;\"><h1>统计报告</h1><br><div>时间：" + startDate + " ~ " + endDate
                    + "</div><div>售票数："
                    + rows.length + "张</div><div>营业额：" + income + "元</div></div></html>";
            JOptionPane.showMessageDialog(this, str, "统计信息", JOptionPane.PLAIN_MESSAGE);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        statButton.setEnabled(false);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        statButton.setEnabled(false);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        // NOTING
    }

    public static void main(String[] args) {
        new OrderQueryAndStatFrame(new Admin(1, "admin", "admin"));
    }
}
