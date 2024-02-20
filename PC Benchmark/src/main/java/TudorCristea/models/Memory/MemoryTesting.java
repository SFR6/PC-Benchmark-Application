package TudorCristea.models.Memory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryTesting
{
    private double mallocScore;
    private double callocScore;
    private double reallocScore;
    private double customPoolScore;

    private int numberOfTests;

    public MemoryTesting(int numberOfTests)
    {
        this.mallocScore = 0;
        this.callocScore = 0;
        this.reallocScore = 0;
        this.customPoolScore = 0;

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

        // Malloc Allocation
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\malloc_out.txt");
            writer0.flush();

            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\malloc_in.txt");
            writer.write("10000 ");
            writer.flush();
            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\Memory\\MallocAllocator.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            mallocScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\malloc_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Calloc Allocation
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\calloc_out.txt");
            writer0.flush();

            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\calloc_in.txt");
            writer.write("10000 ");
            writer.flush();
            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\Memory\\CallocAllocator.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            callocScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\calloc_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Realloc Allocation
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\realloc_out.txt");
            writer0.flush();

            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\realloc_in.txt");
            writer.write("10000 ");
            writer.flush();
            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\Memory\\ReallocAllocator.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            reallocScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\realloc_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Custom Memory Pool Allocation
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\mpool_out.txt");
            writer0.flush();

            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\mpool_in.txt");
            writer.write("100 100 ");
            writer.flush();
            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\Memory\\MemoryPoolAllocator.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            customPoolScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\mpool_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return (long) (mallocScore * 0.15 + callocScore * 0.15 + reallocScore * 0.15 + customPoolScore * 0.55);
    }
}