package org.athul.com.jsonparse;


import org.athul.com.jsonparse.R;
import org.athul.com.jsonparse.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class JSONActivity extends Activity {
    ProgressDialog progress;
    private ArrayList<String> arrayList = new ArrayList<String>();
    private ListView lvResult;
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        lvResult = (ListView) findViewById(R.id.lvResult);
        arrayAdapter=new ArrayAdapter<String>(this,R.layout.item_layout,R.id.tvResult,arrayList);
        lvResult.setAdapter(arrayAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();


        final String url = "https://raw.githubusercontent.com/kuldiep/Android_Projects/master/music2.json#";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        progress = new ProgressDialog(this);
        progress.setMessage("Fetching The File....");
        progress.show();
        //  Create json array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray jsonArray) {
                // Successfully download json
                // So parse it and populate the listview
                for (int i = 0; i < jsonArray.length()-1; i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        arrayList.add(jsonObject.getString("songname"));
                        arrayList.add(jsonObject.getString("artistname"));
                        arrayList.add(jsonObject.getString("duration"));
                    } catch (JSONException e) {
                        Toast.makeText(getBaseContext(), "error"+e, Toast.LENGTH_LONG).show();
                    }
                }

                arrayAdapter.notifyDataSetChanged();
                progress.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getBaseContext(), "error", Toast.LENGTH_LONG).show();
                progress.dismiss();
            }
        });
        // add json array request to the request queue
        requestQueue.add(jsonArrayRequest);
    }
}
