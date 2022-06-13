package com.nerdygadgets.application.util;

import javax.swing.*;

public enum SystemStatus {

    NEUTRAL,
    ONLINE,
    WARNING,
    OFFLINE,
    ;

    public ImageIcon getStatusIcon() {
        return SwingUtils.getIconFromResource(String.format("status-%s.png", name().toLowerCase()));
    }
}
