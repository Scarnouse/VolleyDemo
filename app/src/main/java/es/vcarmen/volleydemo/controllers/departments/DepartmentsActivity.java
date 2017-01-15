package es.vcarmen.volleydemo.controllers.departments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import es.vcarmen.volleydemo.R;

/**
 * Created by Lolo on 02/01/2017.
 */
public class DepartmentsActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);

        Button listEmployees = (Button) findViewById(R.id.listDepartments);

        final Intent listDeptIntent = new Intent(this, DepartmentsRead.class);

        listEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(listDeptIntent);
            }
        });

        Button createEmployee = (Button) findViewById(R.id.createDepartment);

        final Intent createDeptIntent = new Intent(this, DepartmentsCreate.class);

        createEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(createDeptIntent);
            }
        });
    }
}
