package com.example.chatadria;

import android.support.annotation.NonNull;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public  class NoteAdapter extends FirestoreRecyclerAdapter<Ami, NoteAdapter.NoteHolder> {
    private OnItemClickListener listener;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Ami> options) {
        super (options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Ami ami) {
        holder.textViewprenom.setText(ami.getPrenom ());
        holder.textViewnom.setText(ami.getNom ());




    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,
                parent, false);
        return new NoteHolder(v);

    }
    class NoteHolder extends RecyclerView.ViewHolder{
        TextView textViewprenom;
        TextView textViewnom;


        public NoteHolder(@NonNull View itemView) {
            super (itemView);
            textViewprenom = itemView.findViewById(R.id.idprenom);
            textViewnom = itemView.findViewById(R.id.idnom);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {

        void onItemClick(DocumentSnapshot documentSnapshot, int position);

        void OnClick(View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}


