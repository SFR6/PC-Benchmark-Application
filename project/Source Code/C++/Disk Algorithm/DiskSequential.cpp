#include <iostream>
#include <fstream>
#include <chrono>

#define ull unsigned long long
#define MB 1048576

using namespace std;
using namespace chrono;

ifstream fin("seq_in.txt");
ofstream fout("seq_out.txt", ios::app);

int buffer_size = 1024; // 1024 = 1 KB

int main()
{
    fin >> buffer_size;

    char buffer[8192];
    int i;
    for (i = 0; i < buffer_size; ++i)
    {
        buffer[i] = '0';
    }

    ull number_of_writes = 0;
    duration<double> elapsed_time_write;

    // Writing to disk
    ofstream file_out("test.txt", ios::binary);
    high_resolution_clock::time_point start_time = high_resolution_clock::now();
    while (1)
    {
        file_out.write(buffer, buffer_size);
        ++number_of_writes;

        high_resolution_clock::time_point end_time = high_resolution_clock::now();
        elapsed_time_write = duration_cast<duration<double>>(end_time - start_time);
        if (elapsed_time_write.count() > 1.0)
        {
            break;
        }
    }
    file_out.close();

    ull number_of_reads = 0;
    duration<double> elapsed_time_read;

    // Reading from disk
    ifstream file_in("test.txt", ios::binary);
    start_time = high_resolution_clock::now();
    while (!file_in.eof())
    {
        file_in.read(buffer, buffer_size);
        ++number_of_reads;
    }
    high_resolution_clock::time_point end_time = high_resolution_clock::now();
    elapsed_time_read = duration_cast<duration<double>>(end_time - start_time); // this number can be more or less than 1 second
    file_in.close();

    double write_speed = (double)(buffer_size * number_of_writes) / MB;
    double read_speed = (double)((buffer_size * number_of_reads) / elapsed_time_read.count()) / MB;

    fout << (write_speed + read_speed) / 2 << " ";

    fin.close();
    fout.close();
    return 0;
}