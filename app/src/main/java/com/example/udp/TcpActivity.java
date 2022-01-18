package com.example.udp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

public class TcpActivity extends AppCompatActivity {

    private EditText et_ip, et_port;
    private Button btn_connect, btn_disconnect, btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp);
        et_ip = findViewById(R.id.et_ip);
        et_port = findViewById(R.id.et_port);
        btn_connect = findViewById(R.id.btn_connect);
        btn_disconnect = findViewById(R.id.btn_disconnect);
        btn_send = findViewById(R.id.btn_send);
        initListener();
        initDataReceiver();
    }

    private void initListener() {
        //socket connect
        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = et_ip.getText().toString();
                String port = et_port.getText().toString();

                if (TextUtils.isEmpty(ip)) {
                    Toast.makeText(TcpActivity.this, "IP地址为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(port)) {
                    Toast.makeText(TcpActivity.this, "端口号为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                connect(ip, Integer.parseInt(port));
            }
        });

        //socket disconnect
        btn_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnect();
            }
        });

        //socket send
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TcpClient.getInstance().isConnect()) {
                    byte[] data = ("action:service_logon\r\n" +
                            "pwd:xxxxxxxx\r\n" +
                            "seat:104010\r\n").getBytes();
                    send(data);
                } else {
                    Toast.makeText(TcpActivity.this, "尚未连接，请连接Socket", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * socket data receive
     */
    private void initDataReceiver() {
        TcpClient.getInstance().setOnDataReceiveListener(dataReceiveListener);
    }

    /**
     * socket connect
     */
    private void connect(String ip, int port) {
        TcpClient.getInstance().connect(ip, port);
    }

    /**
     * socket disconnect
     */
    private void disconnect() {
        TcpClient.getInstance().disconnect();
        Toast.makeText(TcpActivity.this, "未连接", Toast.LENGTH_SHORT).show();
    }

    /**
     * socket send
     */
    private void send(byte[] data) {
        TcpClient.getInstance().sendByteCmd(data, 1001);
    }


    /**
     * socket data receive
     * data(byte[]) analyze
     */
    private TcpClient.OnDataReceiveListener dataReceiveListener = new TcpClient.OnDataReceiveListener() {
        @Override
        public void onConnectSuccess() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(TcpActivity.this, "已连接", Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public void onConnectFail() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(TcpActivity.this, "未连接", Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public void onDataReceive(byte[] buffer, int size, int requestCode) {
            //获取有效长度的数据
            byte[] data = new byte[size];
            System.arraycopy(buffer, 0, data, 0, size);

//            final String oxValue = Arrays.toString(HexUtil.Byte2Ox(data));

        }
    };


    @Override
    protected void onDestroy() {
        TcpClient.getInstance().disconnect();
        super.onDestroy();
    }
}