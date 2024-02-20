#include <iostream>
#include <fstream>
#include <chrono>
#include <string.h>
#include <climits>

#define ull unsigned long long
#define ll long long

using namespace std;
using namespace chrono;

ifstream fin("mpool_in.txt");
ofstream fout("mpool_out.txt", ios::app);

typedef struct element
{
    char a;
    short b;
    int c;
    ll d;
};

typedef struct MemoryPool
{
    size_t block_size;
    size_t capacity;
    void* memory;
    void** free_list;
};

MemoryPool* memory_pool_create(size_t block_size, size_t capacity) 
{
    MemoryPool* pool = (MemoryPool*)malloc(sizeof(MemoryPool));
    if (pool == NULL) 
    {
        return NULL;
    }

    pool->block_size = block_size;
    pool->capacity = capacity;
    pool->memory = malloc(block_size * capacity);
    if (pool->memory == NULL) 
    {
        free(pool);
        return NULL;
    }

    pool->free_list = (void**)malloc(sizeof(void*) * capacity);
    if (pool->free_list == NULL) 
    {
        free(pool->memory);
        free(pool);
        return NULL;
    }

    size_t i;
    for (i = 0; i < capacity; ++i) 
    {
        pool->free_list[i] = (char*)pool->memory + i * block_size;
    }

    return pool;
}

void* memory_pool_alloc(MemoryPool* pool) 
{
    if (pool == NULL || pool->free_list == NULL) 
    {
        return NULL;
    }

    if (pool->capacity > 0) 
    {
        --pool->capacity;
        void* block = pool->free_list[pool->capacity];
        return block;
    }

    return NULL;
}

void memory_pool_free(MemoryPool* pool, void* block) 
{
    if (pool != NULL && block != NULL) 
    {
        pool->free_list[pool->capacity] = block;
        ++pool->capacity;
    }
}

void memory_pool_destroy(MemoryPool* pool) 
{
    if (pool != NULL) 
    {
        free(pool->free_list);
        free(pool->memory);
        free(pool);
    }
}

int main()
{
    size_t block_size;
    fin >> block_size;
    size_t number_of_blocks;
    fin >> number_of_blocks;

    ull number_of_instructions = 0;

    element E;

    high_resolution_clock::time_point start_time = high_resolution_clock::now();
    while(1)
    {
        number_of_instructions += 2;
        MemoryPool* pool = memory_pool_create(block_size, number_of_blocks);
        element** elem = (element**)malloc(number_of_blocks * sizeof(element*));

        int i;
        for (i = 0; i < number_of_blocks; ++i)
        {
            number_of_instructions += 3;
            elem[i] = (element*)memory_pool_alloc(pool);
            *elem[i] = { CHAR_MAX, SHRT_MAX, INT_MAX, LLONG_MAX };
            E = *elem[i];
        }

        for (i = 0; i < number_of_blocks; ++i)
        {
            ++number_of_instructions;
            memory_pool_free(pool, elem[i]);
        }

        ++number_of_instructions;
        memory_pool_destroy(pool); 
        
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