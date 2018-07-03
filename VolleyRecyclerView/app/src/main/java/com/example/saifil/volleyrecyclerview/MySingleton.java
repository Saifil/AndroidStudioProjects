package com.example.saifil.volleyrecyclerview;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {

    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private MySingleton(Context context) {
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) { //if null: initialize
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext()); //makes this comtext req. queue last forever

        }
        return requestQueue;
    }

    public static synchronized MySingleton getmInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public<T> void addToRequestQueue(Request<T> request) { //add request to queue
        requestQueue.add(request);
    }
}
