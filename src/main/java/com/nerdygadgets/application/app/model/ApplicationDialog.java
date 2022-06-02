package com.nerdygadgets.application.app.model;

import com.nerdygadgets.application.util.SwingUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.JDialog;

/**
 * Super class for all dialog windows within the application.
 *
 * @author Kevin Zuman
 */
public abstract class ApplicationDialog extends JDialog {

    protected final ApplicationWindow window;

    public ApplicationDialog(@NotNull final ApplicationWindow window,  final boolean modal) {
        this.window = window;

        // Configure dialog
        this.setModal(modal);
        this.setIconImage(SwingUtils.getIconFromResource("app.png").getImage());
    }

    /**
     * Moves the window to the center of the screen.
     */
    public void center() {
        this.pack();
        this.setLocationRelativeTo(null);
    }

    protected void hideDialog() {
        window.hideDialog(this);
    }

    /**
     * Method that is called before showing the dialog.
     */
    protected abstract void onShow();

    /**
     * Method that is called after hiding the dialog.
     */
    protected abstract void onHide();
}
