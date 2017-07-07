package com.dara.daraimdemo;

import android.app.Application;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.aop.AdviceBinder;
import com.alibaba.mobileim.aop.PointCutEnum;
import com.alibaba.wxlib.util.SysUtil;
import com.dara.custom.ChattingOperationCustom;

/**
 * Created by Administrator on 2017/7/4.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        final String APP_KEY = "24531197";
//必须首先执行这部分代码, 如果在":TCMSSevice"进程中，无需进行云旺（OpenIM）和app业务的初始化，以节省内存;
        SysUtil.setApplication(this);
        if (SysUtil.isTCMSServiceProcess(this)) {
            return;
        }
        //聊天业务相关
        AdviceBinder.bindAdvice(PointCutEnum.CHATTING_FRAGMENT_OPERATION_POINTCUT,ChattingOperationCustom.class);
//第一个参数是Application Context
//这里的APP_KEY即应用创建时申请的APP_KEY，同时初始化必须是在主进程中
        if (SysUtil.isMainProcess()) {
            YWAPI.init(this, APP_KEY);
        }
    }
}
