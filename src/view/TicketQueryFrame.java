package view;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.time.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import dao.*;
import util.*;
import entity.*;

public class TicketQueryFrame extends JFrame implements ActionListener {
    User USER;
    JLabel flightNumberLabel = new JLabel("航班号"),
            deparetureLabel = new JLabel("出发地"),
            destinationLabel = new JLabel("目的地"),
            dateLabel = new JLabel("出发日期"),
            yearLabel = new JLabel("年"),
            monthLabel = new JLabel("月"),
            dayLabel = new JLabel("日");
    JTextField flightNumberText = new JTextField(),
            yearText = new JTextField(),
            monthText = new JTextField(),
            dayText = new JTextField();
    JComboBox<String> deparetureComboBox = new JComboBox<String>(),
            destinationComboBox = new JComboBox<String>();
    JButton queryBtn = new JButton("查询"),
            buyTicketBtn = new JButton("购买机票");
    JRadioButton flightRadio = new JRadioButton("按航班号查询"),
            ddRadio = new JRadioButton("按起抵地查询"),
            dateRadio = new JRadioButton("按出发日期查询");
    ButtonGroup group = new ButtonGroup();
    JTable table = new JTable();
    String rows[][] = new String[][] {};
    String columns[] = { "航空公司", "机型", "航班号", "起飞时间", "抵达时间", "出发地", "目的地" };
    static ArrayList<JFrame> frames = new ArrayList<JFrame>();

    private void setLocalDate() {
        LocalDate localDate = LocalDate.now();
        yearText.setText(String.valueOf(localDate.getYear()));
        monthText.setText(String.valueOf(localDate.getMonthValue()));
        dayText.setText(String.valueOf(localDate.getDayOfMonth()));
    }

    private class TopPanel extends JPanel {
        private class LeftPanel extends JPanel {
            public LeftPanel() {
                setLayout(new GridBagLayout());
                setBorder(BorderFactory.createTitledBorder("航班号"));
                flightNumberLabel.setFont(new Font(null, Font.PLAIN, 20));
                flightNumberText.setFont(new Font(null, Font.PLAIN, 20));
                add(flightNumberLabel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 0));
                add(flightNumberText, new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(100, 100)
                        .setInsets(10, 10, 10, 10).setIpad(120, 20));
            }
        }

        private class RightPanel extends JPanel {
            public RightPanel() {
                setLayout(new GridBagLayout());
                setBorder(BorderFactory.createTitledBorder("起抵地"));
                deparetureLabel.setFont(new Font(null, Font.PLAIN, 20));
                destinationLabel.setFont(new Font(null, Font.PLAIN, 20));
                deparetureComboBox.setFont(new Font(null, Font.PLAIN, 20));
                destinationComboBox.setFont(new Font(null, Font.PLAIN, 20));
                deparetureComboBox.setModel(new DefaultComboBoxModel<String>(UserDao.getAirlineDepareture()));
                destinationComboBox.setModel(new DefaultComboBoxModel<String>(UserDao.getAirlineDestination()));
                add(deparetureLabel, new GBC(2, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 0));
                add(deparetureComboBox,
                        new GBC(3, 0).setFill(GBC.HORIZONTAL).setWeight(100, 100).setInsets(10, 10, 10, 10).setIpad(120,
                                20));
                add(destinationLabel, new GBC(4, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 0));
                add(destinationComboBox,
                        new GBC(5, 0).setFill(GBC.HORIZONTAL).setWeight(100, 100).setInsets(10, 10, 10, 10).setIpad(120,
                                20));
            }
        }

        private class LastPanel extends JPanel {
            public LastPanel() {
                setLayout(new GridBagLayout());
                setBorder(BorderFactory.createTitledBorder("出发日期"));
                dateLabel.setFont(new Font(null, Font.PLAIN, 20));
                yearLabel.setFont(new Font(null, Font.PLAIN, 20));
                monthLabel.setFont(new Font(null, Font.PLAIN, 20));
                dayLabel.setFont(new Font(null, Font.PLAIN, 20));
                yearText.setFont(new Font(null, Font.PLAIN, 20));
                monthText.setFont(new Font(null, Font.PLAIN, 20));
                dayText.setFont(new Font(null, Font.PLAIN, 20));
                setLocalDate();
                add(dateLabel, new GBC(0, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 0, 10, 0));
                add(yearText,
                        new GBC(1, 1).setFill(GBC.HORIZONTAL).setWeight(100, 100).setInsets(10, 10, 10, 10).setIpad(120,
                                20));
                add(yearLabel, new GBC(2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 0));
                add(monthText,
                        new GBC(3, 1).setFill(GBC.HORIZONTAL).setWeight(100, 100).setInsets(10, 10, 10, 10).setIpad(120,
                                20));
                add(monthLabel, new GBC(4, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 0));
                add(dayText, new GBC(5, 1).setFill(GBC.HORIZONTAL).setWeight(100, 100).setInsets(10, 10, 10, 10)
                        .setIpad(120, 20));
                add(dayLabel, new GBC(6, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 0));
            }
        }

