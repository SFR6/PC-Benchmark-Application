package TudorCristea.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;

public class BenchmarkResultView extends JFrame
{
    private JLabel cpuScoreLabel, gpuScoreLabel, memoryScoreLabel, diskScoreLabel, networkScoreLabel, totalScoreLabel;

    public BenchmarkResultView(WindowListener windowListener)
    {
        this.setResizable(false);
        this.setTitle("PC Benchmark Application - Benchmark");
        this.getContentPane().setBackground(new Color(153, 204, 255));
        this.getContentPane().setLayout(null);

        JLabel gpuLabel = new JLabel("GPU Score");
        gpuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gpuLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        gpuLabel.setBounds(297, 157, 152, 42);
        this.getContentPane().add(gpuLabel);

        JLabel memoryLabel = new JLabel("Memory Score");
        memoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        memoryLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        memoryLabel.setBounds(562, 157, 221, 42);
        this.getContentPane().add(memoryLabel);

        JLabel cpuLabel = new JLabel("CPU Score");
        cpuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cpuLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        cpuLabel.setBounds(52, 157, 152, 42);
        this.getContentPane().add(cpuLabel);

        cpuScoreLabel = new JLabel("");
        cpuScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cpuScoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        cpuScoreLabel.setBounds(26, 209, 221, 42);
        this.getContentPane().add(cpuScoreLabel);

        gpuScoreLabel = new JLabel("");
        gpuScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gpuScoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        gpuScoreLabel.setBounds(257, 209, 228, 42);
        this.getContentPane().add(gpuScoreLabel);

        memoryScoreLabel = new JLabel("");
        memoryScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        memoryScoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        memoryScoreLabel.setBounds(560, 209, 236, 42);
        this.getContentPane().add(memoryScoreLabel);

        JLabel totalLabel = new JLabel("TOTAL Score:");
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        totalLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        totalLabel.setBounds(360, 334, 284, 56);
        this.getContentPane().add(totalLabel);

        totalScoreLabel = new JLabel("");
        totalScoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
        totalScoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        totalScoreLabel.setBounds(654, 344, 382, 42);
        this.getContentPane().add(totalScoreLabel);

        JLabel diskLabel = new JLabel("Disk Score");
        diskLabel.setHorizontalAlignment(SwingConstants.CENTER);
        diskLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        diskLabel.setBounds(860, 157, 181, 42);
        this.getContentPane().add(diskLabel);

        diskScoreLabel = new JLabel("");
        diskScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        diskScoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        diskScoreLabel.setBounds(838, 209, 236, 42);
        this.getContentPane().add(diskScoreLabel);

        JLabel networkLabel = new JLabel("Network Score");
        networkLabel.setHorizontalAlignment(SwingConstants.CENTER);
        networkLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        networkLabel.setBounds(1120, 157, 200, 42);
        this.getContentPane().add(networkLabel);

        networkScoreLabel = new JLabel("");
        networkScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        networkScoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        networkScoreLabel.setBounds(1100, 209, 236, 42);
        this.getContentPane().add(networkScoreLabel);
        
        this.setBounds(100, 100, 1400, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setEnabled(true);

        this.addWindowListener(windowListener);
    }

    public JLabel getCpuScoreLabel()
    {
        return cpuScoreLabel;
    }

    public JLabel getGpuScoreLabel()
    {
        return gpuScoreLabel;
    }

    public JLabel getMemoryScoreLabel()
    {
        return memoryScoreLabel;
    }

    public JLabel getDiskScoreLabel()
    {
        return diskScoreLabel;
    }

    public JLabel getNetworkScoreLabel()
    {
        return networkScoreLabel;
    }

    public JLabel getTotalScoreLabel()
    {
        return totalScoreLabel;
    }
}