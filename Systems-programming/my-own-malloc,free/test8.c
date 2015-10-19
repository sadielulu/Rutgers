#include "malloc.c"

int main()
{
	int *p, i;
	p = malloc(sizeof(int) * 7);
	
	for(i = 0; i < 7; i++){
		p[i] = 3;
	} 
	
	for(i = 0; i < 7; i++){
		printf("The value at %d is %d \n", i, p[i]);
	}	

	return 0;
}