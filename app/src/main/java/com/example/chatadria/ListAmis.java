package com.example.chatadria;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class ListAmis extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("amis");


    private NoteAdapter adapter;
    View v;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.amis_list, container, false);
        Query query = notebookRef.orderBy ("prenom", Query.Direction.ASCENDING);



        FirestoreRecyclerOptions<Ami> options = new FirestoreRecyclerOptions.Builder<Ami> ()
                .setQuery (query, Ami.class)
                .build ();

        adapter = new NoteAdapter (options);

        RecyclerView recyclerView = v.findViewById (R.id.recycler_view);
        // recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager (new LinearLayoutManager (getActivity ()));
        recyclerView.setAdapter (adapter);
        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Ami ami = documentSnapshot.toObject(Ami.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                /*Toast.makeText(getActivity(),
                        "Position: " + position + " ID: " + id, Toast.LENGTH_SHORT).show();

                FragmentTransaction tf= getFragmentManager().beginTransaction();
                tf.replace(R.id.fragment_container,new DetailFragment  ());
                tf.addToBackStack(null);
                tf.commit();*/
            /*   Bundle bundle=new Bundle();
                bundle.putString("id", id);
                //set Fragmentclass Arguments
                ListAmis fragobj=new ListAmis ();
                fragobj.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragobj )
                        .commit();*/
            }
            @Override
            public void OnClick(View view){
                Intent intent= new Intent (getContext (), MessageActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("id", String.valueOf (getId ()));
                startActivity (intent);


            }
        });

        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

