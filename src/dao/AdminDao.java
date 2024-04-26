package dao;

import java.sql.*;
import java.util.*;

import util.*;
import entity.*;

public class AdminDao extends SQLBase {
    public static Admin getAdminInfobyName(String aname) {
        String sql = "SELECT * FROM Admins WHERE aname = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, aname);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setAid(rs.getInt("aid"));
                admin.setName(rs.getString("aname"));
                admin.setPassword(rs.getString("apassword"));
                return admin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新 Admin
     * 
     * @param admin
     * @return
     */
    public static int updateAdmin(Admin admin) {
        String sql = "UPDATE Admins SET aname = ?, apassword = ? WHERE aid = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, admin.getName());
            ps.setString(2, admin.getPassword());
            ps.setInt(3, admin.getAid());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取所有航班信息
     * 
     * @return String[][]
     */
    public static String[][] getAllAirlineInfo() {
        String sql = "SELECT * FROM Airlines";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int rows = 0;
            final int columns = 8;
            ArrayList<String[]> tempTableRows = new ArrayList<String[]>();
            for (int r = 0; rs.next(); r++) {
                tempTableRows.add(new String[columns]);
                for (int c = 1; c <= columns; c++) {
                    tempTableRows.get(r)[c - 1] = rs.getString(c);
                }
                rows++;
            }
            String tableRows[][] = new String[rows][columns];
            for (int r = 0; r < rows; r++)
                for (int c = 0; c < columns; c++)
                    tableRows[r][c] = tempTableRows.get(r)[c];
            return tableRows;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有机票信息
     * 
     * @return String[][]
     */
    public static String[][] getAllTicketInfo() {
        String sql = "SELECT * FROM Tickets";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int rows = 0;
            final int columns = 10;
            ArrayList<String[]> tempTableRows = new ArrayList<String[]>();
            for (int r = 0; rs.next(); r++) {
                tempTableRows.add(new String[columns]);
                for (int c = 1; c <= columns; c++) {
                    tempTableRows.get(r)[c - 1] = rs.getString(c);
                }
                rows++;
            }
            String tableRows[][] = new String[rows][columns];
            for (int r = 0; r < rows; r++)
                for (int c = 0; c < columns; c++)
                    tableRows[r][c] = tempTableRows.get(r)[c];
            return tableRows;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加一个新航班
     * 
     * @param airline
     * @return boolean
     */
    public static boolean addNewAirline(Airline airline) {
        String sql = "INSERT INTO Airlines(company, fltType, flightNumber, takeOffTime, arrivalTime, departure, destination) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, airline.getCompany());
            ps.setString(2, airline.getFlightType());
            ps.setString(3, airline.getFlightNumber());
            ps.setString(4, airline.getTakeOffTime());
            ps.setString(5, airline.getArrivalTime());
            ps.setString(6, airline.getDeparture());
            ps.setString(7, airline.getDestination());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取 airlines 中最大的 fid
     * 
     * @return fid
     */
    public static int getMaxFid() {
        String sql = "SELECT MAX(fid) FROM airlines";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 添加一类新机票（与航班同步添加）
     * 
     * @param ticket
     * @return boolean
     */
    public static boolean addNewTicket(Ticket ticket) {
        String sql = "INSERT INTO Tickets VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ticket.getFid());
            ps.setInt(2, ticket.getEconomyNum());
            ps.setInt(3, ticket.getEconomyPrice());
            ps.setFloat(4, ticket.getEconomyDiscount());
            ps.setInt(5, ticket.getBusinessNum());
            ps.setInt(6, ticket.getBusinessPrice());
            ps.setFloat(7, ticket.getBusinessDiscount());
            ps.setInt(8, ticket.getFirstNum());
            ps.setInt(9, ticket.getFirstPrice());
            ps.setFloat(10, ticket.getFirstDiscount());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 插入一条座位信息
     * 
     * @param fid
     * @param classType
     * @param seatCode
     * @return boolean
     */
    public static boolean insertOneSeat(int fid, String classType, String seatCode) {
        String sql = "INSERT INTO Seats(fid, classType, seatCode) VALUES(?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, fid);
            ps.setString(2, classType);
            ps.setString(3, seatCode);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 添加座位信息
     * 
     * @param fid
     * @param economySeatCodes
     * @param businessSeatCodes
     * @param firstSeatCodes
     * @return boolean
     */
    public static boolean addNewSeats(int fid, String[] economySeatCodes, String[] businessSeatCodes,
            String[] firstSeatCodes) {
        for (String e : economySeatCodes) {
            if (!insertOneSeat(fid, "E", e))
                return false;
        }
        for (String b : businessSeatCodes) {
            if (!insertOneSeat(fid, "B", b))
                return false;
        }
        for (String f : firstSeatCodes) {
            if (!insertOneSeat(fid, "F", f))
                return false;
        }
        return true;
    }

    /**
     * 获取座位信息
     * 
     * @param fid
     * @return String[]
     */
    public static String[][] getSeatInfoByFid(int fid) {
        String sql = "SELECT * FROM Seats WHERE fid = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, fid);
            ResultSet rs = ps.executeQuery();
            var economySeatCodes = new ArrayList<String>();
            var businessSeatCodes = new ArrayList<String>();
            var firstSeatCodes = new ArrayList<String>();
            while (rs.next()) {
                String classType = rs.getString("classType");
                String seatCode = rs.getString("seatCode");
                switch (classType) {
                    case "E":
                        economySeatCodes.add(seatCode);
                        break;
                    case "B":
                        businessSeatCodes.add(seatCode);
                        break;
                    case "F":
                        firstSeatCodes.add(seatCode);
                        break;
                }
            }
            return new String[][] { economySeatCodes.toArray(new String[0]),
                    businessSeatCodes.toArray(new String[0]), firstSeatCodes.toArray(new String[0]) };
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新航班信息
     * 
     * @param airline
     * @return boolean
     */
    public static boolean updateAirline(Airline airline) {
        String sql = "UPDATE airlines SET company = ?, fltType = ?, flightNumber = ?, takeOffTime = ?, arrivalTime = ?, departure = ?, destination = ? WHERE fid = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, airline.getCompany());
            ps.setString(2, airline.getFlightType());
            ps.setString(3, airline.getFlightNumber());
            ps.setString(4, airline.getTakeOffTime());
            ps.setString(5, airline.getArrivalTime());
            ps.setString(6, airline.getDeparture());
            ps.setString(7, airline.getDestination());
            ps.setInt(8, airline.getFid());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 更新机票信息
     * 
     * @param ticket
     * @return boolean
     */
    public static boolean updateTicket(Ticket ticket) {
        String sql = "UPDATE Tickets SET economyNum = ?, economyPrice = ?, economyDiscount = ?, businessNum = ?, businessPrice = ?, businessDiscount = ?, firstNum = ?, firstPrice = ?, firstDiscount = ? WHERE fid = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ticket.getEconomyNum());
            ps.setInt(2, ticket.getEconomyPrice());
            ps.setFloat(3, ticket.getEconomyDiscount());
            ps.setInt(4, ticket.getBusinessNum());
            ps.setInt(5, ticket.getBusinessPrice());
            ps.setFloat(6, ticket.getBusinessDiscount());
            ps.setInt(7, ticket.getFirstNum());
            ps.setInt(8, ticket.getFirstPrice());
            ps.setFloat(9, ticket.getFirstDiscount());
            ps.setInt(10, ticket.getFid());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除该 fid 的所有座位
     * 
     * @param fid
     * @return boolean
     */
    public static boolean delSeatsByFid(int fid) {
        String sql = "DELETE FROM Seats WHERE fid = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, fid);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 更新座位信息
     * 
     * @param fid
     * @param economySeatCodes
     * @param businessSeatCodes
     * @param firstSeatCodes
     * @return boolean
     */
    public static boolean updateSeats(int fid, String[] economySeatCodes, String[] businessSeatCodes,
            String[] firstSeatCodes) {
        if (delSeatsByFid(fid)) {
            return addNewSeats(fid, economySeatCodes, businessSeatCodes, firstSeatCodes);
        }
        return false;
    }

    /**
     * 删除航班信息
     * 
     * @param fid
     * @return boolean
     */
    public static boolean delAirline(int fid) {
        String sql = "DELETE FROM airlines WHERE fid = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, fid);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除机票信息
     * 
     * @param fid
     * @return boolean
     */
    public static boolean delTicket(int fid) {
        String sql = "DELETE FROM Tickets WHERE fid = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, fid);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取一个时间段内的订单和航班信息
     * 
     * @param startTime
     * @param endTime
     * @return String[][8]
     */
    public static String[][] getSoldTicketsAndAirlineByTime(String startTime, String endTime) {
        String sql = "SELECT SoldTickets.userid, Airlines.company, Airlines.fltType, Airlines.flightNumber, Airlines.takeOffTime, Airlines.arrivalTime, Airlines.departure, Airlines.destination, SoldTickets.seatNumber, SoldTickets.class, SoldTickets.finalPrice, SoldTickets.usedPoints, SoldTickets.soldTime FROM soldtickets, airlines WHERE SoldTickets.fid = Airlines.fid AND SoldTickets.soldTime BETWEEN ? AND ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, startTime);
            ps.setString(2, endTime);
            ResultSet rs = ps.executeQuery();
            int rows = 0;
            final int columns = 13;
            ArrayList<String[]> tempResult = new ArrayList<String[]>();
            for (int r = 0; rs.next(); r++) {
                tempResult.add(new String[columns]);
                for (int c = 1; c <= columns; c++) {
                    tempResult.get(r)[c - 1] = rs.getString(c);
                }
                rows++;
            }
            String result[][] = new String[rows][columns];
            for (int r = 0; r < rows; r++)
                for (int c = 0; c < columns; c++)
                    result[r][c] = tempResult.get(r)[c];
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取一个时间段内的所有航班信息
     * 
     * @param startTime
     * @param endTime
     * @return String[][8]
     */
    public static String[][] getAirlineInfoByTime(String startTime, String endTime) {
        String sql = "SELECT * FROM airlines WHERE takeoffTime BETWEEN ? AND ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, startTime);
            ps.setString(2, endTime);
            ResultSet rs = ps.executeQuery();
            int rows = 0;
            final int columns = 8;
            ArrayList<String[]> tempResult = new ArrayList<String[]>();
            for (int r = 0; rs.next(); r++) {
                tempResult.add(new String[columns]);
                for (int c = 1; c <= columns; c++) {
                    tempResult.get(r)[c - 1] = rs.getString(c);
                }
                rows++;
            }
            String result[][] = new String[rows][columns];
            for (int r = 0; r < rows; r++)
                for (int c = 0; c < columns; c++)
                    result[r][c] = tempResult.get(r)[c];
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}