package com.example.dailydash.home.views.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
public class NetWorkCheck {
            public  static  boolean isInternetAvailable(        Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                return activeNetwork != null && activeNetwork.isConnected();


    }
}
