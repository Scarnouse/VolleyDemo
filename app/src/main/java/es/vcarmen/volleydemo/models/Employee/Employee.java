package es.vcarmen.volleydemo.models.Employee;

import java.util.Date;

/**
 * Created by Lolo on 04/01/2017.
 */

public class Employee {

    private int idEmployee;
    private String nameEmployee;
    private String surnameEmployee;
    private Date startDateEmployee;
    private int idDepartment;

    public Employee (int idEmployee, String nameEmployee, String surnameEmployee, Date startDateEmployee, int idDepartment){
        this.idDepartment = idDepartment;
        this.idEmployee = idEmployee;
        this.nameEmployee = nameEmployee;
        this.surnameEmployee = surnameEmployee;
        this.startDateEmployee = startDateEmployee;
    }

    public int getIdDepartment() {
        return idDepartment;
    }

    public Date getStartDateEmployee() {
        return startDateEmployee;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public String getSurnameEmployee() {
        return surnameEmployee;
    }

    @Override
    public String toString() {
        return getIdEmployee() + " " + getNameEmployee() + " " + getSurnameEmployee();
    }
}
