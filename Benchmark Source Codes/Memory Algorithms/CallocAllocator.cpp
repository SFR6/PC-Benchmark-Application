#include <iostream>
#include <fstream>
#include <chrono>
#include <string.h>
#include <climits>

#define ull unsigned long long
#define ll long long

using namespace std;
using namespace chrono;

ifstream fin("calloc_in.txt");
ofstream fout("calloc_out.txt", ios::app);

int main()
{
    int memory_size;
    fin >> memory_size;

    ull number_of_instructions = 0;

    high_resolution_clock::time_point start_time = high_resolution_clock::now();
    char A;
    short B;
    int C;
    ll D;
    while (1)
    {
        // big alloc - big free
        char* a;
        short* b;
        int* c;
        ll* d;

        number_of_instructions += 4;
        a = (char*)calloc(memory_size, sizeof(char));
        b = (short*)calloc(memory_size, sizeof(short));
        c = (int*)calloc(memory_size, sizeof(int));
        d = (ll*)calloc(memory_size, sizeof(ll));

        number_of_instructions += 4;
        *a = CHAR_MAX;
        *b = SHRT_MAX;
        *c = INT_MAX;
        *d = LLONG_MAX;

        number_of_instructions += 4;
        A = *a;
        B = *b;
        C = *c;
        D = *d;

        number_of_instructions += 4;
        free(a);
        free(b);
        free(c);
        free(d);

        // small alloc - big free
        int memory_chunk = memory_size / 100;

        char** aa;
        short** bb;
        int** cc;
        ll** dd;

        number_of_instructions += 4;
        aa = (char**)calloc(memory_chunk, sizeof(char*));
        bb = (short**)calloc(memory_chunk, sizeof(short*));
        cc = (int**)calloc(memory_chunk, sizeof(int*));
        dd = (ll**)calloc(memory_chunk, sizeof(ll*));

        int i;
        for (i = 0; i < memory_chunk; ++i)
        {
            number_of_instructions += 4;
            aa[i] = (char*)calloc(memory_chunk, sizeof(char));
            bb[i] = (short*)calloc(memory_chunk, sizeof(short));
            cc[i] = (int*)calloc(memory_chunk, sizeof(int));
            dd[i] = (ll*)calloc(memory_chunk, sizeof(ll));

            number_of_instructions += 4;
            *aa[i] = CHAR_MAX;
            *bb[i] = SHRT_MAX;
            *cc[i] = INT_MAX;
            *dd[i] = LLONG_MAX;

            number_of_instructions += 4;
            A = *aa[i];
            B = *bb[i];
            C = *cc[i];
            D = *dd[i];
        }
        for (i = 0; i < memory_chunk; ++i)
        {
            number_of_instructions += 4;
            free(aa[i]);
            free(bb[i]);
            free(cc[i]);
            free(dd[i]);
        }

        number_of_instructions += 4;
        free(aa);
        free(bb);
        free(cc);
        free(dd);

        // small alloc - small free

        number_of_instructions += 4;
        aa = (char**)calloc(memory_chunk, sizeof(char*));
        bb = (short**)calloc(memory_chunk, sizeof(short*));
        cc = (int**)calloc(memory_chunk, sizeof(int*));
        dd = (ll**)calloc(memory_chunk, sizeof(ll*));

        for (i = 0; i < memory_chunk; ++i)
        {
            number_of_instructions += 16;
            aa[i] = (char*)calloc(memory_chunk, sizeof(char));
            *aa[i] = CHAR_MAX;
            A = *aa[i];
            free(aa[i]);
            bb[i] = (short*)calloc(memory_chunk, sizeof(short));
            *bb[i] = SHRT_MAX;
            B = *bb[i];
            free(bb[i]);
            cc[i] = (int*)calloc(memory_chunk, sizeof(int));
            *cc[i] = INT_MAX;
            C = *cc[i];
            free(cc[i]);
            dd[i] = (ll*)calloc(memory_chunk, sizeof(ll));
            *dd[i] = LLONG_MAX;
            D = *dd[i];
            free(dd[i]);
        }

        number_of_instructions += 4;
        free(aa);
        free(bb);
        free(cc);
        free(dd);

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