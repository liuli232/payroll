package main.payroll.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import main.payroll.Transaction;
import main.payroll.exception.NoSuchEmployeeExcption;
import main.payroll.Employee;
import main.payroll.PayrollDatabase;
import main.payroll.trans.AddHourlyEmployeeTransaction;
import main.payroll.trans.DeleteEmployeeTransaction;

public class DeleteEmployeeTest {

    @Test
    void testDeleteEmployeeExists() {
        int empId = 2001;

        new AddHourlyEmployeeTransaction(empId, null, null, empId).execute();
        assertNotNull(PayrollDatabase.getEmployee(empId));

        Transaction t = new DeleteEmployeeTransaction(empId);
        t.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNull(e);
    }

    @Test
    void testDeleteEmployeeNotExists() {
        int empId = 2002;

        assertNull(PayrollDatabase.getEmployee(empId));

        Transaction t = new DeleteEmployeeTransaction(empId);
        assertThrows(NoSuchEmployeeExcption.class, () -> {
            t.execute();
        }) ;
    }
    
}
