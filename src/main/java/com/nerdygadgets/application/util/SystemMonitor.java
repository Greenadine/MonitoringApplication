package com.nerdygadgets.application.util;

import com.nerdygadgets.application.app.panel.SystemMonitorPanel;
import com.nerdygadgets.application.exception.PowerShellScriptException;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

public final class SystemMonitor {

    /**
     * --------------Webserver--------------------------------
     * Schedule MonitorWebserverUptime at fixed rate
     * getting uptime by WMIC command and putting value on screen
     */
    public static Future<?> monitorWebserverUptime(@NotNull final SystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
        return Scheduler.scheduleAtFixedRate(new MonitorWebserverUptime(systemMonitorPanel, address, user, password), 0, 5);
    }

    public static class MonitorWebserverUptime implements Runnable {

        private final SystemMonitorPanel systemMonitorPanel;

        private final String address;
        private final String user;
        private final String password;

        public MonitorWebserverUptime(@NotNull final SystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
            this.systemMonitorPanel = systemMonitorPanel;
            this.address = address;
            this.user = user;
            this.password = password;
        }

        @Override
        public void run() {
            final String command = String.format("wmic /node:%s /user:%s /password:%s path Win32_OperatingSystem get LastBootUpTime", address, user, password);
            //final String command = "wmic path Win32_OperatingSystem get LastBootUpTime";

            try {
                final ProcessOutput processOutput = executePowerShellCommand(command);
                final BufferedReader psOut = processOutput.getOutput();

                boolean online = false;

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
                                // Convert output to Instant
                                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                                Instant lastBootUptime = dateFormat.parse(strArr[0]).toInstant();
                                final Duration duration = Duration.between(lastBootUptime, Instant.now());

                                systemMonitorPanel.setUptimeValue(formatDuration(duration));
                                online = true;
                            } catch (ParseException ex) {
                                Logger.error(ex, "Failed to parse last boot-up time '%s' into Instant.", strArr[0]);
                            }
                        }
                    }

