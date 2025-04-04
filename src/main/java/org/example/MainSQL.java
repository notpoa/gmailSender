package org.example;

public class MainSQL {
    public static void main(String[] args) {
        try {
            // Generate table image from MySQL data
            String imagePath = TableRenderer.renderTableAsImage();

            // Send the image via email
            TableImageEmailSender.sendEmail("vincentp@outlook.com", imagePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
