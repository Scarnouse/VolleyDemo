package es.vcarmen.volleydemo.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import es.vcarmen.volleydemo.R;

/**
 * Created by Lolo on 07/01/2017.
 */
public class ProjectsActivity extends AppCompatActivity{

    private Button listProject;
    private Button newProject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        listProject = (Button) findViewById(R.id.listProjects);

        final Intent listProjIntent = new Intent(this, ProjectsRead.class);

        listProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(listProjIntent);
            }
        });
    }
}
