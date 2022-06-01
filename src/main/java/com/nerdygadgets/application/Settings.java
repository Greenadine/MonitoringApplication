package com.nerdygadgets.application;

import com.nerdygadgets.application.app.screen.NetworkMonitorScreen;
import com.nerdygadgets.application.util.Logger;
import com.nerdygadgets.application.util.Utils;

import java.io.*;
import java.util.Properties;

public final class Settings {

    private static final Properties settings;

    static {
        // Create new settings file if required
        if (!(new File("settings.properties")).exists()) {
            try {
                Utils.exportResource("settings.properties");
            } catch (Exception ex) {
                Logger.error(ex, "Failed to create settings file.");
            }
        }

        // Attempt to load settings from file
        settings = new Properties();
        try  {
            settings.load(new FileInputStream("settings.properties"));
        } catch (Exception ex) {
            Logger.error(ex, "Failed to load settings from file.");
        }
    }

    /**
     * Returns whether debug logging should be enabled across the application.
     *
     * @return {@code true} if debug logging should be enabled across the application,
     *         {@code false} if debug logging should be disabled across the application.
     */
    public static boolean isDebugLoggingEnabled() {
        return Boolean.parseBoolean(settings.getProperty("debug-logging", "false"));
    }

    /**
     * Sets whether debug logging should be enabled across the application.
     *
     * @param value {@code true} to enable debug logging across the application,
     *              {@code false} to disable debug logging across the application.
     */
    public static void setDebugLogging(final boolean value) {
        settings.setProperty("debug-logging", String.valueOf(value));
    }

    /**
     * Returns whether the network monitor should keep monitoring the systems' statuses in the background, even when the network monitor panel is not opened.
     *
     * @return {@code true} if the systems should be monitored in the background,
    *          {@code false} if the systems should only be monitored when the network monitor panel is opened.
     */
    public static boolean isBackgroundMonitoringEnabled() {
        return Boolean.parseBoolean(settings.getProperty("background-monitoring", "false"));
    }

    /**
     * Sets whether the network monitor should keep monitoring the systems' statuses in the background, even when the network monitor panel is not opened.
     *
     * @param value {@code true} to keep monitoring the systems in the background,
     *              {@code false} to only monitor the systems when the network monitor panel is opened.
     */
    public static void setBackgroundMonitoring(final boolean value) {
        settings.setProperty("background-monitoring", String.valueOf(value));

        // If background monitoring has been enabled after previously being disabled, start monitoring the systems in the background
/*        if (isBackgroundMonitoringEnabled()) {
            ((NetworkMonitorScreen) Main.mainWindow.getScreen("network-monitor")).startMonitoringSystems();
        } else {
            Main.mainWindow.getCurrentScreen().ifPresent(currentScreen -> {
                if (!(currentScreen instanceof NetworkMonitorScreen)) {
                    ((NetworkMonitorScreen) Main.mainWindow.getScreen("network-monitor")).stopMonitoringSystems();
                }
            });
        }*/

        save();
    }

    /**
     * Saves all the current settings to file.
     */
    public static void save() {
        try {
            settings.store(new FileOutputStream("settings.properties"), null);
        } catch (Exception ex) {
            Logger.error(ex, "Failed to save settings to file.");
        }
    }
}
