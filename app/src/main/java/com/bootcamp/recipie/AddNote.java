package com.bootcamp.recipie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNote extends AppCompatActivity implements View.OnClickListener {
    private EditText body;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        body = findViewById(R.id.body);
        save = findViewById(R.id.save);
        save.setOnClickListener(this);
    }

    private void saveDataToDatabase() {
        ProgressDialog dialog = new ProgressDialog(AddNote.this);
        dialog.setMessage("saving Data");
        dialog.show();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("notes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(body.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    finish();
                    Toast.makeText(AddNote.this, "saved!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                if (!body.getText().toString().isEmpty()) {
                    saveDataToDatabase();
                } else {
                    Toast.makeText(this, "Text body is empty!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}