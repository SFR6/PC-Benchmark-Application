package TudorCristea.models.Network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetworkTesting
{
    private double networkScore;

    private int numberOfTests;

    public NetworkTesting(int numberOfTests)
    {
        this.networkScore = 0;
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

        try
        {
            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\network_out.txt");
            writer.flush();

            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\Network\\NetworkTesting.exe");
                process.waitFor();
            }
            writer.close();

            networkScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\network_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return (long)networkScore;
    }
}