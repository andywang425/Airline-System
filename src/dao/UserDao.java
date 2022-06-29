package dao;

import java.sql.*;
import java.text.*;
import java.util.*;

import util.*;
import entity.*;

public class UserDao extends SQLBase {
    /**
     * 判断用户名是否已存在
     * 
     * @param username
     * @return 0：不存在 1：已存在 -1：数据库错误
     */
    public static int isUserExist(String username) {
        String sql = "SELECT * FROM Users, Admins WHERE uname = ? OR aname = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return 1;
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 添加新用户
     * 
     * @param user
     * @return 1：成功 -1，0：失败
     */
    public static int addNewUser(User user) {
        String sql = "INSERT INTO Users(uname, rname, sex, upassword, points, idNum, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getRealName());
            ps.setString(3, user.getSex());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getPoints());
            ps.setString(6, user.getIDNumber());
            ps.setString(7, user.getPhone());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 更新一位用户的信息
     * 
     * @param id
     * @param user
     * @return 1：成功 -1，0：失败
     */
    public static int updateUser(int id, User user) {
        String sql = "UPDATE Users SET uname = ?, rname = ?, sex = ?, upassword = ?, points = ?, idNum = ?, phone = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getRealName());
            ps.setString(3, user.getSex());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getPoints());
            ps.setString(6, user.getIDNumber());
            ps.setString(7, user.getPhone());
            ps.setInt(8, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 通过用户名获取用户信息
     * 
     * @param username
     * @return 用户信息，发生数据库错误则返回 null
     */
    public static User getUserInfobyName(String username) {
        String sql = "SELECT * FROM Users WHERE uname = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setID(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setRealName(rs.getString(3));
                user.setSex(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setPoints(rs.getInt(6));
                user.setIDNumber(rs.getString(7));
                user.setPhone(rs.getString(8));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过用户id获取用户信息（查询效率最高）
     * 
     * @param id
     */
    public static User getUserInfoByID(int id) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setID(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setRealName(rs.getString(3));
                user.setSex(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setPoints(rs.getInt(6));
                user.setIDNumber(rs.getString(7));
                user.setPhone(rs.getString(8));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定用户的常用联系人
     * 
     * @param id
     * @return String[][]
     */
    public static String[][] getContactInfoByID(int id) {
        String sql = "SELECT rname, telNum FROM topcontacts WHERE cid = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            int rows = 0;
            final int columns = 2;
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
     * 删除指定用户的某位联系人
     * 
     * @param id
     * @param name
     * @return 1：删除成功 -1，0：删除失败
     */
    public static int deleteContactInfoByID(int id, String name) {
        String sql = "delete FROM topcontacts WHERE cid = ? AND rname = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 判断指定用户的某位联系人是否存在
     * 
     * @param id
     * @param name
     * @param phone
     * @return 0：不存在 1：已存在 -1：数据库错误
     */
    public static int isContactExist(int id, String name) {
        String sql = "SELECT * FROM topcontacts WHERE cid = ? AND rname = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return 1;
            else
                return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 添加指定用户的某位联系人
     * 
     * @param id
     * @param name
     * @param phone
     * @return 1：添加成功 -1，0：添加失败
     */
    public static int addContactInfo(int id, String name, String phone) {
        String sql = "INSERT INTO topcontacts(cid, rname, telNum) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, phone);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 通过航班号获取航线信息
     * 
     * @param flightNumber
     * @return String[][]
     */
    public static String[][] getAirlineInfoByFlightNumber(String flightNumber) {
        String sql = "SELECT * FROM airlines WHERE flightNumber = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, flightNumber);
            ResultSet rs = ps.executeQuery();
            int rows = 0;
            final int columns = 8;
            ArrayList<String[]> tempTableRows = new ArrayList<String[]>();
            for (int r = 0; rs.next(); r++) {
                tempTableRows.add(new String[columns]);
                for (int c = 2; c <= columns; c++) {
                    tempTableRows.get(r)[c - 2] = rs.getString(c);
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
     * 通过出发日期获取航线信息
     * 
     * @param flightNumber
     * @return String[][]
     */
    public static String[][] getAirlineInfoByDeparetureDate(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date.getTime()) + "%";
        String sql = "SELECT * FROM airlines WHERE takeOffTime like ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, dateStr);
            ResultSet rs = ps.executeQuery();
            int rows = 0;
            final int columns = 8;
            ArrayList<String[]> tempTableRows = new ArrayList<String[]>();
            for (int r = 0; rs.next(); r++) {
                tempTableRows.add(new String[columns]);
                for (int c = 2; c <= columns; c++) {
                    tempTableRows.get(r)[c - 2] = rs.getString(c);
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
     * 获取所有航线的出发地
     * 
     * @param flightNumber
     * @return String[][]
     */
    public static String[] getAirlineDepareture() {
        String sql = "SELECT distinct departure FROM airlines";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ArrayList<String> departureList = new ArrayList<String>();
            while (rs.next()) {
                departureList.add(rs.getString(1));
            }
            final int colums = departureList.size();
            String tableRows[] = new String[colums];
            for (int c = 0; c < colums; c++) {
                tableRows[c] = departureList.get(c);
            }
            return tableRows;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有航线的目的地
     * 
     * @param flightNumber
     * @return String[][]
     */
    public static String[] getAirlineDestination() {
        String sql = "SELECT distinct destination FROM airlines";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ArrayList<String> destinationList = new ArrayList<String>();
            while (rs.next()) {
                destinationList.add(rs.getString(1));
            }
            final int colums = destinationList.size();
            String tableRows[] = new String[colums];
            for (int c = 0; c < colums; c++) {
                tableRows[c] = destinationList.get(c);
            }
            return tableRows;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过起抵地获取航线信息
     * 
     * @param flightNumber
     * @return String[][]
     */
    public static String[][] getAirlineInfoByDepartureDestination(String departure, String destination) {
        String sql = "SELECT * FROM airlines WHERE departure = ? AND destination = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, departure);
            ps.setString(2, destination);
            ResultSet rs = ps.executeQuery();
            int rows = 0;
            final int columns = 8;
            ArrayList<String[]> tempTableRows = new ArrayList<String[]>();
            for (int r = 0; rs.next(); r++) {
                tempTableRows.add(new String[columns]);
                for (int c = 2; c <= columns; c++) {
                    tempTableRows.get(r)[c - 2] = rs.getString(c);
                }
                rows++;
            }
            String tableRows[][] = new String[rows][columns - 1];
            for (int r = 0; r < rows; r++)
                for (int c = 0; c < columns - 1; c++)
                    tableRows[r][c] = tempTableRows.get(r)[c];
            return tableRows;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过航班号获取 fid
     * 
     * @param flightNumber
     * @return int
     */
    public static int getFidByFlightNumber(String flightNumber) {
        String sql = "SELECT fid FROM airlines WHERE flightNumber = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, flightNumber);
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
     * 通过 fid 获取机票信息
     * 
     * @param fid
     * @return int
     */
    public static Ticket getTicketInfoByFid(int fid) {
        String sql = "SELECT * FROM tickets WHERE fid = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, fid);
            ResultSet rs = ps.executeQuery();
            var ticket = new Ticket();
            if (rs.next()) {
                ticket.setFid(rs.getInt(1));
                ticket.setEconomyNum(rs.getInt(2));
                ticket.setEconomyPrice(rs.getInt(3));
                ticket.setEconomyDiscount(rs.getFloat(4));
                ticket.setBusinessNum(rs.getInt(5));
                ticket.setBusinessPrice(rs.getInt(6));
                ticket.setBusinessDiscount(rs.getFloat(7));
                ticket.setFirstNum(rs.getInt(8));
                ticket.setFirstPrice(rs.getInt(9));
                ticket.setFirstDiscount(rs.getFloat(10));
                return ticket;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过 fid，舱位获取座位信息
     * 
     * @param fid
     * @param classTypeCode
     * @return String[][]
     */
    public static String[][] getSeatsInfoByFidClassType(int fid, String classTypeCode) {
        String sql = "SELECT * FROM seats WHERE fid = ? AND classType = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, fid);
            ps.setString(2, classTypeCode);
            ResultSet rs = ps.executeQuery();
            ArrayList<String> seatsList = new ArrayList<String>();
            while (rs.next()) {
                seatsList.add(rs.getString(4));
            }
            final int colums = 6;
            final int rows = (int) Math.ceil(seatsList.size() / (double) colums);
            final int size = seatsList.size();
            String seats[][] = new String[rows][colums];
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < colums; c++) {
                    if (r * colums + c < size) {
                        seats[r][c] = seatsList.get(r * colums + c);
                    } else {
                        seats[r][c] = "";
                    }
                }
            }
            return seats;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把 tickets 表中符合要求的票减少一张
     * 
     * @param fid
     * @param classTypeCode
     * @return boolean
     */
    public static boolean minusOneTicket(int fid, String classTypeCode) {
        String sql = "UPDATE tickets SET __class__ = __class__ - 1 WHERE fid = ?";
        String classNum = Utils.getFullClassNum(classTypeCode);
        sql = sql.replace("__class__", classNum);
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
     * 删除 seats 中的一个座位
     * 
     * @param fid
     * @param classTypeCode
     * @param seat
     * @return boolean
     */
    public static boolean delSeat(int fid, String classTypeCode, String seat) {
        String sql = "delete FROM seats WHERE fid = ? AND classType = ? AND seatCode = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, fid);
            ps.setString(2, classTypeCode);
            ps.setString(3, seat);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 添加一张被卖出的票到 soldtickets 表中
     * 
     * @param fid
     * @param classTypeCode
     * @param seat
     * @return boolean
     */
    public static boolean addOneSoldTickets(User user, int fid, String classTypeCode, String seat, float finalPrice,
            int usedPoints) {
        String sql = "INSERT INTO soldtickets(fid, userid, seatNumber, class, finalPrice, usedPoints, soldTime) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String soldTime = sdf.format(calendar.getTime());
            ps.setInt(1, fid);
            ps.setInt(2, user.getID());
            ps.setString(3, seat);
            ps.setString(4, classTypeCode);
            ps.setFloat(5, finalPrice);
            ps.setInt(6, usedPoints);
            ps.setString(7, soldTime);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断用户是否已购买过该班次的机票
     * 
     * @param fid
     * @return boolean
     */
    public static boolean checkIfBoughtTicket(int userid, int fid) {
        String sql = "SELECT * FROM soldtickets WHERE fid = ? AND userid = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, fid);
            ps.setInt(2, userid);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 购票
     * 
     * @param user       User类
     * @param fid
     * @param classType
     * @param seatCode
     * @param gotPoints  购票时获得的积分
     * @param finalPrice 最终支付的票价
     * @param usedPoints 使用的积分
     * @return boolean
     */
    public static boolean buyTicket(User user, int fid, String classType, String seatCode, int gotPoints,
            float finalPrice, int usedPoints) {
        user.setPoints(gotPoints);
        if (minusOneTicket(fid, classType) && delSeat(fid, classType, seatCode)
                && addOneSoldTickets(user, fid, classType, seatCode, finalPrice, usedPoints) &&
                updateUser(user.getID(), user) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 通过 fid 获取航班信息
     * 
     * @param fid
     * @return String[][]
     */
    public static String[] getAirlineInfoByFid(int fid) {
        String sql = "SELECT * FROM airlines WHERE fid = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, fid);
            ResultSet rs = ps.executeQuery();
            String result[] = new String[7];
            if (rs.next()) {
                for (int i = 0; i < 7; i++) {
                    result[i] = rs.getString(i + 2);
                }
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取某位用户的所有订单
     * 
     * @param userid
     * @return String[][]
     */
    public static String[][] getUserOrder(int userid) {
        String sql = "SELECT * FROM soldtickets WHERE userid = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            int rows = 0;
            final int columns = 5;
            ArrayList<String[]> tempTableRows = new ArrayList<String[]>();
            for (int r = 0; rs.next(); r++) {
                tempTableRows.add(new String[columns]);
                tempTableRows.get(r)[0] = String.valueOf(rs.getInt("fid"));
                tempTableRows.get(r)[1] = rs.getString("seatNumber");
                tempTableRows.get(r)[2] = rs.getString("class");
                tempTableRows.get(r)[3] = String.valueOf(rs.getFloat("finalPrice"));
                tempTableRows.get(r)[4] = rs.getString("soldTime");
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
     * 删除一位用户的一个订单
     * 
     * @param fid
     * @param userid
     * @return boolean
     */
    public static boolean delOrder(int userid, int fid) {
        String sql = "delete FROM soldtickets WHERE fid = ? AND userid = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, fid);
            ps.setInt(2, userid);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 添加一个座位
     * 
     * @param fid
     * @param classType
     * @param seat
     * @return boolean
     */
    public static boolean addSeat(int fid, String classType, String seat) {
        String sql = "INSERT INTO seats(fid, classType, seatCode) VALUES(?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, fid);
            ps.setString(2, classType);
            ps.setString(3, seat);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 给指定航班的指定舱位加一张机票
     * 
     * @param fid
     * @param classType
     * @return boolean
     */
    public static boolean addOneTicket(int fid, String classType) {
        String sql = "Update tickets SET __classTypeNum__ = __classTypeNum__ + 1 WHERE fid = ?";
        sql = sql.replace("__classTypeNum__", Utils.getFullClassNum(classType));
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
     * 退票
     * 
     * @param user
     * @param fid
     * @param classType
     * @param seatCode
     * @param price
     * @return boolean
     */
    public static boolean refundTicket(User user, int fid, String classType, String seatCode, float price) {
        int points = user.getPoints();
        int newPoints = points - (int) (price * 1.2);
        newPoints = newPoints < 0 ? 0 : newPoints;
        user.setPoints(newPoints);
        if (delOrder(user.getID(), fid) && addSeat(fid, classType, seatCode) && addOneTicket(fid, classType)
                && updateUser(user.getID(), user) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 获取改签信息
     * 
     * @param fid
     * @param departure
     * @param destination
     * @return String[][]
     */
    public static String[][] getRebookInfo(int fid, String departure, String destination) {
        String sql = "SELECT * FROM airlines WHERE departure = ? AND destination = ? AND fid != ? AND takeOffTime > now()";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, departure);
            ps.setString(2, destination);
            ps.setInt(3, fid);
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
     * 改签机票
     * 
     * @param user
     * @param fid
     * @param classType
     * @param seatCode
     * @param rebookFid
     * @return boolean
     */
    public static boolean rebookTicket(User user, int fid, String classType, String seatCode, int rebookFid,
            String newClassType, float newFinalPrice, String newSeatCode) {
        if (
        // 退票
        delOrder(user.getID(), fid) && addSeat(fid, classType, seatCode) && addOneTicket(fid, classType) &&
        // 购票
                minusOneTicket(rebookFid, newClassType) && delSeat(rebookFid, newClassType, newSeatCode) &&
                addOneSoldTickets(user, rebookFid, newClassType, newSeatCode, newFinalPrice, 0)) {
            return true;
        }
        return false;
    }

    /**
     * 判断该身份证是否已注册过账号
     * @param idNumber
     * @return
     */
    public static boolean isIdNumberExist(String idNumber) {
        String sql = "SELECT * FROM Users WHERE idNum = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}