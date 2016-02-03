

#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>
#include "indexer.h"

static int init =0;
WordNode wordnode,newwordnode;
FileNode filenode,newfilenode;
static WordNode root;

/**
checks if list is of size 1 
goes through list and sees where to insert new word node , checks if first in list, between nodes and if last in list 
finds a place and place word in node in list 
initializes count, word, next, prev
it does this in a sorted ascending manner 
if word is in list it goes through another list of that word , the file linked list
goes through that list and finds a place for the file node and place file node in list 
if file is there already increase count to the count variable for that file
before inserting it looks for its right place by count, in descending order
if count is same sort by the pathname in ascending order

*/
int search(char* token,char* filename){
	WordNode wn; 
	//printf("%s\n",filename );
	FileNode fileRoot;
	FileNode fn;
	if (init==0)															//initialize list, first node in list is created
	{
		filenode = (FileNode)malloc(sizeof(FileNode)+10);				
		wordnode = (WordNode)malloc(sizeof(WordNode)+10);
		init=1;
		wordnode->word=token;
		wordnode->nextWord=NULL;
		wordnode->next=filenode;
		filenode->next=NULL;
		filenode->count=1;
		filenode->pathname=filename;
		root=wordnode;
	}
	else 																	//start searching through list
	{
		WordNode curr=root;													//current pointer
		WordNode prev=root;													//previous pointer
		while(curr!=NULL)													//while it doesnt hit the last node			
		{
			int i =strcmp(token,curr->word);								//compare token with current pointer word
			if(i<0) 														//str1 is less than str 2
			{		
				if(curr==root)												//if root	
				{
					wn = (WordNode)malloc(sizeof(WordNode)+10); 			//create word node
					wn->word=token;											//add token to node
					wn->nextWord=curr;										//link 
					newfilenode = (FileNode)malloc(sizeof(FileNode)+10); 	//create file node
					wn->next=newfilenode;									//link file node to current node
					root=wn;												//new root is assigned
					newfilenode->next=NULL;									//filenode's next file node is null because first one in list
					newfilenode->count=1;									//assign count to 1		
					newfilenode->pathname=filename;							//add pathname
					return 0;

				}					
				else														// if its not root or last node
				{
					wn = (WordNode)malloc(sizeof(WordNode)+10); 			//create word node
					wn->word=token;											//add word to node
					prev->nextWord=wn;										//link
					wn->nextWord=curr;										//link
					newfilenode = (FileNode)malloc(sizeof(FileNode)+10); 	//create file node
					wn->next=newfilenode;									//link node to file node
					newfilenode->next=NULL;									//link node
					newfilenode->count=1;									//assign count to 1
					newfilenode->pathname=filename;							//add pathname
					return 0;
				}
			}
			else if (i>0)													//str2 is less than str1
			{
				prev=curr;													//move to next node
				curr=curr->nextWord;
			}
			else if (i==0)													//if word equals the same as current word
			{
				FileNode fileCurr,filePrev; 								// a previous and current file pointer node
				fileCurr=curr->next;										//assign it to current word node's first file
				filePrev=curr->next;

				while(fileCurr!=NULL)										//check if file in list
				{

					int p=strcmp(filename,fileCurr->pathname);				//compare filenames
					if(p==0)												//check if file pathname is equal
					{
						fileCurr->count++;									//add count
						return 0;
					}
					else if(p<0)											//str1 is less than str 2
					{
						if(fileCurr==curr->next)							//if its the first filename in list	
						{
							//printf("HELLO\n");
							fn = (FileNode)malloc(sizeof(FileNode)+10); 	//create node
							fn->count=1;									//add count
							fn->pathname=filename;							//add pathname
							curr->next=fn;									//link current node's first file to this file
							fn->next=fileCurr;								
							return 0;
						}					
						else												//any other than root and last node
						{
							fn = (FileNode)malloc(sizeof(FileNode)+10); 	//create node
							fn->count=1;									//add pathname
							fn->pathname=filename;	
							filePrev->next=fn;
							fn->next=fileCurr;
							return 0;
						}
					}
					else if (p>0)											//str2 is less than str1
					{
						filePrev=fileCurr;									//keep it moving
						fileCurr=fileCurr->next;
					}
				}//end of while
				fn = (FileNode)malloc(sizeof(FileNode)+10); 				//create node
				fn->count=1;												//add count
				fn->pathname=filename;										//add pathname
				filePrev->next=fn;											//link file nodes
				fn->next=NULL;												//file node is last now so file node's next points to null
				return 0;
			}
		}//end of while
		wn = (WordNode)malloc(sizeof(WordNode)+10); 						//create word node at end of list 
		wn->word=token;														//add word
		wn->nextWord=NULL;													//word is at end of list so its next is null								
		prev->nextWord=wn;													//link, curr is null cause reached end of list so use prev
		newfilenode = (FileNode)malloc(sizeof(FileNode)+10); 				//create file node
		wn->next=newfilenode;												//link file node to word node
		newfilenode->next=NULL;												//last and first file node 
		newfilenode->count=1;												//assign count
		newfilenode->pathname=filename;										//add pathname
	}	
	return 0;
}

