#include "sorted-list.h"
#include <stdio.h>

/*
 * SLCreate creates a new, empty sorted list.  The caller must provide
 * a comparator function that can be used to order objects that will be
 * kept in the list, and a destruct function that gets rid of the objects
 * once they are no longer in the list or referred to in an iterator.
 * 
 * If the function succeeds, it returns a (non-NULL) SortedListT object,
 * otherwise, it returns NULL.
 */
SortedListPtr SLCreate(CompareFuncT cf, DestructFuncT df)
{	//check that both params were passed into the function
	if(cf == NULL || df == NULL)
	{
		printf("Missing information to create this list \n"); //print error message if they were not
		return NULL; //return NULL becuase we could not make the list
	}
	//if parameters arent NULL , assign struct members a variable 
	SortedListPtr list = (SortedListPtr) malloc(sizeof(struct SortedList)+100);
	list->root = NULL;
	list->CompareFuncT = cf;
	list->DestructFuncT = df;
	return list;
}

/*
 * SLDestroy destroys a list, freeing all dynamically allocated memory.
 */
void SLDestroy(SortedListPtr list)
{ 	//if the list is empty there is nothing to destroy so don't free anything
	if(list == NULL)
	return;
		else //else something is in the list and we need to delete it
		{	
			node deletePTR; //create a node pointer to delete objects
			while(list->root != NULL) //while nodes exsist delete all dynamically allocated nodes
			{ 
				deletePTR = list->root;
				list->root = list->root->next;//set the root to the next node
				list->DestructFuncT(deletePTR->data); //empty data
				free(deletePTR); // free the pointer
			}
			free(list); //then free the structure
		}
}

/*
 * SLInsert inserts a given object into a sorted list, maintaining sorted
 * order of all objects in the list.  If the new object is equal to a subset
 * of existing objects in the list, then the subset can be kept in any
 * order.
 *
 * If the function succeeds, it returns 1, othrewise it returns 0.
 */
int SLInsert(SortedListPtr list, void *newObj)
{
	node prev;
	int r; //-1 or 0 or 1 
	node curr ;
	curr=list->root; 
	void* object;
	node newnode = (node) malloc(sizeof(struct node)+100);
	if(list->root == NULL)//for first node in the list
	{	
		list->root=newnode;
		newnode->data=newObj;
		newnode->next=NULL;
		newnode->refcount=1;
		return 1;
	}
	newnode->data=newObj;
	object =curr->data;
	r = list->CompareFuncT(newnode->data,object); //newObj is what we are inserting ,object is object in list

	if (r >=0 )// first parameter of compare is greater than or equal to second parameter  
	{
		newnode->next=list->root;
		list->root=newnode; //adding to first of the list 
		list->root->data=newObj;
		list->root->refcount=1;	
		return 1;
	} 
	else if (r <=0) //first parameter is smaller or equal to second parameter 
	{
		while(r<0 && curr->next!=NULL) //if object is small and there exist a next item in the list
		{
			prev=curr;
			curr=curr->next;
			object =curr->data; 
			r = list->CompareFuncT(newnode->data,object);
		}
		if (curr->next==NULL) //check for last item in list
		{
			curr->next=newnode;
			newnode->next=NULL;
			return 1;
		}
		//create node, if it is equal or if it reaches an item that is less than what we are inserting
		prev->next=newnode;
		newnode->next=curr;	
		newnode->data=newObj;
		newnode->refcount=1;	
		return 1;
	}
	return 0;
}

/*
 * SLRemove removes a given object from a sorted list.  Sorted ordering
 * should be maintained.  SLRemove may not change the object whose
 * pointer is passed as the second argument.  This allows you to pass
 * a pointer to a temp object equal to the object in the sorted list you
 * want to remove.
 *
 * If the function succeeds, it returns 1, otherwise it returns 0.
 */

