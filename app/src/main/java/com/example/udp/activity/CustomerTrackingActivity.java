package com.example.udp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.udp.R;
import com.example.udp.adapter.CustomerTrackingAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//客户跟踪页面
public class CustomerTrackingActivity extends AppCompatActivity {

    public CustomerTrackingAdapter customerTrackingAdapter;
    public RecyclerView recyclerView;
    public List<Map<String, String>> mapList = new ArrayList<>();
    public String flag = "";
    public String title = "";

    /**
     * @param context
     * @param message 标题
     * @param flag    从哪边进入
     */
    public static void start(Context context, String message, String flag) {
        Intent starter = new Intent(context, CustomerTrackingActivity.class);
        starter.putExtra("title", message);
        starter.putExtra("flag", flag);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_tracking);
        initViews();

        Intent intent = getIntent();
        String message = intent.getStringExtra("title");
        flag = intent.getStringExtra("flag");
        title = intent.getStringExtra("title");
        Toast.makeText(CustomerTrackingActivity.this, message, Toast.LENGTH_SHORT).show();

        for (int i = 0; i < 10; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("number", "电话号码" + i);
            map.put("name", "姓名" + i);
            map.put("info", "简要信息" + i);
            mapList.add(map);
        }


        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        customerTrackingAdapter = new CustomerTrackingAdapter(this, mapList, listener);
        recyclerView.setAdapter(customerTrackingAdapter);
    }

    public CustomerTrackingAdapter.OnItemClickListener listener = (position, message) -> {
        InputActivity.start(CustomerTrackingActivity.this, title, flag);
        finish();
    };

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }
}