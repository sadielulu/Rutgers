#include "malloc.c"
int main ()
{
	char* p = (char *)malloc( 100 );
	char* x = (char *)malloc( 100 );

	free( p +10);
	

	return 0;
}
