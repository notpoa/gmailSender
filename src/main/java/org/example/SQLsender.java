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

// Import JDBC classes
import java.sql.*;

public class SQLsender {
    // Static connection variable to maintain the database connection
    static Connection conn;

    public static void sendEmail(String recipient, String attachmentPath) {
        // Email settings
        String host = "smtp.gmail.com";
        String port = "587";
        final String username = "vincentpcoding@gmail.com"; // Your Gmail username
        final String password = "txsx xgqn wxqu sufv"; // Your Gmail app-specific password

        // Server properties
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
            // Creating the message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Sender email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient)); // Recipient email
            message.setSubject("Monthly Data Table"); // Email subject

            // HTML body part
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

    public static String generateTableImage() throws IOException, SQLException {
        // Query the database
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MonthName, DataValue FROM RandomData");

        // Prepare data for the table
        String[] columnNames = {"Month", "Value"};
        java.util.List<Object[]> dataList = new java.util.ArrayList<>();

        while (rs.next()) {
            String month = rs.getString("MonthName");
            double value = rs.getDouble("DataValue");
            dataList.add(new Object[]{month, value});
        }

        rs.close();
        stmt.close();

        // Convert dataList to Object[][]
        Object[][] data = new Object[dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i);
        }

        // Create the table
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        table.setGridColor(Color.BLACK);
        table.setShowGrid(true);

        // Table dimensions
        table.setSize(table.getPreferredSize());

        // BufferedImage
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

    public static void setupDatabase() throws SQLException {
        Statement stmt = conn.createStatement();

        // Drop table if it exists
        stmt.execute("DROP TABLE IF EXISTS RandomData");

        // Create a random table with renamed columns to avoid reserved keywords
        stmt.execute("CREATE TABLE RandomData (MonthName VARCHAR(255), DataValue DOUBLE)");

        stmt.close();

        // Insert random data using PreparedStatement
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct"};
        String insertSQL = "INSERT INTO RandomData (MonthName, DataValue) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL);

        for (String month : months) {
            double value = Math.random() * 10;
            pstmt.setString(1, month);
            pstmt.setDouble(2, value);
            pstmt.executeUpdate();
        }

        pstmt.close();
        System.out.println("Database setup complete with random data.");
    }

    public static void main(String[] args) {
        try {
            // Set up the database connection
            conn = DriverManager.getConnection("jdbc:h2:./testdb", "sa", "");
            // Set up the database
            setupDatabase();

            // Generate the table image and get the file path
            String tableImagePath = generateTableImage();

            // Call the sendEmail method with recipient email address and attachment path
            sendEmail("vincentdampham@gmail.com", tableImagePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Database connection closed.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
