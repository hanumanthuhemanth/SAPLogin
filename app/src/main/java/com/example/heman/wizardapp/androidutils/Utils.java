package com.example.heman.wizardapp.androidutils;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heman.wizardapp.BuildConfig;
import com.example.heman.wizardapp.R;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Utils{
    private static String messageToToast;
    private static Toast toast;
    public static void toastAMessage(String msg, final Context context) {
        if (toast != null && toast.getView().isShown()) {
            msg = messageToToast + "\n" + msg;
        }
        else {  //clear any previously shown toasts that have since stopped being displayed
            messageToToast = "";
        }
        messageToToast = msg;
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(context, messageToToast, Toast.LENGTH_LONG);
            toast.show();
        });
    }

    public static String returnDeviceID(Context context){
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
    public static String returnVersionNumber(){
        return BuildConfig.VERSION_NAME;
    }
    public static Snackbar returnTopViewSnackBar(Context context,View v,int length, String message,boolean isError){
        Snackbar mSnackBar = Snackbar.make(v, message, length);
        View mSnackBarView = mSnackBar.getView();
        CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams)mSnackBarView.getLayoutParams();
        mSnackBarView.setLayoutParams(params);
        if (isError) {
            mSnackBarView.setBackgroundColor(context.getColor(R.color.colorError));
        }else{
            mSnackBarView.setBackgroundColor(context.getColor(R.color.colorPrimaryLight));
        }
        TextView mainTextView = (mSnackBarView).findViewById(android.support.design.R.id.snackbar_text);
        mainTextView.setTextColor(Color.WHITE);
        mSnackBar.show();
        mSnackBar.setAction("OK", view -> mSnackBar.dismiss()).setActionTextColor(Color.WHITE).show();
        return mSnackBar;
    }
    public static boolean isNetworkConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isActiveNetworkAvailable() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress inetSocketAddress = new InetSocketAddress("8.8.8.8", 53);
            sock.connect(inetSocketAddress, timeoutMs);
            sock.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
