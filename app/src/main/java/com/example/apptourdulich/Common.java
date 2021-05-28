package com.example.apptourdulich;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class Common {
    public static boolean IsConnectedToInternet(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null){
            NetworkInfo[] networkInfos=connectivityManager.getAllNetworkInfo();
            if(networkInfos!=null) {
                for(int i=0; i<networkInfos.length;i++){
                    if(networkInfos[i].getState()== NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }

            }
        }
        return false;
    }
}
