package com.dara.custom;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMChattingPageOperateion;
import com.alibaba.mobileim.aop.model.ReplyBarItem;
import com.alibaba.mobileim.aop.model.YWChattingPlugin;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationType;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.kit.contact.YWContactHeadLoadHelper;
import com.dara.daraimdemo.ConversationActivity;
import com.dara.daraimdemo.R;
import com.dara.entity.MessageBodyOrder;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */

public class ChattingOperationCustom extends IMChattingPageOperateion {
    /**
     * 请注意不要和内部的ID重合
     * {@link YWChattingPlugin.ReplyBarItem#ID_CAMERA}
     * {@link YWChattingPlugin.ReplyBarItem#ID_ALBUM}
     * {@link YWChattingPlugin.ReplyBarItem#ID_SHORT_VIDEO}
     */
    private static int ITEM_ID_1 = 0x1;

    /***************** 以下是定制自定义消息view的代码 ****************/

    //自定义消息view的种类数
    private final int typeCount = 1;

    /** 自定义viewType，viewType的值必须从0开始，然后依次+1递增，且viewType的个数必须等于typeCount，切记切记！！！***/
    //地理位置消息
    private final int type_order = 0;//"订单消息"

    public class CustomMessageType {
        private static final String MESSAGE_ORDER = "message_order";
    }

    public ChattingOperationCustom(Pointcut pointcut) {
        super(pointcut);
    }

    @Override
    public List<ReplyBarItem> getCustomReplyBarItemList(final Fragment pointcut, YWConversation conversation, List<ReplyBarItem> replyBarItemList) {
        List<ReplyBarItem> replyBarItems = new ArrayList<ReplyBarItem>();

        for (ReplyBarItem replyBarItem : replyBarItemList) {
            if(replyBarItem.getItemId()== YWChattingPlugin.ReplyBarItem.ID_ALBUM){
                //是否隐藏ReplyBarItem中的选择照片选项
                replyBarItem.setNeedHide(false);
                replyBarItem.setItemImageRes(R.drawable.kf_album);
                replyBarItem.setItemLabel("相冊");
                //不自定义ReplyBarItem中的相册的点击事件,设置OnClicklistener（null）
                replyBarItem.setOnClicklistener(null);
                //自定义ReplyBarItem中的相册的点击事件,设置OnClicklistener
//                replyBarItem.setOnClicklistener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        IMNotificationUtils.getInstance().showToastLong(pointcut.getActivity(), "用户点击了选择照片");
//                    }
//                });
            }else if(replyBarItem.getItemId()== YWChattingPlugin.ReplyBarItem.ID_CAMERA){
                //是否隐藏ReplyBarItem中的拍照选项
                replyBarItem.setNeedHide(false);
                replyBarItem.setItemImageRes(R.drawable.kf_photograph);
                replyBarItem.setItemLabel("拍照");
                //不自定义ReplyBarItem中的拍照的点击事件,设置OnClicklistener(null);
                replyBarItem.setOnClicklistener(null);
                //自定义ReplyBarItem中的拍照的点击事件,设置OnClicklistener
//                开发者在自己实现拍照逻辑时，可以在{@link #onActivityResult(int, int, Intent, List<YWMessage>)}中处理拍照完成后的操作
//                replyBarItem.setOnClicklistener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
            }
            replyBarItems.add(replyBarItem);
        }
        ReplyBarItem replyBarItem = new ReplyBarItem();
        replyBarItem.setItemId(ITEM_ID_1);
        replyBarItem.setItemImageRes(R.drawable.kf_order);
        replyBarItem.setItemLabel("订单");
        replyBarItem.setOnClicklistener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context context = pointcut.getActivity();
                Intent intent = new Intent(context, ConversationActivity.class);
                context.startActivity(intent);
                Log.e("TAG", "订单点击");
            }
        });
        replyBarItems.add(replyBarItem);
        return replyBarItems;
    }

    /**
     * 自定义消息view的种类数
     * @return  自定义消息view的种类数
     */
    @Override
    public int getCustomViewTypeCount() {
        return typeCount;
    }

    @Override
    public boolean needHideHead(int viewType) {
        return super.needHideHead(viewType);
    }

    /**
     * 自定义消息view的类型，开发者可以根据自己的需求定制多种自定义消息view，这里需要根据消息返回view的类型
     * @param message 需要自定义显示的消息
     * @return  自定义消息view的类型
     */
    @Override
    public int getCustomViewType(YWMessage message) {

        String msgType = null;
        try {
            String content = message.getMessageBody().getContent();
            JSONObject object = new JSONObject(content);
            msgType = object.getString("customizeMessageType");
        } catch (Exception e) {

        }
        if (!TextUtils.isEmpty(msgType)) {
            if (msgType.equals(CustomMessageType.MESSAGE_ORDER)) {
                return type_order;
            }
        }
        return super.getCustomViewType(message);
    }


    /**
     * 根据viewType获取自定义view
     * @param fragment      聊天窗口fragment
     * @param message       当前需要自定义view的消息
     * @param convertView   自定义view
     * @param viewType      自定义view的类型
     * @param headLoadHelper    头像加载管理器，用户可以调用该对象的方法加载头像
     * @return  自定义view
     */
    @Override
    public View getCustomView(Fragment fragment, YWMessage message, View convertView, int viewType, YWContactHeadLoadHelper headLoadHelper) {
        if(viewType==type_order){
            ViewHolderOrder holder = null;
            String customize = null;
            String content = message.getContent();
            MessageBodyOrder messageBodyOrder = new MessageBodyOrder();
            try {
                JSONObject jsonObject = new JSONObject(content);
                customize = jsonObject.getString("customize");
                messageBodyOrder= (MessageBodyOrder) messageBodyOrder.unpack(customize);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (convertView == null){
                holder = new ViewHolderOrder();
                convertView = View.inflate(fragment.getActivity(), R.layout.order, null);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolderOrder)convertView.getTag();
            }
            holder.iv_order = (ImageView) convertView.findViewById(R.id.iv_order);
            holder.tv_order_num = (TextView) convertView.findViewById(R.id.tv_order_num);
            holder.tv_order_price = (TextView) convertView.findViewById(R.id.tv_order_price);
            holder.tv_order_time = (TextView) convertView.findViewById(R.id.tv_order_time);
            holder.tv_order_status = (TextView) convertView.findViewById(R.id.tv_order_status);
            holder.tv_order_num.setText(messageBodyOrder.getOrderNum());
            holder.tv_order_price.setText("¥"+String.valueOf(messageBodyOrder.getOrderPrice()));
            holder.tv_order_time.setText(messageBodyOrder.getOrderTime());
            holder.tv_order_status.setText(messageBodyOrder.getOrderStatus());
            return convertView;
        }
        return super.getCustomView(fragment, message, convertView, viewType, headLoadHelper);
    }

        public class ViewHolderOrder {
            ImageView iv_order;
            TextView tv_order_num;
            TextView tv_order_price;
            TextView tv_order_time;
            TextView tv_order_status;
    }

    /**************** 以上是定制自定义消息view的代码 ****************/
}
