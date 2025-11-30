package com.cinebook.factory;

import java.util.List;

public class EmailNotification implements Notification{
    List<byte[]> attachments;

    @Override
    public void send(String to, String message) {

    }

    public void addAttachement(byte[] attachment){}
}
