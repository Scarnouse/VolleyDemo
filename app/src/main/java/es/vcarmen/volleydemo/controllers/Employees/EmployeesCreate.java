package es.vcarmen.volleydemo.controllers.Employees;

import android.os.Bundle;
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
public class EmployeesCreate extends AppCompatActivity{

    private RESTEmployeesSingleton requestQueue;
    private final String URL_BASE = "http://192.168.1.42:3000/employees";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_form);

        Button sendEmployee = (Button) findViewById(R.id.inputEmpBtn);

        sendEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmployeeAction();
            }
        });
    }

    private void sendEmployeeAction() {

        requestQueue = RESTEmployeesSingleton.getInstance(getApplicationContext());

        StringRequest postRequest = new StringRequest(
                Request.Method.POST,
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
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("n_empl", ((EditText) findViewById(R.id.inputIdEmployee)).getText().toString());
                params.put("name_empl", ((EditText) findViewById(R.id.inputNameEmployee)).getText().toString());
                params.put("surname_empl", ((EditText) findViewById(R.id.inputSurnameEmployee)).getText().toString());
                params.put("start_date_empl", parseDate());
                params.put("n_dept", ((EditText) findViewById(R.id.inputDeptEmployee)).getText().toString());

                Log.d("params", String.valueOf(params));

                return params;
            }
        };

        requestQueue.addToRequestQueue(postRequest);

        finish();
    }

    private String parseDate() {
        String[] date = ((EditText) findViewById(R.id.inputStartDateEmployee)).getText().toString().split("-");

        return date[2] + "-" + date[1] + "-" + date[0];
    }
}
