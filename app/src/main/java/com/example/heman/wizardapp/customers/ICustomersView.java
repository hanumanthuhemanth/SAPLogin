package com.example.heman.wizardapp.customers;
import java.util.List;

public interface ICustomersView<T> {
    interface ICustomersPresenter{
        void loadAsyncTask();
    }
    void initializeRecyclerView();
    void refreshRecyclerView(List<T> list, int position);

}
