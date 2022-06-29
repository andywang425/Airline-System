package entity;

public class Airline {
    public int fid;
    public String company;
    public String flightType;
    public String flightNumber;
    public String takeOffTime;
    public String arrivalTime;
    public String departure;
    public String destination;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getTakeOffTime() {
        return takeOffTime;
    }

    public void setTakeOffTime(String takeOffTime) {
        this.takeOffTime = takeOffTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * 航班实例
     * 
     * @param fid
     * @param company
     * @param flightType
     * @param flightNumber
     * @param takeOffTime
     * @param arrivalTime
     * @param departure
     * @param destination
     */
    public Airline(int fid, String company, String flightType, String flightNumber, String takeOffTime,
            String arrivalTime, String departure, String destination) {
        this.fid = fid;
        this.company = company;
        this.flightType = flightType;
        this.flightNumber = flightNumber;
        this.takeOffTime = takeOffTime;
        this.arrivalTime = arrivalTime;
        this.departure = departure;
        this.destination = destination;
    }

    /**
     * 航班实例（无 fid ）
     * 
     * @param company
     * @param flightType
     * @param flightNumber
     * @param takeOffTime
     * @param arrivalTime
     * @param departure
     * @param destination
     */
    public Airline(String company, String flightType, String flightNumber, String takeOffTime,
            String arrivalTime, String departure, String destination) {
        this.company = company;
        this.flightType = flightType;
        this.flightNumber = flightNumber;
        this.takeOffTime = takeOffTime;
        this.arrivalTime = arrivalTime;
        this.departure = departure;
        this.destination = destination;
    }

    public Airline() {

    }
}
