#include <iostream>
#include <fstream>
#include <chrono>
#include <curl/curl.h>

using namespace std;
using namespace chrono;

ofstream fout("network_out.txt", ios::app);

size_t WriteCallback(void* contents, size_t size, size_t nmemb, void* userp) 
{
    size_t totalSize = size * nmemb;
    return totalSize;
}

int main() 
{
    CURL* curl;
    CURLcode res;

    double final_download_speed = 0.0;
    int number_of_requests = 0;

    curl_global_init(CURL_GLOBAL_DEFAULT);
    curl = curl_easy_init();
    if (curl) 
    {
        // Set the URL of a speed test "mock-up" server for download
        curl_easy_setopt(curl, CURLOPT_URL, "http://example.com/download-speed-test");

        // Set a callback function for writing received data
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteCallback);

        high_resolution_clock::time_point start_time = high_resolution_clock::now();
        while (1)
        {
            // Perform the download request
            res = curl_easy_perform(curl);

            if (res == CURLE_OK)
            {
                ++number_of_requests;
                double download_speed;
                curl_easy_getinfo(curl, CURLINFO_SPEED_DOWNLOAD, &download_speed);
                final_download_speed += download_speed;
            }
            else
            {
                cerr << "Failed to measure download speed: " << curl_easy_strerror(res) << '\n';
            }

            high_resolution_clock::time_point end_time = high_resolution_clock::now();
            duration<double> elapsed_time = duration_cast<duration<double>>(end_time - start_time);
            if (elapsed_time.count() > 1.0)
            {
                curl_easy_cleanup(curl);
                break;
            }
        }
    }

    curl_global_cleanup();

    if (number_of_requests == 0)
    {
        fout << 0 << " ";
    }
    else
    {
        fout << final_download_speed / number_of_requests << " ";
    }

    fout.close();
    return 0;
}