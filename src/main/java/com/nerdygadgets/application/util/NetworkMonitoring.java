package com.nerdygadgets.application.util;

import com.nerdygadgets.application.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

public final class NetworkMonitoring {

    @SuppressWarnings("ConstantConditions")
    public static NetworkMonitoringResult getResult() throws IOException {
        try {
            URL resourceUrl = Main.class.getClassLoader().getResource("monitoring.ps1");
            File file = Paths.get(resourceUrl.toURI()).toFile();
            Process psProcess = Runtime.getRuntime().exec(new String[] {"powershell.exe", file.getAbsolutePath()});

            BufferedReader psOut = new BufferedReader(new InputStreamReader(psProcess.getInputStream()));
            String line;

            ArrayList<String> result = new ArrayList<>();

            System.out.println("====== PS Out ======");
            int i = 0;
            while ((line = psOut.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                result.add(line);
                System.out.println(i + ": " + line);
                i++;
            }
            System.out.println("--------------------");

            BufferedReader psErr = new BufferedReader(new InputStreamReader(psProcess.getErrorStream()));

            System.out.println("===== PS Error =====");
            while ((line = psErr.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                System.out.println(line);
            }
            System.out.println("--------------------");

            return new NetworkMonitoringResult(result);
        } catch (URISyntaxException ex) {
            return null;
        }
    }
}
