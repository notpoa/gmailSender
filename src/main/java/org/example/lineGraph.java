package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Main extends JPanel {

    private int padding = 25;
    private int labelPadding = 25;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;
    private ArrayList<Double> scores;

    public Main(ArrayList<Double> scores) {
        this.scores = scores;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Create Graphics2D instance for advanced drawing
        Graphics2D g2 = (Graphics2D) g;
        // Enable anti-aliasing for smoother lines
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate scales to fit the data within the panel
        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (scores.size() - 1);
        double yScale =
                ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

        // Generate coordinates for each data point
        ArrayList<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < scores.size(); i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 =
                    (int)
                            ((getMaxScore() - scores.get(i))
                                    * yScale
                                    + padding);
            graphPoints.add(new Point(x1, y1));
        }

        // Draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(
                padding + labelPadding,
                padding,
                getWidth() - (2 * padding) - labelPadding,
                getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        // Draw Y-axis grid lines and labels
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 =
                    getHeight()
                            - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions
                            + padding + labelPadding);
            int y1 = y0;
            if (scores.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(
                        padding + labelPadding + 1 + pointWidth,
                        y0,
                        getWidth() - padding,
                        y1);
                g2.setColor(Color.BLACK);
                String yLabel =
                        String.format("%.2f", getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(
                        yLabel,
                        x0 - labelWidth - 5,
                        y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // Draw X-axis grid lines and labels
        for (int i = 0; i < scores.size(); i++) {
            if (scores.size() > 1) {
                int x0 =
                        i * (getWidth() - padding * 2 - labelPadding) / (scores.size() - 1)
                                + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((scores.size() / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = String.valueOf(i);
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(
                            xLabel,
                            x0 - labelWidth / 2,
                            y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        // Draw axes
        g2.drawLine(
                padding + labelPadding,
                getHeight() - padding - labelPadding,
                padding + labelPadding,
                padding);
        g2.drawLine(
                padding + labelPadding,
                getHeight() - padding - labelPadding,
                getWidth() - padding,
                getHeight() - padding - labelPadding);

        // Draw the line connecting data points
        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        // Draw data points
        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (Point point : graphPoints) {
            int x = point.x - pointWidth / 2;
            int y = point.y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }

    private double getMinScore() {
        double minScore = Double.MAX_VALUE;
        for (Double score : scores) {
            minScore = Math.min(minScore, score);
        }
        return minScore;
    }

    private double getMaxScore() {
        double maxScore = Double.MIN_VALUE;
        for (Double score : scores) {
            maxScore = Math.max(maxScore, score);
        }
        return maxScore;
    }

    private static void createAndShowGui() {
        ArrayList<Double> scores = new ArrayList<>();
        // Sample data points
        scores.add(1.0);
        scores.add(4.0);
        scores.add(3.0);
        scores.add(5.0);
        scores.add(7.0);
        scores.add(6.0);
        scores.add(8.0);
        scores.add(7.0);
        scores.add(9.0);
        scores.add(10.0);

        Main mainPanel = new Main(scores);
        mainPanel.setPreferredSize(new Dimension(800, 600));

        JFrame frame = new JFrame("Line Graph Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGui);
    }
}
