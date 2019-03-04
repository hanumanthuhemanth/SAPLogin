package com.example.heman.wizardapp.androidutils.service.online;

import android.content.Context;

import com.example.heman.wizardapp.MetaService;
import com.example.heman.wizardapp.androidutils.service.IConfiguration;
import com.sap.cloud.mobile.foundation.common.ClientProvider;
import com.sap.cloud.mobile.odata.OnlineODataProvider;
import com.sap.cloud.mobile.odata.http.OKHttpHandler;


public class OnlineManager {
    //final constants
    private final String TAG = this.getClass().getSimpleName();
    //sap components

    private static MetaService metaServiceContainer;
    //android components
    private Context context;


    synchronized public static MetaService getMetaServiceContainer() {
        if (metaServiceContainer==null){
            return metaServiceContainer = new MetaService(getOnlineStoreInstance());
        }else{
            return metaServiceContainer;
        }
    }

    private static OnlineODataProvider getOnlineStoreInstance() {
        OnlineODataProvider onlineODataProvider = new OnlineODataProvider(MetaService.class.getSimpleName(), IConfiguration.BASE_URL);
        onlineODataProvider.getNetworkOptions().setHttpHandler(new OKHttpHandler(ClientProvider.get()));
        onlineODataProvider.getServiceOptions().setCheckVersion(false);
        onlineODataProvider.getServiceOptions().setRequiresType(true);
        onlineODataProvider.getServiceOptions().setCacheMetadata(false);
        return onlineODataProvider;
    }
}
