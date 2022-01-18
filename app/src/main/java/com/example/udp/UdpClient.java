package com.example.udp;

import android.text.TextUtils;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

/**
 * @Description:
 * @Author: yangwj
 * @CreateDate: 2022/1/5 14:51
 * @UpdateUser:
 * @UpdateDate: 2022/1/5 14:51
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class UdpClient {

    private DatagramSocket mDatagramSocket;
    private String ip;
    private int port;

    /**
     * 初始化对象
     *
     * @param ip
     * @param port
     */
    public UdpClient(String ip, int port) {
        try {
            this.ip = ip;
            this.port = port;
            mDatagramSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message, String charsetName) {
        if (TextUtils.isEmpty(ip) || port < 0 || port > 65535) {
            throw new SecurityException("");
        }

        if (TextUtils.isEmpty(message)) {
            throw new SecurityException("不能发送空数据");
        }

        try {
            byte data[] = null;
            if (!TextUtils.isEmpty(charsetName)) {
                data = message.getBytes(Charset.forName(charsetName));
            } else {
                data = message.getBytes();
            }
            byte g_enkey = 10;
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) (data[i] + g_enkey);
                data[i] = (byte) ~data[i];
            }

            DatagramPacket sendPacket = new DatagramPacket(data, data.length, InetAddress.getByName(ip), port);
            mDatagramSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            {

            }
        }
    }

    public String receiveMessage(String charsetName) {
        String info = null;
        try {
            byte[] inBuff = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(inBuff, 1024);
            if (mDatagramSocket == null) {
                return "";
            }
            mDatagramSocket.receive(datagramPacket);
            int len = datagramPacket.getLength();
            byte[] data = datagramPacket.getData();

            byte g_enkey = 10;

            for (int i = 0; i < datagramPacket.getLength(); i++) {
                data[i] = (byte) ~data[i];
                data[i] = (byte) (data[i] - g_enkey);
            }

            if (!TextUtils.isEmpty(charsetName)) {
                info = new String(data, 0, len);
            } else {
                info = new String(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return info;
    }

    public void close() {
        if (mDatagramSocket != null) {
            mDatagramSocket.close();
        }
        mDatagramSocket = null;
    }
}