package es.vcarmen.volleydemo.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.vcarmen.volleydemo.R;
import es.vcarmen.volleydemo.models.RESTEmployeesSingleton;

/**
 * Created by Lolo on 03/01/2017.
 */

public class DepartmentsCreate extends AppCompatActivity{

    private RESTEmployeesSingleton requestQueue;
    private String URL_BASE = "http://192.168.1.42:3000/departments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments_form);

        Button sendDepartment = (Button) findViewById(R.id.inputDeptBtn);

        sendDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestQueue = RESTEmployeesSingleton.getInstance(getApplicationContext());

                StringRequest postRequest = new StringRequest(
                        Request.Method.POST,
                        URL_BASE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Toast.makeText(getApplicationContext(), new JSONObject(response).getString("msg"), Toast.LENGTH_SHORT).show();
                                    //Log.d("Response", response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("Error.Response", String.valueOf(error));
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("n_dept", ((EditText) findViewById(R.id.inputIdDepartment)).getText().toString());
                        params.put("name_dept", ((EditText) findViewById(R.id.inputNameDepartment)).getText().toString());

                        Log.d("params", String.valueOf(params));

                        return params;
                    }
                };

                requestQueue.addToRequestQueue(postRequest);

                finish();
            }
        });
    }
}
