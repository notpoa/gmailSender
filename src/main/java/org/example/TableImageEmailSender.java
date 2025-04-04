package org.example;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class TableImageEmailSender {

    public static void sendEmail(String recipient, String attachmentPath) {
        // email settings
        String host = "smtp.gmail.com";
        String port = "587";
        final String username = "vincentpcoding@gmail.com"; // Your Gmail username
        final String password = "txsx xgqn wxqu sufv"; // Your Gmail app-specific password

        //server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // create a session with authenticator
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // creating the message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // sender email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient)); // recipient email
            message.setSubject("Monthly Data Table"); // email subject

            //HTML body part
            MimeBodyPart htmlBodyPart = new MimeBodyPart();
            String htmlContent = "<h1>Monthly Data Table</h1><p>Table Image:</p>" +
                    "<img src='cid:image_cid'>";
            htmlBodyPart.setContent(htmlContent, "text/html");

            // Create the image body part
            MimeBodyPart imageBodyPart = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(attachmentPath);
            imageBodyPart.setDataHandler(new DataHandler(fds));
            imageBodyPart.setHeader("Content-ID", "<image_cid>"); // Referencing the image in HTML content
            imageBodyPart.setDisposition(MimeBodyPart.INLINE); // Ensure image is inline, not as an attachment

            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlBodyPart);  // Add the HTML part
            multipart.addBodyPart(imageBodyPart); // Add the image part

            // Set the complete message parts
            message.setContent(multipart);

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully with the embedded table image!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static String generateTableImage() throws IOException {
        // Table data
        String[] columnNames = {"Month", "Value"};
        Object[][] data = {
                {"Jan", 1.0},
                {"Feb", 4.0},
                {"Mar", 3.0},
                {"Apr", 5.0},
                {"May", 7.0},
                {"Jun", 6.0},
                {"Jul", 8.0},
                {"Aug", 7.0},
                {"Sep", 9.0},
                {"Oct", 10.0}
        };

        // Create the table
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        table.setGridColor(Color.BLACK);
        table.setShowGrid(true);

        // Table dimensions
        table.setSize(table.getPreferredSize());

        //BufferedImage
        int width = table.getWidth();
        int height = table.getHeight();

        // If the table has not been laid out yet, we need to calculate the size
        if (width == 0 || height == 0) {
            Dimension size = table.getPreferredSize();
            width = size.width;
            height = size.height;
            table.setSize(size);
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Paint the table onto the image
        Graphics2D g2 = image.createGraphics();
        table.paint(g2);
        g2.dispose();

        // Save the image
        String filePath = "table_image.png";
        ImageIO.write(image, "png", new File(filePath));
        System.out.println("Table image generated and saved as " + filePath);
        return filePath;
    }

    public static void main(String[] args) {
        try {
            // Generate the table image and get the file path
            String tableImagePath = generateTableImage();

            // Call the sendEmail method with recipient email address and attachment path
           // sendEmail("vincentdampham@gmail.com", tableImagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//put sql on my machine and create a randome table and read from that table and display it