package entity;

public class Ticket {
    public int fid;
    public int economyNum;
    public int economyPrice;
    public float economyDiscount;
    public int businessNum;
    public int businessPrice;
    public float businessDiscount;
    public int firstNum;
    public int firstPrice;
    public float firstDiscount;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getEconomyNum() {
        return economyNum;
    }

    public void setEconomyNum(int economyNum) {
        this.economyNum = economyNum;
    }

    public int getEconomyPrice() {
        return economyPrice;
    }

    public void setEconomyPrice(int economyPrice) {
        this.economyPrice = economyPrice;
    }

    public float getEconomyDiscount() {
        return economyDiscount;
    }

    public void setEconomyDiscount(float economyDiscount) {
        this.economyDiscount = economyDiscount;
    }

    public int getBusinessNum() {
        return businessNum;
    }

    public void setBusinessNum(int businessNum) {
        this.businessNum = businessNum;
    }

    public int getBusinessPrice() {
        return businessPrice;
    }

    public void setBusinessPrice(int businessPrice) {
        this.businessPrice = businessPrice;
    }

    public float getBusinessDiscount() {
        return businessDiscount;
    }

    public void setBusinessDiscount(float businessDiscount) {
        this.businessDiscount = businessDiscount;
    }

    public int getFirstNum() {
        return firstNum;
    }

    public void setFirstNum(int firstNum) {
        this.firstNum = firstNum;
    }

    public int getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(int firstPrice) {
        this.firstPrice = firstPrice;
    }

    public float getFirstDiscount() {
        return firstDiscount;
    }

    public void setFirstDiscount(float firstDiscount) {
        this.firstDiscount = firstDiscount;
    }

    /**
     * 机票实例
     * 
     * @param fid
     * @param economyNum
     * @param economyPrice
     * @param economyDiscount
     * @param businessNum
     * @param businessPrice
     * @param businessDiscount
     * @param firstNum
     * @param firstPrice
     * @param firstDiscount
     */
    public Ticket(int fid, int economyNum, int economyPrice, float economyDiscount, int businessNum,
            int businessPrice, float businessDiscount, int firstNum, int firstPrice, float firstDiscount) {
        this.fid = fid;
        this.economyNum = economyNum;
        this.economyPrice = economyPrice;
        this.economyDiscount = economyDiscount;
        this.businessNum = businessNum;
        this.businessPrice = businessPrice;
        this.businessDiscount = businessDiscount;
        this.firstNum = firstNum;
        this.firstPrice = firstPrice;
        this.firstDiscount = firstDiscount;
    }

    /**
     * 机票实例（无 fid）
     * 
     * @param economyNum
     * @param economyPrice
     * @param economyDiscount
     * @param businessNum
     * @param businessPrice
     * @param businessDiscount
     * @param firstNum
     * @param firstPrice
     * @param firstDiscount
     */
    public Ticket(int economyNum, int economyPrice, float economyDiscount, int businessNum, int businessPrice,
            float businessDiscount, int firstNum, int firstPrice, float firstDiscount) {
        this.economyNum = economyNum;
        this.economyPrice = economyPrice;
        this.economyDiscount = economyDiscount;
        this.businessNum = businessNum;
        this.businessPrice = businessPrice;
        this.businessDiscount = businessDiscount;
        this.firstNum = firstNum;
        this.firstPrice = firstPrice;
        this.firstDiscount = firstDiscount;
    }

    public Ticket() {

    }
}
