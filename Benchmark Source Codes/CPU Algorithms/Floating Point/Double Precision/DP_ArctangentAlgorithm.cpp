#include <iostream>
#include <fstream>
#include <chrono>

#define ull unsigned long long

using namespace std;
using namespace chrono;

ifstream fin("dp_arctangent_in.txt");
ofstream fout("dp_arctangent_out.txt", ios::app);

int main()
{
    int number_of_iterations;
    fin >> number_of_iterations;
    double tangent;
    fin >> tangent;

    ull number_of_instructions = 0;

    high_resolution_clock::time_point start_time = high_resolution_clock::now();
    while (1)
    {
        number_of_instructions += 3;
        double result = 0.0;
        double numerator = tangent;
        double denominator = 1.0;
        double term;
        int i;
        bool foundTheAngle = false;

        for (i = 1; i <= number_of_iterations && !foundTheAngle; ++i)
        {
            number_of_instructions += 10;
            numerator = -numerator * tangent * tangent;
            denominator += 2;
            term = numerator / denominator;
            result += term;

            ++number_of_instructions;
            if (term < 0) // abs
            {
                number_of_instructions += 2;
                term = -term;
            }

            ++number_of_instructions;
            if (term < 1e-15)
            {
                foundTheAngle = true;
            }
        }
        
        high_resolution_clock::time_point end_time = high_resolution_clock::now();
        duration<double> elapsed_time = duration_cast<duration<double>>(end_time - start_time);
        if (elapsed_time.count() > 1.0)
        {
            break;
        }
    }

    fout << static_cast<double>(number_of_instructions / 1000000.0) << " ";

    fin.close();
    fout.close();
    return 0;
}