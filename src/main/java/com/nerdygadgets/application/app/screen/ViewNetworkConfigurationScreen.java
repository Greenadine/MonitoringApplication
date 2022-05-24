package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.model.NetworkConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ViewNetworkConfigurationScreen extends ApplicationScreen {

    private NetworkConfiguration configuration;

    public ViewNetworkConfigurationScreen(@NotNull final ApplicationWindow window) {
        super(window);
    }

    /**
     * Sets the {@link NetworkConfiguration} to display on the screen.
     *
     * @param configuration The {@code NetworkConfiguration}.
     */
    public void setConfiguration(@Nullable final NetworkConfiguration configuration) {
        this.configuration = configuration;

        // TODO change values to display configuration
    }

    @Override
    protected void onOpenImpl() {

    }

    @Override
    protected void onCloseImpl() {

    }
}
