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

public class ActivitySpor extends AppCompatActivity {
    ListView listViewSpor;
    ProgressDialog dialog;
    String urlFinans="https://gist.githubusercontent.com/Redwoodcutter/9c11f07e6e3210af41f4ab1e361b47e1/raw/aa66cdf40d309d8944c5f694e1bcc5acb589433b/SporR.json";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_finans);

        listViewSpor = (ListView) findViewById(R.id.LwFinans);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Veriler Alınıyor");
        dialog.setCancelable(false);
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, urlFinans, new Response.Listener<String>() {
            @Override
            public void onResponse(String sonuc) {
                ArrayList<Finans> finansList = ParseEt(sonuc); // parse metodu çağrıldı
                ArrayAdapter<Finans> adapter = new ArrayAdapter<>(ActivitySpor.this, android.R.layout.simple_list_item_1, finansList);
                listViewSpor.setAdapter(adapter);
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
