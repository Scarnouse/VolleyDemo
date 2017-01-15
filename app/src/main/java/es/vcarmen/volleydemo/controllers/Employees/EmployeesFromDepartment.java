package es.vcarmen.volleydemo.controllers.Employees;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.vcarmen.volleydemo.R;
import es.vcarmen.volleydemo.models.Employee.Employee;
import es.vcarmen.volleydemo.models.RESTEmployeesSingleton;

/**
 * Created by Lolo on 04/01/2017.
 */
public class EmployeesFromDepartment extends AppCompatActivity {

    List<Employee> employees = new ArrayList<Employee>();
    private RESTEmployeesSingleton requestQueue;
    ListView listEmployee;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);

        final String URL_BASE = "http://192.168.1.42:3000/departments/employees/" + getIntent().getStringExtra("id");

        requestQueue = RESTEmployeesSingleton.getInstance(this);

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_BASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String[] arrayDate = object.getString("start_date_empl").split("T")[0].split("-");
                                try {
                                    Date date = (Date)new SimpleDateFormat("dd-MM-yyyy").parse(arrayDate[2]+"-"+arrayDate[1]+"-"+arrayDate[0]);
                                    //Log.d("objeto", date.toString());
                                    employees.add(new Employee(Integer.parseInt(object.getString("n_empl")), object.getString("name_empl"), object.getString("surname_empl"), date, Integer.parseInt(object.getString("n_dept"))));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }

                            ArrayAdapter adapter = new ArrayAdapter<Employee>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, android.R.id.text1, employees);

                            listEmployee = (ListView) findViewById(R.id.employeesDepartmentListView);

                            listEmployee.setAdapter(adapter);

                            //Log.d("objeto", String.valueOf(employees.size()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error.Response", error.toString());
                    }
                }
        );

        requestQueue.addToRequestQueue(stringRequest);

        //Log.d("emp", String.valueOf(employees.size()));

        /*ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.employees, employees);

        listEmployee = (ListView) findViewById(R.id.employeesListView);

        listEmployee.setAdapter(adapter);*/
    }

}
