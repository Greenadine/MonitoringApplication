package com.nerdygadgets.application.util;

import java.awt.*;

public final class Utils {

    public static void setSizeAll(Component component, Dimension dimension) {
        component.setPreferredSize(dimension);
        component.setMinimumSize(dimension);
        component.setMaximumSize(dimension);
    }

    public static void setSizeMinMax(Component component, Dimension dimension) {
        component.setMinimumSize(dimension);
        component.setMaximumSize(dimension);
    }
}
