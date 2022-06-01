package com.nerdygadgets.application.util;

import com.nerdygadgets.application.app.panel.NewSystemMonitorPanel;
import com.nerdygadgets.application.app.panel.SystemMonitorPanel;
import com.nerdygadgets.application.exception.PowerShellScriptException;
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
        return getCpuLoad(null, null, null);
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
    public static Optional<Integer> getCpuLoad(@Nullable final String address, @Nullable final String user, @Nullable final String password) {
        Integer cpuLoad = null;

        try {
            final String command;

            if (address == null) {
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
        return getLastBootUpTime(null, null, null);
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
    public static Instant getLastBootUpTime(@Nullable final String address, @Nullable final String user, @Nullable final String password) {
        Instant lastBootUpTime = null;

        try {
            final String command;

            if (address == null) {
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
        return getDisks(null, null, null);
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
    public static ArrayList<DiskResult> getDisks(@Nullable final String address, @Nullable final String user, @Nullable final String password) {
        final ArrayList<DiskResult> disks = new ArrayList<>();

        try {
            final String command;

            if (address == null) {
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

    public static String testing() throws  PowerShellScriptException {
        final String command = null;
        // command = "ssh student@192.168.1.2 'sar 1 -u' | ConvertFrom-String -Delimiter ' ' | Select-Object p42";
        // ssh student@192.168.1.2 'sar 1 1 -u' | ConvertFrom-String -Delimiter ' ' | Select-Object p42  //cpu
        // ssh student@192.168.1.2 df / | ConvertFrom-String -Delimiter ' ' | Select-Object p3, p7, p9 //disk in total,free,in use %
        // ssh student@192.168.1.2 uptime -p                                                           //uptime in week,day,hours,minutes
        // ssh admin@192.168.1.1 top | ConvertFrom-String -Delimiter ' ' | Select-Object p14 -skip 2 -first 1
        // ssh admin@192.168.1.1 uptime                                                                   //uptime in week,day,hours,minutes
        // ssh admin@192.168.1.1 df / | ConvertFrom-String -Delimiter ' ' | Select-Object p3, p5, p9      //disk in total,free,in use %
        final ProcessOutput processOutput = executePowerShellCommand(command);
        final BufferedReader psOut = processOutput.getOutput();
        final BufferedReader pserr = processOutput.getErrors();

/* voor ssh student@192.168.1.2 uptime -p
        String a=psOut.readLine();
        a = a.replaceAll("[,]","");
        int indexOfSpace = a.indexOf(' ');
        if (indexOfSpace!= -1){
            a= a.substring(indexOfSpace+1);
        }
        System.out.println(a);//later return
*/
        String line = null;
        try {
            while ((line = psOut.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;

                }
                final String[] strArr = line.split(" ");
                for (String partOfLine:strArr) {
                    if (partOfLine.matches("[0-9]+")){
                        System.out.println(partOfLine);
                    }

                }

                System.out.println(line);

            }
        }catch (IOException ex) {
            Logger.error(ex, "Failed to retrieve last boot-up time of system at address '%s'.");
        }
        return line;
    }

    public static String getDiskInfo()throws PowerShellScriptException{
        final String command;
        command = "ssh student@192.168.1.2 df / | ConvertFrom-String -Delimiter “ “ | Select-Object p3, p7, p9";

        final String[] commandWords = command.split(" ");
        for (String word:commandWords) {
            if (word.equals("student@192.168.1.2")||word.equals("student@192.168.1.3")){

            }else if(word.equals("student@192.168.1.4")||word.equals("student@192.168.1.5")){

            }else if(word.equals("student@192.168.1.1")){

            }else{
                System.out.println("Cant find server");
            }
        }

        final ProcessOutput processOutput = executePowerShellCommand(command);
        final BufferedReader psOut = processOutput.getOutput();
        final BufferedReader pserr = processOutput.getErrors();

        String line = null;
        try {
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
                        new DiskResult(diskName, diskTotalSpace, diskFreeSpace);
                        count++;
                    }

                }
            }
        }catch (IOException ex) {
            Logger.error(ex, "Failed to retrieve last boot-up time of system at address '%s'.");
        }
        return line;
    }
    public static int getCpuInfo()throws PowerShellScriptException{
        final String command;
        int cpu = 0;
        command = "ssh student@192.168.1.2 'sar 1 1 -u' | ConvertFrom-String -Delimiter ' ' | Select-Object p42";

        final ProcessOutput processOutput = executePowerShellCommand(command);
        final BufferedReader psOut = processOutput.getOutput();
        final BufferedReader pserr = processOutput.getErrors();
        try {
            cpu = Integer.parseInt(psOut.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
/*        String line = null;
        try {
            while ((line = psOut.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;

                }
                final String[] strArr = line.split(" ");

                for (String partOfLine : strArr) {
                    if (partOfLine.matches("[0-9]+")){
                        cpu = Integer.parseInt(partOfLine);
                    }
                }
            }

        }catch (IOException ex) {
            Logger.error(ex, "Failed to retrieve last boot-up time of system at address '%s'.");
        }*/
        return cpu;
    }

    public static String getUptimeInfo() throws PowerShellScriptException {
        final String command;
        command = "ssh student@192.168.1.2 uptime -p";
        final ProcessOutput processOutput = executePowerShellCommand(command);
        final BufferedReader psOut = processOutput.getOutput();
        final BufferedReader pserr = processOutput.getErrors();
        String a= null;
        try {
            a = psOut.readLine();
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        a = a.replaceAll("[,]","");
        int indexOfSpace = a.indexOf(' ');
        if (indexOfSpace!= -1){
            a= a.substring(indexOfSpace+1);
        }
        return a;//later return

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
/*
     * Executes a given SSH command, and returns the results.
     *
     * @param command The command to execute.
     *
     * @return A {@link ProcessOutput} containing both the regular output, as well as the error output from executing the command.
     **//*
    private static ProcessOutput executeSshCommand(final String command) throws IOException {
        final Process process = Runtime.getRuntime().exec(new String[] { "ssh myhost", command });
        final BufferedReader psOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
        final BufferedReader psErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        return new ProcessOutput(psOut, psErr);

    }*/

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
    private class DiskUpdater implements Runnable{

        @Override
        public void run() {
            final ArrayList<SystemMonitor.DiskResult> sshDisks = getLocalDisks();
        }
    }
}
