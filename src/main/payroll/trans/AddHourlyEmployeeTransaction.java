package main.payroll.trans;

import main.payroll.classification.HourlyClassification;
import main.payroll.classification.PaymentClassification;

public class AddHourlyEmployeeTransaction extends AddEmployeeTransaction {

    
    private double hourlyRate;

    public AddHourlyEmployeeTransaction(int empId, String name, String address, double hourlyRate) {
        super(empId,name,address);
        this.hourlyRate = hourlyRate;
    }

    @Override
    protected PaymentClassification getPaymentClassification() {
        return new HourlyClassification(hourlyRate);
    }

}
