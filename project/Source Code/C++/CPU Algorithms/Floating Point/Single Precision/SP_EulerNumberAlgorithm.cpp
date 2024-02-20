#include <iostream>
#include <fstream>
#include <chrono>

#define ull unsigned long long

using namespace std;
using namespace chrono;

ifstream fin("sp_euler_in.txt");
ofstream fout("sp_euler_out.txt", ios::app);

int main()
{
    int number_of_terms;
    fin >> number_of_terms;

    ull number_of_instructions = 0;

    high_resolution_clock::time_point start_time = high_resolution_clock::now();
    while (1)
    {
        number_of_instructions += 2;
        float result = 1.0;
        float factorial = 1.0;
        int i;

        for (i = 1; i <= number_of_terms; ++i)
        {
            number_of_instructions += 5;
            factorial *= i;
            result += (1.0 / factorial);
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