package com.example.udp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udp.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupPosition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceActivity extends AppCompatActivity {

    private TextView tv_next, spinner;
    // 主线程Handler
    // 用于将从服务器获取的消息显示出来
    private Handler mMainHandler;
    // Socket变量
    private Socket socket;
    // 线程池
    // 为了方便展示,此处直接采用线程池进行线程管理,而没有一个个开线程
    private ExecutorService mThreadPool;
    public String[] data = null;
    public String[] resuleData = null;

    /**
     * 接收服务器消息 变量
     */
    // 输入流对象
    InputStream is;
    // 输入流读取器对象
    InputStreamReader isr;
    BufferedReader br;
    // 接收服务器发送过来的消息
    String response;
    /**
     * 发送消息到服务器 变量
     */
    // 输出流对象
    OutputStream outputStream;

    public static void start(Context context) {
        Intent starter = new Intent(context, ServiceActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        initViews();
        // 初始化线程池
        mThreadPool = Executors.newCachedThreadPool();


        // 实例化主线程,用于更新接收过来的消息
        mMainHandler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        data = response.split("\r\n");
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < data.length; i++) {
                            String str = new String(Base64.decode(data[i].substring(0, data[i].indexOf(",")), Base64.DEFAULT));
                            list.add(str);
                        }
                        resuleData = list.toArray(new String[list.size()]);
                        new XPopup.Builder(ServiceActivity.this)
                                .hasShadowBg(false)
                                .popupPosition(PopupPosition.Right)
                                .atView(spinner)
                                .asAttachList(resuleData, null,
                                        (position1, text) -> {
                                            spinner.setText(text);
                                        })
                                .show();
                        break;
                }
            }
        };

        connectAndSend();

        //下一步
        tv_next.setOnClickListener(v -> {
            String ip = "";
            if ("请选择服务器地址".equals(spinner.getText().toString())) {
                Toast.makeText(ServiceActivity.this, "请选择服务器地址", Toast.LENGTH_SHORT).show();
                return;
            }
            ip = spinner.getText().toString();
            LoginActivity.start(ServiceActivity.this, ip);
        });
        //选择服务器
        spinner.setOnClickListener(v -> {
            if (data != null) {
                Message msg = Message.obtain();
                msg.what = 0;
                mMainHandler.sendMessage(msg);
                return;
            }
            // 利用线程池直接开启一个线程 & 执行该线程
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        // 步骤1：创建输入流对象InputStream
                        is = socket.getInputStream();
                        byte[] bt = new byte[10240];
                        //获取接收到的字节和字节数
                        int length = is.read(bt);
                        if (length == -1) {
                            return;
                        }
                        response = new String(bt, 0, length, "gbk");
//                            //base64解码实例
//                            String str2 = new String(Base64.decode(response.substring(0, response.indexOf(",")), Base64.DEFAULT));

                        // 步骤4:通知主线程,将接收的消息显示到界面
                        Message msg = Message.obtain();
                        msg.what = 0;
                        mMainHandler.sendMessage(msg);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    /**
     * 连接和发送
     */
    private void connectAndSend() {
        // 利用线程池直接开启一个线程 & 执行该线程
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // 创建Socket对象 & 指定服务端的IP 及 端口号
                    socket = new Socket("58.215.179.46", 10887);
                    if (socket.isConnected()) {
                        try {
                            // 步骤1：从Socket 获得输出流对象OutputStream
                            // 该对象作用：发送数据
                            outputStream = socket.getOutputStream();

                            // 步骤2：写入需要发送的数据到输出流对象中
                            outputStream.write(("action:crm_servername\r\n\r\n").getBytes("GBK"));
                            // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞

                            // 步骤3：发送数据到服务端
                            outputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(() -> {
                            // 判断客户端和服务器是否连接成功
                            Toast.makeText(ServiceActivity.this, socket.isConnected() + "---", Toast.LENGTH_SHORT).show();
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initViews() {
        tv_next = findViewById(R.id.tv_next);
        spinner = findViewById(R.id.spinner);
    }
}