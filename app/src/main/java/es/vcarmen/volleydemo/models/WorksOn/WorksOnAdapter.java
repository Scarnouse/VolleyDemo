package es.vcarmen.volleydemo.models.WorksOn;

import android.content.Context;
import android.widget.ArrayAdapter;

import es.vcarmen.volleydemo.models.RESTEmployeesSingleton;

/**
 * Created by Lolo on 15/01/2017.
 */

public class WorksOnAdapter extends ArrayAdapter{

    private RESTEmployeesSingleton requestQueue;
    private final String URL_BASE = "http://192.168.1.42:3000/projects";

    public WorksOnAdapter(Context context, int resource) {
        super(context, resource);
    }
}
