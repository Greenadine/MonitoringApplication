package com.nerdygadgets.application;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import com.nerdygadgets.application.app.window.MainWindow;
import com.nerdygadgets.application.app.window.SettingsWindow;
import com.nerdygadgets.application.data.NetworkComponents;
import com.nerdygadgets.application.exception.DatabaseException;
import com.nerdygadgets.application.exception.DisplayComponentException;
import com.nerdygadgets.application.util.ApplicationUtils;
import com.nerdygadgets.application.util.Logger;

import javax.swing.*;

public class Main {

    private static NetworkComponents networkComponents;

    public static MainWindow mainWindow;
    public static SettingsWindow settingsWindow;

    public static void main(String[] args) {
        networkComponents = new NetworkComponents();

        // Attempt to set application theme, and create windows
        try {
            UIManager.setLookAndFeel(new FlatOneDarkIJTheme());
            mainWindow = new MainWindow();
            settingsWindow = new SettingsWindow();
        } catch (DatabaseException ex) {
            Logger.error(ex, ex.getMessage());

            ApplicationUtils.showPopupErrorMessage("Failed to reach database", "This could be due to connection issues, invalidly defined database host and credentials, or due to technical issues with the database. " +
                    "Please check the database information defined in the 'database.properties' file.");
        } catch (DisplayComponentException ex) {
            Logger.error(ex, "An exception has occurred when displaying a component.");

            try {
                mainWindow.openScreen("home"); // Attempt to return to home screen
                ApplicationUtils.showPopupErrorMessage("An error has occurred", "There was an error while displaying a panel.\n\nError message: " + ex.getMessage());
            } catch (Exception ex1) {
                ApplicationUtils.showPopupErrorMessage("Fatal error", "An unknown issue is preventing the application from functioning. Please report this issue to management.");
                System.exit(100);
            }
        }
        catch (Exception ex) {
            Logger.error(ex, "An unexpected exception has occurred when opening the application");

            try {
                mainWindow.openScreen("home"); // Attempt to return to home screen
                ApplicationUtils.showPopupErrorMessage("An unexpected error has occurred", "Please try again later. If this issue persists, please report this issue to management.");
            } catch (Exception ex1) {
                ApplicationUtils.showPopupErrorMessage("Fatal error", "An unknown issue is preventing the application from functioning. Please report this issue to management.");
                System.exit(100);
            }
        }
    }

    public static NetworkComponents getNetworkComponents() {
        return networkComponents;
    }
}
