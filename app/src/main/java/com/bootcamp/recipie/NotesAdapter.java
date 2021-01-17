package com.bootcamp.recipie;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.Item> {

    Context c;
    ArrayList<String> list;
    ArrayList<String> ids;

    public NotesAdapter(Context c, ArrayList<String> list,ArrayList<String> ids) {
        this.c = c;
        this.list = list;
        this.ids=ids;
    }

    @NonNull
    @Override
    public Item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.notes_view, parent, false);
        return new Item(v);

    }

    @Override
    public void onBindViewHolder(@NonNull Item holder, int position) {
        String data = list.get(position);
        holder.body.setText(data);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                db.child("notes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ids.get(position)).removeValue();
            }
        });

        holder.entitiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c,UpdateNotes.class);
                intent.putExtra("id",ids.get(position));
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Item extends RecyclerView.ViewHolder {
        TextView body;
        ImageView delete;
        LinearLayout entitiy;

        public Item(@NonNull View itemView) {
            super(itemView);
            body = itemView.findViewById(R.id.body_note);
            delete = itemView.findViewById(R.id.delete);
            entitiy=itemView.findViewById(R.id.entity);

        }
    }
}
