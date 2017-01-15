package es.vcarmen.volleydemo.models.Employee;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.vcarmen.volleydemo.R;
import es.vcarmen.volleydemo.models.RESTEmployeesSingleton;

/**
 * Created by Lolo on 05/01/2017.
 */
public class EmployeeAdapter extends ArrayAdapter {

    private RESTEmployeesSingleton requestQueue;
    private final String URL_BASE = "http://192.168.1.42:3000/employees";
    List<Employee> items;

    public EmployeeAdapter(Context context){
        super(context, 0);

        requestQueue = RESTEmployeesSingleton.getInstance(getContext());

        StringRequest stringRequest = new StringRequest(
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
                R.layout.employees,
                parent,
                false
        ) : convertView;

        Employee employee = items.get(position);

        TextView idEmployee = (TextView) listItemView.findViewById(R.id.idEmployee);
        TextView nameEmployee = (TextView) listItemView.findViewById(R.id.nameEmployee);
        TextView surnameEmployee = (TextView) listItemView.findViewById(R.id.surnameEmployee);
        TextView startDateEmployee = (TextView) listItemView.findViewById(R.id.startDateEmployee);
        TextView deptEmployee = (TextView) listItemView.findViewById(R.id.deptEmployee);

        idEmployee.setText(String.valueOf(employee.getIdEmployee()));
        nameEmployee.setText(employee.getNameEmployee());
        surnameEmployee.setText(employee.getSurnameEmployee());
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-YYYY");
        startDateEmployee.setText(simpleDate.format(employee.getStartDateEmployee()));
        deptEmployee.setText(String.valueOf(employee.getIdDepartment()));

        return listItemView;
    }

    @Nullable
    @Override
    public Employee getItem(int position) {
        return items.get(position);
    }

    private List<Employee> parseJson(String response) {
        List<Employee> employees = new ArrayList<>();
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);

                String[] arrayDate = object.getString("start_date_empl").split("T")[0].split("-");
                try {
                    Date date = (Date)new SimpleDateFormat("dd-MM-yyyy").parse(arrayDate[2]+"-"+arrayDate[1]+"-"+arrayDate[0]);
                    employees.add(new Employee(Integer.parseInt(object.getString("n_empl")), object.getString("name_empl"), object.getString("surname_empl"), date, Integer.parseInt(object.getString("n_dept"))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            //Log.d("empl", employees.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return employees;
    }
}
