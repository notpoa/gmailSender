package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TableRenderer {
    public static String renderTableAsImage() throws IOException {
        String[] columnNames = {"Month", "Value"};
        Object[][] data = DataFetcher.getTableData();

        JTable table = new JTable(data, columnNames);
        table.setGridColor(Color.BLACK);
        table.setShowGrid(true);
        table.setSize(table.getPreferredSize());

        // Safeguard in case the table dimensions are too small or data is missing
        Dimension preferredSize = table.getPreferredSize();
        int width = preferredSize.width > 0 ? preferredSize.width : 300;
        int height = preferredSize.height > 0 ? preferredSize.height : 200;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        table.paint(g2);
        g2.dispose();

        String filePath = "table_from_sql.png";
        ImageIO.write(image, "png", new File(filePath));
        System.out.println("Table image saved as " + filePath);
        return filePath;
    }
}
