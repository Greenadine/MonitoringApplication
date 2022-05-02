package com.nerdygadgets.application.util;

import com.nerdygadgets.application.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

public class SystemMonitor {

    @SuppressWarnings("ConstantConditions")
    public static Instant getSystemLastBootUpTime() {
        Instant lastBootUpTime = null;

        try {
            URL resourceUrl = Main.class.getClassLoader().getResource("scripts/monitor-system-uptime.ps1");
            File scriptFile = Paths.get(resourceUrl.toURI()).toFile();
            Process psProcess = Runtime.getRuntime().exec(new String[] { "powershell.exe", scriptFile.getAbsolutePath() });

            BufferedReader psOut = new BufferedReader(new InputStreamReader(psProcess.getInputStream()));

            String line;
            while ((line = psOut.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                try {
                    lastBootUpTime = new SimpleDateFormat("yyyyMMddhhmmss").parse(line.split("\\.")[0]).toInstant();
                    break;
                } catch (ParseException ignored) { }
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.error(ex, "Failed to retrieve system uptime.");
        }
        return lastBootUpTime;
    }

    @SuppressWarnings("ConstantConditions")
    public static Optional<Integer> getCpuLoad() {
        Integer cpuLoad = null;

        try {
            URL resourceUrl = Main.class.getClassLoader().getResource("scripts/monitor-cpu-usage.ps1");
            File scriptFile = Paths.get(resourceUrl.toURI()).toFile();
            Process psProcess = Runtime.getRuntime().exec(new String[] { "powershell.exe", scriptFile.getAbsolutePath() });

            BufferedReader psOut = new BufferedReader(new InputStreamReader(psProcess.getInputStream()));

            String line;
            while ((line = psOut.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                try {
                    cpuLoad = Integer.parseInt(line);
                    break;
                } catch (NumberFormatException ignored) { }
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.error(ex, "Failed to retrieve CPU load.");
        }

        return Optional.ofNullable(cpuLoad);
    }

    @SuppressWarnings("ConstantConditions")
    public static ArrayList<DiskResult> getDisks() {
        ArrayList<DiskResult> disks = new ArrayList<>();

        try {
            URL resourceUrl = Main.class.getClassLoader().getResource("scripts/monitor-storage-disks.ps1");
            File scriptFile = Paths.get(resourceUrl.toURI()).toFile();
            Process psProcess = Runtime.getRuntime().exec(new String[] { "powershell.exe", scriptFile.getAbsolutePath() });

            BufferedReader psOut = new BufferedReader(new InputStreamReader(psProcess.getInputStream()));

            String line;
            boolean skipFirst = true;
            while ((line = psOut.readLine()) != null) {
                if (skipFirst) {
                    skipFirst = false;
                    continue;
                }

                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] strArr = line.split("\\s+");

                if (strArr.length != 3) {
                    continue;
                }

                String diskName = strArr[1].trim();
                float diskTotalSpace = Long.parseLong(strArr[2]) / 1073741824f;
                float diskFreeSpace = Long.parseLong(strArr[0]) / 1073741824f;
                disks.add(new DiskResult(diskName, diskTotalSpace, diskFreeSpace));
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.error(ex, "Failed to retrieve storage disks.");
        }

        return disks;
    }

    public static class DiskResult {

        private final String name;
        private final float totalSpace;
        private final float freeSpace;

        private DiskResult(final String name, final float totalSpace, final float freeSpace) {
            this.name = name;
            this.totalSpace = totalSpace;
            this.freeSpace = freeSpace;
        }

        public String getName() {
            return name;
        }

        public float getTotalSpace() {
            return totalSpace;
        }

        public float getFreeSpace() {
            return freeSpace;
        }
    }
}