/**
	writes tokens from linked list to given file in argument
	first this function goes through the list and sorts the file names by count number
	then it goes back to root and prints words and files to the file name
**/

int writeToFile(FILE* writeF){
	WordNode wp=root;
	FileNode fileRoot=root->next;
	FileNode current=fileRoot;
	FileNode fc=fileRoot;							
	FileNode fp=fileRoot;
	FileNode prev=fileRoot;

	while(wp!=NULL)															//while word does not equal null
	{
		while(current!=NULL)												//gets current pointer
		{
			fc=current;
			fp=wp->next;
			while(fc!=NULL)													//go through file list
			{
				if(fileRoot->next==NULL)									//if size one in list
				{
					break;
				}
				else if( current->count<fc->count)							//if current pointer's count is less than node count
				{			
					if(current==fileRoot)
					{
						current->next=fc->next;
						wp->next=fc;
						fc->next=current;
						break;
					}					
					else
					{
						current->next=fc->next;
						prev->next=fc;
						fc->next=current;
						break;
					}														//more than one node in file list
				}
				else if(fc->count<=current->count)							//keep moving if node count is less than or equal to current pointer node
				{
					fp=fc;
					fc=fc->next;
				}
			}//end of while		
			prev=current;
			current=current->next;
		}//end of while
		wp=wp->nextWord;													//moves to next word
		if (wp!=NULL)														//this is only done for every word except the last one
		{
			prev=wp->next;
			current=wp->next;
			fileRoot=wp->next;
		}		
	}//end of while
																			//goes through linked list structure and prints it to file
	WordNode wordPtr=root;													//starts at root
	FileNode filePtr;
	fprintf(writeF, "{\"list\": [ \n ");
	while(wordPtr->nextWord!=NULL)											//goes through the words while it does not reach last word
	{
		fprintf(writeF, "\t\t{\"%s\": [ \n ",wordPtr->word);
		filePtr=wordPtr->next;												//goes to first file of word									
		while(filePtr->next!=NULL)											//goes through file nodes of each word except last one
		{
			fprintf(writeF, "\t\t\t\t{\"%s\": %i}, \n ",filePtr->pathname,filePtr->count);
			filePtr=filePtr->next;
		}
		fprintf(writeF, "\t\t\t\t{\"%s\": %i} \n ",filePtr->pathname,filePtr->count);
		fprintf(writeF, "\t\t]}, \n ");
		wordPtr=wordPtr->nextWord;											//moves word pointer to next word in list
	}
	fprintf(writeF, "\t\t{\"%s\": [ \n ",wordPtr->word);
	filePtr=wordPtr->next;													//checks last word in list
	while(filePtr->next!=NULL)												//checks last word's file nodes
	{
		fprintf(writeF, "\t\t\t\t{\"%s\": %i}, \n ",filePtr->pathname,filePtr->count);
		filePtr=filePtr->next;												//moves file pointer to next file node in list
	}
	fprintf(writeF, "\t\t\t\t{\"%s\": %i} \n ",filePtr->pathname,filePtr->count);
	fprintf(writeF, "\t\t]} \n ");
	fprintf(writeF, "]} \n ");
	return 0;

}


