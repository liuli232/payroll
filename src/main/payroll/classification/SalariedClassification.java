package main.payroll.classification;

public class SalariedClassification implements PaymentClassification {
    private double salary;

    public SalariedClassification(double salary) {
        
        this.salary = salary;
    }

    public Double Salary() {
        return salary;
    }

}
