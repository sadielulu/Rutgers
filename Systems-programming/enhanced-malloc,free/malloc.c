#include "malloc.h"
#include <stdlib.h>
#include <stdio.h>

#define malloc(x) myMalloc(x, __FILE__, __LINE__)
#define free(x) myFree(x, __FILE__,__LINE__)
#define BLOCKSIZE 50000										//full size 
#define fragmentatonSize 10000								//size for small part

static char big_block[BLOCKSIZE];
static int initialized =0; 									//makes it keep its state throughout running this function
static struct mementry* lRoot;
static struct mementry* sRoot;

void* myMalloc(unsigned int size,const char* file, const int line)
{
	struct mementry* p;
	struct mementry* succ; 

	if(initialized==0) 										//initialize small root and large root 
	{
		sRoot=(mementry*)big_block;
		sRoot->prev=sRoot->succ=NULL;
		sRoot->size=BLOCKSIZE-sizeof(mementry);
		sRoot->isFree=1;

		lRoot=(mementry*)(big_block+fragmentatonSize+sizeof(mementry)); //large root points to the start of larger chunk for size more than 100 bytes
		lRoot->prev=lRoot->succ=NULL;
		lRoot->size=(BLOCKSIZE-(2*sizeof(mementry)))-fragmentatonSize;
		lRoot->isFree=1;
		initialized=1;
	}
	if(size<=100)
	{
		p=sRoot;
	}
	else 
	{
		p=lRoot;	
	}
	do{
		if(p->size<size) 								//current struct's size not big enough, move to next one
		{
			p=p->succ;
		}
		else if(p->isFree==0)							//size is large enough but its not free , so move to next one
		{
			p=p->succ;
		}
		else if(p->size < (size +sizeof(mementry))) 	//size is just large enough for the size requested 
		{
			p->isFree=0;
			return (char*)p+sizeof(mementry);
		}
		else 											//size large enough for size requested plus new struct 
		{
			succ=(mementry*)((char*)p+sizeof(mementry)+size);
			succ->prev=p;
			succ->succ=p->succ;
			if(p->succ != NULL )						//if new struct is not at end 
			{
				p->succ->prev=succ;	
			}	
			p->succ=succ;
			succ->size=(p->size)-size-sizeof(mementry);
			succ->isFree=1;
			p->size=size;
			p->isFree=0;
			return (char*)p+sizeof(mementry);	
		}
	}while(p!=NULL);
	fprintf(stderr, "theres no space left to allocate anything else \n Error occurred at line %d in file %s \n",line,file);
	return NULL;

	/*
		set up root on both sides
		depends on size p points to root
		p's size is size 
		set up succ with leftover size

		while p is not null
		check if free
		check if size is sufficient 
		//check if size and header is sufficient ,==same size in my program
		else its good so return pointer 
		no error check to see if itsbetweene or at end 
		make p not free, make succ free 
		make p size size and succ size whatever is leftover 
		return p 
		
	*/
}


void myFree(void* p1,const char* file, const int line)
{
	mementry *ptr, *succ, *pred;
	int tf=0;											//flag to see if pointer has been found 
	if(p1==NULL)					
	{
		fprintf(stderr, "pointer is NULL \n Error occurred at line %d in file %s \n",line,file);
		return;
	}
	if(initialized==0)									//roots havent been initialized
	{
		fprintf(stderr, "theres nothing to be freed\n Error occurred at line %d in file %s \n",line,file);
		return;
	}

	mementry* dummy;									//dummy struct pointer goes through the linked structs to find the right struct 
	dummy=sRoot;										//starts at small
														//go through the array and check if ptr+memsize is ==to p1
	while(dummy!=NULL)									//look in smaller space first
	{
		if((char*)dummy+sizeof(mementry)==(char*)p1)
		{
			tf=1;
			//printf("foundd it \n");
			ptr=(mementry*)p1-1;			
			ptr=(mementry*)((char*)p1-sizeof(mementry)); //points to beginning of struct
			
			if(ptr->isFree==1)							//check if ptr is already freed 
			{
				fprintf(stderr, " pointer you are trying to free has already been freed \n Error occurred at line %d in file %s \n",line,file);
				return;
			}
			break;										//break of while 
		}
		else
		{
			dummy=dummy->succ;							//move on to next struct
		}
	}													//end of while

	if(dummy==NULL&& tf==0)								//LOOK IN LARGER SPACE, if wasnt found in smaller space
	{
		dummy=lRoot;
		while(dummy!=NULL)
		{
			if((char*)dummy+sizeof(mementry)==(char*)p1)
			{
				tf=1;
				//printf("found it \n");
				ptr=(mementry*)p1-1;					//points to end of mementry
				ptr=(mementry*)((char*)p1-sizeof(mementry));
				
				if(ptr->isFree==1)						//check if ptr is already freed 
				{
					fprintf(stderr, " pointer you are trying to free has already been freed \n Error occurred at line %d in file %s \n",line,file);					return;
				}
				break;									//break of while
			}
			else
			{
				dummy=dummy->succ;						//move on to next struct 
			}
		}												//end of while
		if(tf==0)
		{
			printf("this pointer was never returned from malloc \n Error occurred at line %d in file %s \n",line,file);
			return;
		}
	}

	if(ptr==NULL)										//check if ptr == null
	{
		fprintf(stderr, " struct of pointer is null \n Error occurred at line %d in file %s \n",line,file);
		return;
	}
	if(ptr->isFree==1)									//check if ptr is already freed //freeing the same thing
	{
			fprintf(stderr, " pointer you are trying to free has already been freed \n Error occurred at line %d in file %s \n",line,file);
		return;
	}													//freeing pointer tht werent allocated
	succ=ptr->succ;
	pred=ptr->prev;
	if(succ!=NULL && succ->isFree==0)					//check if succ is free and if its not null to connect it with current pointer
	{													//unlinks succ struct from structs link
		ptr->size=ptr->size+succ->size+sizeof(mementry);//change size
		ptr->succ=succ->succ;
		if (succ->succ != NULL)
		{
			succ->succ->prev=ptr;
		}
		ptr->isFree=1;
	}
	succ=ptr->succ;
	pred=ptr->prev;
	if(pred!=NULL && pred->isFree==0)					//check if pred is free and if its not null to connect it with current pointer
	{													//unlinks ptr struct from structs link
		pred->size=ptr->size+sizeof(mementry)+pred->size;
		pred->succ=succ;
		if (succ->succ != NULL)
		{
			succ->prev=pred;
		}

		pred->isFree=1;
		return;
	}
	ptr->isFree=1; 										//just make it free 
	return;
}

/*
for free 
ptr= address of first so that minus mementry is ptr and check if its
check if ptr == null// do nothing
//freeing pointer tht werent allovcated 
//freeing the same thing
//freeing pointers tht were not returned by pointer QUESTION

succ=ptr->succ
pred=ptr->prev
if succ is free
succ=ptr->succ
pred=ptr->prev
if pred is free
-------------------------------------------------------------------------
prev|succ|isfree|size|chunk to size|prev|succ|isfree|size|chunk to size	|
-------------------------------------------------------------------------
*/
//testcases, readme 





