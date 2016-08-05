package com.googry.whoru;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by SeokJun on 2016-06-27.
 */
public class IncomingCallBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = "PHONE STATE";
    private static String mLastState;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onReceive(final Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (state.equals(mLastState)) {
            return;
        } else {
            mLastState = state;
        }

        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
//            final String phone_number = PhoneNumberUtils.formatNumber(incomingNumber);

            Intent serviceIntent = new Intent(context, ViewService.class);
            serviceIntent.putExtra(ViewService.EXTRA_CALL_NUMBER, incomingNumber);
            context.startService(serviceIntent);
        }
    }
}