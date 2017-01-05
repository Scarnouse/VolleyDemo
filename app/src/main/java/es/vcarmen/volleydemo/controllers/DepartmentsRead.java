package es.vcarmen.volleydemo.controllers;

import android.content.Intent;
import android.os.Bundle;
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

import es.vcarmen.volleydemo.R;
import es.vcarmen.volleydemo.models.Department;
import es.vcarmen.volleydemo.models.DepartmentAdapter;
import es.vcarmen.volleydemo.models.RESTEmployeesSingleton;

/**
 * Created by Lolo on 02/01/2017.
 */
public class DepartmentsRead extends AppCompatActivity {

    ListView listView;
    ArrayAdapter adapter;
    RESTEmployeesSingleton requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments_list);

        listView = (ListView) findViewById(R.id.departmentsListView);

        adapter = new DepartmentAdapter(this);
        listView.setAdapter(adapter);

        final Intent employeesFromDept = new Intent(this, EmployeesFromDepartment.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.d("department", ((Department) adapter.getItem(i)).getNameDepartment());
                employeesFromDept.putExtra("id", String.valueOf(((Department) adapter.getItem(i)).getIdDepartment()));
                startActivity(employeesFromDept);
            }
        });

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.department_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Department department = (Department) adapter.getItem(info.position);

        switch (item.getItemId()) {
            case R.id.departmentModify:

                //Log.d("item", department.getIdDepartment());
                Intent intent = new Intent(getApplicationContext(), DepartmentModify.class);
                intent.putExtra("id", String.valueOf(department.getIdDepartment()));
                intent.putExtra("name", department.getNameDepartment());
                startActivity(intent);
                finish();
                return true;
            case R.id.departmentDelete:
                final String URL_BASE = "http://192.168.1.42:3000/departments/" + department.getIdDepartment();
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

                return true;
        }

        return false;
    }

}
