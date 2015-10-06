/*
 * sorted-list.c
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "sorted-list.h"

int compareStrings(void *p1, void *p2)
{
	char *s1 = p1;
	char *s2 = p2;
	return strcmp(s1, s2);
}

int compareInts(void *p1, void *p2)
{
	int i1 = *(int*)p1;
	int i2 = *(int*)p2;
	return i1 - i2;
}

int compareDoubles(void *p1, void *p2)
{
	double d1 = *(double*)p1;
	double d2 = *(double*)p2;
	return (d1 < d2) ? -1 : ((d1 > d2) ? 1 : 0);
}
//compare structs

//Destructor functions
void destroyBasicTypeAlloc(void*p)
{
    free(p);
}

void destroyBasicTypeNoAlloc(void *p)
{
	return;
}


int main()
{	
	//string test values
	char * str1 = "two";
	char * str2 = "three";
	char * str3 = "four";
	char * str6 = "apple";
	char * str4 = "one";
	char * str5 = "seven";
	//int test values
	int x = 5;
	int y = 7;
	int g = 4;
	int h = 5;
	int i = 6;
	int j = 7;
	int *pj=&j;
	int m = 10;
	int n = 11;
	int a=1;
	void * printingPtr;

	printf("TEST CASE 1: DOES NOT GIVE ENOUGH PARAMARTERS TO CREATE LIST\n\n");
	SortedListPtr sl1 = SLCreate(compareInts, NULL);
	SLDestroy(sl1);
	printf("\n");
	
	printf("TEST CASE 2: Try to make a pointer to an empty list\n\n");
	SortedListPtr intList = SLCreate(compareInts, destroyBasicTypeNoAlloc);
	SortedListIteratorPtr SlInt1 = SLCreateIterator(intList);
	SLNextItem(SlInt1); 
	SLDestroy(intList);

	printf("TEST CASE 3: Try to get item from an empty list\n");
	SortedListPtr intList1 = SLCreate(compareInts, destroyBasicTypeAlloc);
 	SLInsert(intList1, &i);
	SLInsert(intList1, &j);
	SortedListIteratorPtr SlInt = SLCreateIterator(intList1);
	printf("\n%d\n",*(int*)SLGetItem(SlInt));
	printf("%d\n",*(int*)SLNextItem(SlInt));
	printf("checking\n");
	SLRemove(intList1, &i); 
	printf("\n%d\n",*(int*)SLGetItem(SlInt));
	SLNextItem(SlInt);

	

 printf("TEST CASE 4: inserting strings  \n");
	SortedListPtr intList5 = SLCreate(compareStrings, destroyBasicTypeAlloc);
	SLInsert(intList5, str3);
	SLInsert(intList5,str6);
	SLInsert(intList5,str2);
	SortedListIteratorPtr SlIt5 = SLCreateIterator(intList5);
	printf("\n%s\n",SLGetItem(SlIt5));
	printf("\n%s\n",SLNextItem(SlIt5));
	printf("\n%s\n",SLNextItem(SlIt5));


printf("TEST CASE 5: inserting at tail  \n");
SortedListPtr intList6 = SLCreate(compareInts, destroyBasicTypeAlloc);
SLInsert(intList6, &x); //5
SLInsert(intList6, &y); //7
SLInsert(intList6, &n); //11
SLInsert(intList6, &m); //10
//11 10 7 5
SortedListIteratorPtr iter = SLCreateIterator(intList6);
printf("\n%d\n",*(int*)SLGetItem(iter));
void *item;
while((item = SLNextItem(iter)) != NULL)
{
	printf("\n%d\n",*(int*)SLGetItem(iter));
}
SLInsert(intList6, &a);

//printf("TEST CASE 6: insert iterator and then insert to head \n");
//SortedListPtr intList4 = SLCreate(compareInts, destroyBasicTypeAlloc);
//SLInsert(intList4, &y); //7
//SLInsert(intList4, &g); //4
//SLInsert(intList4, &h); //5
//11,7,6,5,4,
//SortedListIteratorPtr SlIt4 = SLCreateIterator(intList4);
//SLInsert(intList4, &n); //11
//SLInsert(intList4, &i); //6
//printf("\n%d\n",*(int*)SLGetItem(SlIt4));
//printf("\n%d\n",*(int*)SLNextItem(SlIt4));
//printf("\n%d\n",*(int*)SLNextItem(SlIt4));
//printf("\n%d\n",*(int*)SLNextItem(SlIt4));

printf("TEST CASE 7: insert iterator and then insert to head \n");
double xx=5;
double yy=7;
double nn=11;
double mm=10;
double aa=1;
SortedListPtr intList7 = SLCreate(compareDoubles, destroyBasicTypeAlloc);
SLInsert(intList7, &xx); //5
SLInsert(intList7, &yy); //7
SLInsert(intList7, &nn); //11
SLInsert(intList7, &mm); //10
SortedListIteratorPtr iter1 = SLCreateIterator(intList7);
printf("\n%f\n",*(double*)SLGetItem(iter1));
void *it;
while((item = SLNextItem(iter1)) != NULL)
{
    printf("\n%f\n",*(double*)SLGetItem(iter1));
}
SLInsert(intList7, &aa);



//do i have to free the dATA when it is removed or do i just unlink it 144 line

//mkaefile

// if inserting then iterator then inserting again // out of order 	//THE LAST 2 ARE OFF

//Add item to tail after all items have been returned by iterator.



}
