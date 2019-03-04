package com.example.heman.wizardapp.customers;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.heman.wizardapp.Customer;
import com.example.heman.wizardapp.R;
import com.example.heman.wizardapp.androidutils.IRootView;
import com.example.heman.wizardapp.androidutils.Utils;
import com.example.heman.wizardapp.androidutils.service.offline.OfflineManager;
import com.sap.cloud.mobile.odata.GuidValue;
import com.sap.cloud.mobile.odata.LocalDateTime;

public class CustomerCreateActivity extends AppCompatActivity implements IRootView, View.OnClickListener {
    //final constants

    //android components
    EditText editTextFirstName, editTextLastName, editTextEmail, editTextPhoneNumber, editTextPostalCode, editTextCity;
    Button buttonSave;
    //sap components

    //variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_create);
        initializeUI();
    }

    @Override
    public void initializeUI() {
      /*  Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextPostalCode = findViewById(R.id.editTextPostalCode);
        editTextCity = findViewById(R.id.editTextCity);
        buttonSave = findViewById(R.id.buttonSave);
        initializeListeners();
        initializeObjects();

    }

    @Override
    public void initializeListeners() {
        buttonSave.setOnClickListener(this);
    }

    @Override
    public void initializeObjects() {

    }


    @Override
    public void showMessage(int type, String message, boolean validity) {
        Utils.returnTopViewSnackBar(this,buttonSave, Snackbar.LENGTH_INDEFINITE,message,validity);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSave:
                Customer customer = new Customer();
                customer.setFirstName(editTextFirstName.getText().toString());
                customer.setLastName(editTextLastName.getText().toString());
                customer.setEmailAddress(editTextEmail.getText().toString());
                customer.setPhoneNumber(editTextPhoneNumber.getText().toString());
                customer.setPostalCode(editTextPostalCode.getText().toString());
                customer.setCity(editTextCity.getText().toString());
                customer.setCustomerID(GuidValue.random().toString36());
                customer.setDateOfBirth(LocalDateTime.now());
                OfflineManager.getInstance(this).getMetaServiceContainer().createEntityAsync(customer, () -> {
                    showMessage(1,"customer created successfully",false);
                }, e -> {
                    showMessage(1,"customer failed to create",true);
                });
                break;
        }
    }
}
