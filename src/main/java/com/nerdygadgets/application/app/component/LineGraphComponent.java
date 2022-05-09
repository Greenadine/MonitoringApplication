package com.nerdygadgets.application.app.component;

import com.nerdygadgets.application.app.model.PanelComponent;
import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.exception.DisplayComponentException;
import com.nerdygadgets.application.util.Colors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A customizable {@link JComponent} for displaying a simple line graph.
 *
 * @author Kevin Zuman
 */
public class LineGraphComponent extends PanelComponent {

    private List<Double> values; // Values to be displayed in the graph

    // Graph settings
    private boolean displayXAxis = true;
    private boolean displayYAxis = true;
    private boolean drawPoints = true;
    private int yAxisDivisions = 10; // Amount of points displayed on the Y-axis of the grid
    private int maxPoints = 10; // Maximum points (values) to be displayed on the grid (maximum amount of points on the x-axis)
    private Integer minValue; // Minimum value displayed on the Y-axis of the grid, set to lowest value when null
    private Integer maxValue; // Maximum value displayed on the Y-axis of the grid, set to highest value when null
    private int pointDiameter = 3;
    private int lineWidth = 4;
    private int padding = 25; // Padding between the panel borders and the actual graph itself
    private int labelPadding = 25; // Padding between the grid labels and division lines of the grid
    private String xAxisUnit; // Unit displayed on the X-axis
    private String yAxisUnit; // Unit displayed on the Y-axis
    private boolean clearOnHide = false; // Whether or not to clear all existing values of the graph when hiding the panel
    private boolean resetOnHide = false; // Whether or not to reset all values of the graph when hiding the panel

    // Colors
    private Color lineColor = Colors.GRAPH_LINE;
    private Color pointColor = Colors.GRAPH_POINT;
    private Color gridColor = Colors.GRAPH_GRID;
    private Color xAxisColor = Colors.GRAPH_X_AXIS;
    private Color yAxisColor = Colors.GRAPH_Y_AXIS;
    private Color gridBackgroundColor = Colors.GRAPH_BACKGROUND;

    public LineGraphComponent(@NotNull final ApplicationPanel parentPanel, final int width, final int height) {
        super(parentPanel);
        this.values = new ArrayList<>(maxPoints);
        this.setPreferredSize(new Dimension(width, height)); // Set panel size
        this.appendValue(0); // Append initial value to instantly draw grid and axes
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (!(graphics instanceof Graphics2D)) {
            throw new DisplayComponentException("Graphics is not Graphics2D, unable to render graph.");
        }

        final Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Frequently used variables
        final int length = values.size();
        final int width = getWidth();
        final int height = getHeight();
        final int minValue = this.minValue != null ? this.minValue : (int) Math.floor(values.stream().min(Double::compareTo).orElse(0d));
        final int maxValue = this.maxValue != null ? this.maxValue : (int) Math.floor(values.stream().max(Double::compareTo).orElse(0d));
        final int valueRange = maxValue - minValue;

        // Determine scales for both axes
        final double xScale;
        if (displayYAxis) {
            xScale = ((double) width - (2 * padding) - labelPadding) / (length - 1);
        } else {
            xScale = ((double) width - (2 * padding)) / (length - 1);
        }

        final double yScale;
        if (displayXAxis) {
            yScale = ((double) height - 2 * padding - labelPadding) / valueRange;
        } else {
            yScale = ((double) height - 2 * padding) / valueRange;
        }

        // Create points for each value
        final List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            final int x = (int) (i * xScale + padding + labelPadding);
            final int y = (int) ((maxValue - values.get(i)) * yScale + padding);
            graphPoints.add(new Point(x, y));
        }

        // Draw grid background
        g2d.setColor(gridBackgroundColor);

