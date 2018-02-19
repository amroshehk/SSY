package com.applefish.smartshopsyria.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.applefish.smartshopsyria.R;
import com.applefish.smartshopsyria.classes.ConnectChecked;

import java.util.Timer;
import java.util.TimerTask;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.applefish.smartshopsyria.fcm.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.applefish.smartshopsyria.fcm.EndPoints.URL_REGISTER_DEVICE;


public class SplashScreenActivity extends AppCompatActivity {

    private int count = 0;
    private TextView connectState;
    Thread connectThread;

    private Handler handler = new Handler( Looper.getMainLooper() );
    private Timer splashTimer = new Timer();
    private TimerTask splashTimerTask;
    private int timerCount = 0;

    //keys
    private static final String SETTING_KEY_PUSH = "com.applefish.smartshop.SETTING_KEY_PUSH";
    private static final String SETTING_KEY_SOUND = "com.applefish.smartshop.SETTING_KEY_SOUND";
    private static final String SETTING_KEY_VIBRATE = "com.applefish.smartshop.SETTING_KEY_VIBRATE";

    //getString
    private   String SETTING_PUSH;
    private   String SETTING_SOUND;
    private   String SETTING_VIBRATE;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView( R.layout.activity_splash_screen );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        SETTING_PUSH=getBaseContext().getString(R.string.saved_setting_push);
        SETTING_SOUND=getBaseContext().getString(R.string.saved_setting_sound);
        SETTING_VIBRATE=getBaseContext().getString(R.string.saved_setting_vibrate);

        connectState = (TextView)findViewById( R.id.connectState );

        connectThread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if(count == 0)
                                {connectState.setText("Connect .");}
                                else if(count == 1)
                                {connectState.setText("Connect ..");}
                                else if(count == 2)
                                {connectState.setText("Connect ...");}
                                else if(count == 3)
                                {connectState.setText("Connect ....");}
                                else if(count == 4)
                                {connectState.setText("Connect .....");}
                                else if(count == 5)
                                    count=-1;
                                count++;

                            }
                        });
                    }
                } catch (InterruptedException e) {
                    //Log.d("yes","connectThread noooooooooooooooooo" +e);
                }
            }
        };




        splashTimerTask = new TimerTask() {
            @Override
            public void run() {

                handler.postDelayed( new Runnable() {

                    public void run() {

                        try {

                            if (  !ConnectChecked.isNetworkAvailable(getBaseContext())  ) {

                                if(timerCount<3)
                                {
                                    timerCount++;
                                }
                                else
                                {

                                    connectThread.interrupt();

                                    splashTimer.cancel();

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            connectState.setText("Sorry ,can't Connect");

                                        }
                                    },200);

                                }
                            }

                            else {
                                if(!readSharedPreference("REGISTER_FIRST_TIME","SAVING_SITTING").equals("true"))
                                    sendTokenToServer();

                                connectThread.interrupt();
                                connectThread.join();
                                splashTimer.cancel();
                                Intent intent = new Intent();
                                intent.setClass( getBaseContext(), MainActivity.class );
                                startActivity( intent );

                            }

                        } catch (Exception e) {
//                            Log.d("yes","splashTimerTask noooooooooooooooooo");
                        }
                    }
                }, 3000);
            }
        };

        splashTimer.schedule(splashTimerTask, 0, 3500);
        connectThread.start();
    }

    //storing token to mysql server
    private void sendTokenToServer() {
        progressDialog = new ProgressDialog(this);
        // progressDialog.setMessage("Registering Device...");
        progressDialog.setMessage("Notification  Switch...");
        progressDialog.show();

        final String token = SharedPrefManager.getInstance(this).getDeviceToken();
        //final String email = editTextEmail.getText().toString();

        if (token == null) {
            progressDialog.dismiss();
           // Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                         //   Toast.makeText(SplashScreenActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                            if(obj.getString("message").equals("Switch on successfully"))
                            {
                                writeSharedPreference("on",SETTING_KEY_PUSH,SETTING_PUSH);
                                writeSharedPreference("on",SETTING_KEY_SOUND,SETTING_SOUND);
                                writeSharedPreference("on",SETTING_KEY_VIBRATE,SETTING_VIBRATE);
                                writeSharedPreference("true","REGISTER_FIRST_TIME","SAVING_SITTING");
                                //Toast.makeText(getBaseContext(), "Switch on", Toast.LENGTH_LONG).show();
                                Toast.makeText(SplashScreenActivity.this, "Notification  Switch on", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                       // Toast.makeText(SplashScreenActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", token);
                params.put("token", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public String readSharedPreference(String key,String s )
    {
        SharedPreferences sharedPref =getBaseContext().getSharedPreferences(key,MODE_PRIVATE);
        //0 is default_value if no vaule
        String savedSetting = sharedPref .getString(s,"");

        return savedSetting;
    }
    public  void  writeSharedPreference(String savedSetting,String key,String s )
    {
        SharedPreferences sharedPref =getBaseContext().getSharedPreferences(key,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(s, savedSetting);
        editor.commit();
    }




}
