#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "c-sim.h"


int PowerOfTwo(unsigned int x){//method to check if inputs are a power of two
    if ((x != 1) && (x & (x-1))){
        return 0;// it's not a power of two
    }
  else{
      return 1;
}
}

void freeCache(Cache **cacheArray, int setnum){
    int i=0;
  for(;i<setnum;i++){
   free(cacheArray[i]);
  }
  free(cacheArray);
}


int shiftTillOne(int mask){
    int numbit = 0;
    while (mask != 0){
        numbit++;
        mask = mask>>1;
   }
return numbit;
}


int main(int argc, char *argv[]){
   
    FILE *file;
	
	int cacheSize;
	int blockSize;
	int conflictMiss;
	int coldMiss;
	char wb;
	int numSets;
	int n=0;
	int j=0;
	int cacheLines;
	char flag;
	
	Cache **cacheArray;
		
	   int blockPass = atoi(argv[3]);
	   int cachePass = atoi(argv[1]);

	    unsigned int powerOfTwoBlock = PowerOfTwo(blockPass);
	    unsigned int powerOfTwoCache = PowerOfTwo(cachePass);

      if (argc == 2){
          if (strcmp(argv[1], "-h") == 0){
           printf("Please enter the following: c-sim [-h] <cache size> <assoc> <block size> <write policy> <trace file>\n");
           exit(0);
    	}
	}


if (argc ==6){

	if (powerOfTwoCache==0) //not powwer of 2
 	{	
		fprintf(stderr, "ERROR: Cache size inputs must be a power of two\n");
		exit(0);
	}

		cacheSize =  atoi(argv[1]);
	
   
   
   if (powerOfTwoBlock==0 )//not powwer of 2
  	 {   
	  	 fprintf(stderr, "ERROR: Block size inputs must be a power of two\n");
    	  exit(0);
  	 }
  
  	 	 blockSize =  atoi(argv[3]);;
 	 
  

  if (cacheSize < blockSize ){
 	 	fprintf(stderr, "ERROR: Cache size inputs must be larger than the blocksize.\n");
 	   	exit(0);
  }

   
   
   
   
   
  
 	if (strcmp(argv[2], "direct") == 0 ||  strcmp(argv[2], "assoc") == 0 ){
        if (strcmp(argv[2], "direct") == 0){
		   //one line per set, multiple sets
	 	   numSets = cacheSize / blockSize;
		   flag = 'd';
	       for(j = 0; j < numSets; j++){ //array 
	           Cache *temp; 
	           temp=(Cache *)malloc(numSets*sizeof(Cache));
	           cacheArray[j] = temp; 
			  cacheArray[j]->valid = 0;
			  
			  //block size 
		}
		
			
         }
	 
        else if (strcmp(argv[2], "assoc") == 0){
			cacheLines= cacheSize / blockSize;
			flag='a';
			
	        Cache *temp; 
	        temp=(Cache *)malloc(50*sizeof(Cache));
	        printf("test\n");
			
		    for(j = 0; j < cacheLines; j++){
		       
		        cacheArray[j] = temp; 
		 }
		 
          // Cache *temp; 
           //temp=(Cache *)malloc(cacheLines*sizeof(Cache));
        printf("test\n");
		  
 	       for(j = 0; j < cacheLines; j++){
 			  cacheArray[j]->valid = 0;
 	    }
       printf("boo\n");
						//set =0, multiple lines	
        }
	}
     else  { 
		 
		char nWay ;//= *argv[2];
			 int n=0;// = atoi(&nWay[6]);
			 flag='n';
			 if (n ==0){
	 			fprintf( stderr, "ERROR: N-way associativity must be greater than 0\n");
			 	
			 }
			 numSets = cacheSize / blockSize/n;
			 
			 
   	       for(j = 0; j < (n*numSets); j++){
   	           Cache *temp; 
   	           temp=(Cache *)malloc(numSets*sizeof(Cache));
   	           cacheArray[j] = temp; 
   			  cacheArray[j]->valid = 0;
   	    }
	}







 
 if (strcmp(argv[4], "wb") == 0 || strcmp(argv[4], "wt") == 0){
	 
   	  		if (strcmp(argv[4], "wb") == 0){
				wb = 'b';
 		       printf("boo\n");
				
 	   	 	}
 
 	  	  	if (strcmp(argv[4], "wt") == 0){
				wb = 't';
	  	 		}
  	  		}
  else 	{
      		fprintf(stderr, "ERROR: argument four should be wb or wt \n");
			exit(0);
  	 	}	
		
		
		
			
  file = fopen(argv[5], "r");
  printf("boo\n");
        
          if (file == NULL){
              fprintf(stderr, "ERROR: No file found\n");
              exit(0);
  			}
} //END OF IF STATEMENT
		

		else{
		fprintf(stderr, "ERROR: Please enter the correct number of program parameters\n");
		exit(0);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		int tag = 0;
		int blockmask;
		int blockBits;
		int indexmask;
		int indexBits;
		
	    int newi2;
		int cacheHit =0;
		int cacheMiss=0;
		int memoryRead=0;
		int memoryWrite=0;	
		char ch;
  
		char c;
	    int  i;
	    int  i2;
		unsigned int  index;
		char *line;
   	 	int z; //in oldest function
		int m; //oldest function
		
 while (fscanf(file, "%x: %c %x",&i,&c,&i2) != EOF){
	 printf("dddddddddd");
	 
		if (flag=='d'){
			//if write miss donts dir bit
			 blockmask = (blockSize - 1);
			 blockBits = shiftTillOne(blockmask);
			
			indexmask = (numSets - 1);
			indexBits = shiftTillOne(indexmask);
		
			newi2 = i2>>blockBits;
	        index = newi2 & indexmask;
	       tag = newi2>>indexBits;
		   
		}
		else if (flag=='a'){
			
		 blockmask = (blockSize - 1);
		 blockBits = shiftTillOne(blockmask);
	
		tag = i2>>blockBits;
        
	   
	   
		} 
		else if (flag=='n'){
		
		 blockmask = (blockSize - 1);
		 blockBits = shiftTillOne(blockmask);
		
		indexmask = (numSets - 1);
		indexBits = shiftTillOne(indexmask);
	
		newi2 = i2>>blockBits;
        index = newi2 & indexmask;
       tag = newi2>>indexBits;
		
		}
		
		char check='n';
		
		
		
		if (wb =='b'){//if write back 
			
		if (c == 'R' ){//read, do i check if valid ==1
			if(flag=='d'){ 
				for(int i=0; i<numSets; i++){
					if (index==i){
						if(tag==cacheArray[i]->tag){
							cacheHit++;
							break;
							
						}else{
							cacheMiss++;	
							if (cacheArray[i]->dirtyBit== 1){
								//been modified , new so 
								memoryWrite++; //write the old one
								memoryRead++; //get new one since it isnt in cache
								cacheArray[i]->tag=tag; //overwrite it 
								
							}
							else{ //already in memeory so read from it 
								memoryRead++;
								cacheArray[i]->tag=tag;	
							}
							cacheArray[i]->dirtyBit=0;
						}
					}
				}
			} 
		
		
	else if (flag=='a'){
			
			for (int i =0; i<cacheLines; i++){
				if(tag==cacheArray[i]->tag){
					cacheHit++;
					break;
				}
			}
			cacheMiss++;
			int n;
			int oldest =0;
			//look for oldest 
			for (int old = 0; old<cacheLines; old++){
				if(oldest< cacheArray[old]->age){
					
				}else{
					oldest=cacheArray[old]->age;
					n=old;
				}
			}
			//overwrite tag in cache with one coming in 
			//one coming in is it?
			
			if (cacheArray[i]->dirtyBit== 1){
				//been modified , new so 
				memoryWrite++; //write the old one
				memoryRead++; //get new one since it isnt in cache
				cacheArray[i]->tag=tag; //overwrite it 
				if(check=='c'){
				z=cacheArray[m]->age +1;
				cacheArray[i]->age=z;
				m=i;
				break;
				}
				else if(check=='n'){
					m=i;
					z=cacheArray[m]->age +1;
					cacheArray[i]->age=z;
					m=i;
					check='c';	
					}	
				cacheArray[i]->dirtyBit= 0;
			}
			else{ //already in memeory so read from it 
				memoryRead++;
				cacheArray[i]->tag=tag;
				
				
				if(check=='c'){
				z=cacheArray[m]->age +1;
				cacheArray[i]->age=z;
				m=i;
				break;
				}
				else if(check=='n'){
					m=i;
					z=cacheArray[m]->age +1;
					cacheArray[i]->age=z;
					m=i;
					check='c';	
					}	
				
				cacheArray[i]->dirtyBit= 0;
				
			}
	
			} //end of flag =a
			
			
			else if (flag=='n'){
				int nway= n;
				int i ;
				i=(index-1)*n;
				int length = i+nway;
				for (int j =i-1; j<length; i++) {
						if(tag==cacheArray[i]->tag){
							cacheHit++;
							break;
						}
					}
							//LRU
							//overwrite tag in cache with one coming in 
							//one coming in is it?
								//is it dirty?
								//yes wrtie memory++
								//no read memory++
							
							//mark it as not dirty 
							cacheMiss++;
				}
			
		
		
		
		
		}//end of  reAD

		else{//WRITE
				if(flag=='d'){ 
					for(int i=0; i<numSets; i++){
						if (index==i){
							if (cacheArray[i]->valid == 0){
								cacheArray[i]->tag =tag;
								cacheHit++;
								cacheArray[i]->dirtyBit =1;
							}
							else{
								cacheMiss++;
								if (cacheArray[i]->dirtyBit== 1){
									//been modified , new so 
									memoryRead++;
									memoryWrite++;
								}
								else{ //already in memeory so read from it
									 
									memoryRead++;
									}			
									cacheArray[i]->tag =tag;									
									cacheArray[i]->dirtyBit =1;
									
									}
								}
							}
						} 
				else if (flag=='a'){
				for (int i =0; i<cacheLines; i++){
					if (cacheArray[i]->valid == 0){
						
						cacheArray[i]->tag =tag;
						cacheHit++;
						cacheArray[i]->dirtyBit =1;
					
						//mark oldest 
						
						if(check=='c'){
						
						z=cacheArray[m]->age +1;
						cacheArray[i]->age=z;
						m=i;
						
						
						break;
						}
						else if(check=='n'){
							m=i;
							z=cacheArray[m]->age +1;
							cacheArray[i]->age=z;
							m=i;
							check='c';	
							}	

					}
					else{
						cacheMiss++;
						int oldest =0;
						//look for oldest 
						for (int old = 0; old<cacheLines; old++){
							if(oldest< cacheArray[old]->age){
								
							}else{
								oldest=cacheArray[old]->age;
								n=old;
							}
						}
						
						if (cacheArray[n]->dirtyBit==1) {
							
							memoryWrite++;
							memoryRead++;
							
						}
						else{ //already in memeory so read from it
							memoryRead++;
							}			
							cacheArray[n]->tag =tag;	
							
							if(check=='c'){
						
							z=cacheArray[m]->age +1;
							cacheArray[i]->age=z;
							m=i;
						
						
							break;
							}
							else if(check=='n'){
								m=i;
								z=cacheArray[m]->age +1;
								cacheArray[i]->age=z;
								m=i;
								check='c';	
								}	
															
							cacheArray[n]->dirtyBit =1;
							break;
					
						//overwrite tag in cache with one coming in 
						//mark it as dirty
						}
					}
				} 
				else if (flag=='n'){
					int nway= n;
					int i ;
					i=(index-1)*n;
					int length = i+nway;
					for (int j =i-1; j<length; i++) {
							
						if (cacheArray[i]->valid == 0){
							cacheArray[i]->tag =tag;
							cacheHit++;
							break;
							cacheArray[i]->dirtyBit =1;
						}
						else{
							cacheMiss++;
							//LRU
							//dirty? is it already in memory ?
							//yes memorywrite
							//no memoryread
					
							//overwrite tag in cache with one coming in 
							//mark it as dirty
							break;
						}
							}
				}
			
			
		}//END OF WRITE
		
	}//END OF WRITEBACK??
		
		
		
		
		
		
		
			if (wb== 't'){ //write through
				
				
				if (c == 'R' ){
					if(flag=='d'){ 
						for(int i=0; i<numSets; i++){
							if (index==i){
								if(tag==cacheArray[i]->tag){
									cacheHit++;
							
								}else{
								
									cacheArray[n]->tag =tag;									
									memoryRead++;
									cacheMiss++;
									
							
								}
							}
						}
			
				
					}
					 else if (flag=='a'){
			
					for (int i =0; i<cacheLines; i++){
						if(tag==cacheArray[i]->tag){
							cacheHit++;
							
							
							
							
							
							if(check=='c'){
						
							z=cacheArray[m]->age +1;
							cacheArray[i]->age=z;
							m=i;
						
						
							break;
							}
							else if(check=='n'){
								m=i;
								z=cacheArray[m]->age +1;
								cacheArray[i]->age=z;
								m=i;
								check='c';
								break;	
								}	
							
							
						}
					
					}
					
					
					
					//look for oldest 
					int oldest=0;
					for (int old = 0; old<cacheLines; old++){
						if(oldest< cacheArray[old]->age){
							
						}else{
							oldest=cacheArray[old]->age;
							n=old;
						}
					}
					cacheArray[n]->tag =tag;
					
					if(check=='c'){
					
					z=cacheArray[m]->age +1;
					cacheArray[i]->age=z;
					m=i;
					
					
					break;
					}
					else if(check=='n'){
						m=i;
						z=cacheArray[m]->age +1;
						cacheArray[i]->age=z;
						m=i;
						check='c';	
						}	
															
					memoryRead++;
					cacheMiss++;
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					} else if (flag=='n'){
						int nway= n;
						int i ;
						i=(index-1)*n;
						int length = i+nway;
						for (int j =i-1; j<length; i++) {
								if(tag==cacheArray[i]->tag){
									cacheHit++;
									break;
								}
								else{
									//LRU 
									//overwrite tag in cache with one coming in 
									memoryRead++;
									//maybe memry write witht the old one 
									cacheMiss++;
							break;
						}
			
			
				}
				}
				}	
				
				else{
					
					if(flag=='d'){ 
						for(int i=0; i<numSets; i++){
							if (index==i){
								if (cacheArray[i]->valid == 0){
									cacheArray[i]->tag =tag;
									cacheHit++;
									memoryWrite++;
									break;
									
								}
								else{
									cacheMiss++;
									memoryWrite++;
								}
							}
						}
					} 
					else if (flag=='a'){
			
					for (int i =0; i<cacheLines; i++){
						if (cacheArray[i]->valid == 0){
							cacheArray[i]->tag =tag;
							
							if(check=='c'){
						
							z=cacheArray[m]->age +1;
							cacheArray[i]->age=z;
							m=i;
						
						
							break;
							}
							else if(check=='n'){
								m=i;
								z=cacheArray[m]->age +1;
								cacheArray[i]->age=z;
								m=i;
								check='c';	
								}	
							
							
							cacheHit++;
							memoryWrite++;
							break;
						}
						else{
							cacheMiss++;
							memoryWrite++;
							break;
					}
				
					}
					 }
					 else if (flag=='n'){
						int nway= n;
						int i ;
						i=(index-1)*n;
						int length = i+nway;
						for (int j =i-1; j<length; i++) {
							
							if (cacheArray[i]->valid == 0){
								cacheArray[i]->tag =tag;
								cacheHit++;
								memoryWrite++;
								break;
							}
							else{
								cacheMiss++;
								memoryWrite++;
								break;
			
							}
								}
					}
				}//end of while loop
				
				//end of write through

			}
		
	
		}//end of wihle loop
//freeNodes(cacheReplaceL1);
if(flag == 'd' || flag=='n'){

freeCache(cacheArray, numSets);
}

if(flag=='a'){
freeCache(cacheArray, cacheLines);
}
printf("Cache hits: %d\n", cacheHit);
printf("Cache miss: %d\n", cacheMiss);
printf("memory write: %d\n", memoryWrite);
printf("memory read: %d\n", memoryRead);
exit(0);
 
}



