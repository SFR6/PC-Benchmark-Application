package TudorCristea.models.GPU;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GPUTesting
{
    private double rotationScore;
    private double scaleScore;
    private double translationScore;
    private double transformationsScore;
    private double lightScore;
    private double particleScore;

    private int numberOfTests;

    public GPUTesting(int numberOfTests)
    {
        this.rotationScore = 0;
        this.scaleScore = 0;
        this.translationScore = 0;
        this.transformationsScore = 0;
        this.lightScore = 0;
        this.particleScore = 0;

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

        // Rotation
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\cube_out.txt");
            writer0.flush();

            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\GPU\\CubeRenderer.exe");
                process.waitFor();
            }
            writer0.close();

            rotationScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\cube_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Scaling
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sphere_out.txt");
            writer0.flush();

            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sphere_in.txt");
            writer.write("30 30 ");
            writer.flush();
            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\GPU\\SphereRenderer.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            scaleScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sphere_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Translation
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\cylinder_out.txt");
            writer0.flush();

            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\cylinder_in.txt");
            writer.write("20 ");
            writer.flush();
            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\GPU\\CylinderRenderer.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            translationScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\cylinder_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // All Transformations
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\pyramid_out.txt");
            writer0.flush();

            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\GPU\\PyramidRenderer.exe");
                process.waitFor();
            }
            writer0.close();

            transformationsScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\pyramid_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Lighting
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\light_out.txt");
            writer0.flush();

            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\light_in.txt");
            writer.write("30 30 ");
            writer.flush();
            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\GPU\\LightingRenderer.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            lightScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\light_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Particle Generation
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\particles_out.txt");
            writer0.flush();

            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\particles_in.txt");
            writer.write("500 ");
            writer.flush();
            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\GPU\\ParticleRenderer.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            particleScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\particles_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return (long) (rotationScore * 0.1 + scaleScore * 0.1 + translationScore * 0.1 + transformationsScore * 0.45 + lightScore * 0.2 + particleScore * 0.05);
    }
}