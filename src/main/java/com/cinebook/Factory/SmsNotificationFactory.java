package com.cinebook.Factory;

public class SmsNotificationFactory implements NotificationFactory{
    @Override
    public Notification creatNotification(NotificationType type) {
        return new SmsNotification();
    }
}
