package com.example.csa;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;


public class ParseStarterClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);


        Parse.initialize(new Parse.Configuration.Builder(this)
        .applicationId("oIeJFmDlG9VTmrL98D8G4VYN8LD02GNIQaOsFhiB")
        .clientKey("D0OC5s8INVwgtdPdq05UGxmurV32wU5elgp5Xq09")
        .server("https://parseapi.back4app.com/")
        .build());


        /*ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("GCMSenderId","AIzaSyBYWUsQX2H6xgkJCnJmm10q0xfcrofZpPo");
        installation.saveInBackground();
*/
    }
}
