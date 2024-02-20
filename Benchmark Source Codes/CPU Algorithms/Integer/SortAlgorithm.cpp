#include <iostream>
#include <fstream>
#include <chrono>

#define ull unsigned long long

using namespace std;
using namespace chrono;

ifstream fin("sort_in.txt");
ofstream fout("sort_out.txt", ios::app);

int main()
{
    int length;
    int array[100005];
    fin >> length;
    int t;
    for (t = 0; t < length; ++t)
    {
        fin >> array[t];
    }

    ull number_of_instructions = 0;

    high_resolution_clock::time_point start_time = high_resolution_clock::now();
    while (1)
    {
        int i, j;
        bool anySwaps;
        for (i = 0; i < length - 1; ++i)
        {
            anySwaps = false;
            for (j = 0; j < length - i - 1; ++j)
            {
                ++number_of_instructions;
                if (array[j] > array[j + 1])
                {
                    number_of_instructions += 3;
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    anySwaps = true;
                }
            }

            if (!anySwaps)
            {
                break;
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