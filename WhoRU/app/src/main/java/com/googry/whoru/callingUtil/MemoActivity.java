package com.googry.whoru.callingUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.googry.whoru.R;
import com.googry.whoru.ViewService;
import com.googry.whoru.database.MemoDBManager;
import com.googry.whoru.userlist.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by SeokJun on 2016-08-06.
 */
public class MemoActivity extends Activity {
    private Button btn_addmemo;
    private ListView lv_memo;

    private MemoAdapter memoAdapter;

    private MemoDBManager memoDBManager;

    private String call_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        setLayout();

        call_number = getIntent().getStringExtra("call_number");

        memoAdapter = new MemoAdapter(getApplicationContext());
        memoDBManager = new MemoDBManager(getApplicationContext(), MemoDBManager.DBNAME, null, MemoDBManager.DBVERSER);
        memoAdapter.addItems(memoDBManager.getArrayListData());

        btn_addmemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                final View addMemoDialog = inflater.inflate(R.layout.dialog_addmemo, null);
                final EditText et_memo = (EditText) addMemoDialog.findViewById(R.id.et_memo);

                final AlertDialog.Builder builder = new AlertDialog.Builder(MemoActivity.this);
                builder.setTitle("메모 추가");
                builder.setView(addMemoDialog);
                builder.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GregorianCalendar calendar = new GregorianCalendar();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        String strDate = year + "/" + (month + 1) + "/" + day;
                        String strTime = String.format("%02d : %02d", hour, minute);

                        Memo memo = new Memo(call_number, strDate, strTime, et_memo.getText().toString());
                        memoDBManager.insert(memo);
                        memoAdapter.addItem(memo);
                        memoAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

        lv_memo.setAdapter(memoAdapter);


    }

    private void setLayout() {
        btn_addmemo = (Button) findViewById(R.id.btn_addmemo);
        lv_memo = (ListView) findViewById(R.id.lv_memo);
    }

    private class MemoAdapter extends BaseAdapter {
        private ArrayList<Memo> alMemo = new ArrayList<>();
        private Context context = null;

        public MemoAdapter(Context context) {
            super();
            this.context = context;
        }

        public ArrayList<Memo> getAlMemo() {
            return this.alMemo;
        }

        @Override
        public int getCount() {
            return alMemo.size();
        }

        @Override
        public Object getItem(int position) {
            return alMemo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.listitem_memo, null);

                holder.tv_itemDay = (TextView) convertView.findViewById(R.id.tv_itemDay);
                holder.tv_itemContent = (TextView) convertView.findViewById(R.id.tv_itemContent);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Memo memo = alMemo.get(position);

            //나중에 이미지 삽입
//        holder.iv_picture.
            holder.tv_itemDay.setText(memo.getDay());
            holder.tv_itemContent.setText(memo.getContent());
            return convertView;
        }

        private class ViewHolder {
            public TextView tv_itemDay, tv_itemContent;
        }

        public void addItems(ArrayList<Memo> alMemo) {
            this.alMemo = alMemo;
        }

        public void addItem(Memo Memo) {
            this.alMemo.add(Memo);
        }


        public void removeItem(int position) {
            alMemo.remove(position);
        }

        public void removeItem(Memo Memo) {
            alMemo.remove(Memo);
        }

//        public void sortItem() {
//            Collections.sort(alMemo, Memo.ALPHA_COMPARATOR);
//            this.notifyDataSetChanged();
//        }
    }

    @Override
    protected void onDestroy() {
        ViewService.onView();
        super.onDestroy();
    }
}
