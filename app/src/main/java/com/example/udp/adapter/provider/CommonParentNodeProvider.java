package com.example.udp.adapter.provider;

import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.core.view.ViewCompat;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.udp.R;
import com.example.udp.adapter.CommonAdapter;
import com.example.udp.bean.CommonParentBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommonParentNodeProvider extends BaseNodeProvider {

    public CommonParentNodeProvider() {
    }

    @Override
    public int getItemViewType() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_parent_provider;
    }

    @Override
    public void convert(BaseViewHolder helper, BaseNode baseNode) {
        if (baseNode == null) {
            return;
        }
        CommonParentBean commonParentBean = (CommonParentBean) baseNode;
        helper.setText(R.id.tvTitle, commonParentBean.getParentTitle());
        helper.setImageResource(R.id.ivImage, R.drawable.ic_up_gray_2);
        setArrowSpin(helper, baseNode, false);
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @NotNull BaseNode data, @NotNull List<?> payloads) {
        for (Object payload : payloads) {
            if (payload instanceof Integer && (int) payload == CommonAdapter.EXPAND_COLLAPSE_PAYLOAD) {
                // 增量刷新，使用动画变化箭头
                setArrowSpin(helper, data, true);
            }
        }
    }

    private void setArrowSpin(BaseViewHolder helper, BaseNode data, boolean isAnimate) {
        CommonParentBean entity = (CommonParentBean) data;

        ImageView imageView = helper.getView(R.id.ivImage);

        if (entity.isExpanded()) {
            if (isAnimate) {
                ViewCompat.animate(imageView).setDuration(200)
                        .setInterpolator(new DecelerateInterpolator())
                        .rotation(0f)
                        .start();
            } else {
                imageView.setRotation(0f);
            }
        } else {
            if (isAnimate) {
                ViewCompat.animate(imageView).setDuration(200)
                        .setInterpolator(new DecelerateInterpolator())
                        .rotation(180f)
                        .start();
            } else {
                imageView.setRotation(180f);
            }
        }
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        getAdapter().expandOrCollapse(position, true, true);
    }
}