package com.example.udp.adapter.provider;

import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.udp.R;
import com.example.udp.bean.CommonChildBean;

public class CommonChildNodeProvider extends BaseNodeProvider {

    ItemChildListener itemChildListener;

    public CommonChildNodeProvider(ItemChildListener itemChildListener) {
        this.itemChildListener = itemChildListener;
    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_child_provider;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        if (baseNode == null) {
            return;
        }

        CommonChildBean commonChildBean = (CommonChildBean) baseNode;
        baseViewHolder.setText(R.id.tvTitle, commonChildBean.getCommonValue());
        TextView textView = baseViewHolder.getView(R.id.tvTitle);
        textView.setOnClickListener(v -> itemChildListener.onParentClick(textView.getText().toString()));
    }
}