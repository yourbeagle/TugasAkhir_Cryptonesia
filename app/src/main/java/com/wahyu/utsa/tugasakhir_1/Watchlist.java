package com.wahyu.utsa.tugasakhir_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Watchlist extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<WatchlistModel> watchlistModelArrayList;
    WatchlistAdapter watchlistAdapter;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);
        recyclerView = findViewById(R.id.rvWatchlist);
        watchlistModelArrayList = new ArrayList<>();
        watchlistAdapter = new WatchlistAdapter(watchlistModelArrayList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(watchlistAdapter);
        this.db = FirebaseFirestore.getInstance();
        getWatchlist();
    }

    public void getWatchlist(){
        final String username = user.getDisplayName();
        db.collection("Watchlist")
                .whereEqualTo("username", username)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e("Firebase Error", error.getLocalizedMessage());
                            return;
                        }

                        for (DocumentChange documentChange : value.getDocumentChanges()){
                            if (documentChange.getType() == DocumentChange.Type.ADDED){
                                watchlistModelArrayList.add(documentChange.getDocument().toObject(WatchlistModel.class));
                            }
                            watchlistAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}