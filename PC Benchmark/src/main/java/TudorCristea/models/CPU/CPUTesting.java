package TudorCristea.models.CPU;

import java.io.*;
import java.util.*;

public class CPUTesting
{
    private double integerScore;
    private double singlePrecisionFloatingPointScore;
    private double doublePrecisionFloatingPointScore;
    private double stringScore;

    private int numberOfTests;

    public CPUTesting(int numberOfTests)
    {
        this.integerScore = 0;
        this.singlePrecisionFloatingPointScore = 0;
        this.doublePrecisionFloatingPointScore = 0;
        this.stringScore = 0;
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
        this.integerScore = 0;
        this.singlePrecisionFloatingPointScore = 0;
        this.doublePrecisionFloatingPointScore = 0;
        this.stringScore = 0;
        return (long) (integerTesting() * 0.4 + (singlePrecisionFloatingPointTesting() + doublePrecisionFloatingPointTesting()) * 0.5 + stringTesting() * 0.1);
    }

    private double integerTesting()
    {
        int t, i;

        // Prime Number Generator Testing
        double primeScore = 0;
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\prime_out.txt");
            writer0.flush();

            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\prime_in.txt");
            writer.write("100000 ");
            writer.flush();
            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\CPU\\IntegerTesting\\PrimeNumbersAlgorithm.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            primeScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\prime_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.integerScore +=  (primeScore * 0.3);

        // BubbleSort Testing
        double sortScore = 0;
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sort_out.txt");
            writer0.flush();

            Random random = new Random();
            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sort_in.txt");
            for (t = 1; t <= numberOfTests; ++t)
            {
                writer.write("10000 ");
                for (i = 0; i < 10000; ++i)
                {
                    writer.write(Integer.toString(random.nextInt()) + " ");
                }
                writer.flush();

                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\CPU\\IntegerTesting\\SortAlgorithm.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            sortScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sort_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.integerScore +=  (sortScore * 0.4);

        // Fibonacci Number Generation Testing
        double fibonacciScore = 0;
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\fibonacci_out.txt");
            writer0.flush();

            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\fibonacci_in.txt");
            writer.write("100000 ");
            writer.flush();
            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\CPU\\IntegerTesting\\FibonacciAlgorithm.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            fibonacciScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\fibonacci_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.integerScore += (fibonacciScore * 0.15);

        // Factorial Number Calculator Testing
        double factorialScore = 0;
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\factorial_out.txt");
            writer0.flush();

            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\CPU\\IntegerTesting\\FactorialAlgorithm.exe");
                process.waitFor();
            }
            writer0.close();

            factorialScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\factorial_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.integerScore += (factorialScore * 0.15);

        return integerScore;
    }

