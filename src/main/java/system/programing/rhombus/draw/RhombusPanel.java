package system.programing.rhombus.draw;

import system.programing.semantic.analyzer.Rhombus;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RhombusPanel extends JPanel {
    private Rhombus rhombus;
    List<String> vertexLabels;

    public RhombusPanel(Rhombus rhombus) {
        this.rhombus = rhombus;
        vertexLabels = getVertexLabels();
        this.setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawStaticRhombus((Graphics2D) g);
    }

    private void drawStaticRhombus(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font largerFont = new Font("Arial", Font.BOLD, 18);
        g2d.setFont(largerFont);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int sizeX = 300;
        int sizeY = 200;

        int[] xPoints = {
                centerX,
                centerX + sizeX,
                centerX,
                centerX - sizeX
        };
        int[] yPoints = {
                centerY - sizeY,
                centerY,
                centerY + sizeY,
                centerY
        };

        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawPolygon(xPoints, yPoints, 4);

        g2d.setColor(Color.BLACK);
        FontMetrics fm = g2d.getFontMetrics();
        for (int i = 0; i < 4; i++) {
            String label = vertexLabels.get(i);
            int labelWidth = fm.stringWidth(label);
            int labelHeight = fm.getHeight();

            int offsetX = 10;
            int offsetY = 10;

            switch (i) {
                case 0:
                    g2d.drawString(label, xPoints[i] - labelWidth / 2, yPoints[i] - offsetY);
                    break;
                case 1:
                    g2d.drawString(label, xPoints[i] + offsetX, yPoints[i] + labelHeight / 2);
                    break;
                case 2:
                    g2d.drawString(label, xPoints[i] - labelWidth / 2, yPoints[i] + labelHeight + offsetY);
                    break;
                case 3:
                    g2d.drawString(label, xPoints[i] - labelWidth - offsetX, yPoints[i] + labelHeight / 2);
                    break;
            }
        }

        if (rhombus.getSide() != null) {
            g2d.drawString(String.format("%.2f", rhombus.getSide()), 550, 190);
        }

        boolean hasDiagonal1 = rhombus.getDiagonal1() != null;
        boolean hasDiagonal2 = rhombus.getDiagonal2() != null;

        if (hasDiagonal1 || hasDiagonal2) {

            Double diagonal1 = hasDiagonal1 ? rhombus.getDiagonal1() : 0;
            Double diagonal2 = hasDiagonal2 ? rhombus.getDiagonal2() : 0;

            Double d1 = Math.max(diagonal1, diagonal2);
            Double d2 = Math.min(diagonal1, diagonal2);

            drawDiagonals(g2d, xPoints, yPoints, d1, d2);
        }

        if (rhombus.getHeightValue() != null && rhombus.getHeightValue() > 0) {
            drawHeight(g2d, xPoints, yPoints, rhombus.getHeightValue(), rhombus.getHeightName());
        }

        drawHalfDiagonals(g2d, xPoints, yPoints, centerX, centerY);

        displayAreaAndPerimeter(g2d, fm);

        displayAngles(g2d, xPoints, yPoints);

        drawCenter(g2d);
    }

    private List<String> getVertexLabels() {
        List<String> defaultLabels = List.of("A", "B", "C", "D");
        String identifier = rhombus.getIdentifier();

        if (identifier != null && identifier.length() == 4 && !identifier.equals("NULL")) {
            List<String> labels = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                labels.add(String.valueOf(identifier.charAt(i)));
            }
            return labels;
        } else {
            return defaultLabels;
        }
    }

    private void drawDiagonals(Graphics2D g2d, int[] xPoints, int[] yPoints, Double d1, Double d2) {
        g2d.setColor(Color.ORANGE);
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
                new float[]{5, 5}, 0));

        g2d.drawLine(xPoints[0], yPoints[0], xPoints[2], yPoints[2]);
        if (d1 != null && d1 != 0) {
            g2d.drawString(String.format("%.2f", d1 / 2), 250, 290);
            g2d.drawString(String.format("%.2f", d1 / 2), 500, 290);
        }

        g2d.drawLine(xPoints[1], yPoints[1], xPoints[3], yPoints[3]);
        if (d2 != null && d2 != 0) {
            g2d.drawString(String.format("%.2f", d2 / 2), 410, 200);
            g2d.drawString(String.format("%.2f", d2 / 2), 410, 400);
        }
    }

    private void drawHalfDiagonals(Graphics2D g2d, int[] xPoints, int[] yPoints, int centerX, int centerY) {
        FontMetrics fm = g2d.getFontMetrics();

        // Малюємо Першу половину діагоналі (AO та OC), якщо вона задана
        if (rhombus.getHalfDiagonal1Name() != null && rhombus.getHalfDiagonal1Value() != null) {
            g2d.setColor(Color.ORANGE);
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
                    new float[]{4, 4}, 0));


            g2d.drawLine(centerX, centerY, xPoints[0], yPoints[0]);

            g2d.drawLine(centerX, centerY, xPoints[2], yPoints[2]);

            Double hd1Value = rhombus.getHalfDiagonal1Value();

            g2d.setColor(Color.BLACK);
            g2d.drawString(String.format("%.2f", hd1Value), 250, 290);
            g2d.drawString(String.format("%.2f", hd1Value), 500, 290);
        }

        if (rhombus.getHalfDiagonal2Name() != null && rhombus.getHalfDiagonal2Value() != null) {
            g2d.setColor(Color.ORANGE);
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
                    new float[]{4, 4}, 0));


            g2d.drawLine(centerX, centerY, xPoints[1], yPoints[1]);

            g2d.drawLine(centerX, centerY, xPoints[3], yPoints[3]);

            Double hd2Value = rhombus.getHalfDiagonal2Value();
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.format("%.2f", hd2Value), 410, 200);
            g2d.drawString(String.format("%.2f", hd2Value), 410, 400);
        }
    }

    private String getIntersectionName() {
        if (rhombus.getHalfDiagonal1Name() != null) {
            return getName(rhombus.getHalfDiagonal1Name());
        }

        if (rhombus.getHalfDiagonal2Name() != null) {
            return getName(rhombus.getHalfDiagonal2Name());
        }

        return "O";
    }

    private void drawHeight(Graphics2D g2d, int[] xPoints, int[] yPoints, double height, String name) {
        int xTop = xPoints[0];
        int yTop = yPoints[0];

        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
                new float[]{10, 10}, 0));
        g2d.drawLine(xTop, yTop, 200, 370);

        g2d.setColor(Color.MAGENTA);
        g2d.drawString(String.format("%.2f", height), 250, 250);

        g2d.setColor(Color.BLACK);
        if (name != null && !name.equals("NULL")) {
            g2d.drawString(getName(name), 195, 260);
        } else {
            g2d.drawString("H", 190, 385);
        }
    }

    private void displayAreaAndPerimeter(Graphics2D g2d, FontMetrics fm) {
        int paddingRight = 20;
        int paddingTop = 20;
        int lineHeight = fm.getHeight();

        if (rhombus.getPerimeter() != null) {
            String perimeterText = String.format("Perimeter: %.2f", rhombus.getPerimeter());
            int x = getWidth() - fm.stringWidth(perimeterText) - paddingRight;
            int y = paddingTop + lineHeight;
            g2d.setColor(Color.BLACK);
            g2d.drawString(perimeterText, x, y);
        }

        if (rhombus.getArea() != null) {
            String areaText = String.format("Area: %.2f", rhombus.getArea());
            int x = getWidth() - fm.stringWidth(areaText) - paddingRight;
            int y = paddingTop + 2 * lineHeight;
            g2d.setColor(Color.BLACK);
            g2d.drawString(areaText, x, y);
        }
    }

    private void displayAngles(Graphics2D g2d, int[] xPoints, int[] yPoints) {
        if (rhombus.getAngleA() == null || rhombus.getAngleB() == null) {
            return;
        }

        Font angleFont = new Font("Arial", Font.BOLD, 16);
        g2d.setFont(angleFont);
        FontMetrics angleFm = g2d.getFontMetrics();
        g2d.setColor(Color.MAGENTA);

        double angleA = rhombus.getAngleA();
        double angleB = rhombus.getAngleB();

        String angleALabel = String.format("%.2f°", angleB);
        int angleALabelWidth = angleFm.stringWidth(angleALabel);
        int angleALabelX = xPoints[0] - angleALabelWidth / 2;
        int angleALabelY = yPoints[0] + 30;
        g2d.drawString(angleALabel, angleALabelX, angleALabelY);

        int arcRadius = 30;
        g2d.setStroke(new BasicStroke(2));
        g2d.drawArc(xPoints[0] - (arcRadius / 2), yPoints[0] - (arcRadius / 2), arcRadius, arcRadius, 225, 90);


        String angleALabel2 = String.format("%.2f°", angleB);
        int angleALabelWidth2 = angleFm.stringWidth(angleALabel2);
        int angleALabelX2 = xPoints[2] - angleALabelWidth2 / 2;
        int angleALabelY2 = yPoints[2] - 20;
        g2d.drawString(angleALabel2, angleALabelX2, angleALabelY2);

        g2d.drawArc(xPoints[2] - (arcRadius / 2), yPoints[2] - (arcRadius / 2), arcRadius, arcRadius, 44, 90);


        String angleBLabel = String.format("%.2f°", angleA);
        int angleBLabelX = xPoints[1] - 90;
        int angleBLabelY = yPoints[1] - angleFm.getAscent() / 2;
        g2d.drawString(angleBLabel, angleBLabelX, angleBLabelY);

        g2d.drawArc(xPoints[1] - 30, yPoints[1] - (arcRadius / 2), arcRadius, arcRadius, 110, 135);


        String angleBLabel2 = String.format("%.2f°", angleA);
        int angleBLabelX2 = xPoints[3] + 40;
        int angleBLabelY2 = yPoints[3] + angleFm.getAscent() / 2 - 15;
        g2d.drawString(angleBLabel2, angleBLabelX2, angleBLabelY2);

        g2d.drawArc(xPoints[3] + 5, yPoints[3] - (arcRadius / 2), arcRadius, arcRadius, 290, 135);


        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    private void drawCenter(Graphics2D g2d) {
        if (rhombus.getDiagonal1() != null || rhombus.getDiagonal2() != null || rhombus.getHalfDiagonal1Name() != null || rhombus.getHalfDiagonal2Name() != null) {
            String intersectionName = getIntersectionName();

            if (intersectionName != null) {
                g2d.setColor(Color.BLACK);
                g2d.drawString(intersectionName, 400 + 15, 300 - 20);
            }
        }
    }

    private String getName(String name) {
        String char1 = String.valueOf(name.charAt(0));
        String char2 = String.valueOf(name.charAt(1));

        return vertexLabels.contains(char1) ? char2 : char1;
    }
}