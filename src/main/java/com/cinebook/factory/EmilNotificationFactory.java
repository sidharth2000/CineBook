package com.cinebook.factory;

public class EmilNotificationFactory implements NotificationFactory{
    @Override
    public Notification creatNotification(NotificationType type) {
        return new EmailNotification();
    }
}
