package com.bootcamp.recipie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateNotes extends AppCompatActivity {
    private EditText bodyUpdate;
    private Button saveUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);
        bodyUpdate = findViewById(R.id.body_update);
        saveUpdate = findViewById(R.id.save_update);
        saveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = bodyUpdate.getText().toString();
                DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                db.child("notes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getKey()).setValue(data);
                finish();
                Toast.makeText(UpdateNotes.this, "Updated!", Toast.LENGTH_SHORT).show();
            }
        });

        fetchNotes();
    }

    private String getKey() {
        String key = getIntent().getStringExtra("id");
        return key;
    }

    private void fetchNotes() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("notes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data = snapshot.getValue(String.class);
                bodyUpdate.setText(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}