                    systemMonitorPanel.setSystemStatus(online ? SystemStatus.ONLINE : SystemStatus.OFFLINE);
                    processOutput.close();
                } catch (IOException ex) {
                    Logger.error(ex, "Failed to retrieve last boot-up time of system at address '%s'.", address);
                }

            } catch (PowerShellScriptException ex) {
                Logger.error(ex, "Failed to update uptime for webserver '%s'.", systemMonitorPanel.getSystemName());
            }
        }
    }

    /**
     * Schedule MonitorWebserverCpuUsage at fixed rate
     * getting cpu by WMIC command and putting value in the visual graph.
     */
    public static Future<?> monitorWebserverCpuUsage(@NotNull final SystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
        return Scheduler.scheduleAtFixedRate(new MonitorWebserverCpuUsage(systemMonitorPanel, address, user, password), 0, 1);
    }

    public static class MonitorWebserverCpuUsage implements Runnable {

        private final SystemMonitorPanel systemMonitorPanel;

        private final String address;
        private final String user;
        private final String password;

        public MonitorWebserverCpuUsage(@NotNull final SystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
            this.systemMonitorPanel = systemMonitorPanel;
            this.address = address;
            this.user = user;
            this.password = password;
        }

        @Override
        public void run() {
            try {
                final String command;
                command = String.format("wmic /node:%s /user:%s /password:%s CPU get LoadPercentage", address, user, password);
                //command  = "wmic  CPU get LoadPercentage";

                final SystemMonitor.ProcessOutput processOutput = executePowerShellCommand(command);
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
                            systemMonitorPanel.appendCpuValueToGraph(Integer.parseInt(line));
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
        }
    }

    /**
     * Schedule MonitorWebserverCpuUsage at fixed rate
     * getting cpu by WMIC command and putting value in the visual graph.
     */
    public static Future<?> monitorWebserverDisks(@NotNull final SystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
        return Scheduler.scheduleAtFixedRate(new MonitorWebserverDisks(systemMonitorPanel, address, user, password), 0, 300);
    }

    public static class MonitorWebserverDisks implements Runnable {
        private final SystemMonitorPanel systemMonitorPanel;

        private final String address;
        private final String user;
        private final String password;

        public MonitorWebserverDisks(@NotNull final SystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
            this.systemMonitorPanel = systemMonitorPanel;
            this.address = address;
            this.user = user;
            this.password = password;
        }

        @Override
        public void run() {
            try {
                // We have to first remove and then re-add the component for it to update


                final String command;
                command = String.format("wmic /node:%s /user:%s /password:%s LogicalDisk get Name,Size,FreeSpace", address, user, password);
                //command  = "wmic LogicalDisk get Name,Size,FreeSpace";

                final SystemMonitor.ProcessOutput processOutput = executePowerShellCommand(command);
                final BufferedReader psOut = processOutput.getOutput();
                final ArrayList<SystemMonitor.DiskResult> disks = new ArrayList<>();
                removeComponent(systemMonitorPanel);
                addComponent(systemMonitorPanel);
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
                    systemMonitorPanel.addDisksInformation(disks);

                    processOutput.close();
                } catch (IOException ex) {
                    Logger.error(ex, "Failed to retrieve storage disks of system at address '%s'.", address);
                }
            } catch (PowerShellScriptException ex) {
                Logger.error(ex, "Failed to run PowerShell command for retrieving disk(s) information of system at address '%s'.", address);
            }

        }
    }
    /** ----------------------------------------------*/

    /**
     * --------------Database--------------------------------
     * Schedule MonitorWebserverUptime at fixed rate
     * getting uptime by WMIC command and putting value on screen
     */
    public static Future<?> monitorDatabaseUptime(@NotNull final SystemMonitorPanel systemMonitorPanel, @NotNull final String address) {
        return Scheduler.scheduleAtFixedRate(new MonitorDatabaseUptime(systemMonitorPanel, address), 0, 5);
    }

    public static class MonitorDatabaseUptime implements Runnable {
        private final SystemMonitorPanel systemMonitorPanel;

        private final String address;

        public MonitorDatabaseUptime(@NotNull final SystemMonitorPanel systemMonitorPanel, @NotNull final String address) {
            this.systemMonitorPanel = systemMonitorPanel;
            this.address = address;
        }

        @Override
        public void run() {
            try {
                final String command;
                command = "ssh student@" + address + " uptime -p";
                final SystemMonitor.ProcessOutput processOutput = executePowerShellCommand(command);
                final BufferedReader psOut = processOutput.getOutput();

                boolean online = false;

                String a = null;
                try {
                    a = psOut.readLine();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }

                a = a.replaceAll("[,]", "");
                int indexOfSpace = a.indexOf(' ');
                if (indexOfSpace != -1) {
                    systemMonitorPanel.setUptimeValue(a.substring(indexOfSpace + 1));
                    online = true;
                }

                systemMonitorPanel.setSystemStatus(online ? SystemStatus.ONLINE : SystemStatus.OFFLINE);
                processOutput.close();
            } catch (Exception ex) {
                Logger.error(ex, "Failed to retrieve database uptime");
            }
        }
    }

    /**
     * Schedule MonitorWebserverCpuUsage at fixed rate
     * getting cpu by WMIC command and putting value in the visual graph.
     */
    public static Future<?> monitorDatabaseCpuUsage(@NotNull final SystemMonitorPanel systemMonitorPanel, @NotNull final String address) {
        return Scheduler.scheduleAtFixedRate(new MonitorDatabaseCpuUsage(systemMonitorPanel, address), 0, 1);
    }

    public static class MonitorDatabaseCpuUsage implements Runnable {
        private final SystemMonitorPanel systemMonitorPanel;

        private final String address;

        public MonitorDatabaseCpuUsage(@NotNull final SystemMonitorPanel systemMonitorPanel, @NotNull final String address) {
            this.systemMonitorPanel = systemMonitorPanel;
            this.address = address;
        }

        @Override
        public void run() {
            final String command;
            command = "ssh student@" + address + " 'sar 1 1 -u' | ConvertFrom-String -Delimiter ' ' | Select-Object p42";
            final ProcessOutput processOutput;
            try {
                processOutput = executePowerShellCommand(command);
                final BufferedReader psOut = processOutput.getOutput();

                String line;
                while ((line = psOut.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;

                    }
                    final String[] strArr = line.split(" ");

                    for (String partOfLine : strArr) {
                        if (partOfLine.matches("\\d+")) {
                            systemMonitorPanel.appendCpuValueToGraph(100 - Integer.parseInt(partOfLine));
                        }
                    }
                }

                processOutput.close();
            } catch (IOException | PowerShellScriptException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Schedule MonitorWebserverCpuUsage at fixed rate
     * getting cpu by WMIC command and putting value in the visual graph.
     */
    public static Future<?> monitorDatabaseDisks(@NotNull final SystemMonitorPanel systemMonitorPanel, @NotNull final String address) {
        return Scheduler.scheduleAtFixedRate(new MonitorDatabaseDisks(systemMonitorPanel, address), 0, 5);
    }

    public static class MonitorDatabaseDisks implements Runnable {
        private final SystemMonitorPanel systemMonitorPanel;

        private final String address;

        public MonitorDatabaseDisks(@NotNull final SystemMonitorPanel systemMonitorPanel, @NotNull final String address) {
            this.systemMonitorPanel = systemMonitorPanel;
            this.address = address;
        }

        @Override
        public void run() {
            final ArrayList<SystemMonitor.DiskResult> disks = new ArrayList<>();

            try {
                final String command;
                command = "ssh student@" + address + " df / | ConvertFrom-String -Delimiter ' ' | Select-Object p3, p7";
                final SystemMonitor.ProcessOutput processOutput = executePowerShellCommand(command);
                final BufferedReader psOut = processOutput.getOutput();

                String line;

                while (((line = psOut.readLine()) != null)) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }

                    String[] strArr = line.split(" ");
                    int count = 0;

                    float totalSpace = 0;
                    float freeSpace = 0;

                    for (String outputLine : strArr) {
                        if (outputLine.matches("\\d++")) {
                            if (count == 0) {
                                totalSpace = Integer.parseInt(outputLine);
                            } else {
                                freeSpace = Integer.parseInt(outputLine);
                            }
                            count++;
                        }
                    }

                    disks.clear();
                    disks.add(new DiskResult("/", totalSpace / 1024f / 1024f, freeSpace / 1024f / 1024f));
//                    systemMonitorPanel.addDisksInformation(Collections.singletonList(new DiskResult("Test", 420, 69)));
                }

                processOutput.close();
            } catch (IOException | PowerShellScriptException | NullPointerException ex) {
                Logger.error(ex, "Failed to retrieve disk(s) information through SSH.");
            }

            systemMonitorPanel.addDisksInformation(disks);
        }
    }
    /** ---------------------------------------------------------*/

    /**
     * --------------Pfsense--------------------------------
     * // Schedule MonitorWebserverUptime at fixed rate
     * // getting uptime by WMIC command and putting value on screen
     */
    public static Future<?> monitorPfsenseUptime(@NotNull final SystemMonitorPanel systemMonitorPanel) {
        return Scheduler.scheduleAtFixedRate(new MonitorPfsenseUptime(systemMonitorPanel), 0, 5);
    }

    public static class MonitorPfsenseUptime implements Runnable {
        private final SystemMonitorPanel systemMonitorPanel;

        public MonitorPfsenseUptime(@NotNull final SystemMonitorPanel systemMonitorPanel) {
            this.systemMonitorPanel = systemMonitorPanel;
        }

        private void addDisks(ArrayList<DiskResult> disks) {
            systemMonitorPanel.addDisksInformation(disks);

        }

        @Override
        public void run() {

            try {
                final String command;
                command = "ssh admin@192.168.1.1 uptime | ConvertFrom-String -Delimiter ' ' | Select-Object p5, p8";

                final SystemMonitor.ProcessOutput processOutput = executePowerShellCommand(command);
                final BufferedReader psOut = processOutput.getOutput();

                boolean online = false;

                String line;

                while (((line = psOut.readLine()) != null)) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }
                    String[] strArr = line.split(" ");

                    float days = 0;

                    for (String outputLine : strArr) {
                        if (outputLine.matches("[0-9]+")) {
                            days = Integer.parseInt(outputLine);
                            systemMonitorPanel.setUptimeValue((int) days + " days");
                            online = true;
                        }
                    }
                }

                systemMonitorPanel.setSystemStatus(online ? SystemStatus.ONLINE : SystemStatus.OFFLINE);
                processOutput.close();
            } catch (PowerShellScriptException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Schedule MonitorWebserverCpuUsage at fixed rate
     * getting cpu by WMIC command and putting value in the visual graph.
     */
    public static Future<?> monitorPfsenseCpuUsage(@NotNull final SystemMonitorPanel systemMonitorPanel) {
        return Scheduler.scheduleAtFixedRate(new MonitorPfsenseCpuUsage(systemMonitorPanel), 0, 1);
    }

    public static class MonitorPfsenseCpuUsage implements Runnable {
        private final SystemMonitorPanel systemMonitorPanel;

        public MonitorPfsenseCpuUsage(@NotNull final SystemMonitorPanel systemMonitorPanel) {
            this.systemMonitorPanel = systemMonitorPanel;
        }

        @Override
        public void run() {
            final String command;
            command = "ssh admin@192.168.1.1 top | ConvertFrom-String -Delimiter ' ' | Select-Object p14 -skip 2 -first 1";
            final ProcessOutput processOutput;

            try {
                processOutput = executePowerShellCommand(command);
                final BufferedReader psOut = processOutput.getOutput();
                String line;
                while ((line = psOut.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;

                    }
                    final String[] strArr = line.split(" ");

                    for (String partOfLine : strArr) {
                        if (partOfLine.contains("%")) {
                            String output = strArr[0].replace("%", "");
//                            double output1 = Double.parseDouble(output);
//                            long output2 = Math.round(output1);
//                            systemMonitorPanel.appendCpuValueToGraph(100 - (int) output2);
                            systemMonitorPanel.appendCpuValueToGraph(100 - (int) Math.round(Double.parseDouble(output)));
                        }
                    }
                }

                processOutput.close();
            } catch (IOException | PowerShellScriptException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Schedule MonitorWebserverCpuUsage at fixed rate
     * getting cpu by WMIC command and putting value in the visual graph.
     */
    public static Future<?> monitorPfsenseDisks(@NotNull final SystemMonitorPanel systemMonitorPanel) {
        return Scheduler.scheduleAtFixedRate(new MonitorPfsenseDisks(systemMonitorPanel), 0, 300);
    }

    public static class MonitorPfsenseDisks implements Runnable {
        private final SystemMonitorPanel systemMonitorPanel;

        public MonitorPfsenseDisks(@NotNull final SystemMonitorPanel systemMonitorPanel) {
            this.systemMonitorPanel = systemMonitorPanel;
        }

        @Override
        public void run() {
            final ArrayList<SystemMonitor.DiskResult> disks = new ArrayList<>();

            try {
                final String command;
                command = "ssh admin@192.168.1.1 df / | ConvertFrom-String -Delimiter ' ' | Select-Object p3, p5";
                final SystemMonitor.ProcessOutput processOutput = executePowerShellCommand(command);
                final BufferedReader psOut = processOutput.getOutput();

                String line;

                while (((line = psOut.readLine()) != null)) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }

                    String[] strArr = line.split(" ");
                    int count = 0;

                    float totalSpace = 0;
                    float freeSpace = 0;

                    for (String outputLine : strArr) {
                        if (outputLine.matches("\\d++")) {
                            if (count == 0) {
                                totalSpace = Integer.parseInt(outputLine);
                            } else {
                                freeSpace = Integer.parseInt(outputLine);
                            }
                            count++;
                        }
                    }

                    disks.clear();
                    disks.add(new DiskResult("/", totalSpace / 1024f / 1024f, freeSpace / 1024f / 1024f));
//                    systemMonitorPanel.addDisksInformation(Collections.singletonList(new DiskResult("Test", 420, 69)));
                }

                processOutput.close();
            } catch (IOException | PowerShellScriptException | NullPointerException ex) {
                Logger.error(ex, "Failed to retrieve disk(s) information through SSH.");
            }

            systemMonitorPanel.addDisksInformation(disks);
        }
    }
    /**-------------------------------------------------------*/
    /**
     * ----------localhost------------------------------------
     */
    public static Future<?> monitorLocalhostUptime(@NotNull final SystemMonitorPanel systemMonitorPanel) {
        return Scheduler.scheduleAtFixedRate(new MonitorLocalhostUptime(systemMonitorPanel), 0, 60);
    }

    public static class MonitorLocalhostUptime implements Runnable {
        private final SystemMonitorPanel systemMonitorPanel;

        public MonitorLocalhostUptime(@NotNull final SystemMonitorPanel systemMonitorPanel) {
            this.systemMonitorPanel = systemMonitorPanel;
        }

        @Override
        public void run() {
            final String command = "wmic path Win32_OperatingSystem get LastBootUpTime";

            try {
                final ProcessOutput processOutput = executePowerShellCommand(command);

                final BufferedReader psOut = processOutput.getOutput();

                try {
                    boolean online = false;

                    String line;
                    while ((line = psOut.readLine()) != null) {
                        line = line.trim();
                        if (line.isEmpty()) {
                            continue;
                        }

                        final String[] strArr = line.split("\\.");

                        if (strArr.length == 2 && Pattern.compile("\\d{14}").matcher(strArr[0]).matches()) {
                            try {
                                // Convert output to Instant
                                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                                Instant lastBootUptime = dateFormat.parse(strArr[0]).toInstant();
                                final Duration duration = Duration.between(lastBootUptime, Instant.now());

                                systemMonitorPanel.setUptimeValue(formatDuration(duration));
                                online = true;
                            } catch (ParseException ex) {
                                Logger.error(ex, "Failed to parse last boot-up time '%s' into Instant.", strArr[0]);
                            }
                        }
                    }

                    systemMonitorPanel.setSystemStatus(online ? SystemStatus.ONLINE : SystemStatus.OFFLINE);
                    processOutput.close();
                } catch (IOException ex) {
                    Logger.error(ex, "Failed to retrieve last boot-up time of system at address.");
                }

            } catch (PowerShellScriptException ex) {
                Logger.error(ex, "Failed to update uptime for webserver '%s'.", systemMonitorPanel.getSystemName());
            }
        }
    }

    /**
     * Schedule MonitorWebserverCpuUsage at fixed rate
     * getting cpu by WMIC command and putting value in the visual graph.
     */
    public static Future<?> monitorLocalhostCpuUsage(@NotNull final SystemMonitorPanel systemMonitorPanel) {
        return Scheduler.scheduleAtFixedRate(new MonitorLocalhostCpuUsage(systemMonitorPanel), 0, 1);
    }

    public static class MonitorLocalhostCpuUsage implements Runnable {
        private final SystemMonitorPanel systemMonitorPanel;

        public MonitorLocalhostCpuUsage(@NotNull final SystemMonitorPanel systemMonitorPanel) {
            this.systemMonitorPanel = systemMonitorPanel;
        }

        @Override
        public void run() {
            try {
                final String command;
                //command  = String.format("wmic /node:%s /user:%s /password:%s CPU get LoadPercentage", address, user, password);
                command = "wmic CPU get LoadPercentage";

                final SystemMonitor.ProcessOutput processOutput = executePowerShellCommand(command);
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
                            systemMonitorPanel.appendCpuValueToGraph(Integer.parseInt(line));
                            break;
                        }
                    }

                    processOutput.close();
                } catch (IOException ex) {
                    Logger.error(ex, "Failed to retrieve CPU load of system at address.");
                }
            } catch (PowerShellScriptException ex) {
                Logger.error(ex, "Failed to run PowerShell command for retrieving CPU load of system at address .");
            }
        }
    }

    /**
     * Schedule MonitorWebserverCpuUsage at fixed rate
     * getting cpu by WMIC command and putting value in the visual graph.
     */
    public static Future<?> monitorLocalhostDisks(@NotNull final SystemMonitorPanel systemMonitorPanel) {
        return Scheduler.scheduleAtFixedRate(new MonitorLocalhostDisks(systemMonitorPanel), 0, 300);
    }

    public static class MonitorLocalhostDisks implements Runnable {
        private final SystemMonitorPanel systemMonitorPanel;


        public MonitorLocalhostDisks(@NotNull final SystemMonitorPanel systemMonitorPanel) {
            this.systemMonitorPanel = systemMonitorPanel;
        }

        @Override
        public void run() {
            try {
                // We have to first remove and then re-add the component for it to update


                final String command;
                //command  = String.format("wmic /node:%s /user:%s /password:%s LogicalDisk get Name,Size,FreeSpace", address, user, password);
                command = "wmic LogicalDisk get Name,Size,FreeSpace";

                final SystemMonitor.ProcessOutput processOutput = executePowerShellCommand(command);
                final BufferedReader psOut = processOutput.getOutput();
                final ArrayList<SystemMonitor.DiskResult> disks = new ArrayList<>();

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
                        break;
                    }

                    systemMonitorPanel.addDisksInformation(disks);
                    processOutput.close();
                } catch (IOException ex) {
                    Logger.error(ex, "Failed to retrieve storage disks of system at address .");
                }
            } catch (PowerShellScriptException ex) {
                Logger.error(ex, "Failed to run PowerShell command for retrieving disk(s) information of system at address.");
            }

        }

    }

    /**
     * ---------------------------------------------------------
     */

    private static ProcessOutput executePowerShellCommand(final String command) throws PowerShellScriptException {
        try {
            final Process process = Runtime.getRuntime().exec(new String[]{"powershell.exe", command});
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

        public DiskResult(final String name, final float totalSpace, final float freeSpace) {
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

    /**
     * Adds a {@link Component} to the panel.
     * <p>
     * The {@code Component}.
     */
    private static void addComponent(SystemMonitorPanel systemMonitorPanel) {

        systemMonitorPanel.disksTableContentPanel = new JPanel();
        systemMonitorPanel.disksTableContentPanel.setLayout(new GridLayout());
        systemMonitorPanel.disksTableContentPanel.setBorder(new EmptyBorder(2, 5, 0, 5));
        systemMonitorPanel.disksTableContentPanel.setBackground(Colors.MONITOR_TABLE_CONTENT);
        systemMonitorPanel.add(systemMonitorPanel.disksTableContentPanel);

    }

    /**
     * Removes a {@link Component} from the panel.
     * <p>
     * The {@code Component}.
     */
    private static void removeComponent(SystemMonitorPanel systemMonitorPanel) {
        systemMonitorPanel.disksTableContentPanel.removeAll();
        systemMonitorPanel.remove(systemMonitorPanel.disksTableContentPanel);
    }

    /**
     * Formats the {@link Duration} between the last boot-up time and now, into days, hours and minutes.
     *
     * @param duration The {@code Duration} between the last boot-up time, and now.
     * @return A {@code String} with days, hours and minutes between the last boot-up time, and now.
     */
    private static String formatDuration(Duration duration) {
        final StringJoiner joiner = new StringJoiner(" ");

        final String str = DurationFormatUtils.formatDuration(duration.toMillis(), "d' days 'H' hours 'm' minutes'");
        String[] strArr = str.split("\\s+");

        try {
            final int days = Integer.parseInt(strArr[0]);
            if (days > 0) {
                joiner.add(days + " days");
            }

            final int hours = Integer.parseInt(strArr[2]);
            if (hours > 0) {
                joiner.add(hours + " hours");
            }

            final int minutes = Integer.parseInt(strArr[4]);
            if (minutes > 0) {
                joiner.add(minutes + " minutes");
            }
        } catch (NumberFormatException ex) {
            Logger.error(ex, "Failed to format system uptime.");
        }

        String result = joiner.toString();
        result = result.replaceFirst(" 1 minutes", " 1 minute");
        result = result.replaceFirst(" 1 hours", " 1 hour");
        result = result.replaceFirst("$1 days", " 1 day");
        return result;
    }

}
