package main.payroll.classification;

import main.payroll.test.SalesReceipt;

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

    public SalesReceipt getSalesReceiptOfDate(String date) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSalesReceiptOfDate'");
    }

    public void addSalesReceipt(SalesReceipt sr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addSalesReceipt'");
    }

}
