package es.vcarmen.volleydemo.controllers;

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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import es.vcarmen.volleydemo.R;
import es.vcarmen.volleydemo.models.Employee;
import es.vcarmen.volleydemo.models.RESTEmployeesSingleton;

/**
 * Created by Lolo on 08/01/2017.
 */
public class EmployeesFromProject extends AppCompatActivity{

    private String[] array;
    private RESTEmployeesSingleton requestQueue;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);

        final String URL_BASE = "http://192.168.1.42:3000/workson/" + getIntent().getStringExtra("id");

        requestQueue = RESTEmployeesSingleton.getInstance(this);

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_BASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arrayJson = new JSONArray(response);

                            array = new String[arrayJson.length()];
                            for (int i = 0; i < arrayJson.length(); i++) {
                                JSONObject object = arrayJson.getJSONObject(i);

                                String[] arrayDate = object.getString("start_date_proj").split("T")[0].split("-");
                                array[i] = object.getString("name_empl") + " " + object.getString("surname_empl") + " " + arrayDate[2] + "-" + arrayDate[1] + "-" + arrayDate[0] + " " + object.getString("task");
                                //Log.d("obj", array[i]);
                            }

                            //Log.d("objeto", array[0].toString());

                            listView = (ListView) findViewById(R.id.employeesDepartmentListView);

                            listView.setAdapter(new ArrayAdapter(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, array));

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
    }
}
