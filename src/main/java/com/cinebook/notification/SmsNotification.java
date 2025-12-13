/**
 * @author Mathew and Abhaydev
 * 
 * 
 */

package com.cinebook.notification;

public class SmsNotification implements Notification {

    @Override
    public void send(String to, String message) {
        System.out.println("=== Sending SMS ===");
        System.out.println("To: " + to);
        System.out.println("Message: " + message);
        System.out.println("====================");
    }

	@Override
	public void addAttachment(byte[] attachment) {
		
	}
}
