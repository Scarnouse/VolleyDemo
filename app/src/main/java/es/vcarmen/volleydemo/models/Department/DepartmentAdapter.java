package es.vcarmen.volleydemo.models.Department;

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
import es.vcarmen.volleydemo.models.RESTEmployeesSingleton;

/**
 * Created by Lolo on 02/01/2017.
 */

public class DepartmentAdapter extends ArrayAdapter {

    private RESTEmployeesSingleton requestQueue;
    private String URL_BASE = "http://192.168.1.42:3000/departments";
    private static final String TAG = "DepartmentAdapter";
    List<Department> items;

    public DepartmentAdapter(Context context){
        super(context, 0);

        requestQueue = RESTEmployeesSingleton.getInstance(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_BASE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                items = parseJson(response);
                /*for(int i = 0; i < items.size(); i++){
                    Log.d("item", items.get(i).toString());
                }*/
                notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Respuesta", "No funciona");
            }
        });
        requestQueue.addToRequestQueue(stringRequest);

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
               R.layout.department,
               parent,
               false
        ) : convertView;

        Department department = items.get(position);

        TextView idDepartment = (TextView) listItemView.findViewById(R.id.idDepartment);
        TextView nameDepartment = (TextView) listItemView.findViewById(R.id.nameDepartment);

        idDepartment.setText(String.valueOf(department.getIdDepartment()));
        nameDepartment.setText((department.getNameDepartment()));

        return listItemView;
    }

    @Nullable
    @Override
    public Department getItem(int position) {
        return items.get(position);
    }

    private List<Department> parseJson(String response) {

        List<Department> departments = new ArrayList<>();
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(response);

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject objeto = jsonArray.getJSONObject(i);

                Department department = new Department(Integer.parseInt(objeto.getString("n_dept")), objeto.getString("name_dept"));

                departments.add(department);
            }

        } catch (JSONException e) {
            Log.e(TAG, "ERROR parseando: " + e.getMessage());
        }

        return departments;
    }
}
