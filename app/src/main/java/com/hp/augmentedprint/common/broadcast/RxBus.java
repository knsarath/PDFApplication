package com.hp.augmentedprint.common.broadcast;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by sarath on 10/4/18.
 */

public class RxBus implements AppBroadCast {
    private PublishSubject<Notification> mNotificationPublishSubject = PublishSubject.create();

    @Inject
    public RxBus() {
    }

    public void send(Notification notification) {
        if (mNotificationPublishSubject.hasObservers())
            mNotificationPublishSubject.onNext(notification);
    }

    @Override
    public Observable<Notification> listen() {
        return mNotificationPublishSubject;
    }

    @Override
    public Observable<Notification> listenFor(NotificationType notificationType) {
        return mNotificationPublishSubject.filter(notification -> notification.getNotificationType().equals(notificationType)).cast(AppBroadCast.Notification.class);

    }
}
