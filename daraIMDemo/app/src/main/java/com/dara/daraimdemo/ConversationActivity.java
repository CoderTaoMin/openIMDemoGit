package com.dara.daraimdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMCore;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationCreater;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWMessageBody;
import com.alibaba.mobileim.conversation.YWMessageChannel;
import com.dara.entity.MessageBodyOrder;

/**
 * Created by Administrator on 2017/7/5.
 */

public class ConversationActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
    }

    public void sendMessage(View view){
        MessageBodyOrder messageBodyOrder = new MessageBodyOrder("message_order","url","123456789",520,"2015-06-24 15:15:15","待付款");
        String content = messageBodyOrder.pack(messageBodyOrder);
        messageBodyOrder.setContent(content);
        YWMessage message = YWMessageChannel.createCustomMessage(messageBodyOrder);
        final String userID = "test1";
        final String appkey = "24531197";
        YWIMCore imCore = YWAPI.createIMCore(userID, appkey);
        final YWConversationCreater conversationCreater = imCore.getConversationService().getConversationCreater();
        YWConversation conversation = conversationCreater.createConversationIfNotExist("test2");
        conversation.getMessageSender().sendMessage(message, 120, new IWxCallback() {
            @Override
            public void onSuccess(Object... objects) {

            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i) {

            }
        });


//        //从IMKit对象中获取IMCore
//        IMCore imCore = mIMKit.getIMCore()
    }
}
