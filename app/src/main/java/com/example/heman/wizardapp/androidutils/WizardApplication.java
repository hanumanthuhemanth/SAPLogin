package com.example.heman.wizardapp.androidutils;

import android.app.Application;

import com.example.heman.wizardapp.androidutils.securestore.SecureStoreConstants;
import com.example.heman.wizardapp.androidutils.securestore.SecureStorePreferencesUtil;
import com.example.heman.wizardapp.androidutils.service.IConfiguration;
import com.sap.cloud.mobile.foundation.authentication.AppLifecycleCallbackHandler;
import com.sap.cloud.mobile.foundation.common.ClientProvider;
import com.sap.cloud.mobile.foundation.common.EncryptionUtil;
import com.sap.cloud.mobile.foundation.networking.AppHeadersInterceptor;
import com.sap.cloud.mobile.foundation.networking.WebkitCookieJar;

import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;

public class WizardApplication extends Application {
    private static final String Authorization = "AUTHORIZATION";
    private static final int TIME_OUT = 60;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(AppLifecycleCallbackHandler.getInstance());
        EncryptionUtil.initialize(this);
        initHttpClient();
       /* Logging.ConfigurationBuilder cb = new Logging.ConfigurationBuilder()
                .logToConsole(true)
                .initialLevel(Level.ALL);
        Logging.initialize(this.getApplicationContext(), cb);*/
    }
    synchronized private void initHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new AppHeadersInterceptor(IConfiguration.APP_ID,Utils.returnDeviceID(getApplicationContext()),Utils.returnVersionNumber()))
                .authenticator((route, response) -> {
                    if (response.request().header(Authorization) != null) {
                        return null; // Give up, we've already attempted to authenticate. This will avoid multiple requests at a time.
                    }
                    String credential = Credentials.basic(String.valueOf(SecureStorePreferencesUtil.getInstance(this).getStoreValue(SecureStoreConstants.USER_NAME,"")), String.valueOf(SecureStorePreferencesUtil.getInstance(this).getStoreValue(SecureStoreConstants.PASSWORD,"")));
                    return response.request().newBuilder().header(Authorization, credential).build();
                })
                .cookieJar(new WebkitCookieJar())
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
        ClientProvider.set(okHttpClient);
    }
}

