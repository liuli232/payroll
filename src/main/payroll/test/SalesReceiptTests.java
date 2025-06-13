package main.payroll.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import main.payroll.Employee;
import main.payroll.PayrollDatabase;
import main.payroll.Transaction;
import main.payroll.classification.CommissionedClassification;
import main.payroll.classification.PaymentClassification;
import main.payroll.trans.AddCommissionedEmployeeTransaction;

public class SalesReceiptTests {

// 销售凭条
// SalesReceipt empid date amount
    @Test
    void testAddOneSalesReceiptToCommissionedEmployee() {
        int empId = 4001;
        new AddCommissionedEmployeeTransaction(empId, "Bill", "Home", 2000.0, 0.02).execute();

        String date = "2024-05-21";
        double amount = 1000.0;
        Transaction t = new SalesReceiptTransaction(empId, date, amount);
        t.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        assertTrue(pc instanceof CommissionedClassification);
        CommissionedClassification cc = (CommissionedClassification) pc;
        SalesReceipt sr = cc.getSalesReceiptOfDate(date);
        assertEquals(date, sr.getDate());
        assertEquals(amount, sr.getAmount());
    }

}
