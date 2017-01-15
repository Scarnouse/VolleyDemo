package es.vcarmen.volleydemo.controllers.Employees;

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
 * Created by Lolo on 07/01/2017.
 */
public class EmployeeModify extends AppCompatActivity{

    private RESTEmployeesSingleton requestQueue;
    private EditText idEmployee;
    private EditText nameEmployee;
    private EditText surnameEmployee;
    private EditText startDateEmployee;
    private EditText deptEmployee;
    private Button send;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_form);

        final String URL_BASE = "http://192.168.1.42:3000/employees/" + getIntent().getStringExtra("id");

        idEmployee = (EditText) findViewById(R.id.inputIdEmployee);
        nameEmployee = (EditText) findViewById(R.id.inputNameEmployee);
        surnameEmployee = (EditText) findViewById(R.id.inputSurnameEmployee);
        startDateEmployee = (EditText) findViewById(R.id.inputStartDateEmployee);
        deptEmployee = (EditText) findViewById(R.id.inputDeptEmployee);

        idEmployee.setText(getIntent().getStringExtra("id"));
        nameEmployee.setText(getIntent().getStringExtra("name"));
        surnameEmployee.setText(getIntent().getStringExtra("surname"));
        startDateEmployee.setText(getIntent().getStringExtra("date"));
        deptEmployee.setText(getIntent().getStringExtra("dept"));

        send = (Button) findViewById(R.id.inputEmpBtn);

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
                        params.put("n_empl", ((EditText) findViewById(R.id.inputIdEmployee)).getText().toString());
                        params.put("name_empl", ((EditText) findViewById(R.id.inputNameEmployee)).getText().toString());
                        params.put("surname_empl", ((EditText) findViewById(R.id.inputSurnameEmployee)).getText().toString());
                        params.put("start_date_empl", parseDate());
                        params.put("n_dept", ((EditText) findViewById(R.id.inputDeptEmployee)).getText().toString());

                        return params;
                    }
                };

                requestQueue.addToRequestQueue(putRequest);

                finish();
            }
        });
    }

    private String parseDate() {
        String[] date = ((EditText) findViewById(R.id.inputStartDateEmployee)).getText().toString().split("-");

        return date[2] + "-" + date[1] + "-" + date[0];
    }
}
