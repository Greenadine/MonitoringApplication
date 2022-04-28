package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.screen.ApplicationScreen;
import com.nerdygadgets.application.util.ApplicationActions;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.Fonts;
import com.nerdygadgets.application.util.SwingUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * A {@link JPanel} that is commonly used in screens throughout the application as a header, with a {@link JButton} for returning to the home screen.
 *
 * @author Kevin Zuman
 */
public class ScreenHeader extends ApplicationPanel {

    public ScreenHeader(@NotNull ApplicationScreen screen, @NotNull final String screenName, final int width, final int height) {
        super(screen);

        // Configure panel
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(Colors.MAIN_HEADER);
        this.setBorder(new MatteBorder(0, 0, 5, 0, Colors.MAIN_HEADER_ACCENT));
        this.setPreferredSize(new Dimension(width, height));

        createHomeButton(); // Create and add home button
        SwingUtils.addVerticalSeparator(this);  // Add separator
        createTitle(screenName, width);
    }

    private void createHomeButton() {
        JButton homeButton = new JButton("Home");
        homeButton.addActionListener(ApplicationActions::openHome);
        SwingUtils.setButtonIcon(homeButton, "/assets/icons/home.png");
        homeButton.setBackground(Colors.MAIN_ACCENT);
        homeButton.setForeground(Color.WHITE);
        homeButton.setFont(Fonts.MAIN_BUTTON);
        homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeButton.setIconTextGap(15);
        homeButton.setBorder(new EmptyBorder(15, 10, 15, 10));
        this.add(homeButton);
    }

    private void createTitle(@NotNull final String screenName, final int width) {
        int i = width / 2 - 6 * screenName.length();

        JLabel titleLabel = new JLabel(screenName);
        titleLabel.setFont(Fonts.MAIN_HEADER_TITLE);
        titleLabel.setAlignmentX(LEFT_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(5,5, 5, i));
        this.add(titleLabel);
    }

    @Override
    public void onDisplay() {
        // Do nothing
    }

    @Override
    public void onHide() {
        // Do nothing
    }
}
