package util;

import entity.Ticket;

public class Utils {
    /**
     * 通过 class 短名称获取 classNum 字符串
     * 
     * @param classType
     * @return
     */
    public static String getFullClassNum(String classType) {
        if (classType.equals("E"))
            return "economyNum";
        else if (classType.equals("B"))
            return "businessNum";
        else if (classType.equals("F"))
            return "firstNum";
        else
            return null;
    }

    public static String getChineseClassName(String classType) {
        if (classType.equals("E"))
            return "经济舱";
        else if (classType.equals("B"))
            return "商务舱";
        else if (classType.equals("F"))
            return "头等舱";
        else
            return null;
    }

    /**
     * 合并用户订单和航班信息
     * 
     * @param order
     * @param airline
     * @return 合并后二维数组 c
     */
    public static String[][] combineAirlineInfoOrder(String[][] airline, String[][] order) {
        if (airline.length == 0 || order.length == 0)
            return new String[][] {};
        String[][] c = new String[airline.length][order[0].length + airline[0].length - 1];
        // 每次循环存一行的数据
        for (int i = 0; i < airline.length; i++) {
            // 把 airline 中的数据存到 c 中
            for (int j = 0; j < airline[0].length; j++) {
                c[i][j] = airline[i][j];
            }
            // 把 order 中的数据存到 c 中，第一个是 fid 需要丢弃
            for (int j = 1; j < order[0].length; j++) {
                if (j != 2) {
                    c[i][j + airline[0].length - 1] = order[i][j];
                } else {
                    c[i][j + airline[0].length - 1] = getChineseClassName(order[i][j]);
                }
            }
        }
        return c;
    }

    /**
     * 获取改签后的舱位
     * 
     * @param newTicket
     * @param classType
     * @return String
     */
    public static String getRebootClassType(Ticket newTicket, String classType) {
        String newClassType;
        switch (classType) {
            case "E":
                if (newTicket.getEconomyNum() > 0) {
                    newClassType = "E";
                    break;
                }
            case "B":
                if (newTicket.getBusinessNum() > 0) {
                    newClassType = "B";
                    break;
                }
            case "F":
                if (newTicket.getFirstNum() > 0) {
                    newClassType = "F";
                    break;
                }
            default:
                if (newTicket.getEconomyNum() > 0)
                    newClassType = "E";
                else if (newTicket.getBusinessNum() > 0)
                    newClassType = "B";
                else
                    newClassType = "F";
        }
        return newClassType;
    }

    /**
     * 计算改签时的票价
     * 优先选择用户之前的舱位，没有的话选择最便宜的舱位
     * 
     * @param newTicket
     * @param newClassType
     * @return
     */
    public static float calcRebookFinalPrice(Ticket newTicket, String newClassType) {
        float newFinalPrice = 0;
        switch (newClassType) {
            case "E":
                newFinalPrice = newTicket.getEconomyPrice() * newTicket.getEconomyDiscount();
                break;
            case "B":
                newFinalPrice = newTicket.getBusinessPrice() * newTicket.getBusinessDiscount();
                break;
            case "F":
                newFinalPrice = newTicket.getFirstPrice() * newTicket.getFirstDiscount();
                break;
        }
        return newFinalPrice;
    }

    /**
     * 合并航班和机票信息
     * 
     * @param airline
     * @param ticket
     * @return String[][]
     */
    public static String[][] combineAirlineTickets(String[][] airline, String[][] ticket) {
        if (airline.length == 0 || ticket.length == 0)
            return new String[][] {};
        String[][] c = new String[airline.length][ticket[0].length + airline[0].length - 2];
        // 每次循环存一行的数据
        for (int i = 0; i < airline.length; i++) {
            // 把 airline 中的数据存到 c 中
            for (int j = 1; j < airline[0].length; j++) {
                c[i][j - 1] = airline[i][j];
            }
            // 注意调换顺序
            for (int j = 1; j < ticket[0].length; j += 3) {
                c[i][airline[0].length + j - 2] = ticket[i][j + 1];
                c[i][airline[0].length + j - 1] = ticket[i][j + 2];
                c[i][airline[0].length + j] = ticket[i][j];
            }
        }
        return c;
    }
}
