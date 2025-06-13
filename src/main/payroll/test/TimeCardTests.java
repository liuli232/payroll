package main.payroll.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import main.payroll.Transaction;
import main.payroll.Employee;
import main.payroll.PayrollDatabase;
import main.payroll.TimeCard;
import main.payroll.classification.HourlyClassification;
import main.payroll.classification.PaymentClassification;
import main.payroll.exception.NotHourlyClassificationException;
import main.payroll.trans.AddCommissionedEmployeeTransaction;
import main.payroll.trans.AddHourlyEmployeeTransaction;
import main.payroll.trans.AddSalariedEmployeeTransaction;
import main.payroll.trans.TimeCardTransaction;

public class TimeCardTests {

    // 第2到9月卡
    // Truecard copied date hours
    // 测试 1: 分钟后已登记一个到9月卡

    @Test
    void testAddDomTimeCardToken() {
        int empId = 3001; 
        new AddHourlyEmployeeTransaction(empId, "Bill", "home", 12.5).execute();
        
        String date = "2024-05-21";
        double hours = 7.5;
        Transaction t = new TimeCardTransaction(empId, date, hours);
        t.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);
        assertEquals(empId, e.getEmpId());
        PaymentClassification pc = e.getPaymentClassification();
        assertTrue(pc instanceof HourlyClassification);
        
        HourlyClassification hc = (HourlyClassification) pc;
        TimeCard tc = hc.getTimeCardOfDate(date);
        assertNotNull(tc);
        assertEquals(date, tc.getDate()); 
        assertEquals(hours, tc.getHours());
    }

    @Test
    void testAddNonTimeCardsToHourlyEmployee() {

        int empId = 3002;
        new AddHourlyEmployeeTransaction(empId,"Bill","Home",12.5).execute();
    
        String date1 = "2024-05-21";
        double hours1 = 5.5;
        new TimeCardTransaction(empId, date1, hours1).execute();
    
        String date2 = "2024-05-22";
        double hours2 = 10;
        new TimeCardTransaction(empId, date2, hours2).execute();
    
        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);
    
        PaymentClassification pc = e.getPaymentClassification();
        HourlyClassification hc = (HourlyClassification) pc;
    
        TimeCard tc1 = hc.getTimeCardOfDate(date1);
        assertEquals(date1, tc1.getDate());
        assertEquals(hours1, tc1.getHours());
    
        TimeCard tc2 = hc.getTimeCardOfDate(date2);
        assertEquals(date2, tc2.getDate());
        assertEquals(hours2, tc2.getHours());
    }

    // 测试 3：为判断底层登记时间卡，应该跳出异常
    @Test
    void testAddTimeCardToSalariedEmployee() {
        int empId = 3003;
        new AddSalariedEmployeeTransaction(empId, "Bill", "Home", 3000.0).execute();
        assertThrows(NotHourlyClassificationException.class, () -> {
        new TimeCardTransaction(empId, "2024-05-21", 5.5).execute();
    });
}

// 测试 4：为销售经理登记时间卡，应该跳出异常
    @Test
    void testAddTimeCardToCommissionedEmployee() {
        int empId = 3004;
        new AddCommissionedEmployeeTransaction(empId, "Bill", "Home", 2000.0, 0.02).execute();
        assertThrows(NotHourlyClassificationException.class, () -> {
        new TimeCardTransaction(empId, "2024-05-21", 5.5).execute();
        });
    }
}