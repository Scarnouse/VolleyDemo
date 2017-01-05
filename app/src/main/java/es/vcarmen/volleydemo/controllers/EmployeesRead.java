package es.vcarmen.volleydemo.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import es.vcarmen.volleydemo.R;
import es.vcarmen.volleydemo.models.EmployeeAdapter;

/**
 * Created by Lolo on 05/01/2017.
 */
public class EmployeesRead extends AppCompatActivity{

    ListView listView;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_list);

        listView = (ListView) findViewById(R.id.employeesListView);

        adapter = new EmployeeAdapter(this);
        listView.setAdapter(adapter);

    }
}