        if (displayXAxis && displayYAxis) {
            g2d.fillRect(padding + labelPadding, padding, width - (2 * padding) - labelPadding, height - 2 * padding - labelPadding);
        }
        else if (displayXAxis) {
            g2d.fillRect(padding + labelPadding, padding, width - (2 * padding), height - 2 * padding - labelPadding);
        }
        else if (displayYAxis) {
            g2d.fillRect(padding + labelPadding, padding, width - (2 * padding) - labelPadding, height - 2 * padding);
        }

        final FontMetrics fontMetrics = g2d.getFontMetrics();
        final int fontHeight = fontMetrics.getHeight();

        // Draw the X-axis
        if (displayXAxis) {
            // Draw the line along the X-axis
            g2d.setColor(xAxisColor);
            if (displayYAxis) {
                g2d.drawLine(padding + labelPadding, height - padding - labelPadding, width - padding, height - padding - labelPadding);
            } else {
                g2d.drawLine(padding + labelPadding, height - padding, width - padding, height - padding);
            }

            // Create grid lines and hatch marks for the X-axis
            for (int i = 0; i < values.size(); i++) {
                if (length > 1) {
                    final int x;
                    if (displayYAxis) {
                        x = i * (width - padding * 2 - labelPadding) / (length - 1) + padding + labelPadding;
                    } else {
                        x = i * (width - padding * 2) / (length - 1) + padding;
                    }

                    final int y1 = height - padding - labelPadding;
                    final int y2 = y1 - pointDiameter;

                    if ((i % (((length / 20)) + 1)) == 0) {
                        // Draw grid line
                        g2d.setColor(gridColor);
                        g2d.drawLine(x, height - padding - labelPadding - 1 - pointDiameter, x, padding);

                        // Draw axis hatch mark label
                        g2d.setColor(xAxisColor);
                        final String xLabel = i + "";

                        final int labelWidth = fontMetrics.stringWidth(xLabel);
                        g2d.drawString(xLabel, x - labelWidth / 2, y1 + fontHeight + 3);
                    }

                    if (i == maxPoints - 1 && xAxisUnit != null && xAxisUnit.length() > 0) {
                        final int labelWidth = fontMetrics.stringWidth(xAxisUnit);
                        g2d.drawString(xAxisUnit, x - labelWidth / 2 - 10, y1 + fontHeight + 3 + 20);
                    }

                    // Draw axis hatch mark line
                    g2d.drawLine(x, y1, x, y2);
                }
            }
        }

        // Draw the Y-axis
        if (displayYAxis) {
            // Draw the line along the Y-axis
            g2d.setColor(yAxisColor);
            if (displayXAxis) {
                g2d.drawLine(padding + labelPadding, height - padding - labelPadding, padding + labelPadding, padding);
            } else {
                g2d.drawLine(padding + labelPadding, height - padding, padding + labelPadding, padding);
            }

            for (int i = 0; i < yAxisDivisions + 1; i++) {
                final int x1 = padding + labelPadding;
                final int x2 = pointDiameter + padding + labelPadding;

                final int y;
                if (displayXAxis) {
                    y = height - ((i * (height - padding * 2 - labelPadding)) / yAxisDivisions + padding + labelPadding);
                } else {
                    y = height - ((i * (height - padding * 2)) / yAxisDivisions + padding);
                }

                if (values.size() > 0) {
                    // Draw grid line
                    g2d.setColor(gridColor);
                    g2d.drawLine(padding + labelPadding + 1 + pointDiameter, y, width - padding, y);

                    // Draw axis hatch mark label
                    g2d.setColor(yAxisColor);

                    String yLabel = "";
                    if (i == yAxisDivisions && yAxisUnit != null && yAxisUnit.length() > 0) {
                        yLabel += yAxisUnit + " ";
                    }
                    yLabel += String.valueOf(Math.floor(((minValue + (maxValue - minValue) * ((i * 1d) / yAxisDivisions)) * 100)) / 100d).split("\\.")[0];

                    final int labelWidth = fontMetrics.stringWidth(yLabel);
                    g2d.drawString(yLabel, x1 - labelWidth - 5, y + (fontHeight / 2) - 3);
                }

                // Draw axis hatch mark
                g2d.drawLine(x1, y, x2, y);
            }
        }

