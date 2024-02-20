#include <iostream>
#include <fstream>
#include <chrono>

#define ull unsigned long long

using namespace std;
using namespace chrono;

ifstream fin("prime_in.txt");
ofstream fout("prime_out.txt", ios::app);

int main()
{
    int upper_limit;
    fin >> upper_limit;

    ull number_of_instructions = 0;

    high_resolution_clock::time_point start_time = high_resolution_clock::now();
    while (1)
    {
        ull number;
        int number_of_primes = 0;

        number_of_instructions += 2;
        for (number = 0; number <= upper_limit; ++number)
        {
            bool is_prime = true;
            ++number_of_instructions;
            if (number <= 1)
            {
                is_prime = false;
            }
            else
            {
                ull i;
                number_of_instructions += 3;
                for (i = 2; i * i <= number && is_prime; ++i)
                {
                    number_of_instructions += 2;
                    if (number % i == 0)
                    {
                        is_prime = false;
                    }
                    number_of_instructions += 4;
                }
            }

            if (is_prime)
            {
                ++number_of_primes;
            }
            number_of_instructions += 3;
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