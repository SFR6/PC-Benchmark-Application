#include <iostream>
#include <fstream>
#include <chrono>
#include <string.h>
#include <climits>

#define ull unsigned long long
#define ll long long

using namespace std;
using namespace chrono;

ifstream fin("realloc_in.txt");
ofstream fout("realloc_out.txt", ios::app);

int main()
{

    int memory_size;
    fin >> memory_size;

    char A;
    short B;
    int C;
    ll D;

    // big alloc
    char* a;
    short* b;
    int* c;
    ll* d;

    a = (char*)malloc(memory_size * sizeof(char));
    b = (short*)malloc(memory_size * sizeof(short));
    c = (int*)malloc(memory_size * sizeof(int));
    d = (ll*)malloc(memory_size * sizeof(ll));

    // small alloc
    char** aa;
    short** bb;
    int** cc;
    ll** dd;

    int memory_chunk = memory_size / 100;

    aa = (char**)malloc(memory_chunk * sizeof(char*));
    bb = (short**)malloc(memory_chunk * sizeof(short*));
    cc = (int**)malloc(memory_chunk * sizeof(int*));
    dd = (ll**)malloc(memory_chunk * sizeof(ll*));

    int i;
    for (i = 0; i < memory_chunk; ++i)
    {
        aa[i] = (char*)malloc(memory_chunk * sizeof(char));
        bb[i] = (short*)malloc(memory_chunk * sizeof(short));
        cc[i] = (int*)malloc(memory_chunk * sizeof(int));
        dd[i] = (ll*)malloc(memory_chunk * sizeof(ll));
    }

    // small alloc
    char** aaa;
    short** bbb;
    int** ccc;
    ll** ddd;

    aaa = (char**)malloc(memory_chunk * sizeof(char*));
    bbb = (short**)malloc(memory_chunk * sizeof(short*));
    ccc = (int**)malloc(memory_chunk * sizeof(int*));
    ddd = (ll**)malloc(memory_chunk * sizeof(ll*));

    for (i = 0; i < memory_chunk; ++i)
    {
        aaa[i] = (char*)malloc(memory_chunk * sizeof(char));
        bbb[i] = (short*)malloc(memory_chunk * sizeof(short));
        ccc[i] = (int*)malloc(memory_chunk * sizeof(int));
        ddd[i] = (ll*)malloc(memory_chunk * sizeof(ll));
    }

    ull number_of_instructions = 0;

    high_resolution_clock::time_point start_time = high_resolution_clock::now();
    while(1)
    {
        // big alloc

        number_of_instructions += 4;
        a = (char*)realloc(a, memory_size * sizeof(char));
        b = (short*)realloc(b, memory_size * sizeof(short));
        c = (int*)realloc(c, memory_size * sizeof(int));
        d = (ll*)realloc(d, memory_size * sizeof(ll));

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

        // small alloc

        number_of_instructions += 4;
        aa = (char**)realloc(aa, memory_chunk * sizeof(char*));
        bb = (short**)realloc(bb, memory_chunk * sizeof(short*));
        cc = (int**)realloc(cc, memory_chunk * sizeof(int*));
        dd = (ll**)realloc(dd, memory_chunk * sizeof(ll*));

        for (i = 0; i < memory_chunk; ++i)
        {
            number_of_instructions += 4;
            aa[i] = (char*)realloc(aa[i], memory_chunk * sizeof(char));
            bb[i] = (short*)realloc(bb[i], memory_chunk * sizeof(short));
            cc[i] = (int*)realloc(cc[i], memory_chunk * sizeof(int));
            dd[i] = (ll*)realloc(dd[i], memory_chunk * sizeof(ll));

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

        // small alloc

        number_of_instructions += 4;
        aaa = (char**)realloc(aaa, memory_chunk * sizeof(char*));
        bbb = (short**)realloc(bbb, memory_chunk * sizeof(short*));
        ccc = (int**)realloc(ccc, memory_chunk * sizeof(int*));
        ddd = (ll**)realloc(ddd, memory_chunk * sizeof(ll*));

        for (i = 0; i < memory_chunk; ++i)
        {
            number_of_instructions += 12;
            aaa[i] = (char*)realloc(aaa[i], memory_chunk * sizeof(char));
            *aaa[i] = CHAR_MAX;
            A = *aaa[i];
            bbb[i] = (short*)realloc(bbb[i], memory_chunk * sizeof(short));
            *bbb[i] = SHRT_MAX;
            B = *bbb[i];
            ccc[i] = (int*)realloc(ccc[i], memory_chunk * sizeof(int));
            *ccc[i] = INT_MAX;
            C = *ccc[i];
            ddd[i] = (ll*)realloc(ddd[i], memory_chunk * sizeof(ll));
            *ddd[i] = LLONG_MAX;
            D = *ddd[i];
        }

        high_resolution_clock::time_point end_time = high_resolution_clock::now();
        duration<double> elapsed_time = duration_cast<duration<double>>(end_time - start_time);
        if (elapsed_time.count() > 1.0)
        {
            cout << elapsed_time.count();
            break;
        }
    }

    fout << static_cast<double>(number_of_instructions / 1000000.0) << " ";

    fin.close();
    fout.close();
    return 0;
}