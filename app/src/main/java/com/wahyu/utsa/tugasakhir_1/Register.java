package com.wahyu.utsa.tugasakhir_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends AppCompatActivity {

    EditText etFullname, etEmail, etPassword, etConfirmPass;
    Button btnDaftar;
    FirebaseAuth mAuth;
    String fullname, email, password, currpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etFullname = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPass = findViewById(R.id.etConfirmPassword);
        btnDaftar = findViewById(R.id.btn_r_1);
        mAuth = FirebaseAuth.getInstance();
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullname = etFullname.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                currpass = etConfirmPass.getText().toString();
                if (fullname.equals("") && email.equals("") && password.equals("") && currpass.equals("")){
                    Toast.makeText(getApplicationContext(), "Semua Fields harus terisi", Toast.LENGTH_SHORT).show();
                }else {
                    register();
                }
            }
        });
    }

    public void register(){
        fullname = etFullname.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        currpass = etConfirmPass.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, currpass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(fullname).build();
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.updateProfile(profileChangeRequest);
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Berhasil Register", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Gagal Register", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signin(View view){
        startActivity(new Intent(getApplicationContext(), Login.class));
    }

    public void backToRegister(View view){
        startActivity(new Intent(getApplicationContext(), afterSplash.class));
        finish();
    }
}