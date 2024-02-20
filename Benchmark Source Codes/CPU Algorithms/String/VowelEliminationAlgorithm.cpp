#include <iostream>
#include <fstream>
#include <chrono>
#include <string.h>

#define ull unsigned long long

using namespace std;
using namespace chrono;

ifstream fin("vowel_in.txt");
ofstream fout("vowel_out.txt", ios::app);

int main()
{
    char text[10005];
    fin.getline(text, 10005);
    text[strlen(text)] = 0;
    int i, j;

    ull number_of_instructions = 0;

    high_resolution_clock::time_point start_time = high_resolution_clock::now();
    while (1)
    {
        ++number_of_instructions;
        for (i = 0; text[i] != 0; ++i)
        {
            number_of_instructions += 10;
            if (text[i] == 'a' || text[i] == 'e' || text[i] == 'i' || text[i] == 'o' || text[i] == 'u' ||
                text[i] == 'A' || text[i] == 'E' || text[i] == 'I' || text[i] == 'O' || text[i] == 'U')
            {
                ++number_of_instructions;
                for (j = i; text[j] != 0; ++j)
                {
                    number_of_instructions += 2;
                    text[j] = text[j + 1];
                }
                ++number_of_instructions;
                text[j] = text[j + 1]; // copy the NULL as well
                --i;
            }
            ++number_of_instructions;
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