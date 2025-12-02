package com.cinebook.notification;

public interface Notification {
	void addAttachment(byte[] attachment);
    void send(String to, String message);
}
