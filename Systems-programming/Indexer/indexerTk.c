/*
  tokenizer.c
 */
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>
#include "indexer.h"
/*
 * TKCreate creates a new TokenizerT object for a given token stream
 * (given as a string).
 * 
 * TKCreate should copy the arguments so that it is not dependent on
 * them staying immutable after returning.  (In the future, this may change
 * to increase efficiency.)
 *
 * If the function succeeds, it returns a non-NULL TokenizerT.
 * Else it returns NULL.
 *
 */
static int count=0;
TokenizerT *TKCreate( char * ts ) {
	TokenizerT *tk=malloc(sizeof(TokenizerT)); 
	char *tok;
	tok = ((char *)malloc(sizeof(char)* strlen(ts)+10));
	tok=strcpy(tok, ts);
	tk->tokens=tok;
	if (tk==NULL)
	{
		return NULL;
	}
	else
	{
		return tk;
	}
}

/*
 * TKDestroy destroys a TokenizerT object.  It should free all dynamically
 * allocated memory that is part of the object being destroyed.
 *
 */

void TKDestroy( TokenizerT * tk ) {
	free(tk->tokens);
	free(tk);
}

/*
 * TKGetNextToken returns the next token from the token stream as a
 * character string.  Space for the returned token should be dynamically
 * allocated.  The caller is responsible for freeing the space once it is
 * no longer needed.
 *
 * If the function succeeds, it returns a C string (delimited by '\0')
 * containing the token.  Else it returns 0.
 *
 */

char *TKGetNextToken( TokenizerT * tk,long size ) {
	char *tokenToReturn=malloc (sizeof (char) * strlen(tk->tokens)+10);
	char *newTokens=malloc (sizeof (char) * strlen(tk->tokens)+10);
	int a=0;
	int index =0;
	while(index < strlen(tk->tokens))
	{		
		while(isdigit(tk->tokens[index])==0 && isalpha(tk->tokens[index])==0)//not digit or aphabet , everything else
		{
			index++;
			count++;
		}

		while(isdigit(tk->tokens[index])!=0||isalpha(tk->tokens[index])!=0 ) //its alpha or numbers
		{															
			tokenToReturn[a]=tk->tokens[index];
			a++;
			index++;
			count++;
		}
		while(isdigit(tk->tokens[index])==0 && isalpha(tk->tokens[index])==0)//not digit or aphabet , everything else
		{
			index++;
			count++;
		}
		int p;
		for(p=0; index<strlen(tk->tokens);p++) 							//makes string shorter 
		{
			newTokens[p]=tk->tokens[index];
			index++;
		}
		strcpy(tk->tokens, newTokens);
		tokenToReturn[a]='\0';
		count++;
		return tokenToReturn;
	}	
	return 0;		
}

/*
 * main will have a string argument (in argv[1]).
 * The string argument contains the tokens.
 * Print out the tokens in the second string in left-to-right order.
 * Each token should be printed on a separate line.
 */

int tokenize(char *tokenString,char* filename) {
	char *token=malloc (sizeof (char) * strlen(tokenString)+10);
	char *newTokens=malloc (sizeof (char) * strlen(tokenString)+10);
	long filesize=strlen(tokenString);
	TokenizerT* tkT= TKCreate(tokenString); 
	while((token=TKGetNextToken(tkT,filesize))!=0)					//get next token till no more tokens 
	{
		char *nfn=malloc (sizeof (char) * strlen(filename)+10);
		strcpy(nfn,filename);
		int i =0;
		while(i!=strlen(token))										//make to lower case
		{
			if(isalpha(token[i])==0)								//not alpha
			{
				i++;
			}	
			else
			{
				token[i]=tolower(token[i]);							//if alpha
				i++;
			}	
		}
		search(token,nfn);											//search function with token as argument and filename as second argument
	}
	return 0;
}
