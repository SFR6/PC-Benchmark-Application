#include <iostream>
#include <fstream>
#include <chrono>

#define ull unsigned long long

using namespace std;
using namespace chrono;

ifstream fin("dp_lu_in.txt");
ofstream fout("dp_lu_out.txt", ios::app);

int main()
{
    int matrix_size;
    fin >> matrix_size;
    double A[105][105];
    int ii, jj;
    for (ii = 0; ii < matrix_size; ++ii)
    {
        for (jj = 0; jj < matrix_size; ++jj)
        {
            fin >> A[ii][jj];
        }
    }

    ull number_of_instructions = 0;

    high_resolution_clock::time_point start_time = high_resolution_clock::now();
    double L[105][105], U[105][105];
    while (1)
    {
        int i, j, k;
        double sum;

        for (i = 0; i < matrix_size; ++i)
        {
            ++number_of_instructions;
            L[i][i] = 1;

            for (j = i; j < matrix_size; ++j)
            {
                ++number_of_instructions;
                sum = 0;
                for (k = 0; k < i; ++k)
                {
                    number_of_instructions += 3;
                    sum += L[i][k] * U[k][j];
                }
                number_of_instructions += 2;
                U[i][j] = A[i][j] - sum;
            }

            for (j = i + 1; j < matrix_size; ++j)
            {
                ++number_of_instructions;
                sum = 0;
                for (k = 0; k < i; ++k)
                {
                   number_of_instructions += 3;
                    sum += L[j][k] * U[k][i];
                }
                number_of_instructions += 3;
                L[j][i] = (A[j][i] - sum) / U[i][i];
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