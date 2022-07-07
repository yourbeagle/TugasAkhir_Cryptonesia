package com.wahyu.utsa.tugasakhir_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Tracker extends AppCompatActivity {

    EditText etSearch;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<CryptoModal> cryptoModals;
    CryptoAdapter cryptoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        etSearch = findViewById(R.id.etSearch);
        recyclerView = findViewById(R.id.rvItem);
        progressBar = findViewById(R.id.pbLoading);
        cryptoModals = new ArrayList<>();
        cryptoAdapter = new CryptoAdapter(cryptoModals, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cryptoAdapter);
        getCrypto();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterCrypto(editable.toString());
            }
        });
    }

    private void filterCrypto(String name) {
        ArrayList<CryptoModal> filteredList = new ArrayList<>();
        for (CryptoModal items : cryptoModals) {
            if (items.getName().toLowerCase().contains(name.toLowerCase())) {
                filteredList.add(items);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Crypto found for searched query", Toast.LENGTH_SHORT).show();
        } else {
            cryptoAdapter.filterList(filteredList);
        }
    }

    private void getCrypto() {
        progressBar.setVisibility(View.VISIBLE);
//        String url = "https://api.nomics.com/v1/currencies/ticker?key=faef101cef797ee429c0c56511f33062ad299314";
        String url = "https://api.coinmarketcap.com/data-api/v3/cryptocurrency/listing?start=1&limit=500";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObj = jsonObject.getJSONObject("data");
                    JSONArray cryptoArray = dataObj.getJSONArray("cryptoCurrencyList");
                    for (int i = 0; i < cryptoArray.length(); i++){
                        JSONObject cryptoObj = cryptoArray.getJSONObject(i);
                        String coinId = cryptoObj.getString("id");
                        String name = cryptoObj.getString("name");
                        String symbol = cryptoObj.getString("symbol");
                        JSONArray quotesArray = cryptoObj.getJSONArray("quotes");
                        for (int j = 0; j < quotesArray.length(); j++){
                            JSONObject quoteObj = quotesArray.getJSONObject(j);
                            double price = quoteObj.getDouble("price");
                            double percentChange24h = quoteObj.getDouble("percentChange24h");
                            double percentChange1h = quoteObj.getDouble("percentChange1h");
                            double percentChange7d = quoteObj.getDouble("percentChange7d");
                            cryptoModals.add(new CryptoModal(coinId,name,symbol,price,percentChange24h,percentChange1h,percentChange7d));
                        }
                    }
                    cryptoAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(Tracker.this, "Failed to fetch data",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Tracker.this, "Failed to get data..",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}