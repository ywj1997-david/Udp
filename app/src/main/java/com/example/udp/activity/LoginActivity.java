package com.example.udp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udp.R;

public class LoginActivity extends AppCompatActivity {

    private TextView tv_back, tv_login;
    private EditText et_account, et_password;

    public static void start(Context context, String ip) {
        Intent starter = new Intent(context, LoginActivity.class);
        starter.putExtra("ip", ip);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //设置状态栏和背景融合
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        initViews();

        Intent intent = getIntent();
        String ip = intent.getStringExtra("ip");
        Toast.makeText(this, ip, Toast.LENGTH_SHORT).show();

        //返回重新选择服务器地址
        tv_back.setOnClickListener(v -> ServiceActivity.start(LoginActivity.this));

        //登录
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(et_account.getText())) {
                    Toast.makeText(LoginActivity.this, "请输入分机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(et_password.getText())) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //TODO 如果密码为空或123456， 强制用户修改密码。
                if ("123456".equals(et_password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "请修改密码", Toast.LENGTH_SHORT).show();
                    return;
                }


                HomeActivity.start(LoginActivity.this);
            }
        });
    }

    private void initViews() {
        tv_back = findViewById(R.id.tv_back);
        tv_login = findViewById(R.id.tv_login);
        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);

    }
}