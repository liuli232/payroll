package payroll;

public class CommissionedClassification implements PaymentClassification {

    private double salary;
    private double commissionRate;

    public CommissionedClassification(double salary, double commissionRate) {
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    public double Salary() {
        return salary;
    }

    public double CommissionRate() {
        return commissionRate;
    }

}
