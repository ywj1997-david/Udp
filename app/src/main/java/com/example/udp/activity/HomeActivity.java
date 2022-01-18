package com.example.udp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.example.udp.MutableDemoVM;
import com.example.udp.R;
import com.example.udp.adapter.CommonAdapter;
import com.example.udp.adapter.provider.ItemChildListener;
import com.example.udp.bean.CommonChildBean;
import com.example.udp.bean.CommonParentBean;
import com.example.udp.databinding.ActivityHomeBinding;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupPosition;

import java.util.ArrayList;
import java.util.List;


//主页面
public class HomeActivity extends AppCompatActivity {

    private TextView tv_customerEntry, tv_customerTracking, tv_orderTracking, tv_bill, tv_login_out;
    private CommonAdapter commonAdapter;
    private MutableDemoVM mVM = new MutableDemoVM();

    public static void start(Context context) {
        Intent starter = new Intent(context, HomeActivity.class);
        context.startActivity(starter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActivityHomeBinding activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        activityHomeBinding.setViewmodel(mVM);
        initViews();

        commonAdapter = new CommonAdapter(itemChildListener);
        activityHomeBinding.recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        activityHomeBinding.recyclerView.setAdapter(commonAdapter);

        getData();
        mVM.getData.observe(this, list -> {
            commonAdapter.setList(list);
        });


        //客户录入
        tv_customerEntry.setOnClickListener(v -> {
            String[] data1 = new String[]{"电话销售", "网络营销", "他人介绍"};
            new XPopup.Builder(HomeActivity.this)
                    .hasShadowBg(false)
                    .popupPosition(PopupPosition.Bottom)
                    .atView(tv_customerEntry)
                    .asAttachList(data1, null,
                            (position1, text) -> {
                                switch (text) {
                                    case "电话销售":
                                        break;
                                    case "网络营销":
                                        break;
                                    case "他人介绍":
                                        break;
                                }
                            })
                    .show();


            InputActivity.start(HomeActivity.this, "客户录入", "客户录入");
        });
        //客户跟踪
        tv_customerTracking.setOnClickListener(v -> CustomerStateActivity.start(HomeActivity.this));
        //话单
        tv_bill.setOnClickListener(v -> BillActivity.start(HomeActivity.this));
        //登出
        tv_login_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceActivity.start(HomeActivity.this);
                finish();
            }
        });
        //订单跟踪
        tv_orderTracking.setOnClickListener(v -> OrderTrackingActivity.start(HomeActivity.this, "订单跟踪"));
    }

    private void initViews() {
        tv_customerEntry = findViewById(R.id.tv_customerEntry);
        tv_customerTracking = findViewById(R.id.tv_customerTracking);
        tv_orderTracking = findViewById(R.id.tv_orderTracking);
        tv_bill = findViewById(R.id.tv_bill);
        tv_login_out = findViewById(R.id.tv_login_out);
    }

    public void getData() {
        List<CommonParentBean> commonParentBeanList = new ArrayList<>();
        //客户录入
        CommonParentBean commonParentBean = new CommonParentBean();
        commonParentBean.setParentTitle("一、客户录入");
        commonParentBean.setExpanded(false);
        List<CommonChildBean> commonChildBeanList = new ArrayList<>();
        commonChildBeanList.add(new CommonChildBean("(1)系统获客"));
        commonChildBeanList.add(new CommonChildBean("(2)公海客户"));
        commonChildBeanList.add(new CommonChildBean("(3)我的客户"));
        List<BaseNode> baseNodeList = new ArrayList<>(commonChildBeanList);
        commonParentBean.setChildNode(baseNodeList);
        commonParentBeanList.add(commonParentBean);
        //客户跟踪
        CommonParentBean commonParentBean1 = new CommonParentBean();
        commonParentBean1.setParentTitle("二、客户跟踪");
        commonParentBean1.setExpanded(false);
        List<CommonChildBean> commonChildBeanList1 = new ArrayList<>();
        commonChildBeanList1.add(new CommonChildBean("(1)预约回访"));
        commonChildBeanList1.add(new CommonChildBean("(2)重点回访"));
        commonChildBeanList1.add(new CommonChildBean("(3)今日回访"));
        commonChildBeanList1.add(new CommonChildBean("(4)预约来访"));
        commonChildBeanList1.add(new CommonChildBean("(5)今日来访"));
        commonChildBeanList1.add(new CommonChildBean("(6)已经来访"));
        commonChildBeanList1.add(new CommonChildBean("(7)预约出单"));
        List<BaseNode> baseNodeList1 = new ArrayList<>(commonChildBeanList1);
        commonParentBean1.setChildNode(baseNodeList1);
        commonParentBeanList.add(commonParentBean1);
        //订单跟踪
        CommonParentBean commonParentBean2 = new CommonParentBean();
        commonParentBean2.setParentTitle("三、订单跟踪");
        commonParentBean2.setExpanded(false);
        List<CommonChildBean> commonChildBeanList2 = new ArrayList<>();
        commonChildBeanList2.add(new CommonChildBean("(1)订单处理"));
        commonChildBeanList2.add(new CommonChildBean("(2)订单跟踪"));
        commonChildBeanList2.add(new CommonChildBean("(3)特别处理"));
        commonChildBeanList2.add(new CommonChildBean("(4)异常订单"));
        commonChildBeanList2.add(new CommonChildBean("(5)成交客户"));
        commonChildBeanList2.add(new CommonChildBean("(6)潜力客户"));
        commonChildBeanList2.add(new CommonChildBean("(7)回收站"));
        List<BaseNode> baseNodeList2 = new ArrayList<>(commonChildBeanList2);
        commonParentBean2.setChildNode(baseNodeList2);
        commonParentBeanList.add(commonParentBean2);

        mVM.getData.setValue(commonParentBeanList);
    }

    public final ItemChildListener itemChildListener = new ItemChildListener() {
        @Override
        public void onParentClick(String message) {
            if (message.contains("系统获客") || message.contains("公海客户") || message.contains("我的客户")) {
                Toast.makeText(HomeActivity.this, "一", Toast.LENGTH_SHORT).show();
                InputActivity.start(HomeActivity.this,"客户录入","客户录入");
            } else if (message.contains("预约回访") ||
                    message.contains("重点回访") ||
                    message.contains("今日回访") ||
                    message.contains("预约来访") ||
                    message.contains("今日来访") ||
                    message.contains("已经来访") ||
                    message.contains("预约出单")) {
                Toast.makeText(HomeActivity.this, "二", Toast.LENGTH_SHORT).show();
                InputActivity.start(HomeActivity.this,"客户跟踪","客户跟踪");
            } else if (message.contains("订单处理") ||
                    message.contains("订单跟踪") ||
                    message.contains("特别处理") ||
                    message.contains("异常订单") ||
                    message.contains("成交客户") ||
                    message.contains("潜力客户") ||
                    message.contains("回收站")) {
                Toast.makeText(HomeActivity.this, "三", Toast.LENGTH_SHORT).show();
                InputActivity.start(HomeActivity.this,"订单跟踪","订单跟踪");
            }
        }
    };
}