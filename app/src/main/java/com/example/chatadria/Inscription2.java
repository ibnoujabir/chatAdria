package com.example.chatadria;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inscription2 extends AppCompatActivity {
    Intent intent=getIntent();
    EditText eNom, ePrenom, etFonction, etLieu, etTel;
    Button btnRegister, btnCompetences, btnCv, btnPhoto, btnExperience, btnSecteur, btnDisponibilite;
    ImageView imgView;
    TextView Competences, Experience, Secteur, Disponibilite, Cv;

    private Uri filePath , pdfUri;

    private final int PICK_IMAGE_REQUEST = 71;

    String[] listItem, listItemExp, listItemDisp, listItemSecteur;
    boolean[] checkedItem, checkedItemExp, checkedItemSecteur;
    ArrayList<Integer> mUserItem = new ArrayList<>();
    ArrayList<Integer> mUserItemExp = new ArrayList<>();
    ArrayList<Integer> mUserItemDisp = new ArrayList<>();
    ArrayList<Integer> mUserItemSecteur = new ArrayList<>();

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription2);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        eNom = (EditText) findViewById(R.id.etName);
        ePrenom = (EditText) findViewById(R.id.etPrenom);

        etTel = (EditText) findViewById(R.id.etTel);

        btnRegister = (Button) findViewById(R.id.bRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail;
                if(intent!=null){
                    mail = intent.getStringExtra("mail");
                }
                else
                    mail="";

                String nom = eNom.getText().toString();
                String prenom = ePrenom.getText().toString();
                String tel = etTel.getText().toString();


                if (TextUtils.isEmpty(nom)) nom = "";
                if (TextUtils.isEmpty(prenom)) prenom = "";


                user = firebaseAuth.getCurrentUser();
                Map<String, Object> newUser = new HashMap<> ();
;
                newUser.put("Téléphone", tel);

                newUser.put("mail", mail);
                newUser.put("prenom", prenom);
                newUser.put("nom", nom);

                if (user != null) {

                    Log.d("success", user.getUid());
                    db.collection("amis").document(user.getUid())
                            .set(newUser)
                            .addOnSuccessListener(new OnSuccessListener<Void> () {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Inscription2.this, "New user added", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener () {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Error", e.getMessage());
                                }
                            });
                } else {
                    Log.d("Error", "Failed");
                }

                Toast.makeText(getApplicationContext(), "Registration Successful Inscription", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Inscription2.this, MainActivity.class));
                finish();

            }
        });


    }


}
