package com.example.chatadria;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    TextView prenom;
    TextView nom;
    ImageButton send;
    EditText textsend;
    MessageAdapter messageAdapter;
    List<Chat> mchat;
    RecyclerView recyclerView;

    DocumentReference reference;
    DatabaseReference ref;
    Intent  intent;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    protected void onCreate(Bundle savedInstanceState, final String TAG) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_message);
        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);
        getSupportActionBar ().setTitle ("");
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
        toolbar.setNavigationOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                finish ();
            }
        });
        prenom = findViewById (R.id.texttoolbar);
        nom = findViewById (R.id.texttoolbar2);
        send = findViewById (R.id.btnsend);
        textsend = findViewById (R.id.textsend);
        recyclerView = findViewById (R.id.recycler_view2);
        recyclerView.setHasFixedSize (true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (getApplicationContext ());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager (linearLayoutManager);


        intent = getIntent ();
        user = firebaseAuth.getCurrentUser();
        final String id = intent.getStringExtra ("id");
        send.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String msg = textsend.getText ().toString ();
                if (!msg.equals ("")) {
                    sendmessage (user.getUid (), id, msg);
                } else
                    Toast.makeText (MessageActivity.this, "you can't send empty message", Toast.LENGTH_SHORT).show ();



            textsend.setText("");
        }
        });
        reference = FirebaseFirestore.getInstance().collection("amis").document(user.getUid());
        /*reference.addValueEventListener (new ValueEventListener (){
            @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                Ami user = dataSnapshot.getValue (Ami.class);
                prenom.setText (user.getPrenom ());
                nom.setText (user.getNom ());
            }
            @Override
                    public void onCancelled(@NonNull DatabaseError databaseError){
            }

        });*/
        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());
                    readmessage (user.getUid(), id);
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

    }
    public void  sendmessage(String sender,String recevier, String message){
        DatabaseReference reference= FirebaseDatabase.getInstance ().getReference ();
        HashMap<String, Object> hashMap = new HashMap<> ();
        hashMap.put("sender", sender);
        hashMap.put("recevier", recevier);
        hashMap.put ("message",message);
        reference.child("Chats").push ().setValue (hashMap);
    }
    public void readmessage(final String myid,final String id){
        mchat = new ArrayList<> ();
        ref = FirebaseDatabase.getInstance ().getReference ("Chats");
        ref.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear ();
                for (DataSnapshot snapshot : dataSnapshot.getChildren ()){
                    Chat chat = snapshot.getValue (Chat.class);
                    if (chat.getReceiver ().equals(myid) && chat.getSender ().equals (id)|| chat.getReceiver ().equals (id) && chat.getSender ().equals (myid)){
                        mchat.add(chat);
                    }
                    messageAdapter = new MessageAdapter (MessageActivity.this, mchat);
                    recyclerView.setAdapter (messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}

