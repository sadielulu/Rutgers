CC = gcc
CCFLAGS  = -Wall -ansi -pedantic -m32

c-sim: c-sim.o 
	$(CC) c-sim.o -o c-sim 

c-sim.o: c-sim.c c-sim.h
	$(CC) $(CCFLAGS) -c c-sim.c

clean:
			rm -f c-sim
	
		
