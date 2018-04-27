package com.hp.augmentedprint.common.broadcast;

import io.reactivex.Observable;

/**
 * Created by sarath on 10/4/18.
 */


/**
 * Event bus, which is used to trigger App wide broadcast
 */
public interface AppBroadCast {

    void send(Notification notification);

    /**
     * Triggers for all notifications
     * @return
     */
    Observable<Notification> listen();

    /**
     * Triggers for the given notification broadcasts
     * @param type
     * @return
     */
    Observable<Notification> listenFor(NotificationType type);


    class Notification {
        Object mData;
        NotificationType mNotificationType;

        public Notification(NotificationType notificationType, Object data) {
            mData = data;
            mNotificationType = notificationType;
        }

        public NotificationType getNotificationType() {
            return mNotificationType;
        }

        public Object getData() {
            return mData;
        }
    }

    enum NotificationType {
        LAUNCH_WEB_VIEW


    }

}
