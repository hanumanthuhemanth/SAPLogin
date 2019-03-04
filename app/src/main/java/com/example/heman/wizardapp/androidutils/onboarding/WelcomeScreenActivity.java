package com.example.heman.wizardapp.androidutils.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.heman.wizardapp.R;
import com.example.heman.wizardapp.androidutils.IRootView;
import com.example.heman.wizardapp.androidutils.Utils;
import com.example.heman.wizardapp.androidutils.securestore.SecureStoreConstants;
import com.example.heman.wizardapp.androidutils.securestore.SecureStorePreferencesUtil;
import com.example.heman.wizardapp.dashboard.DashBoardActivity;
import com.example.heman.wizardapp.registration.RegistrationActivity;

public class WelcomeScreenActivity extends AppCompatActivity implements IRootView{
    // final constants
    private  final String TAG=this.getClass().getSimpleName();

    // android components
    ProgressBar progressBar;
    TextView textViewVersionName;

    // variables
    WelcomeScreenPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_screen);
        initializeUI();
    }
    @Override
    public void initializeUI(){
        progressBar = findViewById(R.id.progressBar);
        textViewVersionName = findViewById(R.id.textViewVersionName);
        initializeListeners();
        initializeObjects();
    }

    @Override
    public void initializeObjects(){
        boolean isRegistered = (boolean) SecureStorePreferencesUtil.getInstance(this).getStoreValue(SecureStoreConstants.IS_REGISTERED,false);
        presenter = new WelcomeScreenPresenter(this,this,this);
        if (isRegistered){
            presenter.initializeStore();
        }else{
            startActivity(new Intent(this, RegistrationActivity.class));
        }
    }

    @Override
    public void showMessage(int type,String message,boolean validity) {
        switch (type){
            case 1:
//                Utils.returnTopViewSnackBar(this, progressBar, Snackbar.LENGTH_SHORT, message, validity);
                navigateToDashBoardScreen();
                break;
            case 2:
                Utils.returnTopViewSnackBar(this, progressBar, Snackbar.LENGTH_INDEFINITE, message, validity).setAction("OK", v -> {
                    navigateToDashBoardScreen();
                });
                break;
        }
    }

    @Override
    public void showProgress() {
        runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

    }

    @Override
    public void hideProgress() {
        runOnUiThread(() -> progressBar.setVisibility(View.GONE));
    }

    @Override
    public void initializeListeners(){
        textViewVersionName.setText(String.format(getString(R.string.version_name), Utils.returnVersionNumber()));
    }

    private void navigateToDashBoardScreen(){
        startActivity(new Intent(this, DashBoardActivity.class));
    }

}
