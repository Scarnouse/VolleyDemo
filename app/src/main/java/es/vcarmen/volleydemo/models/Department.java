package es.vcarmen.volleydemo.models;

/**
 * Created by Lolo on 02/01/2017.
 */
public class Department {

    private int idDepartment;
    private String nameDepartment;

    public Department(){}

    public Department(int idDepartment, String nameDepartment){
        this.idDepartment = idDepartment;
        this.nameDepartment = nameDepartment;
    }

    public int getIdDepartment() {
        return idDepartment;
    }

    public String getNameDepartment() {
        return nameDepartment;
    }

    @Override
    public String toString() {
        return getIdDepartment()+ " - " + getNameDepartment();
    }
}
