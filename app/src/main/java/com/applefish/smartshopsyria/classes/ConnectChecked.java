package com.applefish.smartshopsyria.classes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Amro on 14/01/2017.
 */

public class ConnectChecked {


    public static  boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService( context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();


        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
    public static boolean hasInternetAccess(final Context context) throws InterruptedException {
        final boolean[] testt = {false};
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    if (isNetworkAvailable(context))
                    {
                        try {
                            HttpURLConnection urlc = (HttpURLConnection)
//                        (new URL("http://clients3.google.com/generate_204")
                                    (new URL("http://www.google.co.uk")
                                            .openConnection());
                           // urlc.setRequestProperty("User-Agent", "Android");
                            //urlc.setRequestProperty("Connection", "close");
                            urlc.setConnectTimeout(1500);
                            urlc.connect();
                            if (urlc.getResponseCode() == 204 &&  urlc.getContentLength() == 0)
                                testt[0] =true;
                        } catch (Exception e)
                        {
                            Log.e("error no internet 1", "Error checking internet connection", e);
                            testt[0]=false;
                        }
                    }
                    else
                    {
                        Log.d("error no internet 2", "No network available!");
                        testt[0]=false;
                    }
                }
                catch (Exception e)
                {
                    Log.d("error no internet 3", "Thread Error!");
                    testt[0]=false;
                }
            }
        });

        thread.start();
        thread.join();
        return  testt[0];
    }

    public static boolean hasActiveInternetConnection(Context context) {
        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.e("error no internet", "Error checking internet connection", e);
            }
        } else {
            Log.d("error no internet", "No network available!");
        }
        return false;
    }


    public static boolean isOnline () {

        Runtime runtime = Runtime.getRuntime();

        try {

            Process ipProcess = runtime.exec( "/system/bin/ping -c 1 8.8.8.8" );
            int exitValue = ipProcess.waitFor();

            return ( exitValue == 0 );
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
        catch ( InterruptedException e ) {
            e.printStackTrace();
        }

        return false;

    }
}
