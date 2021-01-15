package com.bootcamp.recipie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.Item> {

    Context c;
    ArrayList<String> list;

    public NotesAdapter(Context c, ArrayList<String> list) {
        this.c = c;
        this.list = list;
    }

    @NonNull
    @Override
    public Item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.notes_view,parent,false);
        return new Item(v);

    }

    @Override
    public void onBindViewHolder(@NonNull Item holder, int position) {
        String data=list.get(position);
        holder.body.setText(data);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Item extends RecyclerView.ViewHolder{
        TextView body;
        public Item(@NonNull View itemView) {
            super(itemView);
            body= itemView.findViewById(R.id.body_note);
        }
    }
}
