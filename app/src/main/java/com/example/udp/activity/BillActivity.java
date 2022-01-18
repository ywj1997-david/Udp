package com.example.udp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.udp.R;
import com.example.udp.adapter.BillsAdapter;
import com.example.udp.adapter.CustomerTrackingAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 话单页面
 */
public class BillActivity extends AppCompatActivity {

    public RecyclerView recyclerView;

    public BillsAdapter billsAdapter;

    public List<Map<String, String>> mapList = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, BillActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        initViews();

        for (int i = 0; i < 10; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("number", "电话号码" + i);
            map.put("info", "通话简要信息" + i);
            map.put("voice", "录音" + i);
            mapList.add(map);
        }
        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        billsAdapter = new BillsAdapter(this, mapList, listener);
        recyclerView.setAdapter(billsAdapter);
    }

    public BillsAdapter.OnItemClickListener listener = new BillsAdapter.OnItemClickListener() {
        @Override
        public void onClick(int position) {

        }
    };

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }
}