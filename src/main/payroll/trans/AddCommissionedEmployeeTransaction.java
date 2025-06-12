package main.payroll.trans;

import main.payroll.classification.CommissionedClassification;
import main.payroll.classification.PaymentClassification;

public class AddCommissionedEmployeeTransaction extends AddEmployeeTransaction {

    private double salary;
    private double commissionRate;

    public AddCommissionedEmployeeTransaction(int empId, String name, String address, double salary,
            double commissionRate) {
        super(empId, name, address);
        this.salary = salary;
        this.commissionRate = commissionRate;
        
    }
    @Override
    protected PaymentClassification getPaymentClassification() {
        
        return new CommissionedClassification(salary, commissionRate);
    }

}
