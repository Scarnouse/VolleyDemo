package es.vcarmen.volleydemo.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

import es.vcarmen.volleydemo.R;
import es.vcarmen.volleydemo.controllers.EmployeesRead;

/**
 * Created by Lolo on 05/01/2017.
 */
public class EmployeeAdapter extends ArrayAdapter {

    private RESTEmployeesSingleton requestQueue;
    private final String URL_BASE = "http://192.168.1.42:3000/employees";
    List<Employee> items;

    public EmployeeAdapter(Context context){
        super(context, 0);

        requestQueue = RESTEmployeesSingleton.getInstance(getContext());

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_BASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        items = parseJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Respuesta", error.getMessage());
                    }
                }
        );
        requestQueue.addToRequestQueue(stringRequest);
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View listItemView = convertView;

        listItemView = null == convertView ? layoutInflater.inflate(
                R.layout.employees,
                parent,
                false
        ) : convertView;

        Employee employee = items.get(position);

        TextView idEmployee = (TextView) listItemView.findViewById(R.id.idEmployee);
        TextView nameEmployee = (TextView) listItemView.findViewById(R.id.nameEmployee);
        TextView surnameEmployee = (TextView) listItemView.findViewById(R.id.surnameEmployee);
        TextView startDateEmployee = (TextView) listItemView.findViewById(R.id.startDateEmployee);
        TextView deptEmployee = (TextView) listItemView.findViewById(R.id.deptEmployee);

        idEmployee.setText(String.valueOf(employee.getIdEmployee()));
        nameEmployee.setText(employee.getNameEmployee());
        surnameEmployee.setText(employee.getSurnameEmployee());
        startDateEmployee.setText((CharSequence) employee.getStartDateEmployee());
        deptEmployee.setText(String.valueOf(employee.getIdDepartment()));

        return listItemView;
    }

    private List<Employee> parseJson(String response) {
        List<Employee> employees = new ArrayList<>();
        //TO DO
        return employees;
    }
}
