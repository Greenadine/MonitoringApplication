package com.nerdygadgets.application.util;

import com.nerdygadgets.application.Main;
import com.nerdygadgets.application.app.panel.NewSystemMonitorPanel;
import com.nerdygadgets.application.app.panel.SystemMonitorPanel;
import com.nerdygadgets.application.exception.PowerShellScriptException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
import java.util.regex.Pattern;

public final class NewSystemMonitor {

    /** --------------Webserver--------------------------------
     * Schedule MonitorWebserverUptime at fixed rate
     * getting uptime by WMIC command and putting value on screen
     */
    public static void monitorWebserverUptime(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
        Scheduler.scheduleAtFixedRate(new MonitorWebserverUptime(systemMonitorPanel, address, user, password), 0, 60);

    }
    public static class MonitorWebserverUptime implements Runnable {

        private final NewSystemMonitorPanel systemMonitorPanel;

        private final String address;
        private final String user;
        private final String password;

        public MonitorWebserverUptime(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
            this.systemMonitorPanel = systemMonitorPanel;
            this.address = address;
            this.user = user;
            this.password = password;
        }

        @Override
        public void run() {
            //final String command = String.format("wmic /node:%s /user:%s /password:%s path Win32_OperatingSystem get LastBootUpTime", address, user, password);
            final String command = "wmic path Win32_OperatingSystem get LastBootUpTime";

            try {
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
                                // Convert output to Instant
                                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                                Instant lastBootUptime = dateFormat.parse(strArr[0]).toInstant();
                                final Duration duration = Duration.between(lastBootUptime, Instant.now());

                                systemMonitorPanel.setUptimeValue(formatDuration(duration));

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
                Logger.error(ex, "Failed to update uptime for webserver '%s'.", systemMonitorPanel.getSystemName());
            }
        }

        /**
         * Formats the {@link Duration} between the last boot-up time and now, into days, hours and minutes.
         *
         * @param duration The {@code Duration} between the last boot-up time, and now.
         *
         * @return A {@code String} with days, hours and minutes between the last boot-up time, and now.
         */

    }
    /**
     * Schedule MonitorWebserverCpuUsage at fixed rate
     * getting cpu by WMIC command and putting value in the visual graph.
     */
    public static void monitorWebserverCpuUsage(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password){
        Scheduler.scheduleAtFixedRate(new MonitorWebserverCpuUsage(systemMonitorPanel, address, user, password), 0, 1);
    }
    public static class MonitorWebserverCpuUsage implements Runnable{

        private final NewSystemMonitorPanel systemMonitorPanel;

        private final String address;
        private final String user;
        private final String password;

        public MonitorWebserverCpuUsage(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
            this.systemMonitorPanel = systemMonitorPanel;
            this.address = address;
            this.user = user;
            this.password = password;
        }

