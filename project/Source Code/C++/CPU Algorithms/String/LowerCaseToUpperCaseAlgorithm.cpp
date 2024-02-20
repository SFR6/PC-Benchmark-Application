#include <iostream>
#include <fstream>
#include <chrono>
#include <string.h>

#define ull unsigned long long

using namespace std;
using namespace chrono;

ifstream fin("low2up_in.txt");
ofstream fout("low2up_out.txt", ios::app);

int main()
{
    char text[100005], backup_text[100005];
    fin.getline(text, 100005);
    text[strlen(text)] = 0;
    strcpy(backup_text, text);
    int i;

    ull number_of_instructions = 0;

    high_resolution_clock::time_point start_time = high_resolution_clock::now();
    while (1)
    {
        ++number_of_instructions;
        for (i = 0; text[i] != 0; ++i)
        {
            number_of_instructions += 2;
            if (text[i] >= 'a' && text[i] <= 'z')
            {
                number_of_instructions += 2;
                text[i] -= 32;
            }
            ++number_of_instructions;
        }

        strcpy(text, backup_text);

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