package main.payroll.trans;

import main.payroll.Employee;
import main.payroll.PayrollDatabase;
import main.payroll.Transaction;
import main.payroll.classification.CommissionedClassification;
import main.payroll.classification.PaymentClassification;
import main.payroll.exception.NoSuchEmployeeException;
import main.payroll.test.SalesReceipt;
import main.payroll.exception.NotCommissionedClassificationException;

public class SalesReceiptTransaction implements Transaction {

    private int empId;
    private String date;
    private double amount;

    public SalesReceiptTransaction(int empId, String date, double amount) {
        this.empId = empId;
        this.date = date;
        this.amount = amount;
    }

    @Override
    public void execute() {
        Employee e = PayrollDatabase.getEmployee(empId);
        if (e == null)
            throw new NoSuchEmployeeException();
        PaymentClassification pc = e.getPaymentClassification();
        if (pc instanceof CommissionedClassification) {
            CommissionedClassification cc = (CommissionedClassification) pc;
            SalesReceipt sr = new SalesReceipt(date, amount);  
            cc.addSalesReceipt(sr);
        } else {
            throw new NotCommissionedClassificationException();
        }
    }
}
