package com.googry.whoru;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneNumberUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googry.whoru.database.UserDBManager;
import com.googry.whoru.userlist.User;

/**
 * Created by SeokJun on 2016-06-27.
 */
public class ViewService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TextView tv_call_number = (TextView) layout.findViewById(R.id.tv_call_number);
        String call_number = intent.getStringExtra(EXTRA_CALL_NUMBER);
        final String phone_number = PhoneNumberUtils.formatNumber(call_number);
        if (!TextUtils.isEmpty(phone_number)) {
            tv_call_number.setText(phone_number);
        }
        User user = userDBManager.getUserToPhone(call_number);
        if (user != null) {
            TextView tv_name = (TextView) layout.findViewById(R.id.tv_name);
            TextView tv_email = (TextView) layout.findViewById(R.id.tv_email);
            TextView tv_department = (TextView) layout.findViewById(R.id.tv_department);
            tv_name.setText(user.getName());
            tv_email.setText(user.getEmail());
            tv_department.setText(user.getDepartment());
        }else{

        }
        return super.onStartCommand(intent, flags, startId);
    }

    private UserDBManager userDBManager;
    private LinearLayout layout;
    private WindowManager windowManager;
    public static final String EXTRA_CALL_NUMBER = "call_number";
    private WindowManager.LayoutParams params;

    @Override
    public void onCreate() {
        super.onCreate();
        userDBManager = new UserDBManager(getApplicationContext(), UserDBManager.DBNAME, null, UserDBManager.DBVERSER);

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        layout = (LinearLayout) layoutInflater.inflate(R.layout.view_popup, null);

        //get screen size
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                PixelFormat.TRANSLUCENT);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(layout, params);

        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        params.width = (int) (screenWidth * 0.8);
        params.y = (int) ((screenHeight) * 0.1);
        windowManager.updateViewLayout(layout, params);

        Button btn_close = (Button) layout.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSelf();
            }
        });


        TelephonyManager tmgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tmgr.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        Log.i("googry", "RINGING");
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        layout.setVisibility(View.INVISIBLE);
                        Log.i("googry", "OFFHOOK");
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
//                        layout.setVisibility(View.VISIBLE);
                        Log.i("googry", "IDLE");
                        break;

                }
                super.onCallStateChanged(state, incomingNumber);
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (layout != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(layout);
            layout = null;

        }
    }
}
