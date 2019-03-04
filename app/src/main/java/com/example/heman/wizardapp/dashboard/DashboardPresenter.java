package com.example.heman.wizardapp.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.heman.wizardapp.Customer;
import com.example.heman.wizardapp.MetaService;
import com.example.heman.wizardapp.androidutils.securestore.SecureStoreConstants;
import com.example.heman.wizardapp.androidutils.securestore.SecureStorePreferencesUtil;
import com.example.heman.wizardapp.androidutils.service.IConfiguration;
import com.example.heman.wizardapp.androidutils.service.offline.OfflineManager;
import com.sap.cloud.mobile.odata.DataServiceException;
import com.sap.cloud.mobile.odata.http.HttpHeaders;

import java.util.Arrays;
import java.util.List;

import okhttp3.Credentials;
@SuppressLint("StaticFieldLeak")
public class DashboardPresenter implements IDashboardView.IDashboardPresenter {

    private Context context;
    private AppCompatActivity activity;
    IDashboardView view;
    MetaService metaService;

    public DashboardPresenter() {
    }

    public DashboardPresenter(Context context, AppCompatActivity activity, IDashboardView view) {
        this.context = context;
        this.activity = activity;
        this.view = view;
    }

    @Override
    public void loadAsyncTask() {
        new GetCustomersAsyncTask(context,view).execute();
    }

    private static class GetCustomersAsyncTask extends AsyncTask<Void,Void, List<Customer>>{
        private IDashboardView view;
        Context context;
        public GetCustomersAsyncTask() {
        }

        public GetCustomersAsyncTask(Context context,IDashboardView view) {
            this.view = view;
            this.context=context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Customer> doInBackground(Void... voids) {
           MetaService metaService = OfflineManager.getInstance(context).getMetaServiceContainer();
            if (metaService != null) {
                try {
                    String credential = Credentials.basic(String.valueOf(SecureStorePreferencesUtil.getInstance(context).getStoreValue(SecureStoreConstants.USER_NAME,"")), String.valueOf(SecureStorePreferencesUtil.getInstance(context).getStoreValue(SecureStoreConstants.PASSWORD,"")));
                    HttpHeaders httpHeaders= new HttpHeaders();
                    httpHeaders.set(IConfiguration.AUTHORIZATION,credential);
                    httpHeaders.set(IConfiguration.X_SMP_APPID,IConfiguration.APP_ID);
                    return metaService.getCustomers(null,httpHeaders);
                }
                catch (DataServiceException dse) {
                    Log.d("DataServiceException", "Exception " + dse.getMessage());
                }
                catch (Exception e){
                    Log.d("Exception", "Exception " + e.getCause().getMessage());
                }
                return Arrays.asList(new Customer[0]);
            }
            else {
                Log.d("", "service container is null");
                return Arrays.asList(new Customer[0]);
            }
        }

        @Override
        protected void onPostExecute(List<Customer> customers) {
            super.onPostExecute(customers);
            view.refreshRecyclerView(customers,0);
        }
    }


   /* private static class CreateAttendanceAsyncTask extends AsyncTask<Void,Void, Void>{
        private IDashboardView view;
        @SuppressLint("StaticFieldLeak")
        Context context;
        Attendance attendance;
        public CreateAttendanceAsyncTask() {
        }

        public CreateAttendanceAsyncTask(Context context, IDashboardView view, Attendance attendance) {
            this.view = view;
            this.context=context;
            this.attendance=attendance;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("attendance","background");
                HttpHeaders httpHeaders= new HttpHeaders();
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.setPreferNoContent(true);
                OnlineManager.getMetaServiceContainer().createEntityAsync(attendance, () -> {
                    Log.d("attendance","success");
                    Utils.toastAMessage("success",context);
                }, e -> {
                    Log.d("attendance",e.toString());
                    Utils.toastAMessage("failed",context);
                },httpHeaders,requestOptions);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }*/


}
