package com.wahyu.utsa.tugasakhir_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class detailCrypto extends AppCompatActivity {

    TextView tvNameDetail, tvPriceDetail, tvPercentDetail;
    ImageView imageCryptoDetail, tickerCrypto;
    WebView webViewCrypto;
    Button btn1min, btn30min, btn1hour, btn4hour, btn1day;
    FloatingActionButton fab;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    RecyclerView recyclerView;
    ArrayList<KomenModel> komenModelArrayList;
    CheckBox checkWatchlist, checkWatchList2;
    KomenAdapter komenAdapter;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_crypto);
        tvNameDetail = findViewById(R.id.tvNameDetail);
        tvPercentDetail = findViewById(R.id.tvPercentDetail);
        tvPriceDetail = findViewById(R.id.tvPriceDetail);
        imageCryptoDetail = findViewById(R.id.imgCryptoDetail);
        tickerCrypto = findViewById(R.id.tickerCrypto);
        checkWatchlist = findViewById(R.id.checkWatchlist);
        checkWatchList2 = findViewById(R.id.checkWatchlist1);
        fab = findViewById(R.id.fab);
        db = FirebaseFirestore.getInstance();
        btn1min = findViewById(R.id.btn1min);
        btn30min = findViewById(R.id.btn30min);
        btn1hour = findViewById(R.id.btn1h);
        btn4hour = findViewById(R.id.btn4h);
        btn1day = findViewById(R.id.btn1day);
        webViewCrypto = findViewById(R.id.webViewChart);
        komenModelArrayList = new ArrayList<>();
        komenAdapter = new KomenAdapter(komenModelArrayList, this);
        recyclerView = findViewById(R.id.rvKomen);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(komenAdapter);
        final String username = user.getDisplayName();
        getKomentar();
        String coinId = getIntent().getExtras().getString("id");
        double percent = getIntent().getExtras().getDouble("percent");
        String symbol = getIntent().getExtras().getString("symbol");
        String cName = getIntent().getExtras().getString("name");
        String price = String.format("%.02f", getIntent().getExtras().getDouble("price"));

        Glide.with(imageCryptoDetail.getContext()).load("https://s2.coinmarketcap.com/static/img/coins/64x64/"+ coinId + ".png").into(imageCryptoDetail);
        tvNameDetail.setText(cName);
        tvPriceDetail.setText(price);
        if (percent > 0){
            tvPercentDetail.setText(String.format("%.02f", percent)+"%");
            tickerCrypto.setColorFilter(Color.parseColor("#65fa5a"));
            tvPercentDetail.setTextColor(Color.parseColor("#65fa5a"));
        }else{
            tvPercentDetail.setText(String.format("%.02f", percent)+"%");
            tickerCrypto.setColorFilter(Color.parseColor("#bd2f2f"));
            tvPercentDetail.setTextColor(Color.parseColor("#bd2f2f"));
        }

        checkWatchlist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    WatchlistModel watchlistModel = new WatchlistModel(cName, coinId, username);
                    db.collection("Watchlist")
                            .add(watchlistModel)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(detailCrypto.this, "Berhasil Menambahkan ke Watchlist", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(detailCrypto.this, "Gagal Menambahkan ke Watchlist", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(!b){
                    db.collection("Watchlist")
                            .whereEqualTo("cryptoName", cName)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && !task.getResult().isEmpty()){
                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                String docId = documentSnapshot.getId();
                                db.collection("Watchlist")
                                        .document(docId)
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(detailCrypto.this, "Success Remove this from Watchlist", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(detailCrypto.this, "Failure to Remove this from Watchlist", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else {
                                Toast.makeText(detailCrypto.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        btn1min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadChart("1");
            }
        });
        btn30min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadChart("15");
            }
        });
        btn1hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadChart("1H");
            }
        });
        btn4hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadChart("4H");
            }
        });
        btn1day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadChart("D");
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });

    webViewCrypto.loadUrl("https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol="+symbol+"USD&interval=D&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT");
    webViewCrypto.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    webViewCrypto.getSettings().setJavaScriptEnabled(true);
    }

    public void loadChart(String interval){
        String symbol = getIntent().getExtras().getString("symbol");
        webViewCrypto.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webViewCrypto.getSettings().setJavaScriptEnabled(true);
        webViewCrypto.loadUrl("https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol="+ symbol.toString()
                + "USD&interval=" + interval + "&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT");
    }

    public void backToList(View view){
        startActivity(new Intent(getApplicationContext(), Tracker.class));
        finish();
    }

    public void showCustomDialog(){
        final Dialog dialog = new Dialog(detailCrypto.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        final String name = getIntent().getExtras().getString("name");
        final String username = user.getDisplayName();
        dialog.setContentView(R.layout.custom_dialog);

        final EditText etKomentar = dialog.findViewById(R.id.etKomentar);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmitKomen);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KomenModel komenModel = new KomenModel(etKomentar.getText().toString(), name, username);
                db.collection("Komentar")
                        .add(komenModel)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(detailCrypto.this,"Berhasil Menambahkan Komentar", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(detailCrypto.this,"Gagal Menambahkan Komentar", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void getKomentar(){
        String cryptoName = getIntent().getExtras().getString("name");
        db.collection("Komentar")
                .whereEqualTo("cryptoName", cryptoName)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e("Firestore Error", error.getLocalizedMessage());
                            return;
                        }

                        for (DocumentChange documentChange : value.getDocumentChanges()){
                            if (documentChange.getType() == DocumentChange.Type.ADDED){
                                komenModelArrayList.add(documentChange.getDocument().toObject(KomenModel.class));
                            }
                            komenAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        final String username = user.getDisplayName();
        String cName = getIntent().getExtras().getString("name");
        db.collection("Watchlist")
                .whereEqualTo("cryptoName", cName)
                .whereEqualTo("username", username)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().isEmpty()){
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String docId = documentSnapshot.getId();
                    if (documentSnapshot.exists()){
                        checkWatchlist.setVisibility(View.GONE);
                        checkWatchList2.setVisibility(View.VISIBLE);
                        checkWatchList2.setChecked(true);
                        checkWatchList2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b){
                                    checkWatchlist.setChecked(true);
                                }else if(!b){
                                    db.collection("Watchlist")
                                            .document(docId)
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(detailCrypto.this,"Successfully Remove from Watchlist", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(detailCrypto.this,"Failure to Remove from Watchlist", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}