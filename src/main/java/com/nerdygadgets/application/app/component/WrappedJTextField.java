package com.nerdygadgets.application.app.component;

import javax.swing.*;
import java.awt.*;

public class WrappedJTextField extends JPanel {

    private final JTextField textField;

    public WrappedJTextField() {
        // Configure panel
        this.setLayout(new BorderLayout());

        this.textField = new JTextField();

        this.add(textField, BorderLayout.CENTER);
    }

    public JTextField getTextField() {
        return textField;
    }
}
