package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.util.ApplicationActions;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.Fonts;
import com.nerdygadgets.application.util.SwingUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * A {@link JPanel} that is commonly used in screens throughout the application as a header, with a {@link JButton} for returning to the home screen.
 *
 * @author Kevin Zuman
 */
public class ScreenHeaderPanel extends ApplicationPanel {
    private JLabel titleLabel;
    public ScreenHeaderPanel(@NotNull ApplicationScreen screen, @NotNull final String screenName, final int width, final int height, final ActionListener returnAction) {
        super(screen);

        // Configure panel
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(Colors.MAIN_HEADER);
        this.setBorder(new MatteBorder(0, 0, 5, 0, Colors.MAIN_HEADER_ACCENT));
        this.setPreferredSize(new Dimension(width, height));

        createReturnButton(returnAction); // Create and add return button
        SwingUtils.addVerticalSeparator(this); // Add separator
        createTitle(screenName, width);
    }

    private void createReturnButton(ActionListener returnAction) {
        JButton returnButton = new JButton("Return", SwingUtils.getIconFromResource("return.png"));
        returnButton.addActionListener(returnAction);
        returnButton.setBackground(Colors.MAIN_ACCENT);
        returnButton.setForeground(Color.WHITE);
        returnButton.setFont(Fonts.MAIN_BUTTON);
        returnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        returnButton.setIconTextGap(10);
        returnButton.setBorder(new EmptyBorder(15, 10, 15, 15));
        this.add(returnButton);
    }

    private void createTitle(@NotNull final String screenName, final int width) {
        titleLabel = new JLabel(screenName);
        titleLabel.setFont(Fonts.MAIN_HEADER_TITLE);
        titleLabel.setAlignmentX(LEFT_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(5,5, 5, width / 2 - 6 * screenName.length()));
        this.add(titleLabel);
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    @Override
    public void onShowImpl() {
        // Do nothing
    }

    @Override
    public void onHideImpl() {
        // Do nothing
    }
}
