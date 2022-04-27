package com.nerdygadgets.application.util;

import java.awt.*;

public final class Fonts {

    public static final Font DEFAULT = new Font(Font.DIALOG, Font.PLAIN, 14);
    public static final Font PARAGRAPH = DEFAULT.deriveFont(12f);
    public static final Font PARAGRAPH_BIG = DEFAULT.deriveFont(16f);
    public static final Font TITLE = DEFAULT.deriveFont(20f);
}