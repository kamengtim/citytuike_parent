package com.citytuike.interceptor;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.apache.commons.logging.Log;
import org.slf4j.LoggerFactory;
import java.util.Map;

public class JpushClientUtil {
    protected static final org.slf4j.Logger LOG = LoggerFactory.getLogger(JpushClientUtil.class);

    // demo App defined in resources/jpush-api.conf
    protected static final String APP_KEY ="776a656086d817ee20ba3d1a";
    protected static final String MASTER_SECRET = "226b8beb61465873a0149ed1";
    protected static final String GROUP_PUSH_KEY = "2c88a01e073a0fe4fc7b167c";
    protected static final String GROUP_MASTER_SECRET = "b11314807507e2bcfdeebe2e";

    public static final String TITLE = "Test from API example";
    public static final String ALERT = "Test from API Example - alert";
    public static final String MSG_CONTENT = "Test from API Example - msgContent";
    public static final String REGISTRATION_ID = "0900e8d85ef";
    public static final String TAG = "tag_api";
    public static long sendCount = 0;
    private static long sendTotalTime = 0;
    public static void testSendPush(Map<String, String> parm) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
        // Here you can use NativeHttpClient or NettyHttpClient or ApacheHttpClient.
        // Call setHttpClient to set httpClient,
        // If you don't invoke this method, default httpClient will use NativeHttpClient.
//        ApacheHttpClient httpClient = new ApacheHttpClient(authCode, null, clientConfig);
//        jpushClient.getPushClient().setHttpClient(httpClient);
//        final PushPayload payload = buildPushObject_android_and_ios();
//        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_alias_alert(parm);
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
            System.out.println(result);
            // 如果使用 NettyHttpClient，需要手动调用 close 方法退出进程
            // If uses NettyHttpClient, call close when finished sending request, otherwise process will not exit.
            // jpushClient.close();
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            LOG.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            LOG.error("Sendno: " + payload.getSendno());
        }
    }
    public static PushPayload buildPushObject_all_alias_alert(Map<String, String> parm) {

        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(parm.get("RegId")))
                .setNotification(Notification.alert(parm.get("msg")))
                .build();
    }
}
