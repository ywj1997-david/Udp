package com.example.udp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.udp.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.AttachPopupView;
import com.lxj.xpopup.enums.PopupPosition;

//录入页面
public class InputActivity extends AppCompatActivity {

    private TextView tv_input, tv_customerStatus, tv_customerSource, tv_willingness, tv_sex;
    private LinearLayout ll_condition;

    public static void start(Context context, String message, String flag) {
        Intent starter = new Intent(context, InputActivity.class);
        starter.putExtra("title", message);
        starter.putExtra("flag", flag);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        initViews();

        Intent intent = getIntent();
        String flag = intent.getStringExtra("flag");
        String title = intent.getStringExtra("title");
        if (!"客户录入".equals(flag)) {
            ll_condition.setVisibility(View.VISIBLE);
        } else {
            ll_condition.setVisibility(View.GONE);
        }

        //录入
        tv_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        //客户状态
        tv_customerStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] data1 = null;
                if ("客户录入".equals(flag)) {
                    data1 = new String[]{"今日回访", "今日来访", "预约回访", "重点回访", "预约来访", "已经来访", "预约出单", "订单处理"};
                } else if ("客户跟踪".equals(flag)) {
                    data1 = new String[]{"今日回访", "今日来访", "预约回访", "重点回访", "预约来访", "已经来访", "预约出单", "订单处理", "回收站"};
                } else {
                    data1 = new String[]{"订单处理", "订单跟踪", "特别处理", "异常订单", "成交客户", "回收站"};
                }

                if ("订单处理".equals(title) || "订单跟踪".equals(title) || "特别处理".equals(title) || "异常订单".equals(title)) {
                    data1 = new String[]{"订单处理", "订单跟踪", "特别处理", "异常订单", "成交客户", "回收站"};
                }

                if ("成交客户".equals(title)) {
                    data1 = new String[]{"潜力客户", "今日回访", "今日来访", "预约回访", "重点回访", "预约来访", "已经来访", "预约出单", "订单处理"};
                }

                if ("潜力客户".equals(title) || "回收站".equals(title)) {
                    data1 = new String[]{"今日回访", "今日来访", "预约回访", "重点回访", "预约来访", "已经来访", "预约出单", "订单处理"};
                }

                AttachPopupView attachPopupView = new XPopup.Builder(InputActivity.this)
                        .hasShadowBg(false)
                        .isClickThrough(true)
//                        .popupPosition(PopupPosition.Bottom)
                        .atView(tv_customerStatus)
                        .asAttachList(data1, null,
                                (position1, text) -> {
                                    switch (text) {
                                        case "今日回访":
                                            tv_customerStatus.setText("今日回访");
                                            break;
                                        case "今日来访":
                                            tv_customerStatus.setText("今日来访");
                                            break;
                                        case "预约回访":
                                            tv_customerStatus.setText("预约回访");
                                            break;
                                        case "重点回访":
                                            tv_customerStatus.setText("重点回访");
                                            break;
                                        case "预约来访":
                                            tv_customerStatus.setText("预约来访");
                                            break;
                                        case "预约出单":
                                            tv_customerStatus.setText("预约出单");
                                            break;
                                        case "订单处理":
                                            tv_customerStatus.setText("订单处理");
                                            break;
                                        case "已经来访":
                                            tv_customerStatus.setText("已经来访");
                                            break;
                                    }
                                });
                attachPopupView.show();

            }
        });
        //客户来源
        tv_customerSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] data1 = new String[]{"电话销售", "网络营销", "他人介绍"};
                new XPopup.Builder(InputActivity.this)
                        .hasShadowBg(false)
                        .popupPosition(PopupPosition.Bottom)
                        .atView(tv_customerSource)
                        .asAttachList(data1, null,
                                (position1, text) -> {
                                    switch (text) {
                                        case "电话销售":
                                            tv_customerSource.setText("电话销售");
                                            break;
                                        case "网络营销":
                                            tv_customerSource.setText("网络营销");
                                            break;
                                        case "他人介绍":
                                            tv_customerSource.setText("他人介绍");
                                            break;
                                    }
                                })
                        .show();
            }
        });
        //成交意愿
        tv_willingness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] data1 = new String[]{"强", "中", "弱"};
                new XPopup.Builder(InputActivity.this)
                        .hasShadowBg(false)
                        .popupPosition(PopupPosition.Bottom)
                        .atView(tv_willingness)
                        .asAttachList(data1, null,
                                (position1, text) -> {
                                    switch (text) {
                                        case "强":
                                            tv_willingness.setText("强");
                                            break;
                                        case "中":
                                            tv_willingness.setText("中");
                                            break;
                                        case "弱":
                                            tv_willingness.setText("弱");
                                            break;
                                    }
                                })
                        .show();
            }
        });
        //性别
        tv_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] data1 = new String[]{"男", "女"};
                new XPopup.Builder(InputActivity.this)
                        .hasShadowBg(false)
                        .popupPosition(PopupPosition.Bottom)
                        .atView(tv_sex)
                        .asAttachList(data1, null,
                                (position1, text) -> {
                                    switch (text) {
                                        case "男":
                                            tv_sex.setText("男");
                                            break;
                                        case "女":
                                            tv_sex.setText("女");
                                            break;

                                    }
                                })
                        .show();
            }
        });
    }

    private void initViews() {
        tv_input = findViewById(R.id.tv_input);
        tv_customerStatus = findViewById(R.id.tv_customerStatus);
        tv_customerSource = findViewById(R.id.tv_customerSource);
        tv_willingness = findViewById(R.id.tv_willingness);
        tv_sex = findViewById(R.id.tv_sex);
        ll_condition = findViewById(R.id.ll_condition);
    }
}