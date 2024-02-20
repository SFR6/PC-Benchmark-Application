package TudorCristea.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.WindowListener;

public class LogFileView extends JFrame
{
    private JTable table;
    private JTableHeader header;

    public LogFileView(WindowListener windowListener)
    {
        this.setResizable(false);
        this.setTitle("PC Benchmark Application - Log File");
        this.getContentPane().setBackground(new Color(153, 204, 255));
        this.getContentPane().setLayout(new BorderLayout(10, 10));
        this.setBounds(100, 100, 1400, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        table = new JTable();
        table.setBounds(10, 10, 1365, 545);
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setColumnSelectionAllowed(false);
        table.setDragEnabled(false);
        table.setModel(new DefaultTableModel(new String[] { "Time", "CPU Weight", "GPU Weight", "Memory Weight", "Disk Weight", "Network Weight", "CPU Score", "GPU Score", "Memory Score", "Disk Score", "Network Score", "TOTAL SCORE" }, 0));
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        table.setDefaultEditor(Object.class, null);
        this.getContentPane().add(table, BorderLayout.CENTER);

        header = table.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 13));
        header.setBackground(Color.GRAY);
        header.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        this.getContentPane().add(scrollPane);

        this.setVisible(true);
        this.addWindowListener(windowListener);
    }

    public JTable getTable()
    {
        return table;
    }
}