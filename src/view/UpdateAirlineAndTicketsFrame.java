package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import dao.*;
import entity.*;
import util.*;

public class UpdateAirlineAndTicketsFrame extends JFrame implements ActionListener {
    Admin ADMIN;
    Airline AIRLINE;
    Ticket TICKET;
    Runnable runnable;
    String[][] seatInfo;
    JLabel companyLabel = new JLabel("航空公司"),
            planeTypeLabel = new JLabel("机型"),
            flightNumberLabel = new JLabel("航班号"),
            takeOffTimeLabel = new JLabel("起飞时间"),
            arrivalTimeLabel = new JLabel("抵达时间"),
            departureLabel = new JLabel("出发地"),
            destinationLabel = new JLabel("目的地"),
            economyPriceLabel = new JLabel("经济舱票价"),
            economyDiscountLabel = new JLabel("经济舱折扣"),
            businessPriceLabel = new JLabel("商务舱票价"),
            businessDiscountLabel = new JLabel("商务舱折扣"),
            firstPriceLabel = new JLabel("头等舱票价"),
            firstDiscountLabel = new JLabel("头等舱折扣"),
            economySeatNumLabel = new JLabel("经济舱剩余座位数"),
            businessSeatNumLabel = new JLabel("公务舱剩余座位数"),
            firstSeatNumLabel = new JLabel("头等舱剩余座位数"),
            economySeatCodesLabel = new JLabel("经济舱剩余座位号"),
            businessSeatCodesLabel = new JLabel("公务舱剩余座位号"),
            firstSeatCodesLabel = new JLabel("头等舱剩余座位号");
    JTextField companyField = new JTextField(),
            planeTypeField = new JTextField(),
            flightNumberField = new JTextField(),
            takeOffTimeField = new JTextField(),
            arrivalTimeField = new JTextField(),
            departureField = new JTextField(),
            destinationField = new JTextField(),
            economyPriceField = new JTextField(),
            economyDiscountField = new JTextField(),
            businessPriceField = new JTextField(),
            businessDiscountField = new JTextField(),
            firstPriceField = new JTextField(),
            firstDiscountField = new JTextField(),
            economySeatNumField = new JTextField(),
            businessSeatNumField = new JTextField(),
            firstSeatNumField = new JTextField();
    JTextArea economySeatCodesTextArea = new JTextArea(),
            businessSeatCodesTextArea = new JTextArea(),
            firstSeatCodesTextArea = new JTextArea();
    JScrollPane economySeatCodeScrollPanel = new JScrollPane(economySeatCodesTextArea),
            businessSeatCodeScrollPanel = new JScrollPane(businessSeatCodesTextArea),
            firstSeatCodeScrollPanel = new JScrollPane(firstSeatCodesTextArea);
    JButton confirmButton = new JButton("确认");

    private class LeftPanel extends JPanel {
        private void setMyFonts() {
            companyLabel.setFont(new Font(null, Font.BOLD, 20));
            planeTypeLabel.setFont(new Font(null, Font.BOLD, 20));
            flightNumberLabel.setFont(new Font(null, Font.BOLD, 20));
            takeOffTimeLabel.setFont(new Font(null, Font.BOLD, 20));
            arrivalTimeLabel.setFont(new Font(null, Font.BOLD, 20));
            departureLabel.setFont(new Font(null, Font.BOLD, 20));
            destinationLabel.setFont(new Font(null, Font.BOLD, 20));
            economyPriceLabel.setFont(new Font(null, Font.BOLD, 20));
            economyDiscountLabel.setFont(new Font(null, Font.BOLD, 20));
            businessPriceLabel.setFont(new Font(null, Font.BOLD, 20));
            businessDiscountLabel.setFont(new Font(null, Font.BOLD, 20));
            firstPriceLabel.setFont(new Font(null, Font.BOLD, 20));
            firstDiscountLabel.setFont(new Font(null, Font.BOLD, 20));
            companyField.setFont(new Font(null, Font.PLAIN, 20));
            planeTypeField.setFont(new Font(null, Font.PLAIN, 20));
            flightNumberField.setFont(new Font(null, Font.PLAIN, 20));
            takeOffTimeField.setFont(new Font(null, Font.PLAIN, 20));
            arrivalTimeField.setFont(new Font(null, Font.PLAIN, 20));
            departureField.setFont(new Font(null, Font.PLAIN, 20));
            destinationField.setFont(new Font(null, Font.PLAIN, 20));
            economyPriceField.setFont(new Font(null, Font.PLAIN, 20));
            economyDiscountField.setFont(new Font(null, Font.PLAIN, 20));
            businessPriceField.setFont(new Font(null, Font.PLAIN, 20));
            businessDiscountField.setFont(new Font(null, Font.PLAIN, 20));
            firstPriceField.setFont(new Font(null, Font.PLAIN, 20));
            firstDiscountField.setFont(new Font(null, Font.PLAIN, 20));
        }

