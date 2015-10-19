#include "malloc.c"

int main()
{
	char* p = (char *)malloc( 100 );
	free( p );
	p = (char *)malloc( 100 );
	free( p );

	return 0;
}