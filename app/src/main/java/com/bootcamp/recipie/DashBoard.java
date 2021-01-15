package com.bootcamp.recipie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.DatabaseConfig;

import java.util.ArrayList;

public class DashBoard extends AppCompatActivity {
    ArrayList<String> notes;
    RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Button logout = findViewById(R.id.logout);
        list=findViewById(R.id.notes_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        getnotes();



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent moveToLogin = new Intent(DashBoard.this, Login.class);
                startActivity(moveToLogin);
            }
        });
        Button noted = findViewById(R.id.add);
        noted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToLogin = new Intent(DashBoard.this, AddNote.class);
                startActivity(moveToLogin);
            }
        });


    }

    private void getnotes() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait");
        dialog.show();
        notes = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("notes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notes.clear();
                for (DataSnapshot shot : snapshot.getChildren()) {
                    String note = shot.getValue(String.class);
                    notes.add(note);
                }
                NotesAdapter adapter=new NotesAdapter(DashBoard.this,notes);
                list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashBoard.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        dialog.dismiss();
    }


}