package es.vcarmen.volleydemo.controllers.Employees;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import es.vcarmen.volleydemo.R;
import es.vcarmen.volleydemo.models.Employee.Employee;
import es.vcarmen.volleydemo.models.Employee.EmployeeAdapter;
import es.vcarmen.volleydemo.models.RESTEmployeesSingleton;

/**
 * Created by Lolo on 05/01/2017.
 */
public class EmployeesRead extends AppCompatActivity{

    private ListView listView;
    private ArrayAdapter adapter;
    private Employee employee = null;
    private RESTEmployeesSingleton requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_list);

        listView = (ListView) findViewById(R.id.employeesListView);

        adapter = new EmployeeAdapter(this);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        employee = (Employee) adapter.getItem(info.position);

        switch (item.getItemId()){
            case R.id.modify:
                modifyEmployee();
                return true;
            case R.id.delete:
                deleteEmployee();
                return true;
        }

        return false;
    }

    private void deleteEmployee() {
        final String URL_BASE = "http://192.168.1.42:3000/employees/" + employee.getIdEmployee();
        //Log.d("item", URL_BASE);

        requestQueue = RESTEmployeesSingleton.getInstance(getApplicationContext());

        StringRequest delete = new StringRequest(
                Request.Method.DELETE,
                URL_BASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(getApplicationContext(), new JSONObject(response).getString("msg"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error.Response", String.valueOf(error));
                    }
                }
        );

        requestQueue.addToRequestQueue(delete);

        finish();
    }

    private void modifyEmployee() {
        Intent intent = new Intent(getApplicationContext(), EmployeeModify.class);

        intent.putExtra("id", String.valueOf(employee.getIdEmployee()));
        intent.putExtra("name", employee.getNameEmployee());
        intent.putExtra("surname", employee.getSurnameEmployee());
        intent.putExtra("date", new SimpleDateFormat("dd-MM-yyyy").format(employee.getStartDateEmployee()));
        intent.putExtra("dept", String.valueOf(employee.getIdDepartment()));

        startActivity(intent);

        finish();
    }

}
