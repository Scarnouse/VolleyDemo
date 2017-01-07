package es.vcarmen.volleydemo.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import es.vcarmen.volleydemo.R;
import es.vcarmen.volleydemo.models.ProjectAdapter;
import es.vcarmen.volleydemo.models.RESTEmployeesSingleton;

/**
 * Created by Lolo on 07/01/2017.
 */
public class ProjectsRead extends AppCompatActivity{

    private ListView listView;
    private ArrayAdapter adapter;
    private RESTEmployeesSingleton requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_list);

        listView = (ListView) findViewById(R.id.projectsListView);

        adapter = new ProjectAdapter(this);
        listView.setAdapter(adapter);
    }
}
