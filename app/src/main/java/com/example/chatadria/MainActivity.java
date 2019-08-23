package com.example.chatadria;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText etMail,etPassword;
    Button bLogin;
    TextView tvRegister;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etMail = (EditText)findViewById(R.id.etMail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        firebaseAuth = FirebaseAuth.getInstance();
        bLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String login = etMail.getText().toString();
                final String password = etPassword.getText().toString();
                if (TextUtils.isEmpty(login)) {
                    Toast.makeText(getApplicationContext(), "Please fill in the required fields (login) ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Please fill in the required fields (password)", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(login,password).addOnCompleteListener(new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Log.v("Tag","signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Log.d("Tag",user.getEmail().toString());
                            Toast.makeText(getApplicationContext(), "Authentification successed", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent (MainActivity.this,Main3Activity.class));
                            finish();
                        }
                        else{
                            Log.v("Tag","signInWithEmail:failure");
                            Toast.makeText(getApplicationContext(), "Authentification failed !", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View w) {
                startActivity(new Intent(MainActivity.this, Inscription.class));
                finish();
            }

        });



    }
}
