package com.nerdygadgets.application;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import com.nerdygadgets.application.app.ApplicationWindow;
import com.nerdygadgets.application.data.NetworkComponents;
import com.nerdygadgets.application.exception.DatabaseException;
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
            ApplicationUtils.showPopupErrorMessage("Failed to reach database", "This could be due to connection issues, or due to technical issues with the database. Please try again later.");
        }
        catch (Exception ex) {
            ApplicationUtils.showPopupErrorMessage("An unexpected error occurred", "Please try again later. If this issue persists, please report this issue to management.");
            System.out.println("An error occurred when opening the application.");
            ex.printStackTrace();

            try {
                applicationWindow.getHomeScreen().open();
            } catch (Exception ex1) {
                ApplicationUtils.showPopupErrorMessage("Fatal error", "An unknown issue is preventing the application from funcioning. Please report this issue to management.");
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
