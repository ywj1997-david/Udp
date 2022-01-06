package com.example.udp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTv;
    private Button mBtn1;

    private UdpClient mUdpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        initView();
        //初始化数据
        initData();
        //设置监听
        setListener();
    }

    /**
     * 初始化控件
     **/
    private void initView() {
        mTv = findViewById(R.id.tv);
        mBtn1 = findViewById(R.id.btn1);
    }

    /**
     * 初始化数据
     **/
    private void initData() {
        //192.168.50.152
        mUdpClient = new UdpClient("192.168.12.66", 9090);
    }

    /**
     * 设置监听
     **/
    private void setListener() {
        mBtn1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1://测试
                test();
                break;
            default:
                break;
        }
    }

    /**
     * 测试
     **/
    private void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String message = "我是UDP客户端啊,你是UDP服务端吗?";
                CommonBean commonBean = new CommonBean();
//                commonBean.setAction();
//                commonBean.setSeat(104010);
//                commonBean.setId();
                mUdpClient.sendMessage(message, "GBK");
                String result = mUdpClient.receiveMessage("GBK");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTv.setText(result);
                    }
                });

            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭udp链接
        if (mUdpClient != null) {
            mUdpClient.close();
        }
    }
}