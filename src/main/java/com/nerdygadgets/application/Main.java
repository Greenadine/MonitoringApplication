package com.nerdygadgets.application;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import com.nerdygadgets.application.app.ApplicationFrame;
import com.nerdygadgets.application.data.NetworkComponents;
import com.nerdygadgets.application.util.NetworkMonitoring;

import javax.swing.*;
import java.io.IOException;

public class Main {

    private static NetworkComponents networkComponents;

    private static ApplicationFrame applicationFrame;

    public static void main(String[] args) throws IOException
    {
        // Attempt to set application theme
        try {
            UIManager.setLookAndFeel(new FlatOneDarkIJTheme());
        } catch (Exception ex) {
            System.out.println("Failed to set application theme to dark.");
            ex.printStackTrace();
        }

        networkComponents = new NetworkComponents();
        applicationFrame = new ApplicationFrame();
    }

    public static NetworkComponents getNetworkComponents() {
        return networkComponents;
    }
}
