package com.example.heman.wizardapp.dashboard;

interface IDashboardRootView{
    void initializeUI();
    void initializeListeners();
    void initializeObjects();
    void showMessage(String message, int type);
    void showProgress();
    void hideProgress();
}
