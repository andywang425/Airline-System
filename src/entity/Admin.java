package entity;

public class Admin {
    public int aid;
    public String name;
    public String password;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 管理员实例
     * 
     * @param aid
     * @param name
     * @param password
     */
    public Admin(int aid, String name, String password) {
        this.aid = aid;
        this.name = name;
        this.password = password;
    }

    /**
     * 管理员实例（无 aid）
     * 
     * @param name
     * @param password
     */
    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Admin() {

    }
}
