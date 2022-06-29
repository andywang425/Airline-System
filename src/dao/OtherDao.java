package dao;

import java.sql.*;
import util.*;

public class OtherDao extends SQLBase {
    /**
     * 登录校验
     * 
     * @param username
     * @param password
     * @return 0：用户名或密码错误 1：用户 2：管理员 -1：数据库错误
     */
    public static int login(String username, String password) {
        String sqlUsers = "SELECT * FROM Users WHERE uname = ? AND upassword = ?",
                sqlAdmins = "SELECT * FROM Admins WHERE aname = ? AND apassword = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sqlUsers);
            // 判断是否是用户
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return 1;
            // 判断是否是管理员
            ps.close();
            ps = conn.prepareStatement(sqlAdmins);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next())
                return 2;
            // 若都不是，则用户名或密码错误
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
