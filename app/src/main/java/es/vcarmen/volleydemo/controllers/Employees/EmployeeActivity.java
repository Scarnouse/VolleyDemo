package es.vcarmen.volleydemo.controllers.Employees;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import es.vcarmen.volleydemo.R;

/**
 * Created by Lolo on 05/01/2017.
 */
public class EmployeeActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        Button listEmployees = (Button) findViewById(R.id.listEmployees);

        final Intent listEmpIntent = new Intent(this, EmployeesRead.class);

        listEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(listEmpIntent);
            }
        });

        Button createEmployee = (Button) findViewById(R.id.createEmployee);

        final Intent createDeptIntent = new Intent(this, EmployeesCreate.class);

        createEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(createDeptIntent);
            }
        });


    }
}
