package es.vcarmen.volleydemo.models;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Lolo on 03/01/2017.
 */

public final class RESTEmployeesSingleton {

    private static RESTEmployeesSingleton singleton;
    private RequestQueue requestQueue;
    private static Context context;

    private RESTEmployeesSingleton(Context context){
        RESTEmployeesSingleton.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized RESTEmployeesSingleton getInstance(Context context){
        if (singleton == null)
            singleton = new RESTEmployeesSingleton(context);
        return singleton;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context);
        return requestQueue;
    }

    public void addToRequestQueue (Request req){
        getRequestQueue().add(req);
    }
}
