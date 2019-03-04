package com.example.heman.wizardapp.androidutils;

public interface IRootView {
    void initializeUI();
    void initializeListeners();
    void initializeObjects();
    void showMessage(int type, String message, boolean validity);
    void showProgress();
    void hideProgress();
}
