package com.example.gravity.relaxtime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    Context context;
    String tag;
    MusicService musicService;


    @Override
    public void onReceive(Context context, Intent intent) {
        String state=intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
            Toast.makeText(context,"Phone is idel",Toast.LENGTH_SHORT).show();
        }
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
            musicService.onPause();
            Toast.makeText(context,"Phone is Ringing",Toast.LENGTH_SHORT).show();
        }
    }
}
