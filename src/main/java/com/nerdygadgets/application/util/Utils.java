package com.nerdygadgets.application.util;

import com.google.common.primitives.Longs;
import com.nerdygadgets.application.Main;
import com.nerdygadgets.application.model.NetworkComponent;
import com.nerdygadgets.application.util.database.ConnectionToDatabase;
import com.nerdygadgets.application.util.database.GetDataFromDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class Utils {

    /**
     * Export a resource embedded into the jar file to the local file path.
     *
     * @param resourceName ie.: "/SmartLibrary.dll"
     * @return The path to the exported resource.
     */
    @SuppressWarnings("ConstantConditions")
    static public String exportResource(String resourceName) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;

        try {
            stream = Main.class.getResourceAsStream(resourceName); //note that each / is a directory down in the "jar tree" been the jar the root of the tree
            if (stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(jarFolder + resourceName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } finally {
            stream.close();
            resStreamOut.close();
        }

        return jarFolder + resourceName;
    }

    /**
     * Returns an {@code array} containing the IDs of the provided {@code Collection} of {@link NetworkComponent}s.
     *
     * @param networkComponents The {@code Collection} containing the {@code NetworkComponent}s.
     * @return An {@code array} containing the IDs of the provided {@code Collection} of {@code NetworkComponent}s.
     */
    public static long[] getComponentIds(Collection<? extends NetworkComponent> networkComponents) {
        ArrayList<Long> componentIds = new ArrayList<>();

        for (NetworkComponent networkComponent : networkComponents) {
            componentIds.add(networkComponent.getId());
        }

        return Longs.toArray(componentIds);
    }

    /**
     * Returns an {@code ArrayList} containing the databases with the provided IDs.
     *
     * @param ids The {@code array} of IDs.
     * @return An {@code ArrayList} containing the databases with the provided IDs.
     */
    public static ArrayList<NetworkComponent> getDatabasesById(long[] ids) {
        ArrayList<NetworkComponent> databases = new ArrayList<>();

        for (long id : ids) {
            databases.add(new GetDataFromDatabase().getDatabase(id));
        }

        return databases;
    }

    /**
     * Returns an {@code ArrayList} containing the webservers with the provided IDs.
     *
     * @param ids The {@code array} of IDs.
     * @return An {@code ArrayList} containing the {@code Webserver}s with the provided IDs.
     */
    public static ArrayList<NetworkComponent> getWebserversById(long[] ids) {
        ArrayList<NetworkComponent> webservers = new ArrayList<>();

        for (long id : ids) {
            webservers.add(new GetDataFromDatabase().getWebserver(id));
        }

        return webservers;
    }
}
