package util;

public class SQLBase {
	public static String JDBC_DRIVER_CLASSNAME = "com.mysql.cj.jdbc.Driver";
	public static String DB_URL = "jdbc:mysql://127.0.0.1:3306/airline_system?serverTimezone=UTC";
	public static String USER = "root";
	public static String PASSWORD = "root";

    static {
        try {
            Class.forName(JDBC_DRIVER_CLASSNAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
