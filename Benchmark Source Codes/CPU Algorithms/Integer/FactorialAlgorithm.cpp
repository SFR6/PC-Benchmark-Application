#include <iostream>
#include <fstream>
#include <chrono>

#define ull unsigned long long

using namespace std;
using namespace chrono;

ofstream fout("factorial_out.txt", ios::app);

int main()
{
    ull number_of_instructions = 0;

    high_resolution_clock::time_point start_time = high_resolution_clock::now();
    while (1)
    {
        number_of_instructions += 2;
        ull factorial = 1;
        ull i = 2;

        ++number_of_instructions;
        while (factorial > 0)
        {
            number_of_instructions += 5;
            factorial *= i;
            ++i;
        }

        high_resolution_clock::time_point end_time = high_resolution_clock::now();
        duration<double> elapsed_time = duration_cast<duration<double>>(end_time - start_time);
        if (elapsed_time.count() > 1.0)
        {
            break;
        }
    }

    fout << static_cast<double>(number_of_instructions / 1000000.0) << " ";

    fout.close();
    return 0;
}