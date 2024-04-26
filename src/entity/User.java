package entity;

public class User {
    public int id;
    public String username;
    public String realName;
    public String sex;
    public String password;
    public int points;
    public String idNumber;
    public String phone;

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getIDNumber() {
        return idNumber;
    }

    public void setIDNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 用户实例
     * 
     * @param id       用户ID
     * @param username 用户名
     * @param realName 真实姓名
     * @param sex      性别（男，女）
     * @param password 密码
     * @param points   积分
     * @param idNumber 身份证号
     * @param phone    联系电话
     */
    public User(int id, String username, String realName, String sex, String password, int points, String idNumber,
            String phone) {
        this.id = id;
        this.username = username;
        this.realName = realName;
        this.sex = sex;
        this.password = password;
        this.points = points;
        this.idNumber = idNumber;
        this.phone = phone;
    }

    /**
     * 用户实例（无ID）
     * 
     * @param username 用户名
     * @param realName 真实姓名
     * @param sex      性别（男，女）
     * @param password 密码
     * @param points   积分
     * @param idNumber 身份证号
     * @param phone    联系电话
     */
    public User(String username, String realName, String sex, String password, int points, String idNumber,
            String phone) {
        this.username = username;
        this.realName = realName;
        this.sex = sex;
        this.password = password;
        this.points = points;
        this.idNumber = idNumber;
        this.phone = phone;
    }

    public User() {

    }
}
