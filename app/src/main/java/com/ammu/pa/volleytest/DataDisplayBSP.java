package com.ammu.pa.volleytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.ammu.pa.volleytest.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by aneesh pa on 27/3/18.
 */

public class DataDisplayBSP extends AppCompatActivity {
    public static String myDate;
    private String TAG = "from Volley";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display_bsp);

        Intent intent = getIntent();
        myDate = intent.getStringExtra("ProdDate");
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        final String url = "http://127.0.0.1/php/sail/bsp_date.php?Production_Date="+myDate;
        final Integer imageId = R.mipmap.ic_bsp;

        //Log.d(TAG, "URL: " + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, "onResponse: " + response.toString());
                        try{
                            //String HM = response.getString("HM");
                            //editText.setText(HM);

                            // Actual stuff starts here
                            final String[] jsonKeys = new String [response.length()];
                            final String[] jsonVals = new String [response.length()];
                            final Integer[] imageIds = new Integer [response.length()];

                            Iterator<String> keysIter = response.keys();
                            int i = 0;
                            String key;
                            String val;

                            while( keysIter.hasNext()) {
                                key = keysIter.next();
                                val = response.getString(key);
                                jsonKeys[i] = key;
                                jsonVals[i] = val;
                                imageIds[i] = imageId;
                                Log.e("onLoadIntoListView", jsonKeys[i] +" :: " + jsonVals[i] + "::" + imageIds[i]);
                                i++;
                            }
                            //ArrayAdapter <String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, jsonKeys);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CustomListAdapter myAdapter = new CustomListAdapter(DataDisplayBSP.this, jsonKeys, jsonVals, imageIds);
                                    //myAdapter.printVal();
                                    ListView listView = findViewById(R.id.listViewDateBSP);
                                    listView.setAdapter(myAdapter);
                                }
                            });

                        } catch ( JSONException e ) {
                            Log.e(TAG, e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
            }
        }  );

        requestQueue.add(jsonObjectRequest);

    }

}