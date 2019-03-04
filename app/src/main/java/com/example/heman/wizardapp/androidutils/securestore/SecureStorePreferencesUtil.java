package com.example.heman.wizardapp.androidutils.securestore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.sap.cloud.mobile.foundation.common.EncryptionError;
import com.sap.cloud.mobile.foundation.common.EncryptionUtil;
import com.sap.cloud.mobile.foundation.securestore.OpenFailureException;
import com.sap.cloud.mobile.foundation.securestore.SecurePreferenceDataStore;

public class SecureStorePreferencesUtil<T> {
    //final constants
    private static final String PREFERENCE_STORE_NAME = "WIZARD_APP_STORE";
    private static final String PREFERENCE_STORE_ALIAS = "securedSharedPreferences";

    //components
    private static SecurePreferenceDataStore store;
    private static SecureStorePreferencesUtil secureStorePreferencesUtil;

    // secure singleton instance
    synchronized public static SecureStorePreferencesUtil getInstance(Context context){
        if (secureStorePreferencesUtil==null){
            secureStorePreferencesUtil = new SecureStorePreferencesUtil();
            openSecureStorePreference(context);
        }else {
            openSecureStorePreference(context);
        }
        return secureStorePreferencesUtil;
    }

    private synchronized static void openSecureStorePreference(Context context) {
        try {
            final byte[] encryptionKey = EncryptionUtil.getEncryptionKey(PREFERENCE_STORE_ALIAS);
            store = new SecurePreferenceDataStore(context, PREFERENCE_STORE_NAME, encryptionKey);
        } catch (OpenFailureException ex) {
            Log.e("Store Error", "An error occurred while opening the preference data store.", ex);
        } catch (EncryptionError ex) {
            Log.e("Encryption fialed", "Failed to get encryption key", ex);
        }
    }

    public void setStoreValue(String key,Object value){
        try {
            if (store!=null){
                if (value instanceof Integer){
                    store.putInt(key, (Integer) value);
                }else if (value instanceof String){
                    store.putString(key, (String) value);
                }else if(value instanceof Float){
                    store.putFloat(key, (Float) value);
                }else if(value instanceof Long){
                    store.putLong(key, (Long) value);
                }else if(value instanceof Boolean){
                    store.putBoolean(key, (Boolean) value);
                }
            }
        }catch (Throwable throwable){
            throwable.printStackTrace();
        } finally{
            if (store!=null) {
                store.close();
            }
        }
    }
    public Object getStoreValue(String key,@NonNull Object defaultValue){
        try {
            if (store!=null){
                if (defaultValue instanceof Integer){
                   return store.getInt(key, (Integer) defaultValue);
                }else if (defaultValue instanceof String){
                   return store.getString(key, (String) defaultValue);
                }else if(defaultValue instanceof Float){
                   return store.getFloat(key, (Float) defaultValue);
                }else if(defaultValue instanceof Long){
                    return store.getLong(key, (Long) defaultValue);
                }else if(defaultValue instanceof Boolean){
                    return store.getBoolean(key, (Boolean) defaultValue);
                }else{
                    store.close();
                    return null;
                }
            }else{
                return null;
            }
        } finally {
            if (store!=null) {
                store.close();
            }
        }
    }

    public void removeKey(String key){
        if (store!=null){
            store.remove(key);
            store.close();
        }
    }

    public void removeAll(){
        if (store!=null){
            store.removeAll();
            store.close();
        }
    }
}



