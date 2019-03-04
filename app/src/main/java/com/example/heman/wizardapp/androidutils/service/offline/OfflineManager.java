package com.example.heman.wizardapp.androidutils.service.offline;

import android.content.Context;

import com.example.heman.wizardapp.MetaService;
import com.example.heman.wizardapp.androidutils.service.IConfiguration;
import com.sap.cloud.mobile.foundation.common.ClientProvider;
import com.sap.cloud.mobile.odata.core.Action0;
import com.sap.cloud.mobile.odata.core.Action1;
import com.sap.cloud.mobile.odata.core.AndroidSystem;
import com.sap.cloud.mobile.odata.offline.OfflineODataDefiningQuery;
import com.sap.cloud.mobile.odata.offline.OfflineODataException;
import com.sap.cloud.mobile.odata.offline.OfflineODataParameters;
import com.sap.cloud.mobile.odata.offline.OfflineODataProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class OfflineManager {
    //final constants
    private final String TAG = this.getClass().getSimpleName();
    //sap components
    private OfflineODataProvider offlineODataProvider;
    private MetaService metaServiceContainer;

    //android components
    private Context context;

    //variables
    private static OfflineManager offlineManager;
    private IOfflineStoreListener offlineStoreListener;
    private IOfflineStoreDownloadListener storeDownloadListener;
    private IOfflineStoreUploadListener uploadListener;
    private static volatile boolean isOfflineStoreOpened;
    /**
     * @return returns instance of offlineManager
     */
    synchronized public static OfflineManager getInstance(Context context){
        if (offlineManager==null)offlineManager= new OfflineManager(context);
        return offlineManager;
    }

    private OfflineManager(Context context) {
        this.context = context;
    }

    /**
     * @return this will initialize and open offline store
     */
    synchronized public OfflineManager initializeOfflineStore(IOfflineStoreListener offlineStoreListener) {
        this.offlineStoreListener = offlineStoreListener;
        setupOfflineStore(offlineStoreListener);
        return offlineManager;
    }
    /**
     * @return this will initialize and download offline store
     */
    synchronized public OfflineManager initializeOfflineStoreDownload(IOfflineStoreDownloadListener storeDownloadListener) {
        this.storeDownloadListener = storeDownloadListener;
        return offlineManager;
    }
    /**
     * @return this will initialize and download offline store
     */
    synchronized public OfflineManager initializeOfflineStoreUpload(IOfflineStoreUploadListener uploadListener) {
        this.uploadListener = uploadListener;
        return offlineManager;
    }
    private void setupOfflineStore(IOfflineStoreListener offlineStoreListener) {
       /* Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.sap.cloud.mobile.odata");
        logger.setLevel(Level.ALL);*/
        AndroidSystem.setContext(context);
        try {
            URL url = new URL(IConfiguration.BASE_URL);
            OfflineODataParameters offParam = new OfflineODataParameters();
            offParam.setEnableRepeatableRequests(false);
            offlineODataProvider = new OfflineODataProvider(url, offParam, ClientProvider.get(), null, null);
            for (String queries : getDefiningQueriesList()) {
                offlineODataProvider.addDefiningQuery(new OfflineODataDefiningQuery(queries, queries, false));
            }
            offlineODataProvider.open(() -> {
                isOfflineStoreOpened =true;
                offlineStoreListener.offlineStoreOpened(true);
            }, (OfflineODataException offlineOdataException) -> {
                isOfflineStoreOpened =false;
                offlineStoreListener.offlineStoreFailed(true,offlineOdataException);
            });
        } catch (OfflineODataException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (offlineODataProvider!=null) {
            metaServiceContainer = new MetaService(offlineODataProvider);
        }
    }

    /**
     * @return offlineDataProvider instance
     */
    public OfflineODataProvider getOfflineODataProvider(){
        return offlineODataProvider;
    }
    /**
     * @return offlinestore instance
     */
    public static boolean isOfflineStoreOpened(){
        return isOfflineStoreOpened;
    }
    /**
     * @return metaService if store is opened instance
     */
    public MetaService getMetaServiceContainer(){
        if (isOfflineStoreOpened){
            return metaServiceContainer;
        }
        return null;
    }
    /**
     * This method will download the store / update the database with latest data available in server
     * @return offline manager
     */
    public OfflineManager downloadStore(){
        if (offlineODataProvider!=null&&isOfflineStoreOpened()){
            offlineODataProvider.download(new Action0() {
                @Override
                public void call() {
                    storeDownloadListener.offlineStoreDownloadSuccess(true);
                }
            }, new Action1<OfflineODataException>() {
                @Override
                public void call(OfflineODataException e) {
                    storeDownloadListener.offlineStoreDownloadFailed(true,e);
                }
            });
        }
        return offlineManager;
    }
    /**
     * This method will upload the store / upload the latest data available in client to server
     * @return offline manager
     */
    public OfflineManager uploadStore(){
        if (offlineODataProvider!=null&&isOfflineStoreOpened()){
            offlineODataProvider.upload(new Action0() {
                @Override
                public void call() {
                    uploadListener.offlineStoreUploadSuccess(true);
                }
            }, new Action1<OfflineODataException>() {
                @Override
                public void call(OfflineODataException e) {
                    uploadListener.offlineStoreUploadFailed(true,e);
                }
            });
        }
        return offlineManager;
    }

    /**
     * initialize required defining queries
     * @return returns list of defining Queries
     */
    private List<String> getDefiningQueriesList(){
        List<String>definingQueriesList = new ArrayList<>();
        definingQueriesList.add("Customers");
        definingQueriesList.add("Suppliers");
        definingQueriesList.add("Products");
        definingQueriesList.add("ProductCategories");
        definingQueriesList.add("ProductTexts");
        definingQueriesList.add("Stock");
        definingQueriesList.add("PurchaseOrderHeaders");
        definingQueriesList.add("PurchaseOrderItems");
        definingQueriesList.add("SalesOrderHeaders");
        definingQueriesList.add("SalesOrderItems");
        return definingQueriesList;
    }


    //----------------------------------------------------------------------------------------interfaces---------------------------------
    /**
     * This interface methods will give the insance for store result callbacks
     */
    public interface IOfflineStoreListener{
        void offlineStoreOpened(boolean isSuccess);
        void offlineStoreFailed(boolean isFailed, OfflineODataException error);
    }
    /**
     * This interface methods will give the insance for download store result callbacks
     */
    public interface IOfflineStoreDownloadListener{
        void offlineStoreDownloadSuccess(boolean isSuccess);
        void offlineStoreDownloadFailed(boolean isFailed, OfflineODataException error);
    }
    /**
     * This interface methods will give the insance for upload store result callbacks
     */
    public interface IOfflineStoreUploadListener{
        void offlineStoreUploadSuccess(boolean isSuccess);
        void offlineStoreUploadFailed(boolean isFailed, OfflineODataException error);
    }
}
