#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <strings.h>
#include <ctype.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <dirent.h>
#include <sys/types.h>
#include <unistd.h>
#include "indexer.h"

/*
global variables
*/
char c;
int flag=0;
struct stat statbuf;
size_t dirLen;
int check=0;
FILE * f ;
FILE * ff;

/**
recursively traverses through directories
*/
  void dTraverse(char* file)
  {
    char* location="";                                            //where pathname will be stored 
    DIR * dir = opendir( file );                                  //open directory
    if(!dir)
    {                                                             //if file
      f= fopen(file, "r" );                                       //open file
      if (f)
      {                                                           //if its a file 
        check =1;                                                 //1 because it is a file 
        fclose(f);   
      }
      else
      {                                                           //if its not a file 
        fprintf(stderr,"not a file or a directory\n");
        exit(1);
      }
      return;
    }
    chdir(file);
    struct dirent *dptr;                                          //directory pointer
    while(NULL != (dptr = readdir(dir)))
    {                                                             //if directory , goes through it     
      if( strcmp( dptr->d_name, ".")==0 || strcmp( dptr->d_name, ".." )==0 )
      { 
        continue;                                                 //no dot files 
      }                                                           //makes pathname
      size_t dirLen = strlen(file);
      location = malloc( dirLen + strlen(dptr->d_name) + 1 );  
      strncpy( location, file, dirLen );
      location[ dirLen ] = '/';
      location[ dirLen+1 ] =  '\0'; 
      strcat(location, dptr->d_name );
      dTraverse(dptr->d_name);                                    //recursively goes through directories 
      if(check==1)
      {                                                           //if pathname is a file 
        
        ff = fopen(dptr->d_name, "r" );                           //open file
        if (ff)
        {                                                         //if its a file 
          fseek(ff, 0, SEEK_END);
          long input_file_size = ftell(ff);
          rewind(ff);
          char* content = malloc((input_file_size + 1) * (sizeof(char)));
          fread(content, sizeof(char), input_file_size, ff);
          content[input_file_size] = 0; 
          //printf(" [%s] \n " ,dptr->d_name); //filename
          //printf(" [%s] \n " ,location); //pathname
          tokenize(content,location);                              //TOKENIZE
          fclose(ff);
        }
        check=0;
      }
    }
    free(location);
    chdir("..");
    closedir(dir);
  }

/**
checks if file in argument is a regular file or not 
*/

int is_regular_file(const char *path)
{
    struct stat path_stat;
    stat(path, &path_stat);
    return S_ISREG(path_stat.st_mode);
}

/**
checks if file exist in current directory 
*/

int doesFileExist(const char *filename) {
    struct stat st;
    int result = stat(filename, &st);
    return result == 0;
}

int main (int argc, char **argv)
{
  if (argc != 3)                                                    //check arguments 
  {
    fprintf (stderr, "THIS PROGRAM REQUIRES three ARGUMENTS.\n");
    return 0;
  }
  FILE* writeFile;
  FILE* file;
  char *file_contents;
  long input_file_size;
  char *filename = argv[1];                                         //file to write to 
  char *file_or_directory_name =argv[2];                            //directory or file  
  int i = is_regular_file(file_or_directory_name);
  if(i==0)                                                          //its a directory 
  {
    dTraverse(file_or_directory_name);
  }
  else if(i==1)                                                     //its a file
  {
    file = fopen(file_or_directory_name, "r" );                     //open file
                                                                    //put file into a string
    fseek(file, 0, SEEK_END);
    input_file_size = ftell(file);
    rewind(file);
    file_contents = malloc((input_file_size + 1) * (sizeof(char)));
    fread(file_contents, sizeof(char), input_file_size, file);
    fclose(file);
    file_contents[input_file_size] = 0;                             //add \0 at end 
    tokenize(file_contents,file_or_directory_name);                 //TOKENIZE
  }
  else                                                              //file or directory does not exist , not a dir or file
  {
    fprintf(stderr,"FILE OR DIRECTORY DOES NOT EXIST\n");
    exit(1);
  }

                                                                    //write to file STARTS
  if(doesFileExist(filename)==0)                                    //file doesnt exist, create one
  {
    writeFile= fopen(filename, "w" );                               //open file
    if (!writeFile)                                                 //checks if its not a file 
    { 
      fprintf (stderr, "first argument is not a file. \n");
    }
    else                                                            //checks if its file
    {
      writeToFile(writeFile);                                       //write to file function
    }
    fclose(writeFile);                                              //close file
    exit(1);
  }
  else                                                              //already exist
  {
    printf("FILE ALREADY EXISTS DO YOU WANT TO OVERWRITE? enter yes or no\n");
    char answer[20];
    scanf(" %s", answer);                                           //get user response

    int i=0;
    while(i!=strlen(answer))
    {
      answer[i]=tolower(answer[i]);
      i++;
    }    
    int x =strcmp(answer,"yes");
    int y =strcmp(answer,"no");
    while(x!=0 &&  y!=0)
    {
      printf("thats not yes or no, do you want to overwrite this file\n");
      char newanswer[20];
      scanf(" %s", newanswer);                                      //get user response

      i=0;
      while(i!=strlen(newanswer))
      {
        newanswer[i]=tolower(newanswer[i]);
        i++;
      }    
      x =strcmp(newanswer,"yes");
      y =strcmp(newanswer,"no");
    }
    if(x==0)                                                          //overwrite, user said yes
    {
      writeFile= fopen(filename, "w" );                               //open file
      if (!writeFile)                                                 //checks if its not a file 
      { 
        fprintf (stderr, "first argument is not a file. \n");
      }
      else                                                            //checks if its file
      {   
        writeToFile(writeFile);                                       //write to file function
      }
      fclose(writeFile);
      exit(1);
    }
    else if(y==0)                                                     //user said no
    {
      printf("OK GOODBYE\n");
      exit(1);
    }   
  }
  return 0;
}


