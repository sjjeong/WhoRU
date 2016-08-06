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

import com.googry.whoru.callingUtil.MemoActivity;
import com.googry.whoru.database.UserDBManager;
import com.googry.whoru.userlist.AddUserActivity;
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

    public int phoneState;

    private LinearLayout ll_profile, ll_function, ll_donotknownumber, ll_adduser;
    private Button btn_memo, btn_sms, btn_mail, btn_history, btn_schedule, btn_register, btn_cancel;
    private TextView tv_call_number, tv_name, tv_email, tv_department;

    private TelephonyManager tmgr;

    PhoneStateListener psListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    if (phoneState == TelephonyManager.CALL_STATE_RINGING)
                        break;
                    phoneState = TelephonyManager.CALL_STATE_RINGING;
                    Log.i("googry", "RINGING");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (phoneState == TelephonyManager.CALL_STATE_OFFHOOK)
                        break;
                    phoneState = TelephonyManager.CALL_STATE_OFFHOOK;
                    if (ll_profile.getVisibility() == View.VISIBLE) {
                        ll_function.setVisibility(View.VISIBLE);
                    }
                    Log.i("googry", "OFFHOOK");
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if (phoneState == TelephonyManager.CALL_STATE_IDLE)
                        break;
                    if (ll_profile.getVisibility() == View.GONE) {
                        ll_adduser.setVisibility(View.VISIBLE);
                        ll_donotknownumber.setVisibility(View.GONE);
                    } else {
                        stopSelf();
                    }
                    phoneState = TelephonyManager.CALL_STATE_IDLE;
                    Log.i("googry", "IDLE");
                    break;

            }
            super.onCallStateChanged(state, incomingNumber);
        }
    };

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        setLayout();

        final String call_number = intent.getStringExtra(EXTRA_CALL_NUMBER);
        final String phone_number = PhoneNumberUtils.formatNumber(call_number);
        if (!TextUtils.isEmpty(phone_number)) {
            tv_call_number.setText(phone_number);
        }
        User user = userDBManager.getUserToPhone(call_number);
        if (user != null) {
            ll_profile.setVisibility(View.VISIBLE);
            tv_name.setText(user.getName());
            tv_email.setText(user.getEmail());
            tv_department.setText(user.getDepartment());
        } else {
            ll_donotknownumber.setVisibility(View.VISIBLE);
        }

        tmgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tmgr.listen(psListener, PhoneStateListener.LISTEN_CALL_STATE);

        btn_memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getApplicationContext(), MemoActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mIntent.putExtra("call_number",call_number);
                startActivity(mIntent);
//                stopSelf();
            }
        });
        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getApplicationContext(), AddUserActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mIntent.putExtra("call_number",call_number);
                startActivity(mIntent);
                stopSelf();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    private void setLayout() {
        ll_profile = (LinearLayout) layout.findViewById(R.id.ll_profile);
        ll_function = (LinearLayout) layout.findViewById(R.id.ll_function);
        ll_donotknownumber = (LinearLayout) layout.findViewById(R.id.ll_donotknownumber);
        ll_adduser = (LinearLayout) layout.findViewById(R.id.ll_adduser);
        btn_memo = (Button) layout.findViewById(R.id.btn_memo);
        btn_sms = (Button) layout.findViewById(R.id.btn_sms);
        btn_mail = (Button) layout.findViewById(R.id.btn_mail);
        btn_history = (Button) layout.findViewById(R.id.btn_history);
        btn_schedule = (Button) layout.findViewById(R.id.btn_schedule);
        btn_register = (Button) layout.findViewById(R.id.btn_register);
        btn_cancel = (Button) layout.findViewById(R.id.btn_cancel);
        tv_call_number = (TextView) layout.findViewById(R.id.tv_call_number);
        tv_name = (TextView) layout.findViewById(R.id.tv_name);
        tv_email = (TextView) layout.findViewById(R.id.tv_email);
        tv_department = (TextView) layout.findViewById(R.id.tv_department);
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
        params.y = (int) ((screenHeight) * 0.2);
        windowManager.updateViewLayout(layout, params);

//        Button btn_close = (Button) layout.findViewById(R.id.btn_close);
//        btn_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                stopself();
//            }
//        });


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (layout != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(layout);
            layout = null;
        }
        if (tmgr != null) {
            tmgr.listen(psListener, PhoneStateListener.LISTEN_NONE);
        }
    }
}
