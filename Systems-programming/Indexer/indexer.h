#ifndef	INDEXER_H
#define INDEXER_H
#include <stdlib.h>

struct TokenizerT_ {
	char* tokens;
};
typedef struct TokenizerT_ TokenizerT;

struct FileNode
{
	struct FileNode* next;
	int count;
	char* pathname;
};
typedef struct FileNode* FileNode;

struct WordNode
{
	char* word;
	struct WordNode* nextWord;
	FileNode next;
};
typedef struct WordNode* WordNode;

int tokenize(char *tokenString,char* filename);
int search(char* token,char* filename);
char *TKGetNextToken( TokenizerT * tk ,long size);
TokenizerT *TKCreate( char * ts );
int writeToFile(FILE* wf);

#endif