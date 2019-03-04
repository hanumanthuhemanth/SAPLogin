package com.example.heman.wizardapp.dashboard.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.heman.wizardapp.R;
import com.example.heman.wizardapp.customers.CustomersListActivity;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements IHomeView.IHomePresenter {
    private Context context;
    private Activity activity;
    private IHomeView view;

    public HomePresenter() {
    }

    public HomePresenter(Context context, Activity activity, IHomeView view) {
        this.context = context;
        this.activity = activity;
        this.view = view;

    }

    @Override
    public void loadAsyncTask() {

    }

    public void getAppList(){
        List<HomeBean>list = new ArrayList<>();
        list.add(new HomeBean("Customers", R.drawable.ic_customers,new Intent(activity, CustomersListActivity.class)));
        list.add(new HomeBean("Sales Order", R.drawable.ic_sales_order,new Intent(activity, CustomersListActivity.class)));
        list.add(new HomeBean("Products", R.drawable.ic_clients,new Intent(activity, CustomersListActivity.class)));
        list.add(new HomeBean("Customers", R.drawable.ic_customers,new Intent(activity, CustomersListActivity.class)));
        list.add(new HomeBean("Sales Order", R.drawable.ic_sales_order,new Intent(activity, CustomersListActivity.class)));
        list.add(new HomeBean("Products", R.drawable.ic_clients,new Intent(activity, CustomersListActivity.class)));
        list.add(new HomeBean("Customers", R.drawable.ic_customers,new Intent(activity, CustomersListActivity.class)));
        list.add(new HomeBean("Sales Order", R.drawable.ic_sales_order,new Intent(activity, CustomersListActivity.class)));
        list.add(new HomeBean("Products", R.drawable.ic_clients,new Intent(activity, CustomersListActivity.class)));
        view.refreshRecyclerView(list,1);
    }
}
