#include <iostream>
#include <fstream>
#include <chrono>
#include <string.h>

#define ull unsigned long long

using namespace std;
using namespace chrono;

ifstream fin("split_in.txt");
ofstream fout("split_out.txt", ios::app);

int main()
{
    char text[10005], backup_text[10005];
    fin.getline(text, 10005);
    text[strlen(text)] = 0;
    strcpy(backup_text, text);
    int i;

    ull number_of_instructions = 0;

    high_resolution_clock::time_point start_time = high_resolution_clock::now();
    while(1)
    {
        char word[505][35];
        int K1 = 0, K2 = 0;
        ++number_of_instructions;
        for (i = 0; text[i] != 0; ++i)
        {
            ++number_of_instructions;
            if (text[i] != ' ')
            {
                ++number_of_instructions;
                word[K1][K2] = text[i];
                ++K2;
            }
            else
            {
                ++number_of_instructions;
                word[K1][K2] = 0;
                ++K1;
                K2 = 0;
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