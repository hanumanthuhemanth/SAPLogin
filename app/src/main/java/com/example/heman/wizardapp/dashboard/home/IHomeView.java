package com.example.heman.wizardapp.dashboard.home;
import java.util.List;

public interface IHomeView<T> {
    interface IHomePresenter{
        void loadAsyncTask();
    }
    void initializeRecyclerView();
    void refreshRecyclerView(List<T> list, int position);

}
