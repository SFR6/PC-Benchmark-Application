package TudorCristea.controllers;

import TudorCristea.views.LogFileView;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;

public class LogFileController
{
    private LogFileView logFileView;

    public LogFileController(LogFileView logFileView)
    {
        this.logFileView = logFileView;
    }

    public void start()
    {
        ((DefaultTableModel) logFileView.getTable().getModel()).setRowCount(0);

        try
        {
            BufferedReader bf = new BufferedReader(new FileReader("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\log.txt"));
            String line;
            while ((line=bf.readLine()) != null)
            {
                String[] parts = line.split("\\|__\\|");
                String time = "", cpuWeight = "", gpuWeight = "", memoryWeight = "", diskWeight = "", networkWeight = "", cpuScore = "", gpuScore = "", memoryScore = "", diskScore = "", networkScore = "", totalScore = "";
                for (String s : parts)
                {
                    if (s.startsWith("Time:"))
                    {
                        time = s.substring("Time:".length());
                    }
                    else if (s.startsWith("CPU Weight:"))
                    {
                        cpuWeight = s.substring("CPU Weight:".length());
                    }
                    else if (s.startsWith("GPU Weight:"))
                    {
                        gpuWeight = s.substring("GPU Weight:".length());
                    }
                    else if (s.startsWith("Memory Weight:"))
                    {
                        memoryWeight = s.substring("Memory Weight:".length());
                    }
                    else if (s.startsWith("Disk Weight:"))
                    {
                        diskWeight = s.substring("Disk Weight:".length());
                    }
                    else if (s.startsWith("Network Weight:"))
                    {
                        networkWeight = s.substring("Network Weight:".length());
                    }
                    else if (s.startsWith("CPU Score:"))
                    {
                        cpuScore = s.substring("CPU Score:".length());
                    }
                    else if (s.startsWith("GPU Score:"))
                    {
                        gpuScore = s.substring("GPU Score:".length());
                    }
                    else if (s.startsWith("Memory Score:"))
                    {
                        memoryScore = s.substring("Memory Score:".length());
                    }
                    else if (s.startsWith("Disk Score:"))
                    {
                        diskScore = s.substring("Disk Score:".length());
                    }
                    else if (s.startsWith("Network Score:"))
                    {
                        networkScore = s.substring("Network Score:".length());
                    }
                    else if (s.startsWith("TOTAL SCORE:"))
                    {
                        totalScore = s.substring("TOTAL SCORE:".length());
                    }
                }
                ((DefaultTableModel) logFileView.getTable().getModel()).addRow(new String[]{time, cpuWeight, gpuWeight, memoryWeight, diskWeight, networkWeight, cpuScore, gpuScore, memoryScore, diskScore, networkScore, totalScore});
                logFileView.getTable().repaint();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}