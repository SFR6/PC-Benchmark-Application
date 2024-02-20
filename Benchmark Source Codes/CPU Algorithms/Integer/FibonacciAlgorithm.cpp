#include <iostream>
#include <fstream>
#include <chrono>

#define ull unsigned long long

using namespace std;
using namespace chrono;

ifstream fin("fibonacci_in.txt");
ofstream fout("fibonacci_out.txt", ios::app);

int main()
{
    int upper_limit;
    fin >> upper_limit;

    ull number_of_instructions = 0;

    high_resolution_clock::time_point start_time = high_resolution_clock::now();
    while (1)
    {
        number_of_instructions += 3;
        ull a = 0, b = 1;
        ull result = 0;
        int i;

        for (i = 2; i <= upper_limit; ++i)
        {
            number_of_instructions += 4;
            result = a + b;
            a = b;
            b = result;
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