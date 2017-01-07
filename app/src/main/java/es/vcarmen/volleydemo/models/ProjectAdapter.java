package es.vcarmen.volleydemo.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.vcarmen.volleydemo.R;

/**
 * Created by Lolo on 07/01/2017.
 */
public class ProjectAdapter extends ArrayAdapter{

    private RESTEmployeesSingleton requestQueue;
    private final String URL_BASE = "http://192.168.1.42:3000/projects";
    private List<Project> items;

    public ProjectAdapter(Context context){
        super(context, 0);

        requestQueue = RESTEmployeesSingleton.getInstance(getContext());

        StringRequest getRequest = new StringRequest(
                Request.Method.GET,
                URL_BASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        items = parseJson(response);
                        notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Respuesta", error.getMessage());
                    }
                }
        );

        requestQueue.addToRequestQueue(getRequest);
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View listItemView = convertView;

        listItemView = null == convertView ? layoutInflater.inflate(
                R.layout.project,
                parent,
                false
        ) : convertView;

        Project project = items.get(position);

        TextView idProject = (TextView) listItemView.findViewById(R.id.idProject);
        TextView nameProject = (TextView) listItemView.findViewById(R.id.nameProject);
        TextView budgetProject = (TextView) listItemView.findViewById(R.id.budgetProject);

        idProject.setText(String.valueOf(project.getIdProject()));
        nameProject.setText(project.getNameProject());
        budgetProject.setText(String.valueOf(project.getBudgetProject()));

        return listItemView;
    }

    @Nullable
    @Override
    public Project getItem(int position) {
        return items.get(position);
    }

    private List<Project> parseJson(String response) {
        List<Project> projects = new ArrayList<>();

        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(response);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);

                projects.add(new Project(Integer.parseInt(object.getString("n_proj")), object.getString("name_proj"), Integer.parseInt(object.getString("budget_proj"))));
            }

            Log.d("proj", projects.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return projects;
    }
}
