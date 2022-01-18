package com.example.udp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.udp.R;
import com.example.udp.adapter.CustomerStateAdapter;
import com.example.udp.adapter.CustomerTrackingAdapter;

import java.util.ArrayList;
import java.util.List;


//客户跟踪状态页面
public class CustomerStateActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, CustomerStateActivity.class);
        context.startActivity(starter);
    }

    public CustomerStateAdapter customerStateAdapter;

    public List<String> list = new ArrayList<>();

    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_state);
        initViews();

        list.add("预约回访");
        list.add("重点回访");
        list.add("今日回访");
        list.add("预约来访");
        list.add("今日来访");
        list.add("已经来访");
        list.add("预约出单");

        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        customerStateAdapter = new CustomerStateAdapter(this, list, listener);
        recyclerView.setAdapter(customerStateAdapter);
    }

    public CustomerStateAdapter.OnItemClickListener listener = (position, message) ->
            CustomerTrackingActivity.start(CustomerStateActivity.this, message,"客户跟踪");

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }
}