package view;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import util.*;
import entity.*;

public class RebookFrame extends JFrame implements ActionListener {
    String columns[] = { "航空公司", "机型", "航班号", "起飞时间", "抵达时间", "出发地", "目的地" };
    User USER;
    int fid;
    float price;
    String classType, seat;
    Runnable runnable;
    JButton confirmButton = new JButton("确认");
    JTable table = new JTable();
    String rawRows[][];
    String rows[][];
    ArrayList<JFrame> frames = new ArrayList<JFrame>();

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
                    confirmButton.setEnabled(true);
                } else {
                    confirmButton.setEnabled(false);
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

    public RebookFrame(User USER, String[][] rawRows, int fid, String classType, String seat, float price,
            Runnable runnable) {
        this.USER = USER;
        this.rawRows = rawRows;
        this.fid = fid;
        this.classType = classType;
        this.seat = seat;
        this.price = price;
        this.runnable = runnable;
        this.rows = new String[rawRows.length][rawRows[0].length - 1];
        // 复制时去掉 rawrows 中每行的第一列
        for (int i = 0; i < rawRows.length; i++) {
            rows[i] = Arrays.copyOfRange(rawRows[i], 1, rawRows[i].length);
        }
        setLayout(new GridBagLayout());
        confirmButton.setFont(new Font(null, Font.PLAIN, 20));
        add(new TablePanel(), new GBC(0, 0).setFill(GBC.BOTH).setWeight(100, 1000).setInsets(10, 10, 10, 10));
        add(confirmButton,
                new GBC(0, 1).setFill(GBC.VERTICAL).setWeight(100, 100).setInsets(10, 10, 10, 10).setIpad(100, 0));
        confirmButton.setEnabled(false);
        confirmButton.addActionListener(this);
        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                for (JFrame frame : frames) {
                    frame.dispose();
                }
            }
        });
        setTitle("请选择要改签的航班");
        setIconImage(new ImageIcon("assets/pic/frameIcon.png").getImage());
        setSize(1000, 800);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            int row = table.getSelectedRow();
            int selectFid = Integer.parseInt(rawRows[row][0]);
            frames.add(new BuyTicketFrame(USER, selectFid, 1, fid, classType, seat, price, () -> {
                runnable.run();
                dispose();
            }));
        }

    }

    public static void main(String[] args) {
        new RebookFrame(null, new String[][] { { "", "", "", "", "", "", "", "" } }, 0, null, null, 0, () -> {
        });
    }
}
