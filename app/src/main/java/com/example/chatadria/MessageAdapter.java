package com.example.chatadria;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public  class MessageAdapter extends FirestoreRecyclerAdapter<Chat, MessageAdapter.NoteHolder> {


    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChat;
    FirebaseUser fuser;




    private MessageAdapter.OnItemClickListener listener;

    public MessageAdapter(@NonNull FirestoreRecyclerOptions<Chat> options) {
        super (options);
    }

    public MessageAdapter(MessageActivity messageActivity, List<Chat> mchat) {
        super ();
    }


    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Chat chat) {
        chat = mChat.get (position);

        holder.showmesssage.setText(chat.getMessage ());
    }




    @NonNull
    @Override
    public MessageAdapter.NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.chat_item_right,
                    parent, false);
            return new MessageAdapter ().NoteHolder (v);

        }
        else{
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.chat_item_left,
                    parent, false);
            return new MessageAdapter ().NoteHolder (v);
        }
    }
    class NoteHolder extends RecyclerView.ViewHolder{
        TextView showmesssage;
        TextView profiletext;


        public NoteHolder(@NonNull View itemView) {
            super (itemView);
            showmesssage = itemView.findViewById(R.id.show_message);
            profiletext = itemView.findViewById(R.id.profil_prenom);

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

    public void setOnItemClickListener(MessageAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    public int getItemViewType(int position){
        fuser= FirebaseAuth.getInstance ().getCurrentUser ();
        if (mChat.get(position).getSender ().equals (fuser.getUid ())){
            return MSG_TYPE_RIGHT;
        }
        else {
            return MSG_TYPE_LEFT;
        }
    }
}









