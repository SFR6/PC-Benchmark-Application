package TudorCristea.models.Disk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiskTesting
{
    private double sequentialAccessScore;
    private double randomAccessScore;

    private int numberOfTests;

    public DiskTesting(int numberOfTests)
    {
        this.sequentialAccessScore = 0;
        this.randomAccessScore = 0;
        this.numberOfTests = numberOfTests;
    }

    private double getMedianScore(String filePath) throws Exception
    {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        List<Double> scoreValues = new ArrayList<>();
        String line = bufferedReader.readLine();

        String[] values = line.split("\\s+");
        for (String value : values)
        {
            scoreValues.add(Double.parseDouble(value));
        }

        bufferedReader.close();

        Collections.sort(scoreValues);
        return scoreValues.get(numberOfTests / 2);
    }

    public long start()
    {
        int t;

        // Sequential Access
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\seq_out.txt");
            writer0.flush();

            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\seq_in.txt");
            writer.write("1024 ");
            writer.flush();
            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\Disk\\DiskSequential.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            sequentialAccessScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\seq_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Random Access
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\rand_out.txt");
            writer0.flush();

            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\seq_in.txt");
            writer.write("10485760 4096 ");
            writer.flush();
            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\Disk\\DiskRandom.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            randomAccessScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\rand_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return (long)(sequentialAccessScore * 0.5 + randomAccessScore * 0.5);
    }
}