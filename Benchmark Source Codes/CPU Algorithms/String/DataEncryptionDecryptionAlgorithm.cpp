#include <iostream>
#include <fstream>
#include <chrono>
#include <string.h>

#define ull unsigned long long

using namespace std;
using namespace chrono;

ifstream fin("encdec_in.txt");
ofstream fout("encdec_out.txt", ios::app);

int main()
{
    char key[] = "0123456789abcdef"; // secret key
    int key_size = sizeof(key) - 1;

    char text[100005];
    fin.getline(text, 100005);
    text[strlen(text)] = 0;
    int i;

    ull number_of_instructions = 0;

    high_resolution_clock::time_point start_time = high_resolution_clock::now();
    while (1)
    {
        // Encryption
        ++number_of_instructions;
        for (i = 0; text[i] != 0; ++i)
        {
            number_of_instructions += 3;
            text[i] ^= key[i % key_size];
        }

        // Decryption
        ++number_of_instructions;
        for (i = 0; text[i] != 0; ++i)
        {
            number_of_instructions += 3;
            text[i] ^= key[i % key_size];
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