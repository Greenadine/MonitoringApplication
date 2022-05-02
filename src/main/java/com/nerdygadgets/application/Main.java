package com.nerdygadgets.application;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import com.nerdygadgets.application.app.ApplicationWindow;
import com.nerdygadgets.application.data.NetworkComponents;
import com.nerdygadgets.application.exception.DatabaseException;
import com.nerdygadgets.application.exception.DisplayPanelException;
import com.nerdygadgets.application.util.ApplicationUtils;

import javax.swing.*;

public class Main {

    private static NetworkComponents networkComponents;

    private static ApplicationWindow applicationWindow;

    public static void main(String[] args) {
        networkComponents = new NetworkComponents();

        // Attempt to set application theme
        try {
            UIManager.setLookAndFeel(new FlatOneDarkIJTheme());
            applicationWindow = new ApplicationWindow();
        } catch (DatabaseException ex) {
            ApplicationUtils.showPopupErrorMessage("Failed to reach database", "This could be due to connection issues, invalidly defined database host and credentials, or due to technical issues with the database. " +
                    "Please check the database information defined in the 'database.properties' file.");
        } catch (DisplayPanelException ex) {
            ApplicationUtils.showPopupErrorMessage("An error has occurred", "There was an error while displaying a panel.\n\nError message: " + ex.getMessage());

            try {
                applicationWindow.getHomeScreen().open();
            } catch (Exception ex1) {
                ApplicationUtils.showPopupErrorMessage("Fatal error", "An unknown issue is preventing the application from functioning. Please report this issue to management.");
                System.exit(100);
            }
        }
        catch (Exception ex) {
            System.out.println("An error occurred when opening the application.");
            ex.printStackTrace();

            try {
                applicationWindow.getHomeScreen().open();
                ApplicationUtils.showPopupErrorMessage("An unexpected error has occurred", "Please try again later. If this issue persists, please report this issue to management.");
            } catch (Exception ex1) {
                ApplicationUtils.showPopupErrorMessage("Fatal error", "An unknown issue is preventing the application from functioning. Please report this issue to management.");
                System.exit(100);
            }
        }
    }

    public static ApplicationWindow getApplicationFrame() {
        return applicationWindow;
    }

    public static NetworkComponents getNetworkComponents() {
        return networkComponents;
    }
}
