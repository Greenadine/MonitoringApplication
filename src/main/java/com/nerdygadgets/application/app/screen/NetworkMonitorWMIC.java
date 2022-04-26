package com.nerdygadgets.application.app.screen;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class NetworkMonitorWMIC
{
    private ArrayList<String> output;
    public NetworkMonitorWMIC() throws IOException
    {
        output = new ArrayList<>();

        //String command = "powershell.exe  your command";
        //Getting the version
        String command = "powershell.exe  C:\\Users\\jesse\\IdeaProjects\\NerdyGadgetsApplicatie\\src\\main\\java\\com\\nerdygadgets\\application\\app\\screen\\WMICCommands.ps1";
        // Executing the command
        Process powerShellProcess = Runtime.getRuntime().exec(command);
        // Getting the results
        powerShellProcess.getOutputStream().close();
        String line;
        System.out.println("Standard Output:");
        BufferedReader stdout = new BufferedReader(new InputStreamReader(
                powerShellProcess.getInputStream()));
        while ((line = stdout.readLine()) != null) {
            System.out.println(line);
            output.add(line);

        }
        stdout.close();
//        System.out.println("Standard Error:");
//        BufferedReader stderr = new BufferedReader(new InputStreamReader(
//                powerShellProcess.getErrorStream()));
//        while ((line = stderr.readLine()) != null) {
//            System.out.println(line);
//        }
//        stderr.close();
//        System.out.println("Done");

    }

    public ArrayList<String> getOutput()
    {
        return output;
    }

}









