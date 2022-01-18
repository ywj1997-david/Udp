package com.example.udp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udp.R;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: yangwj
 * @CreateDate: 2022/1/15 13:09
 * @UpdateUser:
 * @UpdateDate: 2022/1/15 13:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CustomerTrackingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Map<String, String>> mDatas;

    public CustomerTrackingAdapter(Context context, List<Map<String, String>> datas, OnItemClickListener listener) {
        this.mContext = context;
        this.mDatas = datas;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_customer_tracking, parent, false);
        return new NormalHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NormalHolder normalHolder = (NormalHolder) holder;
        Map<String, String> map = mDatas.get(position);
        normalHolder.tv_number.setText(map.get("number"));
        normalHolder.tv_name.setText(map.get("name"));
        normalHolder.tv_info.setText(map.get("info"));
        normalHolder.tv_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position, mDatas.get(position).toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class NormalHolder extends RecyclerView.ViewHolder {

        public TextView tv_number, tv_name, tv_info, tv_details;

        public NormalHolder(View itemView) {
            super(itemView);
            tv_number = itemView.findViewById(R.id.tv_number);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_info = itemView.findViewById(R.id.tv_info);
            tv_details = itemView.findViewById(R.id.tv_details);
        }

    }

    public OnItemClickListener listener;

    //定义OnItemClickListener接口
    public interface OnItemClickListener {
        void onClick(int position, String message);
    }
}