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

        System.out.println("----- PS Out -----");
        while ((line = psOut.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }
            result.add(line);
            System.out.println(line);
        }
        System.out.println("------------------");

        BufferedReader psErr = new BufferedReader(new InputStreamReader(psProcess.getErrorStream()));

        System.out.println("----- PS Error -----");
        while ((line = psErr.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            System.out.println(line);
        }
        System.out.println("--------------------");

        return new NetworkMonitoringResult(result);
    }
}
