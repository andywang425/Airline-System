package util;

import java.text.*;
import java.util.*;

import dao.*;
import entity.*;

public class Validate {
    /**
     * 登录验证
     * 
     * @param str
     * @return 0：用户名或密码错误 1：用户 2：管理员 -1：数据库错误 -2：用户名或密码为空
     */
    public static int login(String username, String password) {
        if (username.equals("") || password.equals(""))
            return -2;
        return OtherDao.login(username, password);
    }

    /**
     * 判断用户名是否合法
     * 
     * @param str
     * @return 0：合法 1：为空 2：长度大于20 3：用户名已存在 -1：数据库错误
     */
    public static int isUsername(String str) {
        int length = str.length();
        if (length == 0)
            return 1;
        if (length > 20)
            return 2;
        int result = UserDao.isUserExist(str);
        if (result == 1)
            return 3;
        if (result == -1)
            return -1;
        return 0;
    }

    /**
     * 判断密码是否合法
     * 
     * @param str
     * @return 0：符合要求 1：密码长度小于6 2：密码长度大于20
     */
    public static int isPassword(String str) {
        int length = str.length();
        if (length < 6)
            return 1;
        if (length > 20)
            return 2;
        return 0;
    }

    /**
     * 判断真实姓名是否合法
     * 
     * @param str
     * @return 0：合法 1：为空 2：长度大于10
     */
    public static int isRealname(String str) {
        int length = str.length();
        if (length == 0)
            return 1;
        if (length > 10)
            return 2;
        return 0;
    }

    /**
     * 判断身份证号是否合法
     * 
     * @param str
     * @return Boolean
     */
    public static boolean isID(String str) {
        if (str.length() != 18)
            return false;
        int[] weight = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
        char[] validate = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += Integer.parseInt(str.substring(i, i + 1)) * weight[i];
        }
        int mod = sum % 11;
        if (validate[mod] == str.substring(17, 18).toUpperCase().charAt(0))
            return true;
        else
            return false;
    }

    /**
     * 判断联系电话是否合法
     * 
     * @param str
     * @return Boolean
     */
    public static boolean isPhone(String str) {
        String tel = "^(?:(?:\\d{3}-)?\\d{8}|^(?:\\d{4}-)?\\d{7,8})(?:-\\d+)?$",
                phone = "(?:(?:\\+|00)86)?1[3-9]\\d{9}$";
        if (str.matches(tel) || str.matches(phone))
            return true;
        else
            return false;
    }

    /**
     * 判断航线信息是否正确
     * 
     * @param airline
     * @return String "CORRECT"：正确 其他：错误信息
     */
    public static String validateAirline(Airline airline) {
        Calendar cal1 = Calendar.getInstance(),
                cal2 = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (airline.getCompany().equals(""))
            return "航空公司不能为空";
        if (airline.getCompany().length() > 10)
            return "航空公司长度不能超过10";
        if (airline.getFlightType().equals(""))
            return "机型不能为空";
        if (airline.getFlightType().length() != 3)
            return "机型代码长度必须为3";
        if (airline.getFlightNumber().equals(""))
            return "航班号不能为空";
        if (airline.getFlightNumber().length() > 10)
            return "航班号长度不能超过10";
        if (airline.getTakeOffTime().equals(""))
            return "起飞时间不能为空";
        try {
            cal1.setTime(sdf.parse(airline.getTakeOffTime()));
        } catch (ParseException e) {
            return "起飞时间格式不正确";
        }
        if (airline.getArrivalTime().equals(""))
            return "抵达时间不能为空";
        try {
            cal2.setTime(sdf.parse(airline.getArrivalTime()));
        } catch (ParseException e) {
            return "抵达时间格式不正确";
        }
        if (cal1.after(cal2))
            return "起飞时间不能晚于抵达时间";
        if (airline.getDeparture().equals(""))
            return "出发地不能为空";
        if (airline.getDeparture().length() > 10)
            return "出发地长度不能超过10";
        if (airline.getDestination().equals(""))
            return "目的地不能为空";
        if (airline.getDestination().length() > 10)
            return "目的地长度不能超过10";
        return "CORRECT";
    }

    /**
     * 校验座位信息
     * 
     * @param economySeatNum
     * @param businessSeatNum
     * @param firstSeatNum
     * @param economySeatCodes
     * @param businessSeatCodes
     * @param firstSeatCodes
     * @return String "CORRECT"：正确 其他：错误信息
     */
    public static String validateSeats(int economySeatNum, int businessSeatNum, int firstSeatNum,
            String[] economySeatCodes, String[] businessSeatCodes, String[] firstSeatCodes) {
        Boolean economyFlag = false, businessFlag = false, firstFlag = false;
        if (economySeatNum == 0 && economySeatCodes.length == 1 && economySeatCodes[0].equals(""))
            economyFlag = true;
        if (businessSeatNum == 0 && businessSeatCodes.length == 1 && businessSeatCodes[0].equals(""))
            businessFlag = true;
        if (firstSeatNum == 0 && firstSeatCodes.length == 1 && firstSeatCodes[0].equals(""))
            firstFlag = true;
        if (economySeatNum != economySeatCodes.length // 长度不匹配
                && !economyFlag) // 排除数量0且不填任何座位号的情况
            return "经济舱座位数量与座位号数量不一致";
        if (businessSeatNum != businessSeatCodes.length
                && !businessFlag)
            return "商务舱座位数量与座位号数量不一致";
        if (firstSeatNum != firstSeatCodes.length
                && !firstFlag)
            return "头等舱座位数量与座位号数量不一致";
        if (!economyFlag) {
            for (int i = 0; i < economySeatCodes.length; i++) {
                if (economySeatCodes[i].length() != 3)
                    return "经济舱座位号长度必须为3";
            }
        }
        if (!businessFlag) {
            for (int i = 0; i < businessSeatCodes.length; i++) {
                if (businessSeatCodes[i].length() != 3)
                    return "商务舱座位号长度必须为3";
            }
        }
        if (!firstFlag) {
            for (int i = 0; i < firstSeatCodes.length; i++) {
                if (firstSeatCodes[i].length() != 3)
                    return "头等舱座位号长度必须为3";
            }
        }
        return "CORRECT";

    }
}
