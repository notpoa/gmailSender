import javax.swing.*;
import java.awt.*;

public class graph {

    public static void main(String[] args) {
        // Create a new JFrame (window)
        JFrame frame = new JFrame("Simple Table Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Data for the table in a 2D array (rows and columns)
        String[][] data = {
                { "1", "Alice", "23" },
                { "2", "Bob", "28" },
                { "3", "Charlie", "33" }
        };

        // Column names
        String[] columnNames = { "ID", "Name", "Age" };

        // Create a JTable with the data and column names
        JTable table = new JTable(data, columnNames);

        // Put the table in a scroll pane in case it's too big
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }
}
/**
 * <?xml version="1.0" encoding="UTF-8"?>
 * <project xmlns="http://maven.apache.org/POM/4.0.0"
 *          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *          xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
 *     <modelVersion>4.0.0</modelVersion>
 *
 *     <groupId>org.example</groupId>
 *     <artifactId>gmailSender</artifactId>
 *     <version>1.0-SNAPSHOT</version>
 *
 *     <properties>
 *         <maven.compiler.source>21</maven.compiler.source>
 *         <maven.compiler.target>21</maven.compiler.target>
 *         <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
 *     </properties>
 *
 *     <dependencies>
 *         <!-- JavaMail API Dependency -->
 *         <dependency>
 *             <groupId>com.sun.mail</groupId>
 *             <artifactId>javax.mail</artifactId>
 *             <version>1.6.2</version>
 *         </dependency>
 *
 *         <!-- JFreeChart Dependency -->
 *         <dependency>
 *             <groupId>org.jfree</groupId>
 *             <artifactId>jfreechart</artifactId>
 *             <version>1.5.3</version>
 *         </dependency>
 *     </dependencies>
 *
 * </project>
 */