package com.nerdygadgets.application.app.model;

import com.nerdygadgets.application.exception.InvalidWindowScreenException;
import com.nerdygadgets.application.util.AnnotationUtils;
import com.nerdygadgets.application.util.SwingUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Super class for all windows within the application.
 *
 * @author Kevin Zuman
 */
public abstract class ApplicationWindow extends JFrame {

    protected final JPanel contentPanel;

    private final Map<String, ApplicationScreen> applicationScreens;
    protected ApplicationScreen currentScreen;

    private final Map<String, ApplicationDialog> applicationDialogs;

    public ApplicationWindow(@Nullable final String title) {
        this.setTitle(title);
        this.setIconImage(SwingUtils.getIconFromResource("app.png").getImage());

        this.applicationScreens = new HashMap<>();
        this.applicationDialogs = new HashMap<>();

        // Create and add content panel
        this.contentPanel = new JPanel();
        this.contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0, 0));
        this.add(contentPanel);
    }

    /**
     * Registers an {@link ApplicationScreen}
     *
     * @param identifier The unique identifier {@code String} of the {@code ApplicationScreen}.
     * @param applicationScreen The {@code ApplicationScreen}.
     */
    protected void registerScreen(@NotNull final String identifier, @NotNull final ApplicationScreen applicationScreen) {
        applicationScreens.put(identifier, applicationScreen);
    }

    /**
     * Opens the {@link ApplicationScreen} with the given unique identifier.
     *
     * @param screenName The unique identifier of the {@code ApplicationScreen}.
     *
     * @throws InvalidWindowScreenException If no {@code ApplicationScreen} with the given unique identifier exists.
     */
    public void openScreen(@NotNull final String screenName) {
        openScreen(screenName, false);
    }

    /**
     * Opens the {@link ApplicationScreen} with the given unique identifier.
     *
     * @param screenName The unique identifier of the {@code ApplicationScreen}.
     * @param hideWindow {@code true} to hide the window before opening the new screen, and make visible again after it has been opened,
     *             {@code false} to not hide the window before opening the new screen.
     *
     * @throws InvalidWindowScreenException If no {@code ApplicationScreen} with the given unique identifier exists.
     */
    public void openScreen(@NotNull final String screenName, boolean hideWindow) {
        if (!applicationScreens.containsKey(screenName)) {
            throw new IllegalArgumentException(String.format("Invalid screen name '%s'.", screenName));
        }
        ApplicationScreen applicationScreen = applicationScreens.get(screenName);

        if (hideWindow) {
            this.setVisible(false);
        }
        getCurrentScreen().ifPresent(ApplicationScreen::onClose);
        applicationScreen.onOpen();
        contentPanel.removeAll();
        contentPanel.add(applicationScreen);
        contentPanel.repaint();
        this.pack();
        center();
        this.setVisible(true);

        this.currentScreen = applicationScreen;
    }

    /**
     * Gets the currently opened {@link ApplicationScreen}.
     *
     * @return The currently opened {@code AbstractApplicationScreen}.
     */
    public Optional<ApplicationScreen> getCurrentScreen() {
        return Optional.ofNullable(currentScreen);
    }

    /**
     * Gets the {@link ApplicationScreen} with the given unique identifier.
     *
     * @param identifier The unique identifier of the {@code ApplicationScreen}.
     *
     * @return The {@code ApplicationScreen} with the given identifier.
     */
    public ApplicationScreen getScreen(final String identifier) {
        return applicationScreens.get(identifier);
    }

    /**
     * Registers an {@link ApplicationDialog}.
     *
     * @param applicationDialog The {@code ApplicationDialog}.
     */
    public void registerDialog(@NotNull final ApplicationDialog applicationDialog) {
        if (!AnnotationUtils.hasAnnotation(applicationDialog.getClass(), Identifier.class)) {
            throw new IllegalStateException("Dialog not annotated with identifier.");
        }

        final String identifier = AnnotationUtils.getValue(applicationDialog.getClass(), Identifier.class);
        if (applicationDialogs.containsKey(identifier)) {
            throw new IllegalStateException(String.format("Duplicate dialog identifier '%s'.", identifier));
        }

        applicationDialogs.put(identifier, applicationDialog);
    }

    /**
     * Shows the {@link ApplicationDialog} with the given unique identifier.
     *
     * @param dialogName The unique identifier of the {@code ApplicationDialog}.
     */
    public ApplicationDialog showDialog(@NotNull final String dialogName) {
        if (!applicationDialogs.containsKey(dialogName)) {
            throw new IllegalArgumentException(String.format("Invalid dialog name '%s'.", dialogName));
        }

        ApplicationDialog dialog = applicationDialogs.get(dialogName);
        dialog.onShow();
        dialog.setVisible(true);
        return dialog;
    }

    /**
     * Hides the {@link ApplicationDialog} with the given unique identifier.
     *
     * @param dialogName The unique identifier of the {@code ApplicationDialog}.
     */
    public void hideDialog(@NotNull final String dialogName) {
        if (!applicationDialogs.containsKey(dialogName)) {
            throw new IllegalArgumentException(String.format("Invalid dialog name '%s'.", dialogName));
        }
        hideDialog(applicationDialogs.get(dialogName));
    }

    /**
     * Hides the provided {@link ApplicationDialog}.
     *
     * @param applicationDialog The {@code ApplicationDialog}.
     */
    public void hideDialog(@NotNull final ApplicationDialog applicationDialog) {
        if (!AnnotationUtils.hasAnnotation(applicationDialog.getClass(), Identifier.class)) {
            throw new IllegalStateException("Dialog not annotated with identifier.");
        }

        final String identifier = AnnotationUtils.getValue(applicationDialog.getClass(), Identifier.class);
        if (!applicationDialogs.containsKey(identifier)) {
            throw new IllegalStateException("Dialog has not been registered.");
        }

        applicationDialog.setVisible(false);
        applicationDialog.onHide();
    }

    /**
     * Moves the window to the center of the screen.
     */
    public void center() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - contentPanel.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - contentPanel.getHeight()) / 2);
        this.setLocation(x, y);
    }
}
