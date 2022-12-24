package push_server.Pusher;

import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.PushNotificationResponse;
import com.turo.pushy.apns.util.ApnsPayloadBuilder;
import com.turo.pushy.apns.util.SimpleApnsPushNotification;
import com.turo.pushy.apns.util.TokenUtil;
import com.turo.pushy.apns.util.concurrent.PushNotificationFuture;
import push_server.Entity.APNsToken;
import push_server.VO.PushAlert;

public class Pusher {

    private final ApnsClient apnsClient;

    public Pusher(final ApnsClient apnsClient) {
        this.apnsClient = apnsClient;
    }

    public void push_APNS_message(final APNsToken apns_token, final PushAlert push_alert) throws Exception {
        ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
        payloadBuilder.setAlertTitle("계세요");
        payloadBuilder.setAlertBody(push_alert.getMessage());
        payloadBuilder.setSound("default"); // 사운드 파일명 대입 default면 무음
        payloadBuilder.addCustomProperty("timestamp", push_alert.getTimestamp());
        String payload = payloadBuilder.buildWithDefaultMaximumLength();

        String token = TokenUtil.sanitizeTokenString(apns_token.getAPNsToken());
        SimpleApnsPushNotification pushNotification = new SimpleApnsPushNotification(token, "Katarsys.GyeSeoyo", payload);
        PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>> sendNotificationFuture = apnsClient.sendNotification(pushNotification);
        PushNotificationResponse<SimpleApnsPushNotification> pushNotificationResponse = sendNotificationFuture.get();
        if (!pushNotificationResponse.isAccepted()) {
            throw new Exception("APNs에서 요청 거절 : " + pushNotificationResponse.getRejectionReason());
        }
    }
}
