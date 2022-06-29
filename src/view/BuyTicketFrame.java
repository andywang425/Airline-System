package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

import dao.*;
import util.*;
import entity.*;

public class BuyTicketFrame extends JFrame implements ActionListener {
    User USER;
    int FID;
    int typeCode;
    int oldFid;
    float oldPrice;
    String oldClassType, oldSeat;
    Runnable runnable;
    double discount;
    double total;
    Ticket ticket;
    JLabel economyLabel = new JLabel("经济舱"),
            businessLabel = new JLabel("公务舱"),
            firstLabel = new JLabel("头等舱"),
            economyPrice = new JLabel(),
            businessPrice = new JLabel(),
            firstPrice = new JLabel(),
            totalLabel = new JLabel("总价："),
            totalPrice = new JLabel(),
            myPointsLabel = new JLabel("我的积分："),
            myPoints = new JLabel();
    JRadioButton economyButton = new JRadioButton(),
            businessButton = new JRadioButton(),
            firstButton = new JRadioButton();
    ButtonGroup group = new ButtonGroup();
    JTable table = new JTable();
    JButton buyButton = new JButton("购买");
    String rows[][];
    String colums[] = new String[] { "1", "2", "3", "4", "5", "6" };

    private class TopPanel extends JPanel {
        public TopPanel() {
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createTitledBorder("舱位"));
            economyLabel.setFont(new Font(null, Font.BOLD, 20));
            businessLabel.setFont(new Font(null, Font.BOLD, 20));
            firstLabel.setFont(new Font(null, Font.BOLD, 20));
            economyPrice.setFont(new Font(null, Font.PLAIN, 20));
            businessPrice.setFont(new Font(null, Font.PLAIN, 20));
            economyButton.setFont(new Font(null, Font.PLAIN, 20));
            businessButton.setFont(new Font(null, Font.PLAIN, 20));
            firstButton.setFont(new Font(null, Font.PLAIN, 20));
            group.add(economyButton);
            group.add(businessButton);
            group.add(firstButton);
            if (ticket.getEconomyNum() == 0) {
                economyLabel.setText("经济舱（已售罄）");
                economyButton.setEnabled(false);
            }
            if (ticket.getBusinessNum() == 0) {
                businessLabel.setText("公务舱（已售罄）");
                businessButton.setEnabled(false);
            }
            if (ticket.getFirstNum() == 0) {
                firstLabel.setText("头等舱（已售罄）");
                firstButton.setEnabled(false);
            }
            economyPrice.setFont(new Font(null, Font.PLAIN, 20));
            businessPrice.setFont(new Font(null, Font.PLAIN, 20));
            firstPrice.setFont(new Font(null, Font.PLAIN, 20));
            if (ticket.getEconomyDiscount() == 1.0) {
                economyPrice.setText(ticket.getEconomyPrice() + "元");
            } else {
                String oldPrice = ticket.getEconomyPrice() + "元";
                String newPrice = String.format("%.2f", ticket.getEconomyPrice() * ticket.getEconomyDiscount()) + "元";
                economyPrice.setText("<html>" + "<strong style=\"font-size:20px;\">" + newPrice
                        + "</strong>" + "<strike style=\"font-size:10px;\">" + oldPrice + "</strike>" + "</html>"
                        + "元");
            }
            if (ticket.getBusinessDiscount() == 1.0) {
                businessPrice.setText(ticket.getBusinessPrice() + "元");
            } else {
                String oldPrice = ticket.getBusinessPrice() + "元";
                String newPrice = String.format("%.2f", ticket.getBusinessPrice() * ticket.getBusinessDiscount()) + "元";
                businessPrice.setText("<html>" + "<strong style=\"font-size:20px;\">" + newPrice
                        + "</strong>" + "<strike style=\"font-size:10px;\">" + oldPrice + "</strike>" + "</html>"
                        + "元");
            }
            if (ticket.getFirstDiscount() == 1.0) {
                firstPrice.setText(ticket.getFirstPrice() + "元");
            } else {
                String oldPrice = ticket.getFirstPrice() + "元";
                String newPrice = String.format("%.2f", ticket.getFirstPrice() * ticket.getFirstDiscount()) + "元";
                firstPrice.setText("<html>" + "<strong style=\"font-size:20px;\">" + newPrice
                        + "</strong>" + "<strike style=\"font-size:10px;\">" + oldPrice + "</strike>" + "</html>"
                        + "元");
            }
            economyButton.addActionListener(BuyTicketFrame.this);
            businessButton.addActionListener(BuyTicketFrame.this);
            firstButton.addActionListener(BuyTicketFrame.this);
            add(economyButton, new GBC(0, 0).setWeight(100, 100).setInsets(10, 10, 10, 0));
            add(economyLabel, new GBC(1, 0).setWeight(100, 100).setInsets(10, 10, 10, 0));
            add(economyPrice, new GBC(2, 0).setWeight(100, 100).setInsets(10, 0, 10, 10));
            add(businessButton, new GBC(0, 1).setWeight(100, 100).setInsets(10, 10, 10, 0));
            add(businessLabel, new GBC(1, 1).setWeight(100, 100).setInsets(10, 10, 10, 0));
            add(businessPrice, new GBC(2, 1).setWeight(100, 100).setInsets(10, 0, 10, 10));
            add(firstButton, new GBC(0, 2).setWeight(100, 100).setInsets(10, 10, 10, 0));
            add(firstLabel, new GBC(1, 2).setWeight(100, 100).setInsets(10, 10, 10, 0));
            add(firstPrice, new GBC(2, 2).setWeight(100, 100).setInsets(10, 0, 10, 10));
        }
    }

    private class TablePanel extends JPanel {
        public TablePanel() {
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createTitledBorder("座位信息"));
            table = new JTable() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table.setRowHeight(30);
            table.setFont(new Font(null, Font.PLAIN, 20));
            table.getSelectionModel().addListSelectionListener(x -> {
                if (table.getSelectedRow() != -1
                        && (economyButton.isSelected() || businessButton.isSelected() || firstButton.isSelected())) {
                    buyButton.setEnabled(true);
                } else {
                    buyButton.setEnabled(false);
                }
            });
            table.getTableHeader().setReorderingAllowed(false);
            add(new JScrollPane(table), new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 1000).setInsets(10, 10, 10, 10));
        }
    }

    private class MidPanel extends JPanel {
        public MidPanel() {
            setLayout(new GridBagLayout());
            discount = USER.getPoints() / 100.0;
            myPointsLabel.setFont(new Font(null, Font.BOLD, 20));
            myPoints.setFont(new Font(null, Font.PLAIN, 20));
            myPoints.setText(
                    String.valueOf(USER.getPoints()) + (discount > 0 ? "（可抵扣" + String.valueOf(discount) + "元）" : ""));
            totalLabel.setFont(new Font(null, Font.BOLD, 20));
            totalPrice.setFont(new Font(null, Font.PLAIN, 20));
            totalPrice.setText(String.valueOf(0) + "元");
            if (typeCode == 1)
                buyButton.setText("改签");
            buyButton.setFont(new Font(null, Font.PLAIN, 20));
            buyButton.setEnabled(false);
            add(myPointsLabel, new GBC(0, 0).setWeight(100, 100).setInsets(10, 10, 10, 0));
            add(myPoints, new GBC(1, 0).setWeight(100, 100).setInsets(10, 0, 10, 10));
            add(totalLabel, new GBC(0, 1).setWeight(100, 100).setInsets(10, 10, 10, 0));
            add(totalPrice, new GBC(1, 1).setWeight(100, 100).setInsets(10, 0, 10, 10));
            add(buyButton, new GBC(0, 0).setWeight(100, 100).setInsets(10, 10, 10, 10));
        }
    }

    private class BottomPanel extends JPanel {
        public BottomPanel() {
            setLayout(new GridBagLayout());
            buyButton.setFont(new Font(null, Font.PLAIN, 20));
            buyButton.addActionListener(BuyTicketFrame.this);
            add(buyButton, new GBC(0, 0).setWeight(100, 100).setInsets(10, 10, 10, 10).setIpad(70, 20));
        }
    }

    public BuyTicketFrame(User USER, int FID, int typeCode, int oldFid, String oldClassType, String oldSeat,
            float oldPrice, Runnable runnable) {
        this.USER = UserDao.getUserInfoByID(USER.getID());
        this.FID = FID;
        this.typeCode = typeCode;
        this.oldFid = oldFid;
        this.oldClassType = oldClassType;
        this.oldSeat = oldSeat;
        this.oldPrice = oldPrice;
        this.runnable = runnable;
        this.ticket = UserDao.getTicketInfoByFid(this.FID);
        setLayout(new GridBagLayout());
        add(new TopPanel(), new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 0, 10));
        add(new TablePanel(), new GBC(0, 1).setFill(GBC.BOTH).setWeight(100, 1000).setInsets(10, 10, 0, 10));
        add(new MidPanel(), new GBC(0, 2).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 0, 10));
        add(new BottomPanel(), new GBC(0, 3).setFill(GBC.BOTH).setWeight(100, 100).setInsets(0, 10, 10, 10));
        setTitle(typeCode == 0 ? "购票" : "改签");
        setIconImage(new ImageIcon("assets/pic/frameIcon.png").getImage());
        setSize(800, 600);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == economyButton) {
            rows = UserDao.getSeatsInfoByFidClassType(FID, "E");
            table.setModel(new DefaultTableModel(rows, colums));
            total = ticket.getEconomyPrice() * ticket.getEconomyDiscount() - discount;
            totalPrice.setText(String.valueOf(total) + "元");
            if (total < discount) {
                myPoints.setText(String.valueOf(USER.getPoints()) + "（可抵扣" + String.valueOf(total) + "元）");
                discount = total;
            }
        } else if (e.getSource() == businessButton) {
            rows = UserDao.getSeatsInfoByFidClassType(FID, "B");
            table.setModel(new DefaultTableModel(rows, colums));
            total = ticket.getBusinessPrice() * ticket.getBusinessDiscount() - discount;
            totalPrice.setText(String.valueOf(total) + "元");
            if (total < discount) {
                myPoints.setText(String.valueOf(USER.getPoints()) + "（可抵扣" + String.valueOf(total) + "元）");
                discount = total;
            }
        } else if (e.getSource() == firstButton) {
            rows = UserDao.getSeatsInfoByFidClassType(FID, "F");
            table.setModel(new DefaultTableModel(rows, colums));
            total = ticket.getFirstPrice() * ticket.getFirstDiscount() - discount;
            totalPrice.setText(String.valueOf(total) + "元");
            if (total < discount) {
                myPoints.setText(String.valueOf(USER.getPoints()) + "（可抵扣" + String.valueOf(total) + "元）");
                discount = total;
            }
        } else if (e.getSource() == buyButton) {
            int row = table.getSelectedRow();
            int col = table.getSelectedColumn();
            String seatCode = rows[row][col];
            if (seatCode.length() == 0) {
                JOptionPane.showMessageDialog(this, "请选择座位", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String classType = economyButton.isSelected() ? "经济舱"
                    : businessButton.isSelected() ? "公务舱" : firstButton.isSelected() ? "头等舱" : "";
            String classTypeCode = economyButton.isSelected() ? "E"
                    : businessButton.isSelected() ? "B" : firstButton.isSelected() ? "F" : "";
            int gotPoints = (int) total;
            if (typeCode == 0) {
                int result = JOptionPane.showConfirmDialog(this,
                        "是否花费" + totalPrice.getText() + "购买一张座位号为" + seatCode + "的" + classType + "机票（可获得"
                                + String.valueOf(gotPoints) + "积分）？",
                        "购票确认",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    if (UserDao.buyTicket(UserDao.getUserInfoByID(USER.getID()), FID, classTypeCode, seatCode,
                            gotPoints,
                            (float) total,
                            (int) (discount * 100))) {
                        JOptionPane.showMessageDialog(this, "购票成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "购票失败！", "失败", JOptionPane.ERROR_MESSAGE);
                    }

                }
            } else {
                String msgStr;
                float priceDiff = (float) total - oldPrice;
                if (priceDiff > 0)
                    msgStr = "需补差价" + String.valueOf(priceDiff) + "元";
                else if (priceDiff < 0)
                    msgStr = "将退还您" + String.valueOf(-priceDiff) + "元";
                else
                    msgStr = "无需补差价";
                int result = JOptionPane.showConfirmDialog(this,
                        "是否改签（" + msgStr + "）？\n注意：改签无法获得积分",
                        "改签确认",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    if (UserDao.rebookTicket(UserDao.getUserInfoByID(USER.getID()), oldFid,
                            oldClassType, oldSeat, FID,
                            classTypeCode, (float) total, seatCode)) {
                        JOptionPane.showMessageDialog(this, "改签成功！", "成功",
                                JOptionPane.INFORMATION_MESSAGE);
                        runnable.run();
                        return;
                    } else {
                        JOptionPane.showMessageDialog(this, "改签失败！", "失败",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                }
            }

        }

    }

    public static void main(String[] args) {
        new BuyTicketFrame(new User(1, "Zhang", "张三", "女", "123456", 0, "410223197203281185", "13195656565"), 1, 0, -1,
                null, null, 0, () -> {
                });
    }
}
