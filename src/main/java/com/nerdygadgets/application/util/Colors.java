package com.nerdygadgets.application.util;

import java.awt.*;

public final class Colors {

    /* Main application colors */
    public static final Color MAIN_ACCENT = Color.decode("#3572A0");
    public static final Color MAIN_BACKGROUND = Color.decode("#3D424B");
    public static final Color MAIN_BACKGROUND_ACCENT = Color.decode("#282D33");
    public static final Color MAIN_TABLE_HEADER = Color.decode("#2E343A");
    public static final Color MAIN_TABLE_CONTENT = Color.decode("#23272D");

    public static final Color MAIN_HEADER = MAIN_BACKGROUND;
    public static final Color MAIN_HEADER_ACCENT = MAIN_BACKGROUND_ACCENT;

    /* Monitoring panel colors */
    public static final Color MONITOR_BACKGROUND = MAIN_BACKGROUND;
    public static final Color MONITOR_BACKGROUND_ACCENT = MAIN_BACKGROUND_ACCENT;

    public static final Color MONITOR_VALUE_LABEL = Color.decode("#2E343A");
    public static final Color MONITOR_VALUE_CONTENT = Color.decode("#23272D");

    public static final Color MONITOR_TABLE_HEADER = MONITOR_VALUE_LABEL;
    public static final Color MONITOR_TABLE_CONTENT = MONITOR_VALUE_CONTENT;

    /* Graph default colors */
    public static final Color GRAPH_LINE = MAIN_ACCENT;
    public static final Color GRAPH_POINT = MAIN_ACCENT;
    public static final Color GRAPH_GRID = MAIN_BACKGROUND;
    public static final Color GRAPH_X_AXIS = Color.WHITE;
    public static final Color GRAPH_Y_AXIS = Color.WHITE;
    public static final Color GRAPH_BACKGROUND = MAIN_BACKGROUND_ACCENT;
}
