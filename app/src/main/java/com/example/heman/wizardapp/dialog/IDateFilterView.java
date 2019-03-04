package com.example.heman.wizardapp.dialog;

public interface IDateFilterView {

    interface IDateFilterPresenter{
        void getResponseData(String query);
    }

    void initializeUI();
    void initializeObjects();
    void initializeListeners();
    void showProgress();
    void hideProgress();
    void showMessage(String message);
    void refreshDates();
}
