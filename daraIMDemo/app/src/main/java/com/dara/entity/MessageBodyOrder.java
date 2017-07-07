package com.dara.entity;

import android.support.annotation.VisibleForTesting;

import com.alibaba.mobileim.conversation.YWCustomMessageBody;
import com.alibaba.mobileim.conversation.YWMessageBody;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/5.
 */

public class MessageBodyOrder extends YWCustomMessageBody {
    public String customizeMessageType;//消息类型（openIM自定义消息必须定义此属性）
    public String urlImg;//订单图片
    public String orderNum;//订单编号
    public long orderPrice;//订单总价
    public String orderTime;//下单时间
    public String orderStatus;//订单状态

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public float getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(long orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCustomizeMessageType() {
        return customizeMessageType;
    }

    public void setCustomizeMessageType(String customizeMessageType) {
        this.customizeMessageType = customizeMessageType;
    }


    public MessageBodyOrder(String customizeMessageType, String urlImg, String orderNum, long orderPrice, String orderTime, String orderStatus) {
        this.urlImg = urlImg;
        this.orderNum=orderNum;
        this.orderPrice = orderPrice;
        this.orderTime = orderTime;
        this.orderStatus=orderStatus;
        this.customizeMessageType = customizeMessageType;
    }

    public MessageBodyOrder() {

    }

    public YWMessageBody unpack(String content) throws JSONException {
        MessageBodyOrder messageBodyOrder = new MessageBodyOrder();
        // 自定义消息的实现可以使用jsonObject实现，或者也可以根据自定义其他格式，只需要个pack中一致即可
        JSONObject jsonObject = new JSONObject(content);
        messageBodyOrder.setUrlImg(jsonObject.getString("urlImg"));
        messageBodyOrder.setOrderNum(jsonObject.getString("orderNum"));
        messageBodyOrder.setOrderPrice(jsonObject.getLong("orderPrice"));
        messageBodyOrder.setOrderTime(jsonObject.getString("orderTime"));
        messageBodyOrder.setOrderStatus(jsonObject.getString("orderStatus"));
        messageBodyOrder.setCustomizeMessageType("message_order");
        return messageBodyOrder;
    }

    public String pack(YWMessageBody arg1) {
        // 自定义消息的实现可以使用jsonObject实现，或者也可以根据自定义其他格式，只需要个unpack中一致即可
        JSONObject object = new JSONObject();
        try {

            object.put("urlImg", ((MessageBodyOrder) arg1).getUrlImg());
            object.put("orderNum", ((MessageBodyOrder) arg1).getOrderNum());
            object.put("orderPrice", ((MessageBodyOrder) arg1).getOrderPrice());
            object.put("orderTime", ((MessageBodyOrder) arg1).getOrderTime());
            object.put("orderStatus", ((MessageBodyOrder) arg1).getOrderStatus());
            object.put("customizeMessageType", ((MessageBodyOrder) arg1).getCustomizeMessageType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
