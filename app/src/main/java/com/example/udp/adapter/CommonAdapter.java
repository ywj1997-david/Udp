package com.example.udp.adapter;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.example.udp.adapter.provider.CommonChildNodeProvider;
import com.example.udp.adapter.provider.CommonParentNodeProvider;
import com.example.udp.adapter.provider.ItemChildListener;
import com.example.udp.bean.CommonChildBean;
import com.example.udp.bean.CommonParentBean;

import java.util.List;

/**
 * @Description:
 * @Author: yangwj
 * @CreateDate: 2022/1/17 9:33
 * @UpdateUser:
 * @UpdateDate: 2022/1/17 9:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CommonAdapter extends BaseNodeAdapter {

    public static final int EXPAND_COLLAPSE_PAYLOAD = 110;

    public CommonAdapter(ItemChildListener itemChildListener) {
        super();
        CommonParentNodeProvider commonParentNodeProvider = new CommonParentNodeProvider();
        addNodeProvider(commonParentNodeProvider);
        CommonChildNodeProvider commonChildBean = new CommonChildNodeProvider(itemChildListener);
        addNodeProvider(commonChildBean);
    }

    @Override
    protected int getItemType(List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof CommonParentBean) {
            return 0;
        } else if (node instanceof CommonChildBean) {
            return 1;
        }
        return -1;
    }
}