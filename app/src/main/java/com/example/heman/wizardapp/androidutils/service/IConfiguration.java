package com.example.heman.wizardapp.androidutils.service;

public interface IConfiguration {
//    String SERVICE_URL = "https://hcpms-p1942932231trial.hanatrial.ondemand.com";  // hcpms url
//    String APP_ID = "in.hemanth.demowiz";
//    String CONNECTION_ID = "com.sap.edm.sampleservice.v2"; // destination name
//    String BASE_URL = SERVICE_URL + "/" + CONNECTION_ID + "/";

    String SERVICE_URL = "https://hcpms-p2001162792trial.hanatrial.ondemand.com";  // hcpms url
    String APP_ID = "com.ntt.hemanth.wizard";
    String CONNECTION_ID = "com.ntt.hemanth.wizard"; // destination name
    String BASE_URL = SERVICE_URL + "/" + CONNECTION_ID + "/";

    //headers keys
    String AUTHORIZATION = "AUTHORIZATION";
    String X_CSRF_TOKEN = "x-csrf-token";
    String X_SMP_APPID = "x-smp-appid";
    String X_SMP_APPCID = "x-smp-appcid";
}
