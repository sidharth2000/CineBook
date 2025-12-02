package com.cinebook.notification;

public class PushNotification implements Notification {

    @Override
    public void send(String to, String message) {
        System.out.println("=== Sending PUSH NOTIFICATION ===");
        System.out.println("To: " + to);
        System.out.println("Message: " + message);
        System.out.println("==================================");
    }

	@Override
	public void addAttachment(byte[] attachment) {
		
	}
}
