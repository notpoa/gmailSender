package org.example;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class gmailSender {

    public static void sendEmail(String recipient) {
        // Email settings
        String host = "smtp.gmail.com";
        String port = "587";
        final String username = "vincentpcoding@gmail.com"; // Your Gmail username
        final String password = "txsx xgqn wxqu sufv"; // Your Gmail password

        // Set up server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create a session with authenticator
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Sender's email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient)); // Recipient's email
            message.setSubject("hi dad"); // Email subject
            message.setText("This is a test email i jsut sent to you with my bot"); // Email body

            Transport.send(message); // Send the email


            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Call the sendEmail method with recipient email address
        sendEmail("vincentp@outlook.com");
    }
}
//javac gmailSender.java
//java gmailSender


/**
 * generate a fake chart and send it into an email
 * date and number
 * rows and columns
 * attach chart into the email
 */