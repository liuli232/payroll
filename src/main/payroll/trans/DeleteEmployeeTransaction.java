package main.payroll.trans;

import main.payroll.Employee;
import main.payroll.PayrollDatabase;
import main.payroll.Transaction;
import main.payroll.exception.NoSuchEmployeeException;

public class DeleteEmployeeTransaction implements Transaction {

    private int empId;

    public DeleteEmployeeTransaction(int empId) {
        this.empId = empId;
    }

    @Override
    public void execute() {
        Employee e = PayrollDatabase.getEmployee(empId);
        if (e != null) {
            PayrollDatabase.deleteEmployee(empId);
        } else {
            throw new NoSuchEmployeeException();
        }
    }

}
