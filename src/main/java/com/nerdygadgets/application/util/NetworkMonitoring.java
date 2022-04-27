package com.nerdygadgets.application.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public final class NetworkMonitoring {

    public static NetworkMonitoringResult getResult() throws IOException {
        Process psProcess = Runtime.getRuntime().exec(new String[] {"powershell.exe", "C:\\Users\\kevin\\IdeaProjects\\NerdyGadgetsApplicatie\\src\\main\\resources\\monitoring.ps1"});

        BufferedReader psOut = new BufferedReader(new InputStreamReader(psProcess.getInputStream()));
        String line;

        ArrayList<String> result = new ArrayList<>();

        while ((line = psOut.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }
            result.add(line);
        }
        return new NetworkMonitoringResult(result);
    }
}
