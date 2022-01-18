package com.example.udp;

import androidx.lifecycle.MutableLiveData;

import com.example.udp.bean.CommonParentBean;

import java.util.List;

/**
 * @Description:
 * @Author: yangwj
 * @CreateDate: 2022/1/17 11:29
 * @UpdateUser:
 * @UpdateDate: 2022/1/17 11:29
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MutableDemoVM {


    public MutableLiveData<List<CommonParentBean>> getData = new MutableLiveData<>();


    public MutableLiveData<Boolean> getTcpData = new MutableLiveData<>();


}