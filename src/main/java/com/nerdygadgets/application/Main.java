package com.nerdygadgets.application;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import com.nerdygadgets.application.app.frame.MainFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Attempt to set application theme
        try {
            UIManager.setLookAndFeel(new FlatOneDarkIJTheme());
        } catch (Exception ex) {
            System.out.println("Failed to set application theme to dark.");
            ex.printStackTrace();
        }

        new MainFrame();
    }
}
