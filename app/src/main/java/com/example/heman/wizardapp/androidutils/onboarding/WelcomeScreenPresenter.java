package com.example.heman.wizardapp.androidutils.onboarding;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.example.heman.wizardapp.androidutils.IRootView;
import com.example.heman.wizardapp.androidutils.service.offline.OfflineManager;
import com.sap.cloud.mobile.odata.offline.OfflineODataException;

public class WelcomeScreenPresenter implements OfflineManager.IOfflineStoreListener {
    private AppCompatActivity activity;
    private Context context;
    private IRootView rootView;

    public WelcomeScreenPresenter(AppCompatActivity activity, Context context, IRootView rootView) {
        this.activity = activity;
        this.context = context;
        this.rootView = rootView;
    }
    public void initializeStore(){
        try {
            new InitializeOfflineStoreAsyncTask().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void offlineStoreOpened(boolean isSuccess) {
        rootView.showMessage(1,"Offline-store opened successfully",false);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void offlineStoreFailed(boolean isFailed, OfflineODataException error) {
        rootView.showMessage(2,"Offline-store failed to open",true);
    }
    @SuppressLint("StaticFieldLeak")
    private class InitializeOfflineStoreAsyncTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rootView.showProgress();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                OfflineManager.getInstance(context).initializeOfflineStore(WelcomeScreenPresenter.this);
            } catch (Throwable e) {
                e.printStackTrace();
                activity.runOnUiThread(() -> {
                    rootView.showMessage(1, "Offline-store failed to open, Please check Log", true);
                    rootView.hideProgress();
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
