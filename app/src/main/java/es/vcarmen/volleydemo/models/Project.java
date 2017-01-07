package es.vcarmen.volleydemo.models;

/**
 * Created by Lolo on 07/01/2017.
 */
public class Project {

    private int idProject;
    private String nameProject;
    private int budgetProject;

    public Project(int idProject, String nameProject, int budgetProject){
        this.idProject = idProject;
        this.nameProject = nameProject;
        this.budgetProject = budgetProject;
    }

    public int getBudgetProject() {
        return budgetProject;
    }

    public int getIdProject() {
        return idProject;
    }

    public String getNameProject() {
        return nameProject;
    }

    @Override
    public String toString() {
        return getIdProject() + " " + getNameProject() + " " + getBudgetProject();
    }
}
