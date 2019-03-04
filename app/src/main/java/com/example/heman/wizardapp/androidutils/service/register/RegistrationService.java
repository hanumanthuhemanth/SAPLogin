package com.example.heman.wizardapp.androidutils.service.register;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.heman.wizardapp.R;
import com.example.heman.wizardapp.androidutils.Utils;
import com.example.heman.wizardapp.androidutils.securestore.SecureStoreConstants;
import com.example.heman.wizardapp.androidutils.securestore.SecureStorePreferencesUtil;
import com.sap.cloud.mobile.foundation.common.ClientProvider;
import com.sap.cloud.mobile.foundation.common.SettingsParameters;
import com.sap.cloud.mobile.foundation.networking.AppHeadersInterceptor;
import com.sap.cloud.mobile.foundation.networking.WebkitCookieJar;
import com.sap.cloud.mobile.foundation.user.UserInfo;
import com.sap.cloud.mobile.foundation.user.UserRoles;

import java.io.IOException;
import java.net.MalformedURLException;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import static com.example.heman.wizardapp.androidutils.service.IConfiguration.APP_ID;
import static com.example.heman.wizardapp.androidutils.service.IConfiguration.AUTHORIZATION;
import static com.example.heman.wizardapp.androidutils.service.IConfiguration.BASE_URL;
import static com.example.heman.wizardapp.androidutils.service.IConfiguration.SERVICE_URL;
import static com.example.heman.wizardapp.androidutils.service.IConfiguration.X_CSRF_TOKEN;

@SuppressLint("StaticFieldLeak")
public class RegistrationService implements Callback, Authenticator, UserRoles.CallbackListener {

    // final constants

    // android components

    private Context context;
    //sap components
    private OkHttpClient okHttpClient;
    private UserRoles roles;
    private SettingsParameters settingsParameters;
    // variables
    private IRegistrationResponse registrationResponse;
    private static RegistrationService registrationService;
    private String myTag = getClass().getSimpleName();
    private String userName, password;

    public static RegistrationService getInstance(Context context) {
        if (registrationService == null) registrationService = new RegistrationService(context);
        return registrationService;
    }

    public RegistrationService setUserName(String userName) {
        this.userName = userName;
        return registrationService;
    }

    public RegistrationService setUserPassword(String password) {
        this.password = password;
        return registrationService;
    }

    public RegistrationService setRegistrationCallback(IRegistrationResponse registrationResponse) {
        this.registrationResponse = registrationResponse;
        return registrationService;
    }

    private RegistrationService(Context context) {
        this.context = context;
    }

    /**
     * \
     * This method will initialize the OkHttpClient library and will make post request.
     */
    public void registerUser() {
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new AppHeadersInterceptor(APP_ID, Utils.returnDeviceID(context), Utils.returnVersionNumber()))
                .authenticator(this)
                .cookieJar(new WebkitCookieJar()) // enable if u need to store in cache
                .build();
        Request request = new Request.Builder().get().url(BASE_URL).addHeader(X_CSRF_TOKEN, "fetch").build();
        Callback registrationCallBackListener = this;
        okHttpClient.newCall(request).enqueue(registrationCallBackListener);
        ClientProvider.set(okHttpClient);
    }

    /**
     * This will POST & validate the user credentials and return the response
     *
     * @param route    fetch the route details
     * @param response will return response
     */
    @Override
    public Request authenticate(@NonNull Route route, @NonNull Response response) throws IOException {
        if (response.request().header(AUTHORIZATION) != null) {
            return null; // Give up, we've already attempted to authenticate. This will avoid multiple requests at a time.
        }
        String credential = Credentials.basic(userName, password);
        return response.request().newBuilder().header(AUTHORIZATION, credential).build();
    }

    //---------------------------------------------------registration response------------------------------------

    /**
     * This will return the registration callback and the response.
     *
     * @param call !
     */
    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        Log.d(myTag,"Registration success");
        registrationResponse.registrationSuccessResponse(call, response);
        Utils.toastAMessage(context.getString(R.string.response) + ": " + response.message(), context);
        if (response.code() == 200) {
            SecureStorePreferencesUtil.getInstance(context).setStoreValue(SecureStoreConstants.USER_NAME, ((RegistrationService) okHttpClient.authenticator()).userName);
            SecureStorePreferencesUtil.getInstance(context).setStoreValue(SecureStoreConstants.PASSWORD, ((RegistrationService) okHttpClient.authenticator()).password);
            getUser();
        }
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        registrationResponse.registrationFailureResponse(call, e);
        Utils.toastAMessage(context.getString(R.string.registration_failed) + ": " + e.getMessage(), context);
    }

    //--------------------------------------------------end of registration response------------------------------

    private void getUser() {
        try {
            settingsParameters = new SettingsParameters(SERVICE_URL, APP_ID, Utils.returnDeviceID(context), Utils.returnVersionNumber());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Utils.toastAMessage(e.toString(), context);
        }
        if (settingsParameters != null) {
            roles = new UserRoles(okHttpClient, settingsParameters);
        }
        UserRoles.CallbackListener callbackListener = this;
        if (roles != null) {
            roles.load(callbackListener);
        }
    }
    // user roles success

    /**
     * fetch user details here including the roles.
     *
     * @param userInfo this will give all details
     */
    @Override
    public void onSuccess(@NonNull UserInfo userInfo) {
        registrationResponse.registrationUsersSuccessResponse(userInfo);
    }

    // user roles error
    @Override
    public void onError(@NonNull Throwable throwable) {
        registrationResponse.registrationUserFailureResponse(throwable);
        Utils.toastAMessage(context.getString(R.string.user_role_failure) + ": " + throwable.getMessage(), context);
    }

    public static interface IRegistrationResponse {
        void registrationSuccessResponse(@NonNull Call call, @NonNull Response response);
        void registrationFailureResponse(@NonNull Call call, @NonNull IOException e);
        void registrationUsersSuccessResponse(@NonNull UserInfo userInfo);
        void registrationUserFailureResponse(@NonNull Throwable throwable);
    }
}
