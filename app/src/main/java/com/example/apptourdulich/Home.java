package com.example.apptourdulich;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {
    private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new fmHome());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_tour:

                    fragment = new fmTour();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_promo:
                    fragment=new fmPromo();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_home:
                    fragment = new fmHome();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_location:
                    fragment=new fmLocation();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    fragment=new fmProfile();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }


    };
    private void loadFragment(Fragment fragment) {
        // load Fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}