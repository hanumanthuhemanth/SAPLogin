package com.example.heman.wizardapp.registration;

public interface IRegistrationView {
    void initializeUI();
    void initializeListeners();
    void initializeObjects();
    void showMessage(int type);
    void showProgress();
    void hideProgress();

}
