package com.example.udp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTv;
    private Button mBtn1;

    private UdpClient mUdpClient;

    Timer timer = new Timer();


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


        // 每秒执行
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                String result = mUdpClient.receiveMessage("GBK");
                if (result == null) {
                    return;
                }
                String[] data = result.split("\r\n");
                List<Map<String, String>> list = new ArrayList<>();
                if (result.contains("echo")) {
                    return;
                }
                for (int i = 0; i < data.length; i++) {
                    Map<String, String> map = new HashMap<>();
                    String str1 = data[i].substring(0, data[i].indexOf(":"));
                    String str2 = data[i].substring(str1.length() + 1, data[i].length());
                    map.put(str1, str2);
                    list.add(map);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTv.setText(result);
                    }
                });
            }
        }, 1000, 10);
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
        mUdpClient = new UdpClient("221.228.214.86", 18056);
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
//                String message = "action:logon\r\n" +
//                        "seat:104010\r\n" +
//                        "id:" + UUID.randomUUID().toString() + "\r\n";

                String message = "action:connect\r\n" +
                        "seat:104010\r\n" +
                        "id:sid_7\r\n" +
                        "time:637690750391351082\r\n";
                mUdpClient.sendMessage(message, "GBK");


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

        if (timer != null) {
            timer.cancel();
        }
    }
}