#include <stdio.h>
#include <stdlib.h>

typedef struct Cache{
    int valid;
    unsigned int tag;
    int dirtyBit;
	int age;
} Cache;

