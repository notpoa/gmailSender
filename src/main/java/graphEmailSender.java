package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;

public class graphEmailSender {

    public static void sendEmail(String recipient, File chartFile) {
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
            message.setSubject("Generated Chart"); // Email subject
            message.setText("Here is a chart generated for you."); // Email body

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Add the text part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("This is a test email with the attached chart.");
            multipart.addBodyPart(textBodyPart);

            // Add the file part (chart attachment)
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(chartFile);
            multipart.addBodyPart(attachmentBodyPart);

            // Set the content of the message
            message.setContent(multipart);

            // Send the email
            Transport.send(message);

            System.out.println("Email with chart sent successfully!");

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    public static File generateChartAsImage() {
        // Data for the table
        String[][] data = {
                { "1", "Alice", "23" },
                { "2", "Bob", "28" },
                { "3", "Charlie", "33" }
        };

        // Column names
        String[] columnNames = { "ID", "Name", "Age" };

        // Create a JTable with the data and column names
        JTable table = new JTable(data, columnNames);
        table.setSize(table.getPreferredSize());

        // Create an image to render the table
        BufferedImage image = new BufferedImage(table.getWidth(), table.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        table.paint(g2);
        g2.dispose();

        // Save the image to a file
        File chartFile = new File("chart.png");
        try {
            ImageIO.write(image, "png", chartFile);
            System.out.println("Chart saved as image.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return chartFile;
    }

    public static void main(String[] args) {
        // Generate the chart and save it as an image
        File chartFile = generateChartAsImage();

        // Send the email with the chart attached
        sendEmail("thaip@outlook.com", chartFile);
    }
}
