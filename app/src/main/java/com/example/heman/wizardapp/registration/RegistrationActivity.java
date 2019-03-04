package com.example.heman.wizardapp.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.heman.wizardapp.R;
import com.example.heman.wizardapp.androidutils.Utils;
import com.example.heman.wizardapp.androidutils.onboarding.WelcomeScreenActivity;
import com.example.heman.wizardapp.androidutils.securestore.SecureStoreConstants;
import com.example.heman.wizardapp.androidutils.securestore.SecureStorePreferencesUtil;
import com.example.heman.wizardapp.androidutils.service.register.RegistrationService;
import com.sap.cloud.mobile.foundation.user.UserInfo;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, RegistrationService.IRegistrationResponse,IRegistrationView {
    // final constants


    // android components
    Button buttonRegister;
    EditText editTextUserName,editTextPassword;
    ProgressBar progressBar;
    TextView textViewVersionName;

    // variables

    private String myTag=this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
    }
    @Override
    public void initializeUI(){
        buttonRegister = findViewById(R.id.buttonRegister);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        progressBar = findViewById(R.id.progressBar);
        textViewVersionName = findViewById(R.id.textViewVersionName);
        initializeListeners();
        initializeObjects();
    }

    @Override
    public void initializeObjects(){

    }

    @Override
    public void showMessage(int type) {

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        runOnUiThread(() -> progressBar.setVisibility(View.GONE));
    }

    @Override
    public void initializeListeners(){
        buttonRegister.setOnClickListener(this);
        textViewVersionName.setText(String.format("----------Version: %s ----------", Utils.returnVersionNumber()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonRegister:
                showProgress();
                RegistrationService.getInstance(this)
                        .setUserName(editTextUserName.getText().toString())
                        .setUserPassword(editTextPassword.getText().toString())
                        .setRegistrationCallback(this)
                        .registerUser();
                break;
        }
    }

    @Override
    public void registrationSuccessResponse(@NonNull Call call, @NonNull Response response) {
        Log.d(myTag,"Registration Success");
        if (response.code()==200){
            SecureStorePreferencesUtil.getInstance(this).setStoreValue(SecureStoreConstants.IS_REGISTERED,true);
            navigateToDashBoardScreen();
        }else{
            hideProgress();
        }
    }

    @Override
    public void registrationFailureResponse(@NonNull Call call, @NonNull IOException e) {
        Log.e(myTag,"Registration failure");
        hideProgress();
    }

    @Override
    public void registrationUsersSuccessResponse(@NonNull UserInfo userInfo) {
        Log.d(myTag,"UserInfo Success");
        String [] userRollList = userInfo.getRoles();
        for (String anUserRollList : userRollList) {
            Log.d(myTag, "Role Name " + anUserRollList);
        }
        Utils.toastAMessage(userInfo.getId()+userInfo.getUserName(),this);
        hideProgress();
    }

    @Override
    public void registrationUserFailureResponse(@NonNull Throwable throwable) {
        Log.e(myTag,"User Info failed");
        hideProgress();
    }
    private void navigateToDashBoardScreen(){
        startActivity(new Intent(this, WelcomeScreenActivity.class));
    }
}
