package com.nerdygadgets.application.util;

import java.awt.*;

public final class Fonts {

    public static final Font DEFAULT = new Font(Font.DIALOG, Font.PLAIN, 14);
    public static final Font TITLE = DEFAULT.deriveFont(20f);

    public static final Font SIDEBAR_HEADER = DEFAULT.deriveFont(Font.BOLD, 16f);
    public static final Font SIDEBAR_CONTENT = DEFAULT.deriveFont(16f);

    public static final Font TABLE_HEADER = DEFAULT.deriveFont(Font.BOLD, 12f);
    public static final Font TABLE_CONTENT = DEFAULT.deriveFont(12f);
}