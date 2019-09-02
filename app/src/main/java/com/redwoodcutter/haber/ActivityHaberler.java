package com.redwoodcutter.haber;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.redwoodcutter.haber.Model.Haberler;
import com.redwoodcutter.haber.Model.adapters.HaberAdapters;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ActivityHaberler extends AppCompatActivity implements HaberAdapters.HaberlerAdaptersListener {
    private static final String TAG = ActivityHaberler.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Haberler> haberlerList;
    private HaberAdapters mAdapter;
    private SearchView searchView;

    private static final String URL = "https://gist.githubusercontent.com/Redwoodcutter/e4ba3737cc7415a7d5801bc99083c78b/raw/4ded91d8c943a75c69a1a7ff02af7f0ad960e99b/HaberlerSporFinansVerisi.json";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haberler_content);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        haberlerList = new ArrayList<>();
        mAdapter = new HaberAdapters(this, haberlerList,this);

        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        fetchHaber();

    }
    private void fetchHaber(){
        JsonArrayRequest request = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response == null){
                    Toast.makeText(getApplicationContext(), "Bağlantı Sağlanamadı Tekrar Deneyin.", Toast.LENGTH_LONG).show();
                    return;
                }
                List<Haberler> items = new Gson().fromJson(response.toString(), new TypeToken<List<Haberler>>(){
                }.getType());

                haberlerList.clear();
                haberlerList.addAll(items);

                mAdapter.notifyDataSetChanged();
            }
            }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onHaberlerSelected(Haberler contact) {
        Toast.makeText(getApplicationContext(), "Secilen: " + contact.getTarih() + ", " + contact.getIcerik(), Toast.LENGTH_LONG).show();
    }
}