int SLRemove(SortedListPtr list, void *newObj)
{
	node prev; 
	int result;
	node curr = (node) malloc(sizeof(struct node)+100);
	if(list==NULL)//there is no list
	{
		printf("Nothing to remove because there are no items in list \n ");
		return 0;
	}
	if(list->root==NULL)//first item is empty
	{
		printf("Nothing to remove because there are no items in list \n ");
		return 0;
	}
	result = list->CompareFuncT(newObj,list->root->data); //start comparing with root 
	if(list->root->next==NULL && result ==0) //if only one item in list
	{   
		//list->DestructFuncT(list->root->data);
		return 1;
	}
	if (result ==0) //deleting first item
	{
		curr=list->root->next;
		list->DestructFuncT(list->root->data);
		return 1;
	}
	curr=list->root;
	if(curr->next==NULL && result==0)//for the last one
	{	curr->refcount=0;
		prev->next=NULL; 
		//list->DestructFuncT(curr->data); //delete data 
		return 1;
	}
	while(result!=0) //while items are not equal
	{
		if (curr->next==NULL) //if there is no next in list ,went through the list, otherwise continue comparing
		{
			return 0;
		}
		prev=curr;
		curr=curr->next;
		result = list->CompareFuncT(newObj,curr->data);
	}//result equals 0
	//for anywhere in middle 
	if (curr->next!=NULL) //if there is no next in list ,went through the list, otherwise continue comparing
	{	prev->next=curr->next;
		curr->refcount=0;
		//list->DestructFuncT(curr->data); //delete data 
		return 1;
	}
	if(curr->next==NULL && result==0)//for the last one
	{	curr->refcount=0;
		prev->next=NULL; 
		//list->DestructFuncT(curr->data); //delete data 
		return 1;
	}
}

/*
 * SLCreateIterator creates an iterator object that will allow the caller
 * to "walk" through the list from beginning to the end using SLNextItem.
 *
 * If the function succeeds, it returns a non-NULL pointer to a
 * SortedListIterT object, otherwise it returns NULL.  The SortedListT
 * object should point to the first item in the sorted list, if the sorted
 * list is not empty.  If the sorted list object is empty, then the iterator
 * should have a null pointer.
 */

SortedListIteratorPtr SLCreateIterator(SortedListPtr list)
{	//if list is not initalized or if the list is empty do nothing
	if(list==NULL || list->root == NULL )
	{
		printf("List was not allocated or is empty so cannot create a pointer to the list\n");
		return NULL;
	}
	SortedListIteratorPtr iterator = (SortedListIteratorPtr) malloc(sizeof(struct SortedListIterator)+10);	//have the pointer point to the root, must create space in memory
	iterator->curr = list->root; //changed currentnode to current #########
	list->root->refcount++; //increment referencecount pointing to root
	return iterator;
}


/*
 * SLDestroyIterator destroys an iterator object that was created using
 * SLCreateIterator().  Note that this function should destroy the
 * iterator but should NOT affect the original list used to create
 * the iterator in any way.
 *
 */

void SLDestroyIterator(SortedListIteratorPtr iter)
{
	if(iter != NULL)//if the iter exists
	{
		if(iter->curr != NULL)//and if the current node exists
		{
			iter->curr->refcount--; //decrement the refcount
			free(iter); 
		}
	}
}


/*
 * SLGetItem returns the pointer to the data associated with the
 * SortedListIteratorPtr.  It should return 0 if the iterator
 * advances past the end of the sorted list.
 */

void * SLGetItem( SortedListIteratorPtr iter )
{
	if(iter != NULL)//if the iter exists
	{
		if(iter->curr != NULL)// if the current node exsists
		{
			return iter->curr->data; 
		}
		else //there is no item to get, print error message
		{
			printf("No item to get.\n");
			return 0;
		}
	}
}

/*
 * SLNextItem returns the next object in the list encapsulated by the
 * given iterator.  It should return a NULL when the end of the list
 * has been reached.
 *
 * One complication you MUST consider/address is what happens if a
 * sorted list encapsulated within an iterator is modified while that
 * iterator is active.  For example, what if an iterator is "pointing"
 * to some object in the list as the next one to be returned but that
 * object is removed from the list using SLRemove() before SLNextItem()
 * is called.
 *
 */
void * SLNextItem(SortedListIteratorPtr iter)
{
	if(iter == NULL)//iter wasn't allocated right
	{	
		printf("iter points to nothing\n");
		return NULL;
	}
	if(iter->curr->next==NULL) // there is no next item.....end of the list
	{ 
		printf("there is no more items in list so you cant move next \n");
		return NULL;
	}
 		if(iter->curr==NULL)// there is no next item
 		{ 
			return NULL;
		}
	node temp;
	if(iter->curr->data != NULL)// current data is not blank 
	{
		if (iter->curr->refcount==0)// SLremoved has been called on this item and it has been unlinked in list
		{
			temp=iter->curr;
			if(iter->curr->next!=NULL)
			{
				iter->curr=iter->curr->next; 
				iter->curr->refcount=(iter->curr->refcount)+1; //increments refcounter of next item
			}
			free(temp); //frees item that has been removed 
			return iter->curr->data;
		}
		iter->curr->refcount= (iter->curr->refcount)-1; //decrements refcounter of item, before it goes to next
		if(iter->curr->next!=NULL)
		{
			iter->curr=iter->curr->next; 
			iter->curr->refcount=(iter->curr->refcount)+1; //increments refcoutner of next item
		}
		return iter->curr->data;
	}
}