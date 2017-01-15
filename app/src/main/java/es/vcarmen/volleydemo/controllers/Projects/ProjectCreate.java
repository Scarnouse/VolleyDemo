package es.vcarmen.volleydemo.controllers.Projects;

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
 * Created by Lolo on 08/01/2017.
 */
public class ProjectCreate extends AppCompatActivity{

    private RESTEmployeesSingleton requestQueue;
    private final String URL_BASE = "http://192.168.1.42:3000/projects";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_form);

        Button sendProject = (Button) findViewById(R.id.inputProjBtn);

        sendProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                senProjectAction();
            }
        });
    }

    private void senProjectAction() {

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
                params.put("n_proj", ((EditText) findViewById(R.id.inputIdProject)).getText().toString());
                params.put("name_proj", ((EditText) findViewById(R.id.inputNameProject)).getText().toString());
                params.put("budget_proj", ((EditText) findViewById(R.id.inputBudgetProject)).getText().toString());

                Log.d("params", String.valueOf(params));

                return params;
            }
        };

        requestQueue.addToRequestQueue(postRequest);

        finish();

    }
}
