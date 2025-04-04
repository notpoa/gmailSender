package org.example;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class lineGraphOutgoing {

    public static void sendEmail(String recipient, String attachmentPath) {
        // Email settings
        String host = "smtp.gmail.com";
        String port = "587";
        final String username = "vincentpcoding@gmail.com"; // Your Gmail username
        final String password = "txsx xgqn wxqu sufv"; // Your Gmail app-specific password

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
            message.setSubject("Line Graph"); // Email subject

            // Create the HTML body part
            MimeBodyPart htmlBodyPart = new MimeBodyPart();
            String htmlContent = "<h1>Monthly Data Graph</h1><p>Line Graph:</p>" +
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

            System.out.println("Email sent successfully with the embedded graph!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static String generateGraph() throws IOException {
        // Create a dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1.0, "Data", "Jan");
        dataset.addValue(4.0, "Data", "Feb");
        dataset.addValue(3.0, "Data", "Mar");
        dataset.addValue(5.0, "Data", "Apr");
        dataset.addValue(7.0, "Data", "May");
        dataset.addValue(6.0, "Data", "Jun");
        dataset.addValue(8.0, "Data", "Jul");
        dataset.addValue(7.0, "Data", "Aug");
        dataset.addValue(9.0, "Data", "Sep");
        dataset.addValue(10.0, "Data", "Oct");

        // Create the line chart
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Monthly Data",
                "Month",
                "Value",
                dataset
        );

        // Save the chart as a PNG image
        String filePath = "line_chart.png";
        ChartUtils.saveChartAsPNG(new File(filePath), lineChart, 800, 600);
        System.out.println("Graph generated and saved as " + filePath);
        return filePath;
    }

    public static void main(String[] args) {
        try {
            // Generate the graph and get the file path
            String graphPath = generateGraph();

            // Call the sendEmail method with recipient email address and attachment path
            sendEmail("thai.pham41@t-mobile.com", graphPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//thai.pham41@t-mobile.com
