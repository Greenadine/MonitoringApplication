package com.nerdygadgets.application.util;

import java.awt.*;

public final class Fonts {

    private static final Font DEFAULT = new Font(Font.DIALOG, Font.PLAIN, 12);

    /* Main fonts  */
    public static final Font MAIN_TITLE = DEFAULT.deriveFont(20f);
    public static final Font MAIN_BUTTON = DEFAULT.deriveFont(14f);

    public static final Font MAIN_HEADER_TITLE = DEFAULT.deriveFont(Font.BOLD, 20f);

    public static final Font MAIN_SIDEBAR_HEADER = DEFAULT.deriveFont(Font.BOLD, 14f);
    public static final Font MAIN_SIDEBAR_PARAGRAPH = DEFAULT.deriveFont(16f);

    public static final Font MAIN_TABLE_TITLE = DEFAULT.deriveFont(Font.BOLD, 14f);
    public static final Font MAIN_TABLE_PARAGRAPH = DEFAULT.deriveFont(14f);

    /* New/view network configuration screen fonts */
    public static final Font NETWORK_CONFIGURATION_HEADER = DEFAULT.deriveFont(Font.BOLD, 16f);

    /* System monitor fonts */
    public static final Font MONITOR_TITLE = DEFAULT.deriveFont(Font.BOLD, 18f);
    public static final Font MONITOR_SUBTITLE = DEFAULT.deriveFont(Font.BOLD, 16f);
    public static final Font MONITOR_LABEL = DEFAULT.deriveFont(14f);
    public static final Font MONITOR_LABEL_BOLD = MONITOR_LABEL.deriveFont(Font.BOLD);

    public static final Font MONITOR_TABLE_HEADER = DEFAULT.deriveFont(Font.BOLD, 14f);
    public static final Font MONITOR_TABLE_CONTENT = DEFAULT.deriveFont(14f);
}