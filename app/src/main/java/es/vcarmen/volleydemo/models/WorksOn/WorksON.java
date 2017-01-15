package es.vcarmen.volleydemo.models.WorksOn;

import java.util.Date;

/**
 * Created by Lolo on 15/01/2017.
 */

public class WorksOn {

    private int n_proj;
    private int n_empl;
    private Date start_date_proj;
    private String task;

    public WorksOn (int n_proj, int n_empl, Date start_date_proj, String task){
        this.n_empl = n_empl;
        this.n_proj = n_proj;
        this.start_date_proj = start_date_proj;
        this.task = task;
    }

    public Date getStart_date_proj() {
        return start_date_proj;
    }

    public int getN_empl() {
        return n_empl;
    }

    public int getN_proj() {
        return n_proj;
    }

    public String getTask() {
        return task;
    }

    @Override
    public String toString() {
        return getN_empl() + " " + getN_empl();
    }
}
