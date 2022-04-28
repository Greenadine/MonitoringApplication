package com.nerdygadgets.application.util;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;

public class NewNetworkMonitorResult {

    private Instant lastBootUpTime;
    private Integer cpuLoad;
    private final ArrayList<DiskResult> diskResults;

    public NewNetworkMonitorResult() {
        this.diskResults = new ArrayList<>();
    }

    public NewNetworkMonitorResult(@NotNull final ArrayList<String> output) {
        this.diskResults = new ArrayList<>();

        boolean disks = false;
        for (int i = 1; i < output.size(); i++) {
            String line = output.get(i);

            if (!disks) {
                if (line.equalsIgnoreCase("LastBootUpTime")) {
                    i++;

                    try {
                        this.lastBootUpTime = new SimpleDateFormat("yyyyMMddhhmmss").parse(output.get(i).split("\\.")[0]).toInstant();
                    } catch (ParseException ex) {
                        Logger.error(ex, "Failed to parse 'LastBootUpTime' to Instant.");
                    }
                }
                else if (line.equalsIgnoreCase("LoadPercentage")) {
                    i++;

                    try {
                        this.cpuLoad = Integer.parseInt(output.get(i));
                    } catch (NumberFormatException ex) {
                        Logger.error(ex, "Failed to parse 'LoadPercentage' to Integer.");
                    }
                }
                else if (line.startsWith("FreeSpace")) {
                    disks = true;
                }
            } else {
                String[] strArr = line.split("\\s+"); // Split string on whitespace(s)

                String diskName = strArr[1].trim();
                float diskTotalSpace = Long.parseLong(strArr[2]) / 1073741824f;
                float diskFreeSpace = Long.parseLong(strArr[0]) / 1073741824f;

                diskResults.add(new DiskResult(diskName, diskTotalSpace, diskFreeSpace));
                break;
            }
        }
    }

    public Instant getLastBootUpTime() {
        return lastBootUpTime;
    }

    public Integer getCpuLoad() {
        return cpuLoad;
    }

    public ArrayList<DiskResult> getDiskResults() {
        return diskResults;
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
