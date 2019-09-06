package com.example.myhealth1;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // GUI部品
    private ListView itemListView;
    private EditText heightEditText;
    private EditText weightEditText;
    private Button saveButton;
    // DB
    private DBAdapter dbAdapter;
    private HealthStatusListAdapter listAdapter;
    // データ
    List<HealthStatus> healthStatusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // GUI部品
        itemListView = (ListView)findViewById(R.id.itemListView);
        heightEditText = (EditText)findViewById(R.id.heightEditText);
        weightEditText = (EditText)findViewById(R.id.weightEditText);
        saveButton = (Button)findViewById(R.id.saveButton);
        // ボタンが押されたときの処理
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 身長と体重をテーブルに格納する
                double height = Double.parseDouble(heightEditText.getText().toString());  // 入力された身長
                double weight = Double.parseDouble(weightEditText.getText().toString());  // 入力された体重
                dbAdapter.open();
                dbAdapter.saveData(height, weight);
                dbAdapter.close();
                loadHealtStatuses();
            }
        });
        // DB
        dbAdapter = new DBAdapter(this);
        listAdapter = new HealthStatusListAdapter();
        loadHealtStatuses();
        /**/
        itemListView.setAdapter(listAdapter);
        /**/
    }

    private void loadHealtStatuses() {
        if (healthStatusList==null)
            healthStatusList = new ArrayList<>();
        healthStatusList.clear();

        dbAdapter.open();
        Cursor c = dbAdapter.getAllHealthStatuses();
        startManagingCursor(c);
        if (c.moveToFirst()) {
            do {
                HealthStatus status = new HealthStatus(c.getDouble(c.getColumnIndex(DBAdapter.COL_WEIGHT)),
                        c.getDouble(c.getColumnIndex(DBAdapter.COL_HEIGHT)));
                status.setLastupdate(c.getString(c.getColumnIndex(DBAdapter.COL_LASTUPDATE)));
                healthStatusList.add(status);
            } while(c.moveToNext());
        }
        stopManagingCursor(c);
        dbAdapter.close();

        listAdapter.notifyDataSetChanged();
    }

    // ListView Adapter
    private class HealthStatusListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            // ココを記述
            return healthStatusList.size();
        }

        @Override
        public Object getItem(int position) {
            // ココを記述
            return  healthStatusList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // ココを記述
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // ココを記述
            TextView heightTextView;
            TextView weightTextView;
            TextView bmiTextView;
            TextView fatStatusTextView;
            TextView lastupdateTextView;
            View v = convertView;
            if (v == null) {
                LayoutInflater inflater =
                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.row, null);
            }
            HealthStatus healthStatus = (HealthStatus) getItem(position);
            if (healthStatus != null) {
                lastupdateTextView = (TextView) v.findViewById(R.id.lastupdateTextView);
                heightTextView = (TextView) v.findViewById(R.id.heightTextView);
                weightTextView = (TextView) v.findViewById(R.id.weightTextView);
                bmiTextView = (TextView) v.findViewById(R.id.heightTextView);
                fatStatusTextView = (TextView) v.findViewById(R.id.heightTextView);

                heightTextView.setText(String.format("身長：%.1fcm", healthStatus.getHeight()));
                weightTextView.setText(String.format("体重：%.1fkg", healthStatus.getWeight()));
                lastupdateTextView.setText(healthStatus.getLastupdate());
                bmiTextView.setText(String.format("BMI：%.1f", healthStatus.getBmi()));
                fatStatusTextView.setText(healthStatus.getStatus());
            }
            return v;
        }
    }
}
