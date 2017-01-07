package es.vcarmen.volleydemo.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import es.vcarmen.volleydemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button departments = (Button) findViewById(R.id.departmentsBtn);

        final Intent departmentsIntent = new Intent(this, DepartmentsActivity.class);

        departments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(departmentsIntent);
            }
        });

        Button employees = (Button) findViewById(R.id.employeesBtn);

        final Intent employeesIntent = new Intent(this, EmployeeActivity.class);

        employees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(employeesIntent);
            }
        });

        Button projects = (Button) findViewById(R.id.projectsBtn);

        final Intent projectIntent = new Intent(this, ProjectsActivity.class);

        projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(projectIntent);
            }
        });
    }
}
