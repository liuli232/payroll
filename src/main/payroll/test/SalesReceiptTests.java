package main.payroll.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import main.payroll.Employee;
import main.payroll.PayrollDatabase;
import main.payroll.Transaction;
import main.payroll.classification.CommissionedClassification;
import main.payroll.classification.PaymentClassification;
import main.payroll.exception.NotCommissionedClassificationException;
import main.payroll.trans.AddCommissionedEmployeeTransaction;
import main.payroll.trans.AddHourlyEmployeeTransaction;
import main.payroll.trans.AddSalariedEmployeeTransaction;

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

    //多个销售凭条
    @Test
    void testAddTwosalesReceiptsToCommissionedEmployee(){
        int empId = 4090;
        new AddCommissionedEmployeeTransaction(empId, "Bill", "Home", 2000.0, 0.02).execute();

        String date1 = "2024-05-21";
        double amount1 = 1000.0;
        new SalesReceiptTransaction(empId, date1, amount1).execute();

        String date2 = "2024-05-22";
        double amount2 = 2000.0;
        new SalesReceiptTransaction(empId, date2, amount2).execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        assertTrue(pc instanceof CommissionedClassification);
        CommissionedClassification cc = (CommissionedClassification) pc;
        SalesReceipt srl = cc.getSalesReceiptOfDate(date1);
        assertEquals(date1, srl.getDate());
        assertEquals(amount1, srl.getAmount());
        SalesReceipt sr2 = cc.getSalesReceiptOfDate(date2);
        assertEquals(date2, sr2.getDate());
        assertEquals(amount2, sr2.getAmount());
    }
    // 为钟点工登记销售凭条，应该抛出异常
    @Test
    void testAddSalesReceiptToHourlyEmployee() {
        int empId = 4003;
        new AddHourlyEmployeeTransaction(empId, "Bill", "Home", 12.5).execute();
        assertThrows(NotCommissionedClassificationException.class, () -> {
            new SalesReceiptTransaction(empId, "2024-05-21", 1000.0).execute();
        });
    }

    // 为月薪雇员登记销售凭条，应该抛出异常
    @Test
    void testAddSalesReceiptToSalariedEmployee() {
        int empId = 4004;
        new AddSalariedEmployeeTransaction(empId, "Bill", "Home", 3000.0).execute();
        assertThrows(NotCommissionedClassificationException.class, () -> {
            new SalesReceiptTransaction(empId, "2024-05-21", 1000.0).execute();
        });
    }

}
