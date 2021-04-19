package feri.count.it;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;
import androidx.annotation.Nullable;
import feri.count.datalib.User;
import feri.count.it.application.CountItApplication;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;

public class BackUpService extends Service {
    Timer timer ;
    TimerTask timerTask ;
    String TAG = "Timers" ;
    int Your_X_SECS = 60 * 30;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTimer() ;
        return START_STICKY ;
    }

    final Handler handler = new Handler() ;
    public void startTimer () {
        timer = new Timer() ;
        initializeTimerTask() ;
        timer .schedule( timerTask , 500000 , Your_X_SECS * 1000 ) ;
    }
    public void stopTimerTask () {
        if ( timer != null ) {
            timer .cancel() ;
            timer = null;
        }
    }
    public void initializeTimerTask () {
        timerTask = new TimerTask() {
            public void run () {
                handler .post( new Runnable() {
                    public void run () {
                        CountItApplication app = (CountItApplication) getApplication();
                        User user = app.getLoggedUser();
                        Gson gson = new Gson();
                        String json = gson.toJson(user);
                        writeToFile(json, getApplicationContext());
                    }
                }) ;
            }
        } ;
    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("backup.JSON", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void onDestroy() {
        stopTimerTask() ;
        super.onDestroy();
    }
}