        private void setMyText() {
            companyField.setText(AIRLINE.getCompany());
            planeTypeField.setText(AIRLINE.getFlightType());
            flightNumberField.setText(AIRLINE.getFlightNumber());
            takeOffTimeField.setText(AIRLINE.getTakeOffTime());
            arrivalTimeField.setText(AIRLINE.getArrivalTime());
            departureField.setText(AIRLINE.getDeparture());
            destinationField.setText(AIRLINE.getDestination());
            economyPriceField.setText(TICKET.getEconomyPrice() + "");
            economyDiscountField.setText(TICKET.getEconomyDiscount() + "");
            businessPriceField.setText(TICKET.getBusinessPrice() + "");
            businessDiscountField.setText(TICKET.getBusinessDiscount() + "");
            firstPriceField.setText(TICKET.getFirstPrice() + "");
            firstDiscountField.setText(TICKET.getFirstDiscount() + "");
        }

        public LeftPanel() {
            setLayout(new GridBagLayout());
            setMyFonts();
            setMyText();
            add(companyLabel, new GBC(0, 0).setInsets(10, 10, 10, 10));
            add(companyField,
                    new GBC(1, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 0, 10).setIpad(50, 0));
            add(planeTypeLabel, new GBC(0, 1).setInsets(10, 10, 10, 10));
            add(planeTypeField,
                    new GBC(1, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 0, 10).setIpad(50, 0));
            add(flightNumberLabel, new GBC(0, 2).setInsets(10, 10, 10, 10));
            add(flightNumberField,
                    new GBC(1, 2).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 0, 10).setIpad(50, 0));
            add(takeOffTimeLabel, new GBC(0, 3).setInsets(10, 10, 10, 10));
            add(takeOffTimeField,
                    new GBC(1, 3).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 0, 10).setIpad(50, 0));
            add(arrivalTimeLabel, new GBC(0, 4).setInsets(10, 10, 10, 10));
            add(arrivalTimeField,
                    new GBC(1, 4).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 0, 10).setIpad(50, 0));
            add(departureLabel, new GBC(0, 5).setInsets(10, 10, 10, 10));
            add(departureField,
                    new GBC(1, 5).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 0, 10).setIpad(50, 0));
            add(destinationLabel, new GBC(0, 6).setInsets(10, 10, 10, 10));
            add(destinationField,
                    new GBC(1, 6).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 0, 10).setIpad(50, 0));
            add(economyPriceLabel, new GBC(0, 7).setInsets(10, 10, 10, 10));
            add(economyPriceField,
                    new GBC(1, 7).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 0, 10).setIpad(50, 0));
            add(economyDiscountLabel, new GBC(0, 8).setInsets(10, 10, 10, 10));
            add(economyDiscountField,
                    new GBC(1, 8).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 0, 10).setIpad(50, 0));
            add(businessPriceLabel, new GBC(0, 9).setInsets(10, 10, 10, 10));
            add(businessPriceField,
                    new GBC(1, 9).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 0, 10).setIpad(50, 0));
            add(businessDiscountLabel, new GBC(0, 10).setInsets(10, 10, 10, 10));
            add(businessDiscountField,
                    new GBC(1, 10).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 0, 10).setIpad(50, 0));
            add(firstPriceLabel, new GBC(0, 11).setInsets(10, 10, 10, 10));
            add(firstPriceField,
                    new GBC(1, 11).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 0, 10).setIpad(50, 0));
            add(firstDiscountLabel, new GBC(0, 12).setInsets(10, 10, 10, 10));
            add(firstDiscountField,
                    new GBC(1, 12).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10, 10, 0, 10).setIpad(50, 0));

        }
    }

    private class RightPanel extends JPanel {
        private void setMyFonts() {
            economySeatNumLabel.setFont(new Font(null, Font.BOLD, 20));
            businessSeatNumLabel.setFont(new Font(null, Font.BOLD, 20));
            firstSeatNumLabel.setFont(new Font(null, Font.BOLD, 20));
            economySeatNumField.setFont(new Font(null, Font.PLAIN, 20));
            businessSeatNumField.setFont(new Font(null, Font.PLAIN, 20));
            firstSeatNumField.setFont(new Font(null, Font.PLAIN, 20));
            economySeatCodesTextArea.setFont(new Font(null, Font.PLAIN, 20));
            businessSeatCodesTextArea.setFont(new Font(null, Font.PLAIN, 20));
            firstSeatCodesTextArea.setFont(new Font(null, Font.PLAIN, 20));
            economySeatCodesLabel.setFont(new Font(null, Font.BOLD, 20));
            businessSeatCodesLabel.setFont(new Font(null, Font.BOLD, 20));
            firstSeatCodesLabel.setFont(new Font(null, Font.BOLD, 20));
        }

        private void setMyText() {
            for (int i = 0; i < seatInfo[0].length; i++) {
                economySeatCodesTextArea.append(seatInfo[0][i]);
                if ((i + 1) % 6 == 0) {
                    economySeatCodesTextArea.append("\n");
                } else {
                    if (i != seatInfo[0].length - 1)
                        economySeatCodesTextArea.append(" ");
                }
            }
            for (int i = 0; i < seatInfo[1].length; i++) {
                businessSeatCodesTextArea.append(seatInfo[1][i]);
                if ((i + 1) % 6 == 0) {
                    businessSeatCodesTextArea.append("\n");
                } else {
                    if (i != seatInfo[1].length - 1)
                        businessSeatCodesTextArea.append(" ");
                }
            }
            for (int i = 0; i < seatInfo[2].length; i++) {
                firstSeatCodesTextArea.append(seatInfo[2][i]);
                if ((i + 1) % 6 == 0) {
                    firstSeatCodesTextArea.append("\n");
                } else {
                    if (i != seatInfo[2].length - 1)
                        firstSeatCodesTextArea.append(" ");
                }
            }
            economySeatNumField.setText(TICKET.getEconomyNum() + "");
            businessSeatNumField.setText(TICKET.getBusinessNum() + "");
            firstSeatNumField.setText(TICKET.getFirstNum() + "");
        }

        public RightPanel() {
            setLayout(new GridBagLayout());
            setMyFonts();
            setMyText();
            add(economySeatNumLabel, new GBC(0, 0).setInsets(10, 10, 10, 10));
            add(economySeatNumField,
                    new GBC(1, 0).setFill(GBC.BOTH).setWeight(100, 50).setInsets(20, 10, 20, 10).setIpad(50, 0));
            add(economySeatCodesLabel, new GBC(0, 1).setInsets(10, 10, 10, 10));
            add(economySeatCodeScrollPanel,
                    new GBC(1, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(20, 10, 20, 10).setIpad(50, 0));
            add(businessSeatNumLabel, new GBC(0, 2).setInsets(10, 10, 10, 10));
            add(businessSeatNumField,
                    new GBC(1, 2).setFill(GBC.BOTH).setWeight(100, 50).setInsets(20, 10, 20, 10).setIpad(50, 0));
            add(businessSeatCodesLabel, new GBC(0, 3).setInsets(10, 10, 10, 10));
            add(businessSeatCodeScrollPanel,
                    new GBC(1, 3).setFill(GBC.BOTH).setWeight(100, 100).setInsets(20, 10, 20, 10).setIpad(50, 0));
            add(firstSeatNumLabel, new GBC(0, 4).setInsets(10, 10, 10, 10));
            add(firstSeatNumField,
                    new GBC(1, 4).setFill(GBC.BOTH).setWeight(100, 50).setInsets(20, 10, 20, 10).setIpad(50, 0));
            add(firstSeatCodesLabel, new GBC(0, 5).setInsets(10, 10, 10, 10));
            add(firstSeatCodeScrollPanel,
                    new GBC(1, 5).setFill(GBC.BOTH).setWeight(100, 100).setInsets(20, 10, 10, 10).setIpad(50, 0));
        }
    }

    private class ButtonPanel extends JPanel {
        public ButtonPanel() {
            setLayout(new GridBagLayout());
            confirmButton.setFont(new Font(null, Font.PLAIN, 20));
            confirmButton.addActionListener(UpdateAirlineAndTicketsFrame.this);
            add(confirmButton, new GBC(0, 0).setInsets(10, 10, 10, 10).setIpad(200, 30));
        }

    }

    public UpdateAirlineAndTicketsFrame(Admin ADMIN, Airline AIRLINE, Ticket TICKET, Runnable runnable) {
        this.ADMIN = ADMIN;
        this.AIRLINE = AIRLINE;
        this.TICKET = TICKET;
        this.runnable = runnable;
        this.seatInfo = AdminDao.getSeatInfoByFid(AIRLINE.getFid());
        setLayout(new GridBagLayout());
        add(new LeftPanel(), new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 1000).setInsets(10));
        add(new RightPanel(), new GBC(1, 0).setFill(GBC.BOTH).setWeight(100, 1000).setInsets(10));
        add(new ButtonPanel(), new GBC(0, 1, 2, 0).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10));
        setTitle("修改航线与机票信息");
        setIconImage(new ImageIcon("assets/pic/frameIcon.png").getImage());
        setSize(1000, 900);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            String company = companyField.getText();
            String flightType = planeTypeField.getText();
            String flightNumber = flightNumberField.getText();
            String takeOffTime = takeOffTimeField.getText();
            String arrivalTime = arrivalTimeField.getText();
            String departure = departureField.getText();
            String destination = destinationField.getText();
            int economyPrice, businessPrice, firstPrice, economySeatNum, businessSeatNum, firstSeatNum;
            float economyDiscount, businessDiscount, firstDiscount;
            try {
                economyPrice = Integer.parseInt(economyPriceField.getText());
                economyDiscount = Float.parseFloat(economyDiscountField.getText());
                businessPrice = Integer.parseInt(businessPriceField.getText());
                businessDiscount = Float.parseFloat(businessDiscountField.getText());
                firstPrice = Integer.parseInt(firstPriceField.getText());
                firstDiscount = Float.parseFloat(firstDiscountField.getText());
                economySeatNum = Integer.parseInt(economySeatNumField.getText());
                businessSeatNum = Integer.parseInt(businessSeatNumField.getText());
                firstSeatNum = Integer.parseInt(firstSeatNumField.getText());
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(this, "请输入正确的票价、折扣和座位数量", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String[] economySeatCodes = economySeatCodesTextArea.getText().split("\n|[ ]");
            String[] businessSeatCodes = businessSeatCodesTextArea.getText().split("\n|[ ]");
            String[] firstSeatCodes = firstSeatCodesTextArea.getText().split("\n|[ ]");
            var newAirline = new Airline(AIRLINE.getFid(), company, flightType, flightNumber, takeOffTime, arrivalTime,
                    departure,
                    destination);
            String validateMsg = Validate.validateAirline(newAirline);
            if (validateMsg != "CORRECT") {
                JOptionPane.showMessageDialog(this, validateMsg, "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            validateMsg = Validate.validateSeats(economySeatNum, businessSeatNum, firstSeatNum, economySeatCodes,
                    businessSeatCodes, firstSeatCodes);
            if (validateMsg != "CORRECT") {
                JOptionPane.showMessageDialog(this, validateMsg, "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (AdminDao.updateAirline(newAirline)) {
                var newTicket = new Ticket(TICKET.getFid(), economySeatNum, economyPrice, economyDiscount,
                        businessSeatNum,
                        businessPrice,
                        businessDiscount, firstSeatNum, firstPrice, firstDiscount);
                if (AdminDao.updateTicket(newTicket)
                        && AdminDao.updateSeats(TICKET.getFid(), economySeatCodes, businessSeatCodes, firstSeatCodes)) {
                    JOptionPane.showMessageDialog(this, "修改航班和机票信息成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                    runnable.run();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "修改机票信息失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "修改航班信息失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new UpdateAirlineAndTicketsFrame(new Admin(1, "admin", "admin"), new Airline(), new Ticket(), () -> {
        });
    }
}
