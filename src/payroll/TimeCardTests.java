package payroll;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
}