        // Draw dots on each point
        if (drawPoints) {
            g2d.setColor(pointColor);

            for (int i = 0; i < graphPoints.size(); i++) {
                if (i == 0) {
                    continue; // Do not draw a dot on the point that's on the Y-axis line (X = 0)
                }

                Point graphPoint = graphPoints.get(i);
                final int x = graphPoint.x - pointDiameter / 2;
                final int y = graphPoint.y - pointDiameter / 2;

                final Ellipse2D.Double circle = new Ellipse2D.Double(x, y, pointDiameter, pointDiameter);
                g2d.fill(circle);
            }
        }

        // Draw lines between all points
        g2d.setColor(lineColor);

        for (int i = 0; i < graphPoints.size() - 1; i++) {
            final int x1 = graphPoints.get(i).x;
            final int y1 = graphPoints.get(i).y;
            final int x2 = graphPoints.get(i + 1).x;
            final int y2 = graphPoints.get(i + 1).y;
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * Set whether the X-axis should be displayed on the graph.
     * <p>Default: {@code true}.
     *
     * @param displayXAxis {@code true} to display the X-axis on the graph,
     *                     {@code false} to not display the X-axis on the graph.
     */
    public void setDisplayXAxis(final boolean displayXAxis) {
        final boolean oldValue = this.displayXAxis;
        this.displayXAxis = displayXAxis;
        if (displayXAxis != oldValue) {
            repaint();
        }
    }

    /**
     * Sets whether the Y-axis should drawn on the graph.
     * <p>Default: {@code true}.
     *
     * @param displayYAxis {@code true} to draw the Y-axis on the graph,
     *                     {@code false} to not draw the Y-axis on the graph.
     */
    public void setDisplayYAxis(final boolean displayYAxis) {
        final boolean oldValue = this.displayYAxis;
        this.displayYAxis = displayYAxis;
        if (displayYAxis != oldValue) {
            repaint();
        }
    }

    /**
     * Sets whether to draw points on the graph for each value.
     * <p>Default: {@code true}.
     *
     * @param drawPoints {@code true} to draw points on the graph for each value,
     *                   {@code false} to not draw points on the graph for each value.
     */
    public void setDrawPoints(final boolean drawPoints) {
        final boolean oldValue = this.drawPoints;
        this.drawPoints = drawPoints;
        if (drawPoints != oldValue) {
            repaint();
        }
    }

    /**
     * Sets the amount of divisions over the Y-axis of the graph grid.
     * <p>Default: {@code 10}.
     *
     * @param yAxisDivisions The amount of divisions over the Y-axis.
     */
    public void setYAxisDivisions(final int yAxisDivisions) {
        final int oldValue = this.yAxisDivisions;
        this.yAxisDivisions = yAxisDivisions;
        if (yAxisDivisions != oldValue) {
            repaint();
        }
    }

    /**
     * Sets the maximum amount of points (values) to be displayed in the graph.
     * <p>Default: {@code 10}.
     *
     * <P>This value essentially equates to the (maximum) amount of divisions over the X-axis of the graph grid.</P>
     *
     * @param maxPoints The maximum amount of points (values) to display in the graph.
     */
    public void setMaxPoints(final int maxPoints) {
        final int oldValue = this.maxPoints;
        this.maxPoints = maxPoints;
        if (maxPoints != oldValue) {
            repaint();
        }
    }

    /**
     * Sets the minimum value (over the Y-axis) to display on the graph.
     * <p>Defaults to the lowest value when {@code null}.
     *
     * @param minValue The minimum value (over the Y-axis) to display on the graph.
     */
    public void setMinValue(final int minValue) {
        final int oldValue = this.minValue != null ? this.minValue : -1;
        this.minValue = minValue;
        if (minValue != oldValue) {
            repaint();
        }
    }

    /**
     * Sets the maximum value (over the Y-axis) to display on the graph.
     * <p>Defaults to the highest value when {@code null}.
     *
     * @param maxValue The maximum value (over the Y-axis) to display on the graph.
     */
    public void setMaxValue(final int maxValue) {
        final int oldValue = this.maxValue != null ? this.maxValue : -1;
        this.maxValue = maxValue;
        if (maxValue != oldValue) {
            repaint();
        }
    }

    /**
     * Sets the size of the points drawn for each value in the graph.
     * <p>Default: {@code 4}.
     *
     * @param pointDiameter The size of the point for each value.
     */
    public void setPointDiameter(final int pointDiameter) {
        final int oldValue = this.pointDiameter;
        this.pointDiameter = pointDiameter;
        if (pointDiameter != oldValue) {
            repaint();
        }
    }

    /**
     * Sets the width of the line drawn between each point in the graph.
     * <p>Default: {@code 4}.
     *
     * @param lineWidth The width of the line between each point.
     */
    public void setLineWidth(final int lineWidth) {
        final int oldValue = this.lineWidth;
        this.lineWidth = lineWidth;
        if (lineWidth != oldValue) {
            repaint();
        }
    }

    /**
     * Sets the padding for the entire panel.
     * <p>Default: {@code 25}.
     *
     * @param padding The padding for the entire panel (in pixels).
     */
    public void setPadding(final int padding) {
        final int oldValue = this.padding;
        this.padding = padding;
        if (padding != oldValue) {
            repaint();
        }
    }

    /**
     * Sets the padding between the labels on the X- and Y-axis and the borders of the panel.
     * <p>Default: {@code 25}.
     *
     * @param labelPadding The distance between the labels on the axes and the borders of the panel (in pixels).
     */
    public void setLabelPadding(final int labelPadding) {
        final int oldValue = this.labelPadding;
        this.labelPadding = labelPadding;
        if (labelPadding != oldValue) {
            repaint();
        }
    }

    /**
     * Sets the unit to be displayed along the X-axis.
     *
     * @param xAxisUnit The X-axis unit.
     */
    public void setXAxisUnit(@Nullable final String xAxisUnit) {
        final String oldValue = this.xAxisUnit;
        this.xAxisUnit = xAxisUnit;
        if (!Objects.equals(xAxisUnit, oldValue)) {
            repaint();
        }
    }

    /**
     * Sets the unit to be displayed along the Y-axis.
     *
     * @param yAxisUnit The Y-axis unit.
     */
    public void setYAxisUnit(@Nullable final String yAxisUnit) {
        final String oldValue = this.yAxisUnit;
        this.yAxisUnit = yAxisUnit;
        if (!Objects.equals(yAxisUnit, oldValue)) {
            repaint();
        }
    }

    /**
     * Sets whether to clear all existing values of the graph when hiding the panel
     * <p>Default: {@code false}.
     *
     * <p>Note: Can not work in conjunction with {@link #setResetOnHide(boolean)}.
     * This value is prioritized over the value of {@link #setResetOnHide(boolean)}.
     *
     * @param clearOnHide {@code true} to clear all the values of the graph when hiding the panel,
     *                    {@code false} to keep the values of the graph after hiding the panel.
     */
    public void setClearOnHide(final boolean clearOnHide) {
        this.clearOnHide = clearOnHide;
    }

    /**
     * Sets whether to reset all values of the graph when hiding the panel.
     * <p>Default: {@code false}.
     *
     * <p>Note: Can not work in conjunction with {@link #setClearOnHide(boolean)}.
     * The value of {@link #setClearOnHide(boolean)} is prioritized over this value.
     *
     * @param resetOnHide {@code true} to reset all the values of the graph when hiding the panel, {@code false} to keep the values of the graph after hiding the panel.
     */
    public void setResetOnHide(final boolean resetOnHide) {
        this.resetOnHide = resetOnHide;
        resetValues();
    }

    /**
     * Sets the {@link Color} of the line between the points in the graph.
     *
     * @param lineColor The {@code Color} of the graph line.
     */
    public void setLineColor(@Nullable final Color lineColor) {
        final Color oldValue = this.lineColor;
        this.lineColor = lineColor;
        if (lineColor != oldValue) {
            repaint();
        }
    }

    /**
     * Sets the {@link Color} of the points on in the graph.
     *
     * @param pointColor The {@code Color} of the graph points.
     */
    public void setPointColor(@NotNull final Color pointColor) {
        final Color oldValue = this.pointColor;
        this.pointColor = pointColor;
        if (pointColor != oldValue) {
            repaint();
        }
    }

    /**
     * Sets the {@link Color} of the grid for the graph.
     *
     * @param gridColor The {@code Color} of the graph grid.
     */
    public void setGridColor(@NotNull final Color gridColor) {
        final Color oldValue = this.gridColor;
        this.gridColor = gridColor;
        if (gridColor != oldValue) {
            repaint();
        }
    }

    /**
     * Sets the {@link Color} of the labels and the division lines on the X-axis of the grid.
     *
     * @param xAxisColor The {@code Color} of the labels and division lines on the X-axis.
     */
    public void setXAxisColor(@NotNull final Color xAxisColor) {
        final Color oldValue = this.xAxisColor;
        this.xAxisColor = xAxisColor;
        if (xAxisColor != oldValue) {
            repaint();
        }
    }

    /**
     * Sets the {@link Color} of the labels and the division lines on the Y-axis of the grid.
     *
     * @param yAxisColor The {@code Color} of the labels and division lines on the Y-axis.
     */
    public void setYAxisColor(@NotNull final Color yAxisColor) {
        final Color oldValue = this.yAxisColor;
        this.yAxisColor = yAxisColor;
        if (yAxisColor != oldValue) {
            repaint();
        }
    }

    /**
     * Sets the {@link Color} of the background within the graph grid.
     *
     * @param gridBackgroundColor The {@code Color} of the grid background.
     */
    public void setGridBackground(@NotNull final Color gridBackgroundColor) {
        final Color oldValue = this.gridBackgroundColor;
        this.gridBackgroundColor = gridBackgroundColor;
        if (gridBackgroundColor != oldValue) {
            repaint();
        }
    }

    /**
     * Sets the values to be displayed on the graph (over the X-axis).
     *
     * @param values A {@code List} containing the values to be displayed.
     */
    public void setValues(@NotNull final List<Double> values) {
        this.values = values;
        repaint();
    }

    /**
     * Appends the given value as last entry of the values.
     * <p>If the maximum amount of points are exceeded as a result of appending this value,
     * the first value in the {@code List} will be removed, thus not exceeding the maximum values.
     *
     * @param value The value to append.
     */
    public void appendValue(final double value) {
        values.add(value);
        while(values.size() > maxPoints) {
            values.remove(0);
        }
        repaint();
    }

    /**
     * Clears all current values, and fills the graph with the maximum amount of points, each equal to the minimum value ({@code minValue}).
     */
    public void resetValues() {
        final List<Double> values = new ArrayList<>();
        for (int i = 0; i < maxPoints; i++) {
            values.add(0d);
        }
        setValues(values);
    }

    /**
     * Clears all current values.
     */
    public void clearValues() {
        values.clear();
        appendValue(0);
    }

    @Override
    public void onShow() {
        // Do nothing
    }

    @Override
    public void onHide() {
        if (clearOnHide) {
            clearValues();
        }
        else if (resetOnHide) {
            resetValues();
        }
    }
}