        public TopPanel() {
            setLayout(new GridBagLayout());
            add(new LeftPanel(), new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(00, 0, 0, 5));
            add(new RightPanel(), new GBC(1, 0).setFill(GBC.BOTH).setWeight(100, 100));
            add(new LastPanel(), new GBC(0, 2, 2, 1).setFill(GBC.BOTH).setWeight(100, 100));
        }
    }

    private class TablePanel extends JPanel {
        public TablePanel() {
            setLayout(new GridBagLayout());
            table = new JTable(rows, columns) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table.setRowHeight(30);
            setTableWeight(table);
            table.setFont(new Font(null, Font.PLAIN, 20));
            table.getSelectionModel().addListSelectionListener(x -> {
                if (table.getSelectedRow() != -1) {
                    buyTicketBtn.setEnabled(true);
                } else {
                    buyTicketBtn.setEnabled(false);
                }
            });
            add(new JScrollPane(table), new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 1000).setInsets(10, 10, 10, 10));
        }

        private static void setTableWeight(JTable t) {
            t.getColumnModel().getColumn(1).setMaxWidth(50);
            t.getColumnModel().getColumn(1).setMinWidth(50);
            t.getColumnModel().getColumn(2).setMaxWidth(100);
            t.getColumnModel().getColumn(2).setMinWidth(100);
            t.getColumnModel().getColumn(3).setMinWidth(200);
            t.getColumnModel().getColumn(3).setMaxWidth(200);
            t.getColumnModel().getColumn(4).setMinWidth(200);
            t.getColumnModel().getColumn(4).setMaxWidth(200);
        }
    }

    private class MidPanel extends JPanel {
        public MidPanel() {
            setLayout(new GridBagLayout());
            flightRadio.setFont(new Font(null, Font.PLAIN, 20));
            ddRadio.setFont(new Font(null, Font.PLAIN, 20));
            dateRadio.setFont(new Font(null, Font.PLAIN, 20));
            group.add(flightRadio);
            group.add(ddRadio);
            group.add(dateRadio);
            flightRadio.setSelected(true);
            add(flightRadio, new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 0));
            add(ddRadio, new GBC(1, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 0));
            add(dateRadio, new GBC(2, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 0));
        }
    }

    private class BottomPanel extends JPanel {
        public BottomPanel() {
            setLayout(new GridBagLayout());
            queryBtn.setFont(new Font(null, Font.PLAIN, 20));
            buyTicketBtn.setFont(new Font(null, Font.PLAIN, 20));
            buyTicketBtn.setEnabled(false);
            queryBtn.addActionListener(TicketQueryFrame.this);
            buyTicketBtn.addActionListener(TicketQueryFrame.this);
            add(queryBtn, new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
            add(buyTicketBtn, new GBC(1, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
        }
    }

    public TicketQueryFrame(User USER) {
        this.USER = USER;
        setLayout(new GridBagLayout());
        add(new TopPanel(), new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
        add(new TablePanel(), new GBC(0, 1).setFill(GBC.BOTH).setWeight(100, 1000).setInsets(0, 10, 0, 10));
        add(new MidPanel(), new GBC(0, 2).setFill(GBC.BOTH).setWeight(100, 100).setInsets(5, 10, 10, 10));
        add(new BottomPanel(), new GBC(0, 3).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 10, 10));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                for (JFrame frame : frames) {
                    frame.dispose();
                }
                dispose();
            }
        });
        setTitle("机票查询");
        setIconImage(new ImageIcon("assets/pic/frameIcon.png").getImage());
        setSize(1000, 800);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == queryBtn) {
            if (flightRadio.isSelected()) {
                String flightNumber = flightNumberText.getText();
                if (flightNumber.equals("")) {
                    JOptionPane.showMessageDialog(this, "请输入航班号！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                rows = UserDao.getAirlineInfoByFlightNumber(flightNumber);
                table.setModel(new DefaultTableModel(rows, columns));
                TablePanel.setTableWeight(table);
            } else if (ddRadio.isSelected()) {
                String departure = (String) deparetureComboBox.getSelectedItem(),
                        destination = (String) destinationComboBox.getSelectedItem();
                if (departure.equals(destination)) {
                    JOptionPane.showMessageDialog(this, "出发地不能和目的地相同", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                rows = UserDao.getAirlineInfoByDepartureDestination(departure, destination);
                table.setModel(new DefaultTableModel(rows, columns));
                TablePanel.setTableWeight(table);
            } else if (dateRadio.isSelected()) {
                int year = Integer.parseInt(yearText.getText()),
                        month = Integer.parseInt(monthText.getText()), // 月份范围 0 - 11
                        day = Integer.parseInt(dayText.getText());
                Calendar now = Calendar.getInstance();
                String takeOffTime = String.format("%d-%d-%d", year, month, day);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                try {
                    cal.setTime(sdf.parse(takeOffTime));
                } catch (ParseException e1) {
                    JOptionPane.showMessageDialog(this, "出发日期格式不正确！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                now.add(Calendar.DAY_OF_YEAR, -1);
                if (cal.before(now)) {
                    JOptionPane.showMessageDialog(this, "出发日期不能早于今天！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                rows = UserDao.getAirlineInfoByDeparetureDate(cal);
                table.setModel(new DefaultTableModel(rows, columns));
                TablePanel.setTableWeight(table);
            }
        } else if (e.getSource() == buyTicketBtn) {
            int row = table.getSelectedRow();
            Calendar cal = Calendar.getInstance(), now = Calendar.getInstance();
            String takeOffTime = (String) table.getValueAt(row, 3);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                cal.setTime(sdf.parse(takeOffTime));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            if (cal.before(now)) {
                JOptionPane.showMessageDialog(this, "不能购买已过期的机票！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int fid = UserDao.getFidByFlightNumber(table.getValueAt(row, 2).toString());
            if (UserDao.checkIfBoughtTicket(USER.getID(), fid)) {
                JOptionPane.showMessageDialog(this, "该您已购买过该班次的机票了", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            frames.add(new BuyTicketFrame(USER, fid, 0, -1, null, null, 0, () -> {
            }));
        }
    }

    public static void main(String[] args) {
        frames.add(new TicketQueryFrame(
                new User(1, "Zhang", "张三", "女", "123456", 0, "410223197203281185", "13195656565")));
    }
}
