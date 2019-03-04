package com.example.heman.wizardapp.dashboard;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.heman.wizardapp.R;
import com.example.heman.wizardapp.androidutils.Utils;
import com.example.heman.wizardapp.dashboard.admin.AdminFragment;
import com.example.heman.wizardapp.dashboard.home.HomeFragment;

public class DashBoardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, IDashboardRootView,DashboardFragment.OnFragmentInteractionListener, HomeFragment.OnHomeFragmentInteractionListener, AdminFragment.OnAdminFragmentInteractionListener {
    //final constants

    //android components
    ConstraintLayout constraintLayoutContainer;
    BottomNavigationView bottomNavigationView;
    //variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        initializeUI();
    }

    @Override
    public void initializeUI() {
        constraintLayoutContainer = findViewById(R.id.constraintLayoutContainer);
        bottomNavigationView = findViewById(R.id.navigation);
        initializeListeners();
        initializeObjects();
    }

    @Override
    public void initializeListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public void initializeObjects() {

    }

    @Override
    public void showMessage(String message,int type) {
        Utils.toastAMessage(message,this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayoutContainer,new HomeFragment()).commit();
                return true;
            case R.id.navigation_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayoutContainer,new DashboardFragment()).commit();
                return true;
            case R.id.navigation_notifications:
                getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayoutContainer,new AdminFragment()).commit();
                return true;
        }
        return false;
    }

    @Override
    public void onFragmentInteraction(Object object) {

    }

    @Override
    public void onAdminFragmentInteraction(Uri uri) {

    }

    @Override
    public void onHomeFragmentInteraction(Uri uri) {

    }
}
