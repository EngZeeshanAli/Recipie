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
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth myAuth;
    private Button signUp;
    private EditText email, password;
    private TextView signIn;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        myAuth = FirebaseAuth.getInstance();
        signUp = findViewById(R.id.register_signUp);
        signUp.setOnClickListener(this);
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        signIn = findViewById(R.id.go_to_signin);
        signIn.setOnClickListener(this);
    }
    private void saveUserData(User user){
        DatabaseReference db=FirebaseDatabase.getInstance().getReference();
        db.child("users").child(user.getUid()).setValue(user);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_signUp:
                if (email.getText().toString().isEmpty()) {
                    email.setError("IT IS EMPTY");
                }
                if (password.getText().toString().isEmpty() || password.getText().toString().length() < 8) {
                    password.setError("Some Problem");
                } else {
                    progressDialog = new ProgressDialog(Register.this);
                    progressDialog.setMessage("PLEASE WAIT...");
                    progressDialog.setTitle("Login Process");
                    progressDialog.show();
                    String emailS = email.getText().toString();
                    String passwordS = password.getText().toString();
                    myAuth.createUserWithEmailAndPassword(emailS, passwordS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                User user=new User(emailS,passwordS,uid);
                                saveUserData(user);
                                Intent login = new Intent(Register.this, Login.class);
                                startActivity(login);
                                Toast.makeText(Register.this, "Sign Up Successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(Register.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    progressDialog.dismiss();
                }
                break;
            case R.id.go_to_signin:
                Intent login = new Intent(Register.this, Login.class);
                startActivity(login);
                finish();
                break;
        }
    }
}