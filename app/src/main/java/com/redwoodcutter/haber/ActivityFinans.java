package com.redwoodcutter.haber;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.redwoodcutter.haber.Model.Finans;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ActivityFinans extends AppCompatActivity {
    ListView listViewFinans;
    ProgressDialog dialog;
    String urlFinans="https://gist.githubusercontent.com/Redwoodcutter/ad374d3877d07bbdd3c2f2e91ec356db/raw/eb5ce81210bc99175029e1263bde69c956b2bfa6/FinansVeri.json";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_finans);

        listViewFinans = (ListView) findViewById(R.id.LwFinans);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Veriler Alınıyor");
        dialog.setCancelable(false);
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, urlFinans, new Response.Listener<String>() {
            @Override
            public void onResponse(String sonuc) {
                ArrayList<Finans> finansList = ParseEt(sonuc);
                ArrayAdapter<Finans> adapter = new ArrayAdapter<>(ActivityFinans.this, android.R.layout.simple_list_item_1, finansList);
                listViewFinans.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Veriler alırken hata oluştu", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        AppController.getInstance().addToRequestQueue(request, "Finans");
    }

    ArrayList<Finans> ParseEt(String okunanJson) {
        ArrayList<Finans> finansList = new ArrayList<>();
        try {
            JSONArray arrayFinans = new JSONArray(okunanJson);
            for (int i = 0; i < arrayFinans.length(); ++i) {
                    finansList.add(new Finans(arrayFinans.getJSONObject(i).get("isim").toString(), arrayFinans.getJSONObject(i).get("tarih").toString(),
                            arrayFinans.getJSONObject(i).get("url").toString(), arrayFinans.getJSONObject(i).get("kategori").toString(),
                            arrayFinans.getJSONObject(i).get("tanim").toString(),arrayFinans.getJSONObject(i).get("icerik").toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();
        return finansList;
    }






}
