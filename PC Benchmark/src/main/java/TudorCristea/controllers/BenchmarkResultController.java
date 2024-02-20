package TudorCristea.controllers;

import TudorCristea.models.CPU.CPUTesting;
import TudorCristea.models.Disk.DiskTesting;
import TudorCristea.models.GPU.GPUTesting;
import TudorCristea.models.Memory.MemoryTesting;
import TudorCristea.models.Network.NetworkTesting;
import TudorCristea.views.BenchmarkResultView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BenchmarkResultController
{
    private BenchmarkResultView benchmarkResultView;

    private CPUTesting cpuTesting;
    private GPUTesting gpuTesting;
    private MemoryTesting memoryTesting;
    private DiskTesting diskTesting;
    private NetworkTesting networkTesting;

    private double cpuWeight;
    private double gpuWeight;
    private double memoryWeight;
    private double diskWeight;
    private double networkWeight;

    private String cpuName, cpuArchitecture, cpuClockFrequency, cpuNumberOfCores, cpuNumberOfLogicalProcessors;
    private String gpuName, gpuMemory, gpuArchitecture, gpuCurrentResolution, gpuCurrentRefreshRate;
    private String memoryManufacturer, memoryFrequency, memoryCapacity;
    private String diskName, diskCapacity, diskFileSystemType;
    private String networkName;

    protected BenchmarkResultController(BenchmarkResultView benchmarkResultView, double cpuWeight, double gpuWeight, double memoryWeight, double diskWeight, double networkWeight, String cpuName, String cpuArchitecture, String cpuClockFrequency, String cpuNumberOfCores, String cpuNumberOfLogicalProcessors, String gpuName, String gpuMemory, String gpuArchitecture, String gpuCurrentResolution, String gpuCurrentRefreshRate, String memoryManufacturer, String memoryFrequency, String memoryCapacity, String diskName, String diskCapacity, String diskFileSystemType, String networkName)
    {
        this.benchmarkResultView = benchmarkResultView;

        this.cpuWeight = cpuWeight;
        this.gpuWeight = gpuWeight;
        this.memoryWeight = memoryWeight;
        this.diskWeight = diskWeight;
        this.networkWeight = networkWeight;

        this.cpuName = cpuName;
        this.cpuArchitecture = cpuArchitecture;
        this.cpuClockFrequency = cpuClockFrequency;
        this.cpuNumberOfCores = cpuNumberOfCores;
        this.cpuNumberOfLogicalProcessors = cpuNumberOfLogicalProcessors;
        this.gpuName = gpuName;
        this.gpuMemory = gpuMemory;
        this.gpuArchitecture = gpuArchitecture;
        this.gpuCurrentResolution = gpuCurrentResolution;
        this.gpuCurrentRefreshRate = gpuCurrentRefreshRate;
        this.memoryManufacturer = memoryManufacturer;
        this.memoryFrequency = memoryFrequency;
        this.memoryCapacity = memoryCapacity;
        this.diskName = diskName;
        this.diskCapacity = diskCapacity;
        this.diskFileSystemType = diskFileSystemType;
        this.networkName = networkName;
    }

    public void start()
    {
        double totalScore = 0;

        double cpuScore = 0;
        String cpuScoreString = "";
        if (cpuWeight != 0.0)
        {
            cpuScore = cpuTesting.start();
            cpuScoreString = String.valueOf((long)cpuScore);

            totalScore += (cpuScore * cpuWeight);
        }
        else
        {
            cpuScoreString = "-";
        }
        benchmarkResultView.getCpuScoreLabel().setText(cpuScoreString);

        double gpuScore = 0;
        String gpuScoreString = "";
        if (gpuWeight != 0.0)
        {
            gpuScore = gpuTesting.start();
            gpuScoreString = String.valueOf((long)gpuScore);

            totalScore += (gpuScore * gpuWeight);
        }
        else
        {
            gpuScoreString = "-";
        }
        benchmarkResultView.getGpuScoreLabel().setText(gpuScoreString);

        double memoryScore = 0;
        String memoryScoreString = "";
        if (memoryWeight != 0.0)
        {
            memoryScore = memoryTesting.start();
            memoryScoreString = String.valueOf((long)memoryScore);

            totalScore += (memoryScore * memoryWeight);
        }
        else
        {
            memoryScoreString = "-";
        }
        benchmarkResultView.getMemoryScoreLabel().setText(memoryScoreString);

        double diskScore = 0;
        String diskScoreString = "";
        if (diskWeight != 0.0)
        {
            diskScore = diskTesting.start();
            diskScoreString = String.valueOf((long)diskScore);

            totalScore += (diskScore * diskWeight);
        }
        else
        {
            diskScoreString = "-";
        }
        benchmarkResultView.getDiskScoreLabel().setText(diskScoreString);

        double networkScore = 0;
        String networkScoreString = "";
        if (networkWeight != 0.0)
        {
            networkScore = networkTesting.start();
            networkScoreString = String.valueOf((long)networkScore);

            totalScore += (networkScore * networkWeight);
        }
        else
        {
            networkScoreString = "-";
        }
        benchmarkResultView.getNetworkScoreLabel().setText(networkScoreString);

        String totalScoreString = String.valueOf((long)totalScore);
        benchmarkResultView.getTotalScoreLabel().setText(totalScoreString);

        try
        {
            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\log.txt", true);

            LocalDateTime currentTimestamp = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedTimestamp = currentTimestamp.format(formatter);

            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\software_info.txt"));
            String osName = br.readLine();
            String osArchitecture = br.readLine();
            String osVersion = br.readLine();

            writer.write("Time:" + formattedTimestamp + "|__|OS Name:" + osName + "|__|OS Architecture:" + osArchitecture + "|__|OS Version:" + osVersion + "|__|CPU Name:" + cpuName + "|__|CPU Architecture:" + cpuArchitecture + "|__|CPU Clock Frequency:" + cpuClockFrequency + "|__|CPU Number of Cores:" + cpuNumberOfCores + "|__|CPU Number of Logical Processors:" + cpuNumberOfLogicalProcessors + "|__|GPU Name:" + gpuName + "|__|GPU Memory:" + gpuMemory + "|__|GPU Architecture:" + gpuArchitecture + "|__|GPU Current Resolution:" + gpuCurrentResolution + "|__|GPU Current Refresh Rate:" + gpuCurrentRefreshRate + "|__|Memory Manufacturer:" + memoryManufacturer + "|__|Memory Frequency:" + memoryFrequency + "|__|Memory Capacity:" + memoryCapacity + "|__|Disk Name:" + diskName + "|__|Disk Capacity:" + diskCapacity + "|__|Disk File System Type:" + diskFileSystemType + "|__|Network Name:" + networkName + "|__|CPU Weight:" + String.valueOf(cpuWeight) + "|__|GPU Weight:" + String.valueOf(gpuWeight) + "|__|Memory Weight:" + String.valueOf(memoryWeight) + "|__|Disk Weight:" + String.valueOf(diskWeight) + "|__|Network Weight:" + String.valueOf(networkWeight) + "|__|CPU Score:" + cpuScoreString + "|__|GPU Score:" + gpuScoreString + "|__|Memory Score:" + memoryScoreString + "|__|Disk Score:" + diskScoreString + "|__|Network Score:" + networkScoreString + "|__|TOTAL SCORE:" + totalScoreString + "\n");
            writer.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setCpuTesting(CPUTesting cpuTesting)
    {
        this.cpuTesting = cpuTesting;
    }

    public void setGpuTesting(GPUTesting gpuTesting)
    {
        this.gpuTesting = gpuTesting;
    }

    public void setMemoryTesting(MemoryTesting memoryTesting)
    {
        this.memoryTesting = memoryTesting;
    }

    public void setDiskTesting(DiskTesting diskTesting)
    {
        this.diskTesting = diskTesting;
    }

    public void setNetworkTesting(NetworkTesting networkTesting)
    {
        this.networkTesting = networkTesting;
    }
}