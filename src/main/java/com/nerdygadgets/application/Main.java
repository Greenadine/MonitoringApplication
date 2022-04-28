package com.nerdygadgets.application;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import com.nerdygadgets.application.app.ApplicationWindow;
import com.nerdygadgets.application.data.NetworkComponents;

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
        } catch (Exception ex) {
            System.out.println("An error occurred when opening the application.");
            ex.printStackTrace();
        }
    }

    public static ApplicationWindow getApplicationFrame() {
        return applicationWindow;
    }

    public static NetworkComponents getNetworkComponents() {
        return networkComponents;
    }
}
