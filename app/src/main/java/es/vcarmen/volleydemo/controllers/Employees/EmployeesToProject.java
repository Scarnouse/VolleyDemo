package es.vcarmen.volleydemo.controllers.Employees;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.vcarmen.volleydemo.R;
import es.vcarmen.volleydemo.models.Employee.Employee;
import es.vcarmen.volleydemo.models.RESTEmployeesSingleton;

/**
 * Created by Lolo on 14/01/2017.
 */
public class EmployeesToProject extends AppCompatActivity{

    private RESTEmployeesSingleton requestQueue;
    private Spinner spinner;
    private EditText date;
    private EditText task;
    private Button send;

    private List<Employee> listEmployees;
    private List<String> listSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_to_project_form);

        final String URL_BASE = "http://192.168.1.42:3000/workson/";

        spinner = (Spinner) findViewById(R.id.spinnerName);
        date = (EditText) findViewById(R.id.inputStartDateProject);
        task = (EditText) findViewById(R.id.task);
        send = (Button) findViewById(R.id.inputEmpToProjBtn);

        requestQueue = RESTEmployeesSingleton.getInstance(getApplicationContext());

        addItemsToSpinner();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //requestQueue = RESTEmployeesSingleton.getInstance(getApplicationContext());

                StringRequest putRequest = new StringRequest(
                        Request.Method.POST,
                        URL_BASE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Toast.makeText(getApplicationContext(), new JSONObject(response).getString("msg"), Toast.LENGTH_SHORT).show();
                                    //Log.d("Response", object.getString("msg"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", String.valueOf(error));
                            }
                        }
                ) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("n_proj", getIntent().getStringExtra("id"));
                        params.put("n_empl", String.valueOf(getNEmpl()));
                        params.put("start_date_proj", parseDate());
                        params.put("task", task.getText().toString());

                        return params;
                    }
                };

                requestQueue.addToRequestQueue(putRequest);

                finish();
            }
        });

    }

    private void addItemsToSpinner() {

        final String URL_BASE = "http://192.168.1.42:3000/employees";
        listEmployees = new ArrayList<>();
        listSpinner = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_BASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONArray jsonArray = null;

                        try {
                            jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);

                                String[] arrayDate = object.getString("start_date_empl").split("T")[0].split("-");
                                try {
                                    Date date = (Date)new SimpleDateFormat("dd-MM-yyyy").parse(arrayDate[2]+"-"+arrayDate[1]+"-"+arrayDate[0]);
                                    listEmployees.add(new Employee(Integer.parseInt(object.getString("n_empl")), object.getString("name_empl"), object.getString("surname_empl"), date, Integer.parseInt(object.getString("n_dept"))));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for(Employee employee : listEmployees){
                            listSpinner.add(employee.getNameEmployee() + " " + employee.getSurnameEmployee());
                        }

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(dataAdapter);
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

    private int getNEmpl() {
        String[] array = spinner.getSelectedItem().toString().split(" ");
        String name = array[0] + " " + array[1];
        String surname = array[2];

        int employeeId = 0;

        for(Employee employee : listEmployees){
            if(employee.getSurnameEmployee().equals(surname) && employee.getNameEmployee().equals(name))
                employeeId = employee.getIdEmployee();
        }

        return employeeId;
    }

    private String parseDate() {
        String[] dateArray = date.getText().toString().split("-");

        return dateArray[2] + "-" + dateArray[1] + "-" + dateArray[0];
    }

}
