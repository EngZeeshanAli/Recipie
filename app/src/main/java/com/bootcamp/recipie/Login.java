package com.bootcamp.recipie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth myAuth;
    private Button signIn;
    private EditText email, password;
    private TextView signUp;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        myAuth = FirebaseAuth.getInstance();
        signIn = findViewById(R.id.login_signin);
        signIn.setOnClickListener(this);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        signUp = findViewById(R.id.go_to_signUn);
        signUp.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent moveToDashBoard = new Intent(Login.this, DashBoard.class);
            startActivity(moveToDashBoard);
            finish();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_signin:
                if (email.getText().toString().isEmpty()) {
                    email.setError("is epmty");
                }
                if (password.getText().toString().isEmpty()) {
                    password.setError("is epmty");
                } else {
                    progressDialog = new ProgressDialog(Login.this);
                    progressDialog.setMessage("PLEASE WAIT...");
                    progressDialog.setTitle("Login Process");
                    progressDialog.show();
                    String emailS = email.getText().toString();
                    String passwordS = password.getText().toString();
                    myAuth.signInWithEmailAndPassword(emailS, passwordS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    Intent moveToDashBoard = new Intent(Login.this, DashBoard.class);
                                    startActivity(moveToDashBoard);
                                    finish();
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    progressDialog.dismiss();
                }

                break;
            case R.id.go_to_signUn:
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                break;
        }
    }
}