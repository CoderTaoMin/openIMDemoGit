package com.dara.daraimdemo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.conversation.EServiceContact;
import com.alibaba.mobileim.gingko.plugin.action.OPENIM;
import com.alibaba.wxlib.util.SysUtil;
import com.dara.daraimdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    final String APP_KEY = "24531197";
    //0787e00dc95302a8f765cf86ea8f1e92
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
    }
    public void init(){
        binding.login.setOnClickListener(this);
        binding.databindTest.setOnClickListener(this);
    }
    public void login(){
        //此实现不一定要放在Application onCreate中
        final String userid = "test1";
//此对象获取到后，保存为全局对象，供APP使用
//此对象跟用户相关，如果切换了用户，需要重新获取
        YWIMKit mIMKit=null;
        if(SysUtil.isMainProcess()){  //初始化openim的sdk。不要放在其他if语句外面初始化。
             mIMKit = YWAPI.getIMKitInstance(userid, APP_KEY);
        }
        //开始登录
        String password = "666666";
        IYWLoginService loginService = mIMKit.getLoginService();
        YWLoginParam loginParam = YWLoginParam.createLoginParam(userid, password);
        final YWIMKit finalMIMKit = mIMKit;
        final YWIMKit finalMIMKit1 = mIMKit;
        loginService.login(loginParam, new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {
                Toast.makeText(MainActivity.this,"登录成功:",Toast.LENGTH_SHORT).show();
                Log.e("TAG", "登录成功:");
                final String target = "test2"; //消息接收者ID
                final String appkey = "24531197"; //消息接收者appKey
                Intent intent = finalMIMKit.getChattingActivityIntent(target, appkey);
                startActivity(intent);

                //userid是客服帐号，第一个参数是客服帐号，第二个是组ID，如果没有，传0
//                EServiceContact contact = new EServiceContact("test2", 0);
//如果需要发给指定的客服帐号，不需要Server进行分流(默认Server会分流)，请调用EServiceContact对象
//的setNeedByPass方法，参数为false。
//                contact.setNeedByPass(false);
//                Intent intent = finalMIMKit1.getChattingActivityIntent(contact);
//                startActivity(intent);

            }

            @Override
            public void onProgress(int arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onError(int errCode, String description) {
                Toast.makeText(MainActivity.this,"登录失败:"+description,Toast.LENGTH_SHORT).show();
                Log.e("TAG", "登录失败:" + description);
                //如果登录失败，errCode为错误码,description是错误的具体描述信息
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:{
                login();
                break;
            }
            case R.id.databind_test:{
                Log.e("TAG", "databindOnclick");
                break;
            }
        }
    }
}
