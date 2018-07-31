package com.huiyou.mzzn.utils;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JPushUtil {

	private static final Logger LOG = LoggerFactory.getLogger(JPushUtil.class);
	
	private static String appKey = "530e614672384df3cd4bc4ad";
    private static String masterSecret = "98561e58d2fa8f2f2c666584";
    private static JPushClient jPushClient;
    static {
	jPushClient = new JPushClient(masterSecret, appKey);
    }

    /**
     * 单点发送消息
     * 
     * @param messageId
     * @param content
     * @param registration_id
     */
    public static boolean sendNotification(String messageId, String msgContent, String registrationId) {

	return sendNotification(messageId, msgContent, Arrays.asList(registrationId));
    }

    /**
     * 单点发送消息
     * @param messageId
     * @param msgContent
     * @param registrationId
     * @return
     */
    public static boolean sendNotification(String messageId, String msgContent, List<String> registrationId) {
	try {

	    PushPayload pushPayload = PushPayload.newBuilder().setPlatform(Platform.all())
		    .setAudience(Audience.registrationId(registrationId))
		    .setNotification(Notification.newBuilder().addPlatformNotification(IosNotification.newBuilder().setAlert(msgContent).setContentAvailable(true).addExtra("messageId", messageId).build())
			    .addPlatformNotification(AndroidNotification.newBuilder().setAlert(msgContent)
				    .addExtra("messageId", messageId).build())
			    .build()).setOptions(Options.newBuilder().setApnsProduction(true) .build())
		    .build();
	    PushResult result = jPushClient.sendPush(pushPayload);
	    LOG.info("Got result - " + result);
	    return result.isResultOK();
	} catch (APIConnectionException e) {
	    LOG.error("Connection error, should retry later", e);
	} catch (APIRequestException e) {
	    LOG.error("Should review the error, and fix the request", e);
	    LOG.info("HTTP Status: " + e.getStatus());
	    LOG.info("Error Code: " + e.getErrorCode());
	    LOG.info("Error Message: " + e.getErrorMessage());
	}
	return false;
    }

    /**
     * 通知所有人
     * 
     * @param msgContent
     * @return
     */
    public static boolean notificationAll(String msgContent) {
	try {
	    PushResult result = jPushClient.sendNotificationAll(msgContent);
	    LOG.info("Got result - " + result);
	    return result.isResultOK();
	} catch (APIConnectionException e) {
	    LOG.error("Connection error, should retry later", e);
	} catch (APIRequestException e) {
	    LOG.error("Should review the error, and fix the request", e);
	    LOG.info("HTTP Status: " + e.getStatus());
	    LOG.info("Error Code: " + e.getErrorCode());
	    LOG.info("Error Message: " + e.getErrorMessage());
	}
	return false;
    }
    public static void main(String[] args) {
    	sendNotification("100", "推送标题啊~~~", "170976fa8aba59cca81");
	}
}
