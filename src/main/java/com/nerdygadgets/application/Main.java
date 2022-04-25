package com.nerdygadgets.application;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import com.nerdygadgets.application.app.ApplicationFrame;

import javax.swing.*;

public class Main {

    private static ApplicationFrame applicationFrame;

    public static void main(String[] args) {
        // Attempt to set application theme
        try {
            UIManager.setLookAndFeel(new FlatOneDarkIJTheme());
        } catch (Exception ex) {
            System.out.println("Failed to set application theme to dark.");
            ex.printStackTrace();
        }

        applicationFrame = new ApplicationFrame();
    }
}
