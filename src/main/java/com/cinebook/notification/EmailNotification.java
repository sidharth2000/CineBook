/**
 * @author Mathew and Abhaydev
 * 
 * 
 */

package com.cinebook.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;

public class EmailNotification implements Notification {

	private List<byte[]> attachments = new ArrayList<>();

	public void addAttachment(byte[] attachment) {
		attachments.add(attachment);
	}

	@Override
	public void send(String to, String messageBody) {
		System.out.println("=== Sending EMAIL ===");
		System.out.println("To: " + to);
		System.out.println("Message: " + messageBody);
		System.out.println("Attachments count: " + attachments.size());

		// Outlook SMTP configuration
		final String username = "";
		final String password = "";
		String host = "smtp.office365.com";
		int port = 587;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("CineBook Notification");

			// Create multipart message (body + attachments)
			Multipart multipart = new MimeMultipart();

			// Body part
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setText(messageBody);
			multipart.addBodyPart(bodyPart);

			// Attachments
			for (int i = 0; i < attachments.size(); i++) {
				MimeBodyPart attachmentPart = new MimeBodyPart();
				DataSource source = new ByteArrayDataSource(attachments.get(i), "application/pdf");
				attachmentPart.setDataHandler(new DataHandler(source));
				attachmentPart.setFileName("ticket_" + (i + 1) + ".pdf");
				multipart.addBodyPart(attachmentPart);
			}

			message.setContent(multipart);

			// Commented out actual sending for now
			// Transport.send(message);

			System.out.println(
					"Email send simulated successfully to: " + to + " with " + attachments.size() + " attachments.");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to send email: " + e.getMessage());
		}
	}
}