    private double doublePrecisionFloatingPointTesting()
    {
        int t, i, j;

        // LU Decomposition Testing
        double luDecompositionScore = 0;
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\dp_lu_out.txt");
            writer0.flush();

            Random random = new Random();
            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\dp_lu_in.txt");
            for (t = 1; t <= numberOfTests; ++t)
            {
                writer.write("100 ");
                for (i = 0; i < 100; ++i)
                {
                    for (j = 0; j < 100; ++j)
                    {
                        writer.write(Double.toString(Math.round(random.nextDouble() * 10000000000.0) / 10000000000.0) + " ");
                    }
                }
                writer.flush();

                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\CPU\\FloatingPointTesting\\DP_LUDecompositionAlgorithm.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            luDecompositionScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\dp_lu_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.doublePrecisionFloatingPointScore += (luDecompositionScore * 0.25);

        // Euler Number Testing
        double eulerNumberScore = 0;
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\dp_euler_out.txt");
            writer0.flush();

            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\dp_euler_in.txt");
            writer.write("100000 ");
            writer.flush();
            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\CPU\\FloatingPointTesting\\DP_EulerNumberAlgorithm.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            eulerNumberScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\dp_euler_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.doublePrecisionFloatingPointScore += (eulerNumberScore * 0.25);

        // PI Digits Computation Testing
        double piDigitsScore = 0;
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\dp_pi_out.txt");
            writer0.flush();

            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\dp_pi_in.txt");
            writer.write("100000 ");
            writer.flush();
            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\CPU\\FloatingPointTesting\\DP_PIDigitsAlgorithm.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            piDigitsScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\dp_pi_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.doublePrecisionFloatingPointScore += (piDigitsScore * 0.25);

        // Angle Computation using Arctangent Testing
        double arctangentScore = 0;
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\dp_arctangent_out.txt");
            writer0.flush();

            for (t = 1; t <= numberOfTests; ++t)
            {
                FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\dp_arctangent_in.txt", false);
                writer.write("100000 ");
                writer.write(Double.toString((Math.round((Math.random() / Math.random()) * 10000000000.0) / 10000000000.0) + 1.0) + " ");
                writer.flush();
                writer.close();

                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\CPU\\FloatingPointTesting\\DP_ArctangentAlgorithm.exe");
                process.waitFor();
            }
            writer0.close();

            arctangentScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\dp_arctangent_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.doublePrecisionFloatingPointScore += (arctangentScore * 0.25);

        return doublePrecisionFloatingPointScore;
    }

    private double singlePrecisionFloatingPointTesting()
    {
        int t, i, j;

        // LU Decomposition Testing
        double luDecompositionScore = 0;
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sp_lu_out.txt");
            writer0.flush();

            Random random = new Random();
            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sp_lu_in.txt");
            for (t = 1; t <= numberOfTests; ++t)
            {
                writer.write("100 ");
                for (i = 0; i < 100; ++i)
                {
                    for (j = 0; j < 100; ++j)
                    {
                        writer.write(Double.toString(Math.round(random.nextDouble() * 10000000000.0) / 10000000000.0) + " ");
                    }
                }
                writer.flush();

                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\CPU\\FloatingPointTesting\\SP_LUDecompositionAlgorithm.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            luDecompositionScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sp_lu_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.singlePrecisionFloatingPointScore += (luDecompositionScore * 0.25);

        // Euler Number Testing
        double eulerNumberScore = 0;
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sp_euler_out.txt");
            writer0.flush();

            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sp_euler_in.txt");
            writer.write("100000 ");
            writer.flush();
            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\CPU\\FloatingPointTesting\\SP_EulerNumberAlgorithm.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            eulerNumberScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sp_euler_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.singlePrecisionFloatingPointScore += (eulerNumberScore * 0.25);

        // PI Digits Computation Testing
        double piDigitsScore = 0;
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sp_pi_out.txt");
            writer0.flush();

            FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sp_pi_in.txt");
            writer.write("100000 ");
            writer.flush();
            for (t = 1; t <= numberOfTests; ++t)
            {
                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\CPU\\FloatingPointTesting\\SP_PIDigitsAlgorithm.exe");
                process.waitFor();
            }
            writer0.close();
            writer.close();

            piDigitsScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sp_pi_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.singlePrecisionFloatingPointScore += (piDigitsScore * 0.25);

        // Angle Computation using Arctangent Testing
        double arctangentScore = 0;
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sp_arctangent_out.txt");
            writer0.flush();

            for (t = 1; t <= numberOfTests; ++t)
            {
                FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sp_arctangent_in.txt", false);
                writer.write("100000 ");
                writer.write(Double.toString((Math.round((Math.random() / Math.random()) * 10000000000.0) / 10000000000.0) + 1.0) + " ");
                writer.flush();
                writer.close();

                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\CPU\\FloatingPointTesting\\SP_ArctangentAlgorithm.exe");
                process.waitFor();
            }
            writer0.close();

            arctangentScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\sp_arctangent_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.singlePrecisionFloatingPointScore += (arctangentScore * 0.25);

        return singlePrecisionFloatingPointScore;
    }

    private double stringTesting()
    {
        int t, i;

        // Lower Case To Upper Case Testing
        double lowerCaseToUpperCaseScore = 0;
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\low2up_out.txt");
            writer0.flush();

            for (t = 1; t <= numberOfTests; ++t)
            {
                FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\low2up_in.txt", false);
                Random random = new Random();
                StringBuilder stringBuilder = new StringBuilder(100000);
                for (i = 0; i < 100000; ++i)
                {
                    int randomInt = random.nextInt(52);

                    if (randomInt < 26)
                    {
                        stringBuilder.append((char) (randomInt + 65));
                    }
                    else
                    {
                        stringBuilder.append((char) (randomInt + 71));
                    }
                }
                writer.write(stringBuilder.toString());
                writer.flush();
                writer.close();

                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\CPU\\StringTesting\\LowerCaseToUpperCaseAlgorithm.exe");
                process.waitFor();
            }
            writer0.close();

            lowerCaseToUpperCaseScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\low2up_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.stringScore += (lowerCaseToUpperCaseScore * 0.2);

        // Vowel Elimination Testing
        double vowelEliminationScore = 0;
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\vowel_out.txt");
            writer0.flush();

            for (t = 1; t <= numberOfTests; ++t)
            {
                FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\vowel_in.txt", false);
                Random random = new Random();
                StringBuilder stringBuilder = new StringBuilder(10000);
                for (i = 0; i < 10000; ++i)
                {
                    int randomInt = random.nextInt(52);

                    if (randomInt < 26)
                    {
                        stringBuilder.append((char) (randomInt + 65));
                    }
                    else
                    {
                        stringBuilder.append((char) (randomInt + 71));
                    }
                }
                writer.write(stringBuilder.toString());
                writer.flush();
                writer.close();

                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\CPU\\StringTesting\\VowelEliminationAlgorithm.exe");
                process.waitFor();
            }
            writer0.close();

            vowelEliminationScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\vowel_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.stringScore += (vowelEliminationScore * 0.2);

        // Text Splitting Into Words Testing
        double textSplittingIntoWordsScore = 0;
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\split_out.txt");
            writer0.flush();

            for (t = 1; t <= numberOfTests; ++t)
            {
                FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\split_in.txt", false);
                Random random = new Random();
                StringBuilder stringBuilder = new StringBuilder(10000);
                int randomIntSeparator = random.nextInt(11) + 20;
                int j = 0;
                for (i = 0; i < 10000; ++i, ++j)
                {
                    if (j == randomIntSeparator)
                    {
                        stringBuilder.append(' ');
                        randomIntSeparator = random.nextInt(11) + 20;
                        j = 0;
                    }
                    else
                    {
                        int randomInt = random.nextInt(52);

                        if (randomInt < 26)
                        {
                            stringBuilder.append((char) (randomInt + 65));
                        }
                        else
                        {
                            stringBuilder.append((char) (randomInt + 71));
                        }
                    }
                }
                writer.write(stringBuilder.toString());
                writer.flush();
                writer.close();

                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\CPU\\StringTesting\\TextSplittingIntoWordsAlgorithm.exe");
                process.waitFor();
            }
            writer0.close();

            textSplittingIntoWordsScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\split_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.stringScore += (textSplittingIntoWordsScore * 0.2);

        //Data Encryption Decryption Testing
        double dataEncryptionDecryptionScore = 0;
        try
        {
            FileWriter writer0 = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\encdec_out.txt");
            writer0.flush();

            for (t = 1; t <= numberOfTests; ++t)
            {
                FileWriter writer = new FileWriter("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\encdec_in.txt", false);
                Random random = new Random();
                StringBuilder stringBuilder = new StringBuilder(100000);
                for (i = 0; i < 100000; ++i)
                {
                    int randomInt = random.nextInt(95) + 32;
                    stringBuilder.append((char) randomInt);
                }
                writer.write(stringBuilder.toString());
                writer.flush();
                writer.close();

                Process process = Runtime.getRuntime().exec("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\src\\main\\java\\TudorCristea\\models\\CPU\\StringTesting\\DataEncryptionDecryptionAlgorithm.exe");
                process.waitFor();
            }
            writer0.close();

            dataEncryptionDecryptionScore = getMedianScore("C:\\Users\\Tudor Cristea\\Documents\\SCS\\project\\PC Benchmark\\encdec_out.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.stringScore += (dataEncryptionDecryptionScore * 0.4);

        return stringScore;
    }
}