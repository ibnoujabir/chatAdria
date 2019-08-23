package com.example.chatadria;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main3);


        BottomNavigationView navView = findViewById (R.id.bottom_navigation);
        navView.setOnNavigationItemSelectedListener (mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager ().beginTransaction ().replace (R.id.fragment_container,
                    new ListAmis ()).commit ();
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener () {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId ()) {
                case R.id.navigation_chat:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListAmis ()).commit();
                    return true;
                case R.id.navigation_amis:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListAmis ()).commit();
                    return true;

            }
            return false;
        }

    };
}
