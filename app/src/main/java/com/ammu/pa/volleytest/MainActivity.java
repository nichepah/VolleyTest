package com.ammu.pa.volleytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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


public class MainActivity extends AppCompatActivity {


    private String TAG = "VolleyTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String addr = "http://127.0.0.1/php/sail/all5_date.php?Production_Date="; //2018-02-27";
        String year = "2018";
        String month = "03";
        String day ="01";
        final String myDate = year+"-"+month+"-"+day;
        final String url = addr+myDate;

        final Integer imageId = R.mipmap.ic_g5;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: " + response.toString());
                try{

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

                    /*
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CustomListAdapter myAdapter = new CustomListAdapter(MainActivity.this, jsonKeys, jsonVals, imageIds);
                            //myAdapter.printVal();
                            ListView listView = findViewById(R.id.listView);
                            listView.setAdapter(myAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                }
                            });
                        }
                    });*/

                    CustomListAdapter myAdapter = new CustomListAdapter(MainActivity.this, jsonKeys, jsonVals, imageIds);
                    //myAdapter.printVal();
                    ListView listView = findViewById(R.id.listView);
                    listView.setAdapter(myAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                            //String item = view.toString();

                            //Toast.makeText(getApplicationContext(), "Clicked: "+position+"_"+id, Toast.LENGTH_LONG).show();
                            Intent intent;
                            intent = new Intent(getApplicationContext(), DataDisplayBSP.class);
                            intent.putExtra("ProdDate", myDate);
                            startActivity(intent);

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
