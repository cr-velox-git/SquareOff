package com.example.squareoff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Model> modelList;
    private RecyclerView recyclerView;
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.main_recycleView);

        initalizedDataList(); // method to call the inner class from the main class

        //oriantation check and measures
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "potrate", Toast.LENGTH_SHORT).show();
            LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
            manager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(manager);
        }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
        }


        new fletchData().start();
    }

    private void initalizedDataList() {
        modelList = new ArrayList<>();

        adapter = new Adapter(modelList);
        recyclerView.setAdapter(adapter);
    }

    // class to fletch data from the api
    class fletchData extends Thread {

        String data = "";

        @Override
        public void run() {
            super.run();

            mainHandler.post(new Runnable() { // pre requiredment for better ux expriance
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Fetching Data");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });

            //code connect and get data from the api
            try {
                URL url = new URL("https://followchess.com/config.json");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    data = data + line;
                }

                if (!data.isEmpty()) {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray trns = jsonObject.getJSONArray("trns");
                    modelList.clear();
                    for (int i = 0; i < trns.length(); i++) {
                        JSONObject move = trns.getJSONObject(i);

                        String name = move.getString("name");
                        String slug = move.getString("slug");
                        long status = move.getLong("status");

                        try {
                            String img = move.getString("img");
                            modelList.add(new Model(name, slug, img, status));
                        }catch (Exception e){
                            modelList.add(new Model(name, slug, "", status));
                        }

                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            mainHandler.post(new Runnable() {  // post method after fetching data from API
                @Override
                public void run() {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();

                        adapter.notifyDataSetChanged();
                    }
                }
            });

        }
    }
}