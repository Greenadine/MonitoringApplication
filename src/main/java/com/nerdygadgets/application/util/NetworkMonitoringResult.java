package com.nerdygadgets.application.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class NetworkMonitoringResult {

    private Instant lastBootUpTime;
    private int cpuLoad;
    private ArrayList<DiskResult> diskResults;

    public NetworkMonitoringResult(final List<String> output) {
        this.diskResults = new ArrayList<>();

        for (int i = 0; i < output.size(); i++) {
            String line = output.get(i).trim();

            switch (i) {
                case 0:
                case 2:
                case 4: {
                    // These lines do not contain data, and can be skipped
                    continue;
                }
                case 1: {
                    // Last bootup time
                    // TODO find a way to convert last boot up time to Instant (format example: '20220426172411.500000+120')
                    break;
                }
                case 3: {
                    // CPU load
                    this.cpuLoad = Integer.parseInt(line);
                    break;
                }
                default: {
                    // All other lines, which contain data about every disk in the system
                    String[] strArr = line.split("\\s+"); // Split string on whitespace(s)

                    String diskName = strArr[1].trim();
                    float diskTotalSpace = Long.parseLong(strArr[2]) / 1000000000f;
                    float diskFreeSpace = Long.parseLong(strArr[0]) / 1000000000f;

                    diskResults.add(new DiskResult(diskName, diskTotalSpace, diskFreeSpace));
                    break;
                }
            }
        }
    }

    public Instant getLastBootUpTime() {
        return lastBootUpTime;
    }

    public int getCpuLoad() {
        return cpuLoad;
    }

    public ArrayList<DiskResult> getDisks() {
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
