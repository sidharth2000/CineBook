/**
 * @author Mathew and Abhaydev
 * 
 * 
 */

package com.cinebook.notification;

public class NotificationFactory {

    public static Notification createNotification(NotificationType type) {

        switch (type) {
            case EMAIL:
                return new EmailNotification();

            case SMS:
                return new SmsNotification();

            case PUSH:
                return new PushNotification();

            default:
                throw new IllegalArgumentException("Unknown notification type: " + type);
        }
    }
}
