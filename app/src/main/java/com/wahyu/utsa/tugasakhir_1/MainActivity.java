package com.wahyu.utsa.tugasakhir_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView txtHello, txtLogout;
    CardView cardViewTracker, cardViewWatchlist;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtHello = findViewById(R.id.txtWelcome);
        txtLogout = findViewById(R.id.tvLogout);
        cardViewTracker = findViewById(R.id.cardViewTracker);
        cardViewWatchlist = findViewById(R.id.cardViewWatchlist);
        cardViewTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Tracker.class));

            }
        });
        cardViewWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Watchlist.class));

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user == null){
            finish();
            startActivity(new Intent(getApplicationContext(), Login.class));
        }else {
            txtHello.setText("Welcome "+user.getDisplayName());
        }
    }

    public void Logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
    }
}