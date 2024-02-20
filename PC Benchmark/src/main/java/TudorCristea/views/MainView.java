package TudorCristea.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame
{
    class MyButton extends JButton
    {
        private Color hoverBackgroundColor;
        private Color pressedBackgroundColor;

        public MyButton(String text)
        {
            super(text);
            super.setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            if (getModel().isPressed())
            {
                g.setColor(pressedBackgroundColor);
            }
            else if (getModel().isRollover())
            {
                g.setColor(hoverBackgroundColor);
            }
            else
            {
                g.setColor(getBackground());
            }
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }

        public void setHoverBackgroundColor(Color hoverBackgroundColor)
        {
            this.hoverBackgroundColor = hoverBackgroundColor;
        }

        public void setPressedBackgroundColor(Color pressedBackgroundColor)
        {
            this.pressedBackgroundColor = pressedBackgroundColor;
        }
    }

    private JTextField cpuNameTextField, cpuArchitectureTextField, cpuClockFrequencyTextField, cpuNumberOfCoresTextField, cpuNumberOfLogicalProcessorsTextField;
    private JTextField gpuNameTextField, gpuArchitectureTextField, gpuMemoryTextField, gpuCurrentResolutionTextField, gpuCurrentRefreshRateTextField;
    private JTextField memoryFrequencyTextField, memoryCapacityTextField, memoryManufacturerTextField;
    private JTextField diskNameTextField, diskCapacityTextField, diskFileSystemTypeTextField;
    private JTextField networkNameTextField;
    private JTextField cpuWeightTextField, gpuWeightTextField, memoryWeightTextField, diskWeightTextField, networkWeightTextField;

    private MyButton startBenchmarkButton, logFileButton;

    public MainView()
    {
        this.setResizable(false);
        this.setTitle("PC Benchmark Application - Setup");
        this.getContentPane().setBackground(new Color(153, 204, 255));
        this.getContentPane().setLayout(null);

        JLabel cpuLabel = new JLabel("CPU");
        cpuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cpuLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        cpuLabel.setBounds(109, 10, 102, 42);
        this.getContentPane().add(cpuLabel);

        JLabel gpuLabel = new JLabel("GPU");
        gpuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gpuLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        gpuLabel.setBounds(410, 10, 102, 42);
        this.getContentPane().add(gpuLabel);

        JLabel memoryLabel = new JLabel("Memory");
        memoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        memoryLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        memoryLabel.setBounds(679, 10, 102, 42);
        this.getContentPane().add(memoryLabel);

        JLabel cpuNameLabel = new JLabel("Name: ");
        cpuNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        cpuNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        cpuNameLabel.setBounds(128, 77, 60, 42);
        this.getContentPane().add(cpuNameLabel);

        cpuNameTextField = new JTextField();
        cpuNameTextField.setEditable(false);
        cpuNameTextField.setHorizontalAlignment(SwingConstants.LEFT);
        cpuNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 6));
        cpuNameTextField.setBounds(187, 77, 130, 42);
        this.getContentPane().add(cpuNameTextField);
        cpuNameTextField.setColumns(100);

        JLabel cpuArchitectureLabel = new JLabel("Architecture: ");
        cpuArchitectureLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        cpuArchitectureLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        cpuArchitectureLabel.setBounds(97, 129, 91, 42);
        this.getContentPane().add(cpuArchitectureLabel);

        cpuArchitectureTextField = new JTextField();
        cpuArchitectureTextField.setHorizontalAlignment(SwingConstants.LEFT);
        cpuArchitectureTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        cpuArchitectureTextField.setEditable(false);
        cpuArchitectureTextField.setColumns(100);
        cpuArchitectureTextField.setBounds(187, 129, 130, 42);
        this.getContentPane().add(cpuArchitectureTextField);

        JLabel cpuClockFrequencyLabel = new JLabel("Clock Frequency: ");
        cpuClockFrequencyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        cpuClockFrequencyLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        cpuClockFrequencyLabel.setBounds(75, 181, 113, 42);
        this.getContentPane().add(cpuClockFrequencyLabel);

        cpuClockFrequencyTextField = new JTextField();
        cpuClockFrequencyTextField.setHorizontalAlignment(SwingConstants.LEFT);
        cpuClockFrequencyTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        cpuClockFrequencyTextField.setEditable(false);
        cpuClockFrequencyTextField.setColumns(100);
        cpuClockFrequencyTextField.setBounds(187, 181, 130, 42);
        this.getContentPane().add(cpuClockFrequencyTextField);

        JLabel cpuNumberOfCoresLabel = new JLabel("Number of Cores:");
        cpuNumberOfCoresLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        cpuNumberOfCoresLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        cpuNumberOfCoresLabel.setBounds(64, 233, 116, 42);
        this.getContentPane().add(cpuNumberOfCoresLabel);

        cpuNumberOfCoresTextField = new JTextField();
        cpuNumberOfCoresTextField.setHorizontalAlignment(SwingConstants.LEFT);
        cpuNumberOfCoresTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        cpuNumberOfCoresTextField.setEditable(false);
        cpuNumberOfCoresTextField.setColumns(100);
        cpuNumberOfCoresTextField.setBounds(187, 233, 130, 42);
        this.getContentPane().add(cpuNumberOfCoresTextField);

        JLabel cpuNumberOfLogicalProcessorsLabel = new JLabel("Number of Logical Processors:");
        cpuNumberOfLogicalProcessorsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        cpuNumberOfLogicalProcessorsLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        cpuNumberOfLogicalProcessorsLabel.setBounds(-11, 280, 188, 42);
        this.getContentPane().add(cpuNumberOfLogicalProcessorsLabel);

        cpuNumberOfLogicalProcessorsTextField = new JTextField();
        cpuNumberOfLogicalProcessorsTextField.setHorizontalAlignment(SwingConstants.LEFT);
        cpuNumberOfLogicalProcessorsTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        cpuNumberOfLogicalProcessorsTextField.setEditable(false);
        cpuNumberOfLogicalProcessorsTextField.setColumns(100);
        cpuNumberOfLogicalProcessorsTextField.setBounds(187, 280, 130, 42);
        this.getContentPane().add(cpuNumberOfLogicalProcessorsTextField);

        JLabel gpuNameLabel = new JLabel("Name: ");
        gpuNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gpuNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        gpuNameLabel.setBounds(410, 77, 54, 42);
        this.getContentPane().add(gpuNameLabel);

        gpuNameTextField = new JTextField();
        gpuNameTextField.setHorizontalAlignment(SwingConstants.LEFT);
        gpuNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 9));
        gpuNameTextField.setEditable(false);
        gpuNameTextField.setColumns(100);
        gpuNameTextField.setBounds(468, 80, 130, 42);
        this.getContentPane().add(gpuNameTextField);

        JLabel gpuMemoryLabel = new JLabel("Memory:");
        gpuMemoryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gpuMemoryLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        gpuMemoryLabel.setBounds(395, 129, 69, 42);
        this.getContentPane().add(gpuMemoryLabel);

        gpuMemoryTextField = new JTextField();
        gpuMemoryTextField.setHorizontalAlignment(SwingConstants.LEFT);
        gpuMemoryTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gpuMemoryTextField.setEditable(false);
        gpuMemoryTextField.setColumns(100);
        gpuMemoryTextField.setBounds(468, 132, 130, 42);
        this.getContentPane().add(gpuMemoryTextField);

        JLabel gpuArchitectureLabel = new JLabel("Architecture:");
        gpuArchitectureLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gpuArchitectureLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        gpuArchitectureLabel.setBounds(373, 181, 91, 42);
        this.getContentPane().add(gpuArchitectureLabel);

        gpuArchitectureTextField = new JTextField();
        gpuArchitectureTextField.setHorizontalAlignment(SwingConstants.LEFT);
        gpuArchitectureTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gpuArchitectureTextField.setEditable(false);
        gpuArchitectureTextField.setColumns(100);
        gpuArchitectureTextField.setBounds(468, 184, 130, 42);
        this.getContentPane().add(gpuArchitectureTextField);

        JLabel gpuCurrentResolutionLabel = new JLabel("Current Resolution:");
        gpuCurrentResolutionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gpuCurrentResolutionLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        gpuCurrentResolutionLabel.setBounds(335, 228, 130, 42);
        this.getContentPane().add(gpuCurrentResolutionLabel);

        gpuCurrentResolutionTextField = new JTextField();
        gpuCurrentResolutionTextField.setHorizontalAlignment(SwingConstants.LEFT);
        gpuCurrentResolutionTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gpuCurrentResolutionTextField.setEditable(false);
        gpuCurrentResolutionTextField.setColumns(100);
        gpuCurrentResolutionTextField.setBounds(468, 231, 130, 42);
        this.getContentPane().add(gpuCurrentResolutionTextField);

        JLabel gpuCurrentRefreshRateLabel = new JLabel("Current Refresh Rate::");
        gpuCurrentRefreshRateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gpuCurrentRefreshRateLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        gpuCurrentRefreshRateLabel.setBounds(335, 280, 130, 42);
        this.getContentPane().add(gpuCurrentRefreshRateLabel);

        gpuCurrentRefreshRateTextField = new JTextField();
        gpuCurrentRefreshRateTextField.setHorizontalAlignment(SwingConstants.LEFT);
        gpuCurrentRefreshRateTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gpuCurrentRefreshRateTextField.setEditable(false);
        gpuCurrentRefreshRateTextField.setColumns(100);
        gpuCurrentRefreshRateTextField.setBounds(468, 280, 130, 42);
        this.getContentPane().add(gpuCurrentRefreshRateTextField);

        JLabel memoryManufacturerLabel = new JLabel("Manufacturer: ");
        memoryManufacturerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        memoryManufacturerLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        memoryManufacturerLabel.setBounds(612, 77, 97, 42);
        this.getContentPane().add(memoryManufacturerLabel);

        memoryManufacturerTextField = new JTextField();
        memoryManufacturerTextField.setHorizontalAlignment(SwingConstants.LEFT);
        memoryManufacturerTextField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        memoryManufacturerTextField.setEditable(false);
        memoryManufacturerTextField.setColumns(100);
        memoryManufacturerTextField.setBounds(719, 77, 130, 42);
        this.getContentPane().add(memoryManufacturerTextField);

        JLabel memoryFrequencyLabel = new JLabel("Frequency: ");
        memoryFrequencyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        memoryFrequencyLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        memoryFrequencyLabel.setBounds(622, 129, 87, 42);
        this.getContentPane().add(memoryFrequencyLabel);

        memoryFrequencyTextField = new JTextField();
        memoryFrequencyTextField.setHorizontalAlignment(SwingConstants.LEFT);
        memoryFrequencyTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        memoryFrequencyTextField.setEditable(false);
        memoryFrequencyTextField.setColumns(100);
        memoryFrequencyTextField.setBounds(719, 132, 130, 42);
        this.getContentPane().add(memoryFrequencyTextField);

        JLabel memoryCapacityLabel = new JLabel("Capacity: ");
        memoryCapacityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        memoryCapacityLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        memoryCapacityLabel.setBounds(632, 181, 77, 42);
        this.getContentPane().add(memoryCapacityLabel);

        memoryCapacityTextField = new JTextField();
        memoryCapacityTextField.setHorizontalAlignment(SwingConstants.LEFT);
        memoryCapacityTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        memoryCapacityTextField.setEditable(false);
        memoryCapacityTextField.setColumns(100);
        memoryCapacityTextField.setBounds(719, 184, 130, 42);
        this.getContentPane().add(memoryCapacityTextField);

        JLabel cpuWeightLabel = new JLabel("CPU Weight:");
        cpuWeightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        cpuWeightLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        cpuWeightLabel.setBounds(82, 362, 91, 42);
        this.getContentPane().add(cpuWeightLabel);

        cpuWeightTextField = new JTextField();
        cpuWeightTextField.setHorizontalAlignment(SwingConstants.LEFT);
        cpuWeightTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        cpuWeightTextField.setColumns(100);
        cpuWeightTextField.setBounds(187, 362, 130, 42);
        this.getContentPane().add(cpuWeightTextField);

        JLabel gpuWeightLabel = new JLabel("GPU Weight:");
        gpuWeightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gpuWeightLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        gpuWeightLabel.setBounds(373, 362, 91, 42);
        this.getContentPane().add(gpuWeightLabel);

        gpuWeightTextField = new JTextField();
        gpuWeightTextField.setHorizontalAlignment(SwingConstants.LEFT);
        gpuWeightTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gpuWeightTextField.setColumns(100);
        gpuWeightTextField.setBounds(468, 362, 130, 42);
        this.getContentPane().add(gpuWeightTextField);

        JLabel memoryWeightLabel = new JLabel("Memory Weight:");
        memoryWeightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        memoryWeightLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        memoryWeightLabel.setBounds(596, 362, 113, 42);
        this.getContentPane().add(memoryWeightLabel);

        memoryWeightTextField = new JTextField();
        memoryWeightTextField.setHorizontalAlignment(SwingConstants.LEFT);
        memoryWeightTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        memoryWeightTextField.setColumns(100);
        memoryWeightTextField.setBounds(719, 362, 130, 42);
        this.getContentPane().add(memoryWeightTextField);

        JLabel noteLabel = new JLabel("Note: The weights need to be floating point numbers in the interval [0,1], and have, at most, two decimals. In addition, their sum has to be equal to 1.");
        noteLabel.setForeground(new Color(102, 51, 102));
        noteLabel.setHorizontalAlignment(SwingConstants.LEFT);
        noteLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        noteLabel.setBounds(7, 414, 1226, 42);
        this.getContentPane().add(noteLabel);

        JTextField verticalSeparator1 = new JTextField();
        verticalSeparator1.setForeground(Color.BLACK);
        verticalSeparator1.setBackground(Color.BLACK);
        verticalSeparator1.setEditable(false);
        verticalSeparator1.setBounds(327, -8, 4, 423);
        this.getContentPane().add(verticalSeparator1);
        verticalSeparator1.setColumns(10);

        JTextField verticalSeparator2 = new JTextField();
        verticalSeparator2.setForeground(Color.BLACK);
        verticalSeparator2.setEditable(false);
        verticalSeparator2.setColumns(10);
        verticalSeparator2.setBackground(Color.BLACK);
        verticalSeparator2.setBounds(608, -8, 4, 423);
        this.getContentPane().add(verticalSeparator2);

        JTextField horizontalSeparator1 = new JTextField();
        horizontalSeparator1.setForeground(Color.BLACK);
        horizontalSeparator1.setEditable(false);
        horizontalSeparator1.setColumns(10);
        horizontalSeparator1.setBackground(Color.BLACK);
        horizontalSeparator1.setBounds(0, 346, 1386, 6);
        this.getContentPane().add(horizontalSeparator1);

        JTextField horizontalSeparator3 = new JTextField();
        horizontalSeparator3.setForeground(Color.BLACK);
        horizontalSeparator3.setEditable(false);
        horizontalSeparator3.setColumns(10);
        horizontalSeparator3.setBackground(Color.BLACK);
        horizontalSeparator3.setBounds(0, 453, 1386, 6);
        this.getContentPane().add(horizontalSeparator3);

        JTextField horizontalSeparator2 = new JTextField();
        horizontalSeparator2.setForeground(Color.BLACK);
        horizontalSeparator2.setEditable(false);
        horizontalSeparator2.setColumns(10);
        horizontalSeparator2.setBackground(Color.BLACK);
        horizontalSeparator2.setBounds(0, 414, 1386, 6);
        this.getContentPane().add(horizontalSeparator2);

        JTextField verticalSeparator3 = new JTextField();
        verticalSeparator3.setForeground(Color.BLACK);
        verticalSeparator3.setEditable(false);
        verticalSeparator3.setColumns(10);
        verticalSeparator3.setBackground(Color.BLACK);
        verticalSeparator3.setBounds(859, -8, 4, 423);
        this.getContentPane().add(verticalSeparator3);

        JLabel diskLabel = new JLabel("Disk");
        diskLabel.setHorizontalAlignment(SwingConstants.CENTER);
        diskLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        diskLabel.setBounds(932, 10, 102, 42);
        this.getContentPane().add(diskLabel);

        JLabel diskNameLabel = new JLabel("Name:");
        diskNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        diskNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        diskNameLabel.setBounds(913, 77, 60, 42);
        this.getContentPane().add(diskNameLabel);

        diskNameTextField = new JTextField();
        diskNameTextField.setHorizontalAlignment(SwingConstants.LEFT);
        diskNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 6));
        diskNameTextField.setEditable(false);
        diskNameTextField.setColumns(100);
        diskNameTextField.setBounds(983, 77, 130, 42);
        this.getContentPane().add(diskNameTextField);

        JLabel diskCapacityLabel = new JLabel("Capacity:");
        diskCapacityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        diskCapacityLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        diskCapacityLabel.setBounds(913, 129, 60, 42);
        this.getContentPane().add(diskCapacityLabel);

        diskCapacityTextField = new JTextField();
        diskCapacityTextField.setHorizontalAlignment(SwingConstants.LEFT);
        diskCapacityTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        diskCapacityTextField.setEditable(false);
        diskCapacityTextField.setColumns(100);
        diskCapacityTextField.setBounds(983, 129, 130, 42);
        this.getContentPane().add(diskCapacityTextField);

        JLabel diskFileSystemTypeLabel = new JLabel("File System Type:");
        diskFileSystemTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        diskFileSystemTypeLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        diskFileSystemTypeLabel.setBounds(862, 181, 110, 42);
        this.getContentPane().add(diskFileSystemTypeLabel);

        diskFileSystemTypeTextField = new JTextField();
        diskFileSystemTypeTextField.setHorizontalAlignment(SwingConstants.LEFT);
        diskFileSystemTypeTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        diskFileSystemTypeTextField.setEditable(false);
        diskFileSystemTypeTextField.setColumns(100);
        diskFileSystemTypeTextField.setBounds(983, 181, 130, 42);
        this.getContentPane().add(diskFileSystemTypeTextField);

        JTextField verticalSeparator4 = new JTextField();
        verticalSeparator4.setForeground(Color.BLACK);
        verticalSeparator4.setEditable(false);
        verticalSeparator4.setColumns(10);
        verticalSeparator4.setBackground(Color.BLACK);
        verticalSeparator4.setBounds(1123, -8, 4, 423);
        this.getContentPane().add(verticalSeparator4);

        JLabel networkNameLabel = new JLabel("Name:");
        networkNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        networkNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        networkNameLabel.setBounds(1173, 77, 60, 42);
        this.getContentPane().add(networkNameLabel);

        networkNameTextField = new JTextField();
        networkNameTextField.setHorizontalAlignment(SwingConstants.LEFT);
        networkNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 8));
        networkNameTextField.setEditable(false);
        networkNameTextField.setColumns(100);
        networkNameTextField.setBounds(1246, 77, 130, 42);
        this.getContentPane().add(networkNameTextField);

        JLabel networkLabel = new JLabel("Network");
        networkLabel.setHorizontalAlignment(SwingConstants.CENTER);
        networkLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        networkLabel.setBounds(1199, 10, 113, 42);
        this.getContentPane().add(networkLabel);

        JLabel diskWeightLabel = new JLabel("Disk Weight:");
        diskWeightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        diskWeightLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        diskWeightLabel.setBounds(882, 362, 91, 42);
        this.getContentPane().add(diskWeightLabel);

        diskWeightTextField = new JTextField();
        diskWeightTextField.setHorizontalAlignment(SwingConstants.LEFT);
        diskWeightTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        diskWeightTextField.setColumns(100);
        diskWeightTextField.setBounds(983, 362, 130, 42);
        this.getContentPane().add(diskWeightTextField);

        JLabel networkWeightLabel = new JLabel("Network Weight:");
        networkWeightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        networkWeightLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        networkWeightLabel.setBounds(1137, 362, 102, 42);
        this.getContentPane().add(networkWeightLabel);

        networkWeightTextField = new JTextField();
        networkWeightTextField.setHorizontalAlignment(SwingConstants.LEFT);
        networkWeightTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        networkWeightTextField.setColumns(100);
        networkWeightTextField.setBounds(1246, 362, 130, 42);
        this.getContentPane().add(networkWeightTextField);

        logFileButton = new MyButton("Log File");
        logFileButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
        logFileButton.setBounds(82, 481, 506, 59);
        logFileButton.setBackground(new Color(255, 153, 102));
        logFileButton.setHoverBackgroundColor(new Color(206, 125, 81));
        logFileButton.setPressedBackgroundColor(new Color(152, 92, 60));
        this.getContentPane().add(logFileButton);

        startBenchmarkButton = new MyButton("Start Benchmark");
        startBenchmarkButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
        startBenchmarkButton.setBounds(798, 481, 506, 59);
        startBenchmarkButton.setBackground(new Color(0, 204, 153));
        startBenchmarkButton.setHoverBackgroundColor(new Color(0, 150, 117));
        startBenchmarkButton.setPressedBackgroundColor(new Color(2, 112, 81));
        this.getContentPane().add(startBenchmarkButton);
        this.setBounds(100, 100, 1400, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JTextField getCpuNameTextField()
    {
        return cpuNameTextField;
    }

    public JTextField getCpuArchitectureTextField()
    {
        return cpuArchitectureTextField;
    }

    public JTextField getCpuClockFrequencyTextField()
    {
        return cpuClockFrequencyTextField;
    }

    public JTextField getCpuNumberOfCoresTextField()
    {
        return cpuNumberOfCoresTextField;
    }

    public JTextField getCpuNumberOfLogicalProcessorsTextField()
    {
        return cpuNumberOfLogicalProcessorsTextField;
    }

    public JTextField getGpuNameTextField()
    {
        return gpuNameTextField;
    }

    public JTextField getGpuArchitectureTextField()
    {
        return gpuArchitectureTextField;
    }

    public JTextField getGpuMemoryTextField()
    {
        return gpuMemoryTextField;
    }

    public JTextField getGpuCurrentResolutionTextField()
    {
        return gpuCurrentResolutionTextField;
    }

    public JTextField getGpuCurrentRefreshRateTextField()
    {
        return gpuCurrentRefreshRateTextField;
    }

    public JTextField getMemoryFrequencyTextField()
    {
        return memoryFrequencyTextField;
    }

    public JTextField getMemoryCapacityTextField()
    {
        return memoryCapacityTextField;
    }

    public JTextField getMemoryManufacturerTextField()
    {
        return memoryManufacturerTextField;
    }

    public JTextField getCpuWeightTextField()
    {
        return cpuWeightTextField;
    }

    public JTextField getGpuWeightTextField()
    {
        return gpuWeightTextField;
    }

    public JTextField getMemoryWeightTextField()
    {
        return memoryWeightTextField;
    }

    public JTextField getDiskNameTextField()
    {
        return diskNameTextField;
    }

    public JTextField getDiskCapacityTextField()
    {
        return diskCapacityTextField;
    }

    public JTextField getDiskFileSystemTypeTextField()
    {
        return diskFileSystemTypeTextField;
    }

    public JTextField getNetworkNameTextField()
    {
        return networkNameTextField;
    }

    public JTextField getDiskWeightTextField()
    {
        return diskWeightTextField;
    }

    public JTextField getNetworkWeightTextField()
    {
        return networkWeightTextField;
    }

    public void addLogFileButtonListener(ActionListener actionListener)
    {
        logFileButton.addActionListener(actionListener);
    }

    public void addStartBenchmarkButtonListener(ActionListener actionListener)
    {
        startBenchmarkButton.addActionListener(actionListener);
    }

    public void showErrorMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}