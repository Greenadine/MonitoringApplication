package com.nerdygadgets.application.util;

import com.nerdygadgets.application.app.panel.SystemMonitorPanel;

import javax.annotation.Nullable;
import java.util.concurrent.Future;

public class SystemMonitorSchedulers {

    private final int type;
    private final SystemMonitorPanel panel;
    private final String address;
    private final String user;
    private final String password;


    private Future<?> uptimeScheduler;
    private Future<?> cpuUsageScheduler;
    private Future<?> disksInformationScheduler;

    public SystemMonitorSchedulers(final SystemMonitorPanel panel, @Nullable final String address, @Nullable final String user, @Nullable final String password, final int type) {
        this.type = type;
        this.panel = panel;
        this.address = address;
        this.user = user;
        this.password = password;
    }

    /**
     * (Re)schedules the monitoring {@link Scheduler}s for the system.
     */
    public void schedule() {
        if (uptimeScheduler != null) {
            cancel();
        }
        switch (type) {
            default: {
                throw new IllegalArgumentException("Invalid type");
            }
            case 0: {
                // pfSense
                uptimeScheduler = SystemMonitor.monitorPfsenseUptime(panel);
                cpuUsageScheduler = SystemMonitor.monitorPfsenseCpuUsage(panel);
                disksInformationScheduler = SystemMonitor.monitorPfsenseDisks(panel);
                break;
            }
            case 1: {
                // Webservers
                if (address == null || user == null || password == null) {
                    throw new IllegalArgumentException("Address, user, password is required for monitoring webservers.");
                }

                uptimeScheduler = SystemMonitor.monitorWebserverUptime(panel, address, user, password);
                cpuUsageScheduler = SystemMonitor.monitorWebserverCpuUsage(panel, address, user, password);
                disksInformationScheduler = SystemMonitor.monitorWebserverDisks(panel, address, user, password);
                break;
            }
            case 2: {
                // Databases
                if (address == null) {
                    throw new IllegalArgumentException("Address is required for monitoring databases.");
                }
                uptimeScheduler = SystemMonitor.monitorDatabaseUptime(panel, address);
                cpuUsageScheduler = SystemMonitor.monitorDatabaseCpuUsage(panel,address);
                disksInformationScheduler = SystemMonitor.monitorDatabaseDisks(panel, address);
                break;
            }
            case 3: {
                // Localhost
                uptimeScheduler = SystemMonitor.monitorLocalhostUptime(panel);
                cpuUsageScheduler = SystemMonitor.monitorLocalhostCpuUsage(panel);
                disksInformationScheduler = SystemMonitor.monitorLocalhostDisks(panel);
                break;
            }
        }
    }

    /**
     * Cancels the monitoring {@link Scheduler}s for the system.
     */
    public void cancel() {
        if (uptimeScheduler != null) {
            uptimeScheduler.cancel(true);
            cpuUsageScheduler.cancel(true);
            disksInformationScheduler.cancel(true);

            uptimeScheduler = null;
            cpuUsageScheduler = null;
            disksInformationScheduler = null;
        }
    }
}
