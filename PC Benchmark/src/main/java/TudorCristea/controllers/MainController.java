package TudorCristea.controllers;

import TudorCristea.models.CPU.CPUTesting;
import TudorCristea.models.Disk.DiskTesting;
import TudorCristea.models.GPU.GPUTesting;
import TudorCristea.models.Memory.MemoryTesting;
import TudorCristea.models.Network.NetworkTesting;
import TudorCristea.views.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainController
{
    private CPUTesting cpuTesting;
    private GPUTesting gpuTesting;
    private MemoryTesting memoryTesting;
    private DiskTesting diskTesting;
    private NetworkTesting networkTesting;

    private MainView view;

    private BenchmarkResultView benchmarkResultView;
    private LogFileView logFileView;

    private BenchmarkResultController benchmarkResultController;
    private LogFileController logFileController;

    private WindowListener windowListener;

    public MainController(MainView mainView)
    {
        this.view = mainView;
        this.view.setEnabled(false);

        windowListener = new WindowListener()
        {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e)
            {
                view.setEnabled(true);
            }

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        };

        this.view.addLogFileButtonListener(new LogFileButtonListener());
        this.view.addStartBenchmarkButtonListener(new StartBenchmarkButtonListener());

        try
        {
            Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\SIGAR.exe");
            process.waitFor();

            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\hardware_info.txt"));
            String line;

            String activeComponent = "";
            int lineNumber = 0;
            while ((line = br.readLine()) != null)
            {
                if (line.startsWith("CPU"))
                {
                    activeComponent = "CPU";
                    lineNumber = 0;
                }
                else if (line.startsWith("GPU"))
                {
                    activeComponent = "GPU";
                    lineNumber = 0;
                }
                else if (line.startsWith("Memory"))
                {
                    activeComponent = "Memory";
                    lineNumber = 0;
                }
                else if (line.startsWith("Disk"))
                {
                    activeComponent = "Disk";
                    lineNumber = 0;
                }
                else if (line.startsWith("Network"))
                {
                    activeComponent = "Network";
                    lineNumber = 0;
                }
                else
                {
                    ++lineNumber;
                    if (activeComponent.equals("CPU"))
                    {
                        switch (lineNumber)
                        {
                            case 1:
                            {
                                this.view.getCpuNameTextField().setText(line);
                                break;
                            }
                            case 2:
                            {
                                this.view.getCpuArchitectureTextField().setText(line);
                                break;
                            }
                            case 3:
                            {
                                this.view.getCpuClockFrequencyTextField().setText(line);
                                break;
                            }
                            case 4:
                            {
                                this.view.getCpuNumberOfCoresTextField().setText(line);
                                break;
                            }
                            case 5:
                            {
                                this.view.getCpuNumberOfLogicalProcessorsTextField().setText(line);
                                break;
                            }
                            default: break;
                        }
                    }
                    else if (activeComponent.equals("GPU"))
                    {
                        switch (lineNumber)
                        {
                            case 1:
                            {
                                this.view.getGpuNameTextField().setText(line);
                                break;
                            }
                            case 2:
                            {
                                this.view.getGpuMemoryTextField().setText(line);
                                break;
                            }
                            case 3:
                            {
                                this.view.getGpuArchitectureTextField().setText(line);
                                break;
                            }
                            case 4:
                            {
                                this.view.getGpuCurrentResolutionTextField().setText(line);
                                break;
                            }
                            case 5:
                            {
                                this.view.getGpuCurrentRefreshRateTextField().setText(line);
                                break;
                            }
                            default: break;
                        }
                    }
                    else if (activeComponent.equals("Memory"))
                    {
                        switch (lineNumber)
                        {
                            case 1:
                            {
                                this.view.getMemoryManufacturerTextField().setText(line);
                                break;
                            }
                            case 2:
                            {
                                this.view.getMemoryFrequencyTextField().setText(line);
                                break;
                            }
                            case 3:
                            {
                                this.view.getMemoryCapacityTextField().setText(line);
                                break;
                            }
                            default: break;
                        }
                    }
                    else if (activeComponent.equals("Disk"))
                    {
                        switch (lineNumber)
                        {
                            case 1:
                            {
                                this.view.getDiskNameTextField().setText(line);
                                break;
                            }
                            case 2:
                            {
                                this.view.getDiskCapacityTextField().setText(line);
                                break;
                            }
                            case 3:
                            {
                                this.view.getDiskFileSystemTypeTextField().setText(line);
                                break;
                            }
                            default: break;
                        }
                    }
                    else if (activeComponent.equals("Network"))
                    {
                        switch (lineNumber)
                        {
                            case 1:
                            {
                                this.view.getNetworkNameTextField().setText(line);
                                break;
                            }
                            default: break;
                        }
                    }
                }
            }

        }
        catch (Exception ignored)
        {

        }

        this.view.setEnabled(true);
    }

    private boolean isEmptyTextField(String weight)
    {
        return (weight == null || weight.isEmpty());
    }

    private boolean isValidWeight(String weight)
    {
        String regex = "^\\d+(\\.\\d{1,2})?$";
        return Pattern.matches(regex, weight);
    }

    private boolean isSumOfWeightsEqualToOne(List<Double> weightList)
    {
        double sum = 0.0;
        for (double weight: weightList)
        {
            sum += weight;
        }
        return (sum == 1.0);
    }

    class StartBenchmarkButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            boolean error = false;
            String errorMessage = "";

            List<String> weightList = new ArrayList<>();
            weightList.add(view.getCpuWeightTextField().getText());
            weightList.add(view.getGpuWeightTextField().getText());
            weightList.add(view.getMemoryWeightTextField().getText());
            weightList.add(view.getDiskWeightTextField().getText());
            weightList.add(view.getNetworkWeightTextField().getText());
            for (String s: weightList)
            {
                if (isEmptyTextField(s))
                {
                    error = true;
                    errorMessage = "At least one of the weight text fields is empty!";
                    break;
                }

                if (!isValidWeight(s))
                {
                    error = true;
                    errorMessage = "At least one of the weights has more than two decimal digits!";
                    break;
                }
            }

            List<Double> weightList2 = new ArrayList<>();
            if (!error)
            {
                for (String s: weightList)
                {
                    weightList2.add(Double.parseDouble(s));
                }
                if (!isSumOfWeightsEqualToOne(weightList2))
                {
                    error = true;
                    errorMessage = "The sum of the weights is not equal to 1!";
                }
            }

            if (error)
            {
                view.showErrorMessage(errorMessage);
            }
            else
            {
                view.setEnabled(false);

                if (benchmarkResultView == null)
                {
                    benchmarkResultView = new BenchmarkResultView(windowListener);
                }

                benchmarkResultController = new BenchmarkResultController(benchmarkResultView, weightList2.get(0), weightList2.get(1), weightList2.get(2), weightList2.get(3), weightList2.get(4), view.getCpuNameTextField().getText(), view.getCpuArchitectureTextField().getText(), view.getCpuClockFrequencyTextField().getText(), view.getCpuNumberOfCoresTextField().getText(), view.getCpuNumberOfLogicalProcessorsTextField().getText(), view.getGpuNameTextField().getText(), view.getGpuMemoryTextField().getText(), view.getGpuArchitectureTextField().getText(), view.getGpuCurrentResolutionTextField().getText(), view.getGpuCurrentRefreshRateTextField().getText(), view.getMemoryManufacturerTextField().getText(), view.getMemoryFrequencyTextField().getText(), view.getMemoryCapacityTextField().getText(), view.getDiskNameTextField().getText(), view.getDiskCapacityTextField().getText(), view.getDiskFileSystemTypeTextField().getText(), view.getNetworkNameTextField().getText());

                if (cpuTesting == null)
                {
                    cpuTesting = new CPUTesting(20);
                }
                benchmarkResultController.setCpuTesting(cpuTesting);
                if (gpuTesting == null)
                {
                    gpuTesting = new GPUTesting(5);
                }
                benchmarkResultController.setGpuTesting(gpuTesting);
                if (memoryTesting == null)
                {
                    memoryTesting = new MemoryTesting(20);
                }
                benchmarkResultController.setMemoryTesting(memoryTesting);
                if (diskTesting == null)
                {
                    diskTesting = new DiskTesting(20);
                }
                benchmarkResultController.setDiskTesting(diskTesting);
                if (networkTesting == null)
                {
                    networkTesting = new NetworkTesting(20);
                }
                benchmarkResultController.setNetworkTesting(networkTesting);

                benchmarkResultView.setEnabled(true);
                benchmarkResultView.setVisible(true);
                benchmarkResultController.start();
            }
        }
    }

    class LogFileButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            view.setEnabled(false);

            if (logFileView == null)
            {
                logFileView = new LogFileView(windowListener);
            }
            if (logFileController == null)
            {
                logFileController = new LogFileController(logFileView);
            }

            logFileView.setVisible(true);
            logFileView.setEnabled(true);
            logFileController.start();
        }
    }
}