package es.vcarmen.volleydemo.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
 * Created by Lolo on 04/01/2017.
 */
public class DepartmentModify extends AppCompatActivity{

    private RESTEmployeesSingleton requestQueue;
    EditText editId;
    EditText editName;
    Button send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments_form);

        final String URL_BASE = "http://192.168.1.42:3000/departments/" + getIntent().getStringExtra("id");
        //Log.d("modify", getIntent().getStringExtra("name"));

        editId = (EditText) findViewById(R.id.inputIdDepartment);
        editId.setText(getIntent().getStringExtra("id"));
        editName = (EditText) findViewById(R.id.inputNameDepartment);
        editName.setText(getIntent().getStringExtra("name"));

        send = (Button) findViewById(R.id.inputDeptBtn);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestQueue = RESTEmployeesSingleton.getInstance(getApplicationContext());

                StringRequest putRequest = new StringRequest(
                        Request.Method.PUT,
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
                        params.put("n_dept", editId.getText().toString());
                        params.put("name_dept", editName.getText().toString());

                        return params;
                    }
                };

                requestQueue.addToRequestQueue(putRequest);

                finish();
            }
        });
    }
}
