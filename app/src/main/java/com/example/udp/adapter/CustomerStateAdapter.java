package com.example.udp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udp.R;

import java.util.List;
import java.util.Map;

/**
 * @Description: 跟踪客户状态
 * @Author: yangwj
 * @CreateDate: 2022/1/15 13:09
 * @UpdateUser:
 * @UpdateDate: 2022/1/15 13:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CustomerStateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<String> mDatas;

    public CustomerStateAdapter(Context context, List<String> datas, OnItemClickListener listener) {
        this.mContext = context;
        this.mDatas = datas;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_customer_state, parent, false);
        return new NormalHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NormalHolder normalHolder = (NormalHolder) holder;
        normalHolder.tv_state.setText(mDatas.get(position));
        normalHolder.tv_state.setOnClickListener(v -> listener.onClick(position,mDatas.get(position)));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class NormalHolder extends RecyclerView.ViewHolder {

        public TextView tv_state;

        public NormalHolder(View itemView) {
            super(itemView);
            tv_state = itemView.findViewById(R.id.tv_state);
        }

    }

    public OnItemClickListener listener;

    //定义OnItemClickListener接口
    public interface OnItemClickListener {
        void onClick(int position, String message);
    }
}