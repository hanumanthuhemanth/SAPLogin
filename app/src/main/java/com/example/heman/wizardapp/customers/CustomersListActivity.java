package com.example.heman.wizardapp.customers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.example.heman.wizardapp.Customer;
import com.example.heman.wizardapp.R;
import com.example.heman.wizardapp.androidutils.AdapterInterface;
import com.example.heman.wizardapp.androidutils.IRootView;
import com.example.heman.wizardapp.androidutils.SimpleRecyclerViewAdapter;
import com.example.heman.wizardapp.dashboard.CustomerViewHolder;

import java.util.List;

public class CustomersListActivity extends AppCompatActivity implements IRootView,ICustomersView,SwipeRefreshLayout.OnRefreshListener, AdapterInterface, View.OnClickListener {
    //final constants

    //android components
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefresh;
    FloatingActionButton fab;
    SimpleRecyclerViewAdapter<?> recyclerViewAdapter;
    //sap components

    //variables
    CustomersListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_list);
        initializeUI();
    }

    @Override
    public void initializeUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        initializeListeners();
        initializeObjects();
        initializeRecyclerView();
    }

    @Override
    public void initializeListeners() {
        fab.setOnClickListener(this);
    }

    @Override
    public void initializeObjects() {
        presenter = new CustomersListPresenter(this, this,this);
        presenter.loadAsyncTask();
    }

    @Override
    public void initializeRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new SimpleRecyclerViewAdapter<>(this,R.layout.recycler_view_customers,this,recyclerView,null);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void refreshRecyclerView(List list, int position) {
        recyclerViewAdapter.refreshAdapter(list);
    }
    @Override
    public void showMessage(int type, String message, boolean validity) {

    }

    @Override
    public void showProgress() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
    }


    @Override
    public void onItemClick(Object object, View view, int position) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position, View view) {
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, Object object) {
        if (object instanceof Customer){
            Customer customer = (Customer) object;
            ((CustomerViewHolder)viewHolder).textViewName.setText(String.format("%s%s", customer.getFirstName(), customer.getLastName()));
            ((CustomerViewHolder)viewHolder).textViewID.setText(customer.getPhoneNumber());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                startActivity(new Intent(this,CustomerCreateActivity.class));
                break;
        }
    }
}
