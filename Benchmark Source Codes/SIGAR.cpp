#include <windows.h>
#include <iostream>
#include <fstream>
#include <comdef.h>
#include <Wbemidl.h>
#include <string>
#include <cstdlib>
#include <cstdio>

#define MB 1048576
#define GB 1073741824
#define ull unsigned long long

#pragma comment(lib, "wbemuuid.lib")

using namespace std;

ofstream fout("hardware_info.txt");
ofstream fout2("software_info.txt");

void keep_digits(char s[])
{
    int i, j;
    for (i = 0; s[i] != NULL; ++i)
    {
        if (s[i] <= '0' || s[i] >= '9')
        {
            for (j = i; s[j] != NULL; ++j)
            {
                s[j] = s[j + 1];
            }
            s[j] = s[j + 1]; // also copy the NULL
            --i;
        }
    }

    return;
}

int main() 
{
    HRESULT hres, hres1;

    // Initialize COM library
    hres = CoInitializeEx(0, COINIT_MULTITHREADED);
    if (FAILED(hres)) 
    {
        cerr << "Failed to initialize COM library. Error code: " << hres << '\n';
        return 1;
    }

    // Initialize COM security
    hres = CoInitializeSecurity(
        NULL,
        -1,   // Default authentication service
        NULL, // Default authorization service
        NULL,
        RPC_C_AUTHN_LEVEL_DEFAULT,
        RPC_C_IMP_LEVEL_IMPERSONATE,
        NULL,
        EOAC_NONE,
        NULL
    );

    if (FAILED(hres)) 
    {
        cerr << "Failed to initialize COM security. Error code: " << hres << '\n';
        CoUninitialize();
        return 1;
    }

    // Initialize COM interface
    IWbemLocator* pLoc = NULL;
    hres = CoCreateInstance(
        CLSID_WbemLocator,
        0,
        CLSCTX_INPROC_SERVER,
        IID_IWbemLocator,
        (LPVOID*)&pLoc
    );

    if (FAILED(hres)) 
    {
        cerr << "Failed to create IWbemLocator object. Error code: " << hres << '\n';
        CoUninitialize();
        return 1;
    }

    // Connect to WMI
    IWbemServices* pSvc = NULL;
    hres = pLoc->ConnectServer(
        _bstr_t(L"ROOT\\CIMV2"),
        NULL,
        NULL,
        0,
        NULL,
        0,
        0,
        &pSvc
    );

    if (FAILED(hres)) 
    {
        cerr << "Failed to connect to WMI. Error code: " << hres << '\n';
        pLoc->Release();
        CoUninitialize();
        return 1;
    }

    // Set security levels
    hres = CoSetProxyBlanket(
        pSvc,
        RPC_C_AUTHN_WINNT,
        RPC_C_AUTHZ_NONE,
        NULL,
        RPC_C_AUTHN_LEVEL_CALL,
        RPC_C_IMP_LEVEL_IMPERSONATE,
        NULL,
        EOAC_NONE
    );

    if (FAILED(hres)) 
    {
        cerr << "Failed to set proxy blanket. Error code: " << hres << '\n';
        pSvc->Release();
        pLoc->Release();
        CoUninitialize();
        return 1;
    }

    // Query for CPU information
    IEnumWbemClassObject* pEnumerator = NULL;
    hres = pSvc->ExecQuery(
        bstr_t("WQL"),
        bstr_t("SELECT * FROM Win32_Processor"),
        WBEM_FLAG_FORWARD_ONLY | WBEM_FLAG_RETURN_IMMEDIATELY,
        NULL,
        &pEnumerator
    );

    if (FAILED(hres)) 
    {
        cerr << "Failed to execute CPU query. Error code: " << hres << '\n';
        pSvc->Release();
        pLoc->Release();
        CoUninitialize();
        return 1;
    }

    fout << "CPU\n";

    // Retrieve CPU information
    IWbemClassObject* pclsObj = NULL;
    ULONG uReturn = 0;
    while (pEnumerator) 
    {
        hres = pEnumerator->Next(WBEM_INFINITE, 1, &pclsObj, &uReturn);
        if (uReturn == 0) 
        {
            break;
        }

        VARIANT vtProp;
        hres = pclsObj->Get(L"Name", 0, &vtProp, 0, 0);
        if (SUCCEEDED(hres)) 
        {
            int size_needed = WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, nullptr, 0, nullptr, nullptr);
            string strValue(size_needed, 0);
            WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, &strValue[0], size_needed, nullptr, nullptr);
            size_t actualLength = strlen(strValue.c_str());
            strValue.resize(actualLength);

            fout << strValue << '\n';
            VariantClear(&vtProp);
        }

        hres = pclsObj->Get(L"Architecture", 0, &vtProp, 0, 0);
        if (SUCCEEDED(hres))
        {
            string architecture;
            switch (vtProp.uintVal)
            {
                case 0:
                {
                    architecture = "x86";
                    break;
                }                
                case 1:
                {
                    architecture = "MIPS";
                    break;
                }
                case 2:
                {
                    architecture = "Alpha";
                    break;
                }
                case 3:
                {
                    architecture = "PowerPC";
                    break;
                }
                case 5:
                {
                    architecture = "ARM";
                    break;
                }
                case 6:
                {
                    architecture = "ia64";
                    break;
                }
                case 9:
                {
                    architecture = "x64";
                    break;
                }
                case 12:
                {
                    architecture = "ARM64";
                    break;
                }
                default:
                {
                    architecture = "-";
                    break;
                }
            }
            fout << architecture << '\n';
            VariantClear(&vtProp);
        }

        hres = pclsObj->Get(L"MaxClockSpeed", 0, &vtProp, 0, 0);
        if (SUCCEEDED(hres)) 
        {
            fout << vtProp.uintVal << " MHz\n";
            VariantClear(&vtProp);
        }

        hres = pclsObj->Get(L"NumberOfCores", 0, &vtProp, 0, 0);
        if (SUCCEEDED(hres)) 
        {
            fout << vtProp.uintVal << '\n';
            VariantClear(&vtProp);
        }

        hres = pclsObj->Get(L"NumberOfLogicalProcessors", 0, &vtProp, 0, 0);
        if (SUCCEEDED(hres)) 
        {
            fout << vtProp.uintVal << '\n';
            VariantClear(&vtProp);
        }

        pclsObj->Release();
    }

    // Query for GPU information
    pEnumerator = NULL;
    hres = pSvc->ExecQuery(
        bstr_t("WQL"),
        bstr_t("SELECT * FROM Win32_VideoController"),
        WBEM_FLAG_FORWARD_ONLY | WBEM_FLAG_RETURN_IMMEDIATELY,
        NULL,
        &pEnumerator
    );

    if (FAILED(hres)) 
    {
        cerr << "Failed to execute GPU query. Error code: " << hres << '\n';
        pSvc->Release();
        pLoc->Release();
        CoUninitialize();
        return 1;
    }

    fout << "GPU\n";

    // Retrieve GPU information
    while (pEnumerator) 
    {
        hres = pEnumerator->Next(WBEM_INFINITE, 1, &pclsObj, &uReturn);
        if (uReturn == 0) 
        {
            break;
        }

        VARIANT vtProp, vtProp1;
        UINT currentRefreshRate;
        // We first obtain the refresh rate in order to determine which GPU (if there are multiple) is the active one (the active one has a non-zero refresh rate)
        hres = pclsObj->Get(L"CurrentRefreshRate", 0, &vtProp, 0, 0);
        if (SUCCEEDED(hres))
        {
            if (vtProp.uintVal == 0)
            {
                VariantClear(&vtProp);
                continue;
            }
            else
            {
                currentRefreshRate = vtProp.uintVal;
            }
            VariantClear(&vtProp);
        }

        hres = pclsObj->Get(L"Name", 0, &vtProp, 0, 0);
        if (SUCCEEDED(hres)) 
        {
            int size_needed = WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, nullptr, 0, nullptr, nullptr);
            string strValue(size_needed, 0);
            WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, &strValue[0], size_needed, nullptr, nullptr);
            size_t actualLength = strlen(strValue.c_str());
            strValue.resize(actualLength);

            fout << strValue << '\n';
            VariantClear(&vtProp);
        }

        hres = pclsObj->Get(L"AdapterRAM", 0, &vtProp, 0, 0);
        if (SUCCEEDED(hres)) 
        {
            fout << vtProp.uintVal / MB << " MB\n";
            VariantClear(&vtProp);
        }

        hres = pclsObj->Get(L"VideoArchitecture", 0, &vtProp, 0, 0);
        if (SUCCEEDED(hres))
        {
            string video_architecture;
            switch (vtProp.uintVal)
            {
                case 3:
                {
                    video_architecture = "CGA";
                    break;
                }
                case 4:
                {
                    video_architecture = "EGA";
                    break;
                }
                case 5:
                {
                    video_architecture = "VGA";
                    break;
                }
                case 6:
                {
                    video_architecture = "SVGA";
                    break;
                }
                case 7:
                {
                    video_architecture = "MDA";
                    break;
                }
                case 8:
                {
                    video_architecture = "HGC";
                    break;
                }
                case 9:
                {
                    video_architecture = "MCGA";
                    break;
                }
                case 10:
                {
                    video_architecture = "8514A";
                    break;
                }
                case 11:
                {
                    video_architecture = "XGA";
                    break;
                }
                case 12:
                {
                    video_architecture = "Linear Frame Buffer";
                    break;
                }
                case 160:
                {
                    video_architecture = "PC-98";
                    break;
                }
                default:
                {
                    video_architecture = "-";
                    break;
                }
            }
            fout << video_architecture << '\n';
            VariantClear(&vtProp);
        }

        hres = pclsObj->Get(L"CurrentHorizontalResolution", 0, &vtProp, 0, 0);
        hres1 = pclsObj->Get(L"CurrentVerticalResolution", 0, &vtProp1, 0, 0);
        if (SUCCEEDED(hres) && SUCCEEDED(hres1))
        {
            fout << vtProp.uintVal << " x " << vtProp1.uintVal << '\n';
            VariantClear(&vtProp);
            VariantClear(&vtProp1);
        }

        fout << currentRefreshRate << " Hz\n";

        pclsObj->Release();
    }

    // Query for Memory information

    pEnumerator = NULL;
    hres = pSvc->ExecQuery(
        bstr_t("WQL"),
        bstr_t("SELECT * FROM Win32_PhysicalMemory"),
        WBEM_FLAG_FORWARD_ONLY | WBEM_FLAG_RETURN_IMMEDIATELY,
        NULL,
        &pEnumerator
    );

    if (FAILED(hres))
    {
        cerr << "Failed to execute memory query. Error code: " << hres << '\n';
        pSvc->Release();
        pLoc->Release();
        CoUninitialize();
        return 1;
    }

    fout << "Memory\n";

    // Retrieve Memory information

    while (pEnumerator)
    {
        hres = pEnumerator->Next(WBEM_INFINITE, 1, &pclsObj, &uReturn);
        if (uReturn == 0)
        {
            break;
        }

        VARIANT vtProp;
        hres = pclsObj->Get(L"Manufacturer", 0, &vtProp, 0, 0);
        if (SUCCEEDED(hres))
        {
            int size_needed = WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, nullptr, 0, nullptr, nullptr);
            string strValue(size_needed, 0);
            WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, &strValue[0], size_needed, nullptr, nullptr);
            size_t actualLength = strlen(strValue.c_str());
            strValue.resize(actualLength);

            fout << strValue << '\n';
            VariantClear(&vtProp);
        }

        hres = pclsObj->Get(L"ConfiguredClockSpeed", 0, &vtProp, 0, 0);
        if (SUCCEEDED(hres))
        {
            fout << vtProp.uintVal << " MHz\n";
            VariantClear(&vtProp);
        }

        pclsObj->Release();

        break;
    }

    FILE* fp;
    char buffer[1024];

    // Open a pipe to the command
    fp = _popen("wmic MemoryChip get Capacity", "r");
    if (fp == NULL) 
    {
        cerr << "Error openeing pipe.\n";
        return -1;
    }

    ull mem_capacity = 0;
    int line_index = 0;
    // Read the output of the command
    while (fgets(buffer, sizeof(buffer), fp) != NULL) 
    {
        if (line_index > 0 && buffer[0] >= '0' && buffer[0] <= '9')
        {
            keep_digits(buffer);
            mem_capacity += stoll(buffer);
        }
        ++line_index;
    }

    // Close the pipe
    _pclose(fp);

    fout << mem_capacity / MB << " GB\n";

    // Query for Disk information - Part 1

    hres = pSvc->ExecQuery(
        bstr_t("WQL"),
        bstr_t("SELECT * FROM Win32_DiskDrive"),
        WBEM_FLAG_FORWARD_ONLY | WBEM_FLAG_RETURN_IMMEDIATELY,
        NULL,
        &pEnumerator
    );

    if (FAILED(hres)) 
    {
        printf("Query for operating system failed. Error code = 0x%x\n", hres);
        pSvc->Release();
        pLoc->Release();
        CoUninitialize();
        return 1;
    }

    // Retrieve Disk information - Part 1

    fout << "Disk\n";

    while (pEnumerator)
    {
        hres = pEnumerator->Next(WBEM_INFINITE, 1, &pclsObj, &uReturn);
        if (uReturn == 0)
        {
            break;
        }

        VARIANT vtProp;
        hres = pclsObj->Get(L"Caption", 0, &vtProp, 0, 0);
        if (SUCCEEDED(hres))
        {
            int size_needed = WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, nullptr, 0, nullptr, nullptr);
            string strValue(size_needed, 0);
            WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, &strValue[0], size_needed, nullptr, nullptr);
            size_t actualLength = strlen(strValue.c_str());
            strValue.resize(actualLength);

            fout << strValue << '\n';
            VariantClear(&vtProp);
        }

        pclsObj->Release();
        break;
    }

    // Open a pipe to the command
    fp = _popen("wmic DiskDrive get Size", "r");
    if (fp == NULL)
    {
        cerr << "Error openeing pipe.\n";
        return -1;
    }

    ull disk_capacity = 0;
    // Read the output of the command
    while (fgets(buffer, sizeof(buffer), fp) != NULL)
    {
        if (buffer[0] >= '0' && buffer[0] <= '9')
        {
            disk_capacity += stoll(buffer);
            break;
        }
    }

    // Close the pipe
    _pclose(fp);

    fout << (double)(disk_capacity / GB) << " GB\n";

    // Query for Disk information - Part 2

    hres = pSvc->ExecQuery(
        bstr_t("WQL"),
        bstr_t("SELECT * FROM Win32_LogicalDisk"),
        WBEM_FLAG_FORWARD_ONLY | WBEM_FLAG_RETURN_IMMEDIATELY,
        NULL,
        &pEnumerator
    );

    if (FAILED(hres)) 
    {
        printf("Query for operating system failed. Error code = 0x%x\n", hres);
        pSvc->Release();
        pLoc->Release();
        CoUninitialize();
        return 1;
    }

    // Retrieve Disk information - Part 2

    while (pEnumerator)
    {
        hres = pEnumerator->Next(WBEM_INFINITE, 1, &pclsObj, &uReturn);
        if (uReturn == 0)
        {
            break;
        }

        VARIANT vtProp;
        hres = pclsObj->Get(L"FileSystem", 0, &vtProp, 0, 0);
        if (SUCCEEDED(hres))
        {
            int size_needed = WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, nullptr, 0, nullptr, nullptr);
            string strValue(size_needed, 0);
            WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, &strValue[0], size_needed, nullptr, nullptr);
            size_t actualLength = strlen(strValue.c_str());
            strValue.resize(actualLength);

            fout << strValue << '\n';
            VariantClear(&vtProp);
        }

        pclsObj->Release();

        break;
    }

    // Query for Network information

    pEnumerator = NULL;
    hres = pSvc->ExecQuery(
        _bstr_t("WQL"),
        _bstr_t("SELECT * FROM Win32_NetworkAdapterConfiguration"),
        WBEM_FLAG_FORWARD_ONLY | WBEM_FLAG_RETURN_IMMEDIATELY,
        NULL,
        &pEnumerator
    );
    if (FAILED(hres)) 
    {
        cout << "Query for network adapter configurations failed. Error code: " << hres << '\n';
        pSvc->Release();
        pLoc->Release();
        CoUninitialize();
        return 1;
    }

    fout << "Network\n";

    // Retreive Network information

    while (pEnumerator)
    {
        hres = pEnumerator->Next(WBEM_INFINITE, 1, &pclsObj, &uReturn);
        if (uReturn == 0) 
        {
            break;
        }

        bool activeNetworkAdapter = false;
        VARIANT vtProp;

        hres = pclsObj->Get(L"IPEnabled", 0, &vtProp, 0, 0);
        if (!FAILED(hres)) 
        {
            activeNetworkAdapter = (vtProp.boolVal == VARIANT_TRUE) ? true : false;
            VariantClear(&vtProp);
        }

        if (activeNetworkAdapter == false)
        {
            continue;
        }

        hres = pclsObj->Get(L"Description", 0, &vtProp, 0, 0);
        if (!FAILED(hres)) 
        {
            int size_needed = WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, nullptr, 0, nullptr, nullptr);
            string strValue(size_needed, 0);
            WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, &strValue[0], size_needed, nullptr, nullptr);
            size_t actualLength = strlen(strValue.c_str());
            strValue.resize(actualLength);

            fout << strValue << '\n';
            VariantClear(&vtProp);
        }

        pclsObj->Release();
    }

    // Query for OS information

    pEnumerator = nullptr;
    hres = pSvc->ExecQuery(
        bstr_t("WQL"),
        bstr_t("SELECT * FROM Win32_OperatingSystem"),
        WBEM_FLAG_FORWARD_ONLY | WBEM_FLAG_RETURN_IMMEDIATELY,
        nullptr,
        &pEnumerator
    );

    if (FAILED(hres)) 
    {
        pSvc->Release();
        pLoc->Release();
        CoUninitialize();
        cout << "Query for operating system data failed. Error code: " << hres << '\n';
        return 1;
    }

    // Retrieve OS information

    while (pEnumerator) 
    {
        hres = pEnumerator->Next(WBEM_INFINITE, 1, &pclsObj, &uReturn);
        if (uReturn == 0) 
        {
            break;
        }

        VARIANT vtProp;

        hres = pclsObj->Get(L"Caption", 0, &vtProp, 0, 0);
        if (SUCCEEDED(hres)) 
        {
            int size_needed = WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, nullptr, 0, nullptr, nullptr);
            string strValue(size_needed, 0);
            WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, &strValue[0], size_needed, nullptr, nullptr);
            size_t actualLength = strlen(strValue.c_str());
            strValue.resize(actualLength);

            fout2 << strValue << '\n';
            VariantClear(&vtProp);
        }

        hres = pclsObj->Get(L"OSArchitecture", 0, &vtProp, 0, 0);
        if (SUCCEEDED(hres)) 
        {
            int size_needed = WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, nullptr, 0, nullptr, nullptr);
            string strValue(size_needed, 0);
            WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, &strValue[0], size_needed, nullptr, nullptr);
            size_t actualLength = strlen(strValue.c_str());
            strValue.resize(actualLength);

            fout2 << strValue << '\n';
            VariantClear(&vtProp);
        }

        hres = pclsObj->Get(L"Version", 0, &vtProp, 0, 0);
        if (SUCCEEDED(hres)) 
        {
            int size_needed = WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, nullptr, 0, nullptr, nullptr);
            string strValue(size_needed, 0);
            WideCharToMultiByte(CP_UTF8, 0, vtProp.bstrVal, -1, &strValue[0], size_needed, nullptr, nullptr);
            size_t actualLength = strlen(strValue.c_str());
            strValue.resize(actualLength);

            fout2 << strValue << '\n';
            VariantClear(&vtProp);
        }

        pclsObj->Release();
    }

    // Cleanup
    pSvc->Release();
    pLoc->Release();
    pEnumerator->Release();
    CoUninitialize();

    return 0;
}