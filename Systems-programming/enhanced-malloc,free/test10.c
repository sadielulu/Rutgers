#include "malloc.c"

int main()
{
	int *p;
	p = malloc(sizeof(int*));
	free(p + 100);
	return 0;
}
