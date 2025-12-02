package com.cinebook.notification;

import java.util.ArrayList;
import java.util.List;

public class EmailNotification implements Notification {

    private List<byte[]> attachments = new ArrayList<>();

    public void addAttachment(byte[] attachment) {
        attachments.add(attachment);
    }

    @Override
    public void send(String to, String message) {
        System.out.println("=== Sending EMAIL ===");
        System.out.println("To: " + to);
        System.out.println("Message: " + message);
        System.out.println("Attachments count: " + attachments.size());
        System.out.println("======================");
    }
}
