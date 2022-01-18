package com.example.udp.bean;

import com.chad.library.adapter.base.entity.node.BaseNode;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class CommonChildBean extends BaseNode {

    private String commonId;
    private String commonText;
    private String commonValue;

    public CommonChildBean(String commonText, String commonValue) {
        this.commonText = commonText;
        this.commonValue = commonValue;
    }

    public CommonChildBean(String commonValue) {
        this.commonValue = commonValue;
    }

    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }

}