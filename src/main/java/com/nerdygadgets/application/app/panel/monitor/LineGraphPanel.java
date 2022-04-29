package com.nerdygadgets.application.app.panel.monitor;

import com.nerdygadgets.application.app.panel.ApplicationPanel;
import com.nerdygadgets.application.app.screen.ApplicationScreen;
import com.nerdygadgets.application.util.Colors;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LineGraphPanel extends ApplicationPanel {

    private final int padding = 25;
    private final int labelPadding = 25;
    private final Color lineColor = Colors.MAIN_ACCENT;
    private final Color pointColor = Colors.MAIN_ACCENT;
    private final Color gridColor = Colors.MAIN_BACKGROUND;
    private final Color backgroundColor = Colors.MAIN_BACKGROUND_ACCENT;
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private final int pointWidth = 4;
    private final int numberYDivisions = 10;
    private List<Double> scores;

    private final int maxPoints;

    public LineGraphPanel(@NotNull final ApplicationScreen applicationScreen, final int width, final int height, final int maxPoints) {
        super(applicationScreen);
        this.maxPoints = maxPoints;

        this.setPreferredSize(new Dimension(width, height));
        resetScores();
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (scores.size() - 1);
        double yScale = ((double) getHeight() - 2 * padding) / (getMaxScore() - getMinScore());

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < scores.size(); i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getMaxScore() - scores.get(i)) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
        }

        // draw background
        g2.setColor(backgroundColor);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding);
        g2.setColor(gridColor);

        // create hatch marks and grid lines for y axis.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2)) / numberYDivisions + padding);
            if (scores.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y0);
                g2.setColor(Color.WHITE);

                String yLabel;
                if (i == numberYDivisions) {
                    yLabel = "% " + String.valueOf(Math.floor(((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1d) / numberYDivisions)) * 100)) / 100d).split("\\.")[0];
                } else {
                    yLabel = String.valueOf(Math.floor(((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1d) / numberYDivisions)) * 100)) / 100d).split("\\.")[0];
                }

                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y0);
        }

        // create x and y axes
        g2.drawLine(padding + labelPadding, getHeight() - padding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding, getWidth() - padding, getHeight() - padding);

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

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
    }

    private double getMinScore() {
        return 0;
    }

    private double getMaxScore() {
        return 100;
    }

    public void setScores(List<Double> scores) {
        if (scores.size() > maxPoints) {
            throw new IllegalArgumentException("Too many points");
        }
        this.scores = scores;
        invalidate();
        this.repaint();
    }

    public List<Double> getScores() {
        return scores;
    }

    public void appendScore(final double score) {
        if (scores.size() >= maxPoints) {
            scores.remove(0);
        }
        scores.add(score);

        invalidate();
        this.repaint();
    }

    public void resetScores() {
        List<Double> scores = new ArrayList<>();
        for (int i = 0; i < getMaxPoints(); i++) {
            scores.add(0d);
        }
        setScores(scores);
    }

    @Override
    public void onDisplay() {

    }

    @Override
    public void onHide() {

    }
}
