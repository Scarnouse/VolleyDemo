package es.vcarmen.volleydemo.controllers.Projects;

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

import es.vcarmen.volleydemo.R;
import es.vcarmen.volleydemo.controllers.Employees.EmployeesFromProject;
import es.vcarmen.volleydemo.controllers.Employees.EmployeesToProject;
import es.vcarmen.volleydemo.models.Project.Project;
import es.vcarmen.volleydemo.models.Project.ProjectAdapter;
import es.vcarmen.volleydemo.models.RESTEmployeesSingleton;

/**
 * Created by Lolo on 07/01/2017.
 */
public class ProjectsRead extends AppCompatActivity{

    private ListView listView;
    private ArrayAdapter adapter;
    private RESTEmployeesSingleton requestQueue;
    private Project project = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_list);

        listView = (ListView) findViewById(R.id.projectsListView);

        adapter = new ProjectAdapter(this);
        listView.setAdapter(adapter);

        final Intent employeesFromProj = new Intent(this, EmployeesFromProject.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                employeesFromProj.putExtra("id", String.valueOf(((Project) adapter.getItem(i)).getIdProject()));
                startActivity(employeesFromProj);
            }
        });

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.menu_project, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        project = (Project) adapter.getItem(info.position);

        switch (item.getItemId()){
            case R.id.modify:
                modifyProject();
                return true;
            case R.id.delete:
                deleteProject();
                return true;
            case R.id.addEmployeeToProject:
                addEmployeeToProject();
                return true;
        }

        return false;
    }

    private void addEmployeeToProject() {
        Intent intent = new Intent(getApplicationContext(), EmployeesToProject.class);

        intent.putExtra("id", String.valueOf(project.getIdProject()));

        startActivity(intent);

        finish();
    }

    private void modifyProject() {
        Intent intent = new Intent(getApplicationContext(), ProjectModify.class);

        intent.putExtra("id", String.valueOf(project.getIdProject()));
        intent.putExtra("name", project.getNameProject());
        intent.putExtra("budget", String.valueOf(project.getBudgetProject()));

        startActivity(intent);

        finish();
    }

    private void deleteProject(){
        final String URL_BASE = "http://192.168.1.42:3000/projects/" + project.getIdProject();
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
}
