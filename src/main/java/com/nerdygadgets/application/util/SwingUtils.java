package com.nerdygadgets.application.util;

import com.nerdygadgets.application.Main;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

public final class SwingUtils {

    /**
     * Sets the size of the given {@link JComponent}.
     *
     * @param target The {@code JComponent}.
     * @param width The width in pixels.
     * @param height The height in pixels.
     */
    public static void setSize(@NotNull final JComponent target, final int width, final int height) {
        final Dimension dimension = new Dimension(width, height);
        target.setPreferredSize(dimension);
        target.setMaximumSize(dimension);
    }

    /**
     * Adds a button to the given target {@link JComponent}.
     *
     * @param target The target {@code JComponent}.
     * @param text The text on the button.
     * @param iconResourcePath The path to the image in the resources.
     * @param width The width of the button in pixels.
     * @param height The height of the button in pixels.
     * @param actionListener The {@link ActionListener} for the button.
     */
    public static JButton addButton(@NotNull final JComponent target, @Nullable final String text, @Nullable final String iconResourcePath, final int width, final int height, @NotNull final ActionListener actionListener) {
        return (JButton) target.add(createButton(text, iconResourcePath != null ? getIconFromResource(iconResourcePath) : null, width, height, actionListener));
    }

    /**
     * Creates a new {@link JButton}.
     *
     * @param text The text on the button.
     * @param icon The {@link Icon} to display on the button.
     * @param width The width of the button in pixels.
     * @param height The height of the button in pixels.
     * @param actionListener The {@link ActionListener} for the button.
     *
     * @return The newly created {@code JButton}.
     */
    public static JButton createButton(@Nullable final String text, @Nullable final Icon icon, final int width, final int height, @NotNull final ActionListener actionListener) {
        JButton button = createButton(text, icon, actionListener);
        setSize(button, width, height);
        return button;
    }

    /**
     * Creates a new {@link JButton}.
     *
     * @param text The text on the button.
     * @param icon The {@link Icon} to dispaly onthe button.
     * @param actionListener The {@link ActionListener} for the button.
     *
     * @return The newly created {@code JButton}.
     */
    public static JButton createButton(@Nullable final String text, @Nullable final Icon icon, @NotNull final ActionListener actionListener) {
        JButton button = new JButton(text, icon);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(Fonts.MAIN_BUTTON);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        return button;
    }

    public static void addVerticalSpacer(@NotNull final JComponent target, final int height, @NotNull final Color color) {
        addVerticalSpacer(target, height);
        JPanel spacer = new JPanel();
        spacer.add(new JLabel());
        spacer.setBackground(color);
        spacer.setPreferredSize(new Dimension(target.getWidth(), height));
        target.add(spacer);
    }

    public static void addHorizontalSpacer(@NotNull final JComponent target, final int width) {
        target.add(Box.createHorizontalStrut(width));
    }

    public static void addHorizontalSeparator(@NotNull final JComponent target) {
        target.add(new JSeparator(SwingConstants.HORIZONTAL));
    }

    public static void addVerticalSeparator(@NotNull final JComponent target) {
        target.add(new JSeparator(SwingConstants.VERTICAL));
    }

    public static void addVerticalSpacer(@NotNull final JComponent target, final int height) {
        target.add(Box.createVerticalStrut(height));
    }

    public static InputStream getResource(@NotNull final String resourcePath) {
        return Main.class.getResourceAsStream(resourcePath);
    }

    public static BufferedImage getImageFromResource(@NotNull final String resourcePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getResource("assets/images" + resourcePath));
        } catch (IOException ex) {
            Logger.error(ex, "Failed to load image icon from resource path '%s'.", resourcePath);
        }
        return image;
    }

    public static ImageIcon getIconFromResource(@NotNull final String resourcePath) {
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(ImageIO.read(Objects.requireNonNull(getResource("/assets/icons/" + resourcePath))));
        } catch (Exception ex) {
            Logger.error(ex, "Failed to load image icon from resource path '%s'.", resourcePath);
        }
        return icon;
    }

    public static void setButtonIcon(@NotNull final JButton button, @NotNull final String resourcePath) {
        try {
            button.setIcon(new ImageIcon(ImageIO.read(getResource(resourcePath))));
        } catch (Exception ex) {
            Logger.error(ex, "Failed to set button icon.");
        }
    }
}
