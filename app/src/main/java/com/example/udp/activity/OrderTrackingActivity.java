package com.example.udp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.udp.R;
import com.example.udp.adapter.CustomerStateAdapter;
import com.example.udp.adapter.OrderTrackingAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderTrackingActivity extends AppCompatActivity {

    public static void start(Context context,String flag) {
        Intent starter = new Intent(context, OrderTrackingActivity.class);
        starter.putExtra("flag",flag);
        context.startActivity(starter);
    }

    private RecyclerView recyclerView;

    private OrderTrackingAdapter orderTrackingAdapter;

    public List<String> list = new ArrayList<>();

    private String flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);
        intitViews();

        Intent intent = getIntent();
        flag = intent.getStringExtra("flag");

        list.add("订单处理");
        list.add("订单跟踪");
        list.add("特别处理");
        list.add("异常订单");
        list.add("成交客户");
        list.add("老客回访");
        list.add("回收站");

        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        orderTrackingAdapter = new OrderTrackingAdapter(this, list, listener);
        recyclerView.setAdapter(orderTrackingAdapter);
    }

    public OrderTrackingAdapter.OnItemClickListener listener = (position, message) -> CustomerTrackingActivity.start(OrderTrackingActivity.this, message,flag);

    private void intitViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }
}