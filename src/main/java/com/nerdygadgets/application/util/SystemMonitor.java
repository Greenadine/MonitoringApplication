package com.nerdygadgets.application.util;

import com.nerdygadgets.application.exception.PowerShellScriptException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

public class SystemMonitor {

    /**
     * Runs a PowerShell command for retrieving the CPU load of the local system in percentages.
     *
     * @return The system's CPU load in percentage, or {@link Optional#empty()} if failed to retrieve.
     */
    public static Optional<Integer> getLocalCpuLoad() {
        return getCpuLoad("localhost", null, null);
    }

    /**
     * Runs a PowerShell command for retrieving the CPU load in percentages of the system at the given address, with the provided credentials.
     *
     * @param address The address of the system.
     * @param user The username to use for accessing the system's OS information.
     * @param password The password to use for accessing the system's OS information.
     *
     * @return The system's CPU load in percentage, or {@link Optional#empty()} if failed to retrieve.
     */
    public static Optional<Integer> getCpuLoad(@NotNull final String address, @Nullable final String user, @Nullable final String password) {
        Integer cpuLoad = null;

        try {
            final String command;

            if (address.equalsIgnoreCase("localhost")) {
                command = "wmic CPU get LoadPercentage";
            } else {
                command  = String.format("wmic /node:%s /user:%s /password:%s CPU get LoadPercentage", address, user, password);
            }

            final ProcessOutput processOutput = executePowerShellCommand(command);
            final BufferedReader psOut = processOutput.getOutput();

            try {
                String line;
                while ((line = psOut.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }

                    // If the line only consists of digits, parse it into an int
                    if (Pattern.compile("\\d+").matcher(line).matches()) {
                        cpuLoad = Integer.parseInt(line);
                        break;
                    }
                }

                processOutput.close();
            } catch (IOException ex) {
                Logger.error(ex, "Failed to retrieve CPU load of system at address '%s'.", address);
            }
        } catch (PowerShellScriptException ex) {
            Logger.error(ex, "Failed to run PowerShell command for retrieving CPU load of system at address '%s'.", address);
        }
        return Optional.ofNullable(cpuLoad);
    }

    /**
     * Runs a PowerShell command for retrieving the last boot-up time of the local system.
     *
     * @return An {@code Instant} of the system's last boot-up time, or {@code null} if failed to retrieve.
     */
    public static Instant getLocalLastBootUpTime() {
        return getLastBootUpTime("localhost", null, null);
    }

    /**
     * Runs a PowerShell command for retrieving the last boot-up time of the system at the given address, with the provided credentials.
     *
     * @param address The address of the system.
     * @param user The username to use for accessing the system's OS information.
     * @param password The password to use for accessing the system's OS information.
     *
     * @return An {@code Instant} of the system's last boot-up time, or {@code null} if failed to retrieve.
     */
    public static Instant getLastBootUpTime(@NotNull final String address, @Nullable final String user, @Nullable final String password) {
        Instant lastBootUpTime = null;

        try {
            final String command;

            if (address.equalsIgnoreCase("localhost")) {
                command = "wmic path Win32_OperatingSystem get LastBootUpTime";
            } else {
                command  = String.format("wmic /node:%s /user:%s /password:%s path Win32_OperatingSystem get LastBootUpTime", address, user, password);
            }

            final ProcessOutput processOutput = executePowerShellCommand(command);
            final BufferedReader psOut = processOutput.getOutput();

            try {
                String line;
                while ((line = psOut.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }

                    final String[] strArr = line.split("\\.");

                    if (strArr.length == 2 && Pattern.compile("\\d{14}").matcher(strArr[0]).matches()) {
                        try {
                            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                            lastBootUpTime = dateFormat.parse(strArr[0]).toInstant();
                        } catch (ParseException ex) {
                            Logger.error(ex, "Failed to parse last boot-up time '%s' into Instant.", strArr[0]);
                        }
                    }
                }

                processOutput.close();
            } catch (IOException ex) {
                Logger.error(ex, "Failed to retrieve last boot-up time of system at address '%s'.", address);
            }
        } catch (PowerShellScriptException ex) {
            Logger.error(ex, "Failed to run PowerShell command for retrieving last boot-up time of system at address '%s'.", address);
        }
        return lastBootUpTime;
    }

    /**
     * Runs a PowerShell comman for retrieving disk(s) information of the local system.
     *
     * @return An {@code ArrayList} containing a {@link DiskResult} for every storage disk present in the system.
     */
    public static ArrayList<DiskResult> getLocalDisks() {
        return getDisks("localhost", null, null);
    }

    /**
     * Runs a PowerShell command for retrieving disk(s) information of the system at the given address, with the provided credentials.
     *
     * @param address The address of the system.
     * @param user The username to use for accessing the system's OS information.
     * @param password The password to use for accessing the system's OS information.
     *
     * @return An {@code ArrayList} containing a {@link DiskResult} for every storage disk present in the system.
     */
    public static ArrayList<DiskResult> getDisks(@NotNull final String address, @Nullable final String user, @Nullable final String password) {
        final ArrayList<DiskResult> disks = new ArrayList<>();

        try {
            final String command;

            if (address.equalsIgnoreCase("localhost")) {
                command = "wmic LogicalDisk get Name,Size,FreeSpace";
            } else {
                command  = String.format("wmic /node:%s /user:%s /password:%s LogicalDisk get Name,Size,FreeSpace", address, user, password);
            }

            final ProcessOutput processOutput = executePowerShellCommand(command);
            final BufferedReader psOut = processOutput.getOutput();

            try {
                String line;
                boolean skipFirst = true;

                while ((line = psOut.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }
                    if (skipFirst) {
                        skipFirst = false; // Skip first line, as this always contains a header
                        continue;
                    }

                    final String[] strArr = line.split("\\s+");
                    final float diskFreeSpace = Long.parseLong(strArr[0]) / 1073741824f;
                    final String diskName = strArr[1].trim();
                    final float diskTotalSpace = Long.parseLong(strArr[2]) / 1073741824f;
                    disks.add(new DiskResult(diskName, diskTotalSpace, diskFreeSpace));
                }

                processOutput.close();
            } catch (IOException ex) {
                Logger.error(ex, "Failed to retrieve storage disks of system at address '%s'.", address);
            }
        } catch (PowerShellScriptException ex) {
            Logger.error(ex, "Failed to run PowerShell command for retrieving disk(s) information of system at address '%s'.", address);
        }
        return disks;
    }

    /**
     * Executes the given PowerShell command, and returns the results.
     *
     * @param command The command to execute.
     *
     * @return A {@link ProcessOutput} containing both the regular output, as well as the error output from executing the command.
     *
     * @throws PowerShellScriptException If an exception occurred while executing the command.
     */
    private static ProcessOutput executePowerShellCommand(final String command) throws PowerShellScriptException {
        try {
            final Process process = Runtime.getRuntime().exec(new String[] { "powershell.exe", command});
            final BufferedReader psOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
            final BufferedReader psErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            return new ProcessOutput(psOut, psErr);
        } catch (Exception ex) {
            throw new PowerShellScriptException(ex);
        }
    }

    private static class ProcessOutput {

        private final BufferedReader psOut;
        private final BufferedReader psErr;

        private ProcessOutput(final BufferedReader psOut, final BufferedReader psErr) {
            this.psOut = psOut;
            this.psErr = psErr;
        }

        private BufferedReader getOutput() {
            return psOut;
        }

        private BufferedReader getErrors() {
            return psErr;
        }

        private void close() throws IOException {
            psOut.close();
            psErr.close();
        }
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
