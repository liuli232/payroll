package payroll;

public class TimeCard {

    private String date;
    private double hours;

    public TimeCard(String date, double hours) {
        this.date = date;
        this.hours = hours;
        
    }

    public String getDate() {
        return date;
    }

    public Double getHours() {
        return hours;
    }

}
