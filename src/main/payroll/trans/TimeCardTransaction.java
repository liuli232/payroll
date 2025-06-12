package main.payroll.trans;

import main.payroll.Employee;
import main.payroll.PayrollDatabase;
import main.payroll.TimeCard;
import main.payroll.Transaction;
import main.payroll.classification.HourlyClassification;
import main.payroll.classification.PaymentClassification;

public class TimeCardTransaction implements Transaction {

    private int empId;
    private String date;
    private double hours;

    public TimeCardTransaction(int empId, String date, double hours) {
        this.empId = empId;
        this.date = date;
        this.hours = hours;
        
    }

    @Override
    public void execute() {
        Employee e = PayrollDatabase.getEmployee(empId);
        PaymentClassification pc = e.getPaymentClassification();
        HourlyClassification hc = (HourlyClassification) pc;
        TimeCard tc = new TimeCard(date, hours);
        hc.addTimeCard(tc);
    }

}
