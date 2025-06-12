package main.payroll.test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import main.payroll.Transaction;
import main.payroll.Employee;
import main.payroll.PayrollDatabase;
import main.payroll.classification.CommissionedClassification;
import main.payroll.classification.HourlyClassification;
import main.payroll.classification.PaymentClassification;
import main.payroll.classification.SalariedClassification;
import main.payroll.method.HoldMethod;
import main.payroll.method.PaymentMethod;
import main.payroll.trans.AddCommissionedEmployeeTransaction;
import main.payroll.trans.AddHourlyEmployeeTransaction;
import main.payroll.trans.AddSalariedEmployeeTransaction;

public class AddEmployeeTest {
    //钟点工
    @Test
    void testAddHourlyEmployee() {
        int empId = 1001;
        String name = "Bill";
        String address = "No 391, Huanghe Rd.";
        double hourlyRate = 8.0;

        Transaction t = new AddHourlyEmployeeTransaction(empId, name, address, hourlyRate);
        t.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);
        assertEquals(empId, e.getEmpId());
        assertEquals(name, e.getName());
        assertEquals(address, e.getAddress());

        PaymentClassification pc = e.getPaymentClassification();
        assertTrue(pc instanceof HourlyClassification);
        HourlyClassification hc = (HourlyClassification) pc;
        assertEquals(hourlyRate, hc.getHourlyRate());

        PaymentMethod pm = e.getPaymentMethod();
        assertTrue(pm instanceof HoldMethod);
    }
    //月薪雇员
    @Test
    void testAddSalariedEmployee() {
        int empId = 1001;
        String name = "Bill";
        String address = "No 391, Huanghe Rd.";
        double salary = 3000;

        Transaction t = new AddSalariedEmployeeTransaction(empId, name, address, salary);
        t.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);
        assertEquals(empId, e.getEmpId());
        assertEquals(name, e.getName());
        assertEquals(address, e.getAddress());

        PaymentClassification pc = e.getPaymentClassification();
        assertTrue(pc instanceof SalariedClassification);
        SalariedClassification hc = (SalariedClassification) pc;
        assertEquals(salary, hc.Salary());

        PaymentMethod pm = e.getPaymentMethod();
        assertTrue(pm instanceof HoldMethod);

    }
    //销售经理
    @Test
    void testAddCommissionedEmployee() {
        int empId = 1001;
        String name = "Bill";
        String address = "No 391, Huanghe Rd.";
        double salary = 3000;
        double commissionRate = 0.02;

        Transaction t = new AddCommissionedEmployeeTransaction(empId, name, address, salary, commissionRate);
        t.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);
        assertEquals(empId, e.getEmpId());
        assertEquals(name, e.getName());
        assertEquals(address, e.getAddress());

        PaymentClassification pc = e.getPaymentClassification();
        assertTrue(pc instanceof CommissionedClassification);
        CommissionedClassification hc = (CommissionedClassification) pc;
        assertEquals(salary, hc.Salary());
        assertEquals(commissionRate, hc.CommissionRate());

        PaymentMethod pm = e.getPaymentMethod();
        assertTrue(pm instanceof HoldMethod);

    }


}
