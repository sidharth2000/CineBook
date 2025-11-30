package com.cinebook.factory;

public class SmsNotificationFactory implements NotificationFactory{
    @Override
    public Notification creatNotification(NotificationType type) {
        return new SmsNotification();
    }
}
