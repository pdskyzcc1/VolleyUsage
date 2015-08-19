package com.yao.volleyusage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yao.volleyusage.request.JokeRequest;
import com.yao.volleyusage.response.JokeResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private ListView lv;
    private Context thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisContext = this;
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,String> item = (Map<String, String>) parent.getAdapter().getItem(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(thisContext);
                builder.setTitle(item.get("title"));
                builder.setMessage(item.get("content"));
                builder.show();
            }
        });
        getDataFromServer();
    }


    private void getDataFromServer() {
        JokeRequest request = new JokeRequest(new Response.Listener<JokeResponse>() {
            @Override
            public void onResponse(JokeResponse response) {
                Log.i(TAG, "result:" + response);

                if (response != null && response.jokes != null) {

                    List<Map<String, String>> list = new ArrayList<>();
                    Map<String, String> item;
                    for (JokeResponse.Joke joke : response.jokes) {
                        item = new HashMap<>();
                        item.put("title", joke.title);
                        item.put("content", joke.content);
                        list.add(item);
                    }
                    SimpleAdapter adapter = new SimpleAdapter(thisContext, list, R.layout.item, new String[]{"title", "content"}, new int[]{R.id.tv_title, R.id.tv_content});
                    lv.setAdapter(adapter);

                } else {
                    Toast.makeText(thisContext, "获取笑话失败", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "error:" + error);

            }
        });
        request.addToRequestQueue();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
