package com.example.udp.bean;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class CommonParentBean extends BaseExpandNode {

    private String parentTitle;
    private List<BaseNode> childNode;

    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }
}