        @Override
        public void run() {
            try {
                final String command;
                //command  = String.format("wmic /node:%s /user:%s /password:%s CPU get LoadPercentage", address, user, password);
                command  = "wmic  CPU get LoadPercentage";

                final NewSystemMonitor.ProcessOutput processOutput = executePowerShellCommand(command);
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
    public static void monitorWebserverDisks(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password){
        Scheduler.scheduleAtFixedRate(new MonitorWebserverDisks(systemMonitorPanel, address, user, password), 0, 300);
    }
    public static class MonitorWebserverDisks implements Runnable{
        private final NewSystemMonitorPanel systemMonitorPanel;

        private final String address;
        private final String user;
        private final String password;

        public MonitorWebserverDisks(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
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
                //command  = String.format("wmic /node:%s /user:%s /password:%s LogicalDisk get Name,Size,FreeSpace", address, user, password);
                command  = "wmic LogicalDisk get Name,Size,FreeSpace";

                final NewSystemMonitor.ProcessOutput processOutput = executePowerShellCommand(command);
                final BufferedReader psOut = processOutput.getOutput();
                final ArrayList<NewSystemMonitor.DiskResult> disks = new ArrayList<>();
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
                    systemMonitorPanel.addDiskInformation(disks);
                    ((GridLayout) systemMonitorPanel.disksTableContentPanel.getLayout()).setRows(disks.size());

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

    /** --------------Database--------------------------------
         * Schedule MonitorWebserverUptime at fixed rate
     * getting uptime by WMIC command and putting value on screen
     */
    public static void monitorDatabaseUptime(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
        Scheduler.scheduleAtFixedRate(new MonitorDatabaseUptime(systemMonitorPanel, address, user, password), 0, 60);
    }
    public static class MonitorDatabaseUptime implements Runnable{
        private final NewSystemMonitorPanel systemMonitorPanel;

        private final String address;
        private final String user;
        private final String password;

        public MonitorDatabaseUptime(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
            this.systemMonitorPanel = systemMonitorPanel;
            this.address = address;
            this.user = user;
            this.password = password;
        }

        @Override
        public void run() {
            try {
                final String command;
                command = "ssh student@192.168.1.2 uptime -p";
                final NewSystemMonitor.ProcessOutput processOutput = executePowerShellCommand(command);
                final BufferedReader psOut = processOutput.getOutput();
                final BufferedReader pserr = processOutput.getErrors();
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
                }
            } catch (PowerShellScriptException e) {
                e.printStackTrace();
            }


        }
    }
    /**
     * Schedule MonitorWebserverCpuUsage at fixed rate
     * getting cpu by WMIC command and putting value in the visual graph.
     */
    public static void monitorDatabaseCpuUsage(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
        Scheduler.scheduleAtFixedRate(new MonitorDatabaseCpuUsage(systemMonitorPanel, address, user, password), 0, 1);
    }
    public static class MonitorDatabaseCpuUsage implements Runnable {
        private final NewSystemMonitorPanel systemMonitorPanel;

        private final String address;
        private final String user;
        private final String password;

        public MonitorDatabaseCpuUsage(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
            this.systemMonitorPanel = systemMonitorPanel;
            this.address = address;
            this.user = user;
            this.password = password;
        }

        @Override
        public void run() {
            final String command;
            command = "ssh student@192.168.1.2 'sar 1 1 -u' | ConvertFrom-String -Delimiter ' ' | Select-Object p42";
            final ProcessOutput processOutput;
            try {
                processOutput = executePowerShellCommand(command);
            final BufferedReader psOut = processOutput.getOutput();
            final BufferedReader pserr = processOutput.getErrors();
                String line = null;
                    while ((line = psOut.readLine()) != null) {

                        line = line.trim();
                        if (line.isEmpty()) {
                            continue;

                        }
                        final String[] strArr = line.split(" ");

                        for (String partOfLine : strArr) {
                            if (partOfLine.matches("[0-9]+")){
                                systemMonitorPanel.appendCpuValueToGraph(Integer.parseInt(partOfLine));
                            }
                        }
                    }

            } catch (IOException | PowerShellScriptException e) {
                e.printStackTrace();
            }

        }
    }
    /**
     * Schedule MonitorWebserverCpuUsage at fixed rate
     * getting cpu by WMIC command and putting value in the visual graph.
     */
    public static void monitorDatabaseDisks(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
        Scheduler.scheduleAtFixedRate(new MonitorDatabaseDisks(systemMonitorPanel, address, user, password), 0, 300);
    }
    public static class MonitorDatabaseDisks implements Runnable {
        private final NewSystemMonitorPanel systemMonitorPanel;

        private final String address;
        private final String user;
        private final String password;

        public MonitorDatabaseDisks(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
            this.systemMonitorPanel = systemMonitorPanel;
            this.address = address;
            this.user = user;
            this.password = password;
        }

        @Override
        public void run() {
            final String command;
            command = "ssh student@192.168.1.2 df / | ConvertFrom-String -Delimiter “ “ | Select-Object p3, p7, p9";

            try {
                final NewSystemMonitor.ProcessOutput processOutput = executePowerShellCommand(command);
                final BufferedReader psOut = processOutput.getOutput();
                final BufferedReader pserr = processOutput.getErrors();
                final ArrayList<NewSystemMonitor.DiskResult> disks = new ArrayList<>();
                removeComponent(systemMonitorPanel);
                addComponent(systemMonitorPanel);

                String line = null;

                while ((line = psOut.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;

                    }
                    final String[] strArr = line.split(" ");
                    final String[] finalStrArr = null;
                    int count = 0;
                    for (String partOfLine:strArr) {
                        if (partOfLine.matches("[0-9]+")){
                            finalStrArr[count] = partOfLine;
                            final float diskFreeSpace = Long.parseLong(finalStrArr[1]) / 1024f;
                            final String diskName = "root";
                            final float diskTotalSpace = Long.parseLong(finalStrArr[0]) / 1024f;
                            disks.add(new NewSystemMonitor.DiskResult(diskName, diskTotalSpace, diskFreeSpace));
                            count++;
                        }
                    }
                    systemMonitorPanel.addDiskInformation(disks);
                    ((GridLayout) systemMonitorPanel.disksTableContentPanel.getLayout()).setRows(disks.size());
                }
            }catch (IOException | PowerShellScriptException | NullPointerException ex) {
                Logger.error(ex, "Failed to retrieve last boot-up time of system at address '%s'.");
            }

        }
    }
    /** ---------------------------------------------------------*/

    /** --------------Pfsense--------------------------------
    // Schedule MonitorWebserverUptime at fixed rate
    // getting uptime by WMIC command and putting value on screen
     */
    public static void monitorPfsenseUptime(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
        Scheduler.scheduleAtFixedRate(new MonitorPfsenseUptime(systemMonitorPanel, address, user, password), 0, 60);
    }
    public static class MonitorPfsenseUptime implements Runnable {
        private final NewSystemMonitorPanel systemMonitorPanel;

        private final String address;
        private final String user;
        private final String password;

        public MonitorPfsenseUptime(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
            this.systemMonitorPanel = systemMonitorPanel;
            this.address = address;
            this.user = user;
            this.password = password;
        }

        @Override
        public void run() {
            try {
                final String command;
                command = "ssh admin@192.168.1.1 uptime | ConvertFrom-String -Delimiter ' ' | Select-Object p4, p6";

                final NewSystemMonitor.ProcessOutput processOutput = executePowerShellCommand(command);
                final BufferedReader psOut = processOutput.getOutput();
                final BufferedReader pserr = processOutput.getErrors();
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
                }
            } catch (PowerShellScriptException e) {
                e.printStackTrace();
            }

        }
    }
    /**
     * Schedule MonitorWebserverCpuUsage at fixed rate
     * getting cpu by WMIC command and putting value in the visual graph.
     */
    public static void monitorPfsenseCpuUsage(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
        Scheduler.scheduleAtFixedRate(new MonitorPfsenseCpuUsage(systemMonitorPanel, address, user, password), 0, 60);
    }
    public static class MonitorPfsenseCpuUsage implements Runnable {
        private final NewSystemMonitorPanel systemMonitorPanel;

        private final String address;
        private final String user;
        private final String password;

        public MonitorPfsenseCpuUsage(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
            this.systemMonitorPanel = systemMonitorPanel;
            this.address = address;
            this.user = user;
            this.password = password;
        }

        @Override
        public void run() {

        }
    }
    /**
     * Schedule MonitorWebserverCpuUsage at fixed rate
     * getting cpu by WMIC command and putting value in the visual graph.
     */
    public static void monitorPfsenseDisks(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
        Scheduler.scheduleAtFixedRate(new MonitorPfsenseDisks(systemMonitorPanel, address, user, password), 0, 60);
    }
    public static class MonitorPfsenseDisks implements Runnable {
        private final NewSystemMonitorPanel systemMonitorPanel;

        private final String address;
        private final String user;
        private final String password;

        public MonitorPfsenseDisks(@NotNull final NewSystemMonitorPanel systemMonitorPanel, @NotNull final String address, @NotNull final String user, @NotNull final String password) {
            this.systemMonitorPanel = systemMonitorPanel;
            this.address = address;
            this.user = user;
            this.password = password;
        }

        @Override
        public void run() {

        }
    }
    /**-------------------------------------------------------*/
    /**----------localhost------------------------------------*/
    public static void monitorLocalhostUptime(@NotNull final NewSystemMonitorPanel systemMonitorPanel) {
        Scheduler.scheduleAtFixedRate(new MonitorLocalhostUptime(systemMonitorPanel), 0, 60);
    }
    public static class MonitorLocalhostUptime implements Runnable {
        private final NewSystemMonitorPanel systemMonitorPanel;
        public MonitorLocalhostUptime(@NotNull final NewSystemMonitorPanel systemMonitorPanel) {
            this.systemMonitorPanel = systemMonitorPanel;
        }

        @Override
        public void run() {
            final String command = "wmic path Win32_OperatingSystem get LastBootUpTime";

            try {
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
                                // Convert output to Instant
                                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                                Instant lastBootUptime = dateFormat.parse(strArr[0]).toInstant();
                                final Duration duration = Duration.between(lastBootUptime, Instant.now());

                                systemMonitorPanel.setUptimeValue(formatDuration(duration));

                            } catch (ParseException ex) {
                                Logger.error(ex, "Failed to parse last boot-up time '%s' into Instant.", strArr[0]);
                            }
                        }
                    }

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
    public static void monitorLocalhostCpuUsage(@NotNull final NewSystemMonitorPanel systemMonitorPanel) {
        Scheduler.scheduleAtFixedRate(new MonitorLocalhostCpuUsage(systemMonitorPanel), 0, 1);
    }
    public static class MonitorLocalhostCpuUsage implements Runnable {
        private final NewSystemMonitorPanel systemMonitorPanel;

        public MonitorLocalhostCpuUsage(@NotNull final NewSystemMonitorPanel systemMonitorPanel) {
            this.systemMonitorPanel = systemMonitorPanel;
        }
        @Override
        public void run() {
                try {
                    final String command;
                    //command  = String.format("wmic /node:%s /user:%s /password:%s CPU get LoadPercentage", address, user, password);
                    command  = "wmic CPU get LoadPercentage";

                    final NewSystemMonitor.ProcessOutput processOutput = executePowerShellCommand(command);
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
                        Logger.error(ex, "Failed to retrieve CPU load of system at address .");
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
    public static void monitorLocalhostDisks(@NotNull final NewSystemMonitorPanel systemMonitorPanel) {
        Scheduler.scheduleAtFixedRate(new MonitorLocalhostDisks(systemMonitorPanel), 0, 300);
    }
    public static class MonitorLocalhostDisks implements Runnable {
        private final NewSystemMonitorPanel systemMonitorPanel;


        public MonitorLocalhostDisks(@NotNull final NewSystemMonitorPanel systemMonitorPanel) {
            this.systemMonitorPanel = systemMonitorPanel;
        }

        @Override
        public void run() {
            try {
                // We have to first remove and then re-add the component for it to update


                final String command;
                //command  = String.format("wmic /node:%s /user:%s /password:%s LogicalDisk get Name,Size,FreeSpace", address, user, password);
                command  = "wmic LogicalDisk get Name,Size,FreeSpace";

                final NewSystemMonitor.ProcessOutput processOutput = executePowerShellCommand(command);
                final BufferedReader psOut = processOutput.getOutput();
                final ArrayList<NewSystemMonitor.DiskResult> disks = new ArrayList<>();
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
                    systemMonitorPanel.addDiskInformation(disks);
                    ((GridLayout) systemMonitorPanel.disksTableContentPanel.getLayout()).setRows(disks.size());

                    processOutput.close();
                } catch (IOException ex) {
                    Logger.error(ex, "Failed to retrieve storage disks of system at address .");
                }
            } catch (PowerShellScriptException ex) {
                Logger.error(ex, "Failed to run PowerShell command for retrieving disk(s) information of system at address.");
            }

        }

        }

    /**---------------------------------------------------------*/

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
    }        /**
     * Adds a {@link Component} to the panel.
     *
     * The {@code Component}.
     */
    protected static void addComponent(NewSystemMonitorPanel systemMonitorPanel) {

        systemMonitorPanel.disksTableContentPanel = new JPanel();
        systemMonitorPanel.disksTableContentPanel.setLayout(new GridLayout());
        systemMonitorPanel.disksTableContentPanel.setBorder(new EmptyBorder(2, 5, 0, 5));
        systemMonitorPanel.disksTableContentPanel.setBackground(Colors.MONITOR_TABLE_CONTENT);
        systemMonitorPanel.add(systemMonitorPanel.disksTableContentPanel);

    }

    /**
     * Removes a {@link Component} from the panel.
     *
     *  The {@code Component}.
     */
    protected static void removeComponent(NewSystemMonitorPanel systemMonitorPanel) {
        systemMonitorPanel.disksTableContentPanel.removeAll();
        systemMonitorPanel.remove(systemMonitorPanel.disksTableContentPanel);
    }
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
        result = StringUtils.replaceOnce(result, " 1 minutes", " 1 minute");
        result = StringUtils.replaceOnce(result, " 1 hours", " 1 hour");
        result = StringUtils.replaceOnce(result, "1 days", " 1 day");
        return result;
    }

}
