package com.example.heman.wizardapp.dashboard;
import java.util.List;

public interface IDashboardView<T> {
    interface IDashboardPresenter{
        void loadAsyncTask();
    }
    void initializeUI();
    void initializeListeners();
    void initializeObjects();
    void initializeRecyclerView();
    void showMessage(String message, int type);
    void showProgress();
    void hideProgress();
    void refreshRecyclerView(List<T> list, int position);

}
