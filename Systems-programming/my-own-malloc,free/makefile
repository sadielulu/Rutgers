CC = gcc
CCFLAGS = -Wall -g
EXECS = test1 test2 test3 test4 test5 test6 test7 test8 test9 test10 test11 test12

ALL_OBJ = $(TARGET:%=%.o)
ALL_TARGET = $(TARGET:%=%.c)
DEP = malloc.h


all: $(EXECS)

%.o: %.c $(DEP)
	$(CC) $(CCFLAGS) -c -o $@ $< $(ALL_TARGET)  

test1: test1.o
	$(CC) $(CCFLAGS) -o test1 test1.o

test2: test2.o
	$(CC) $(CCFLAGS) -o test2 test2.o

test3: test3.o
	$(CC) $(CCFLAGS) -o test3 test3.o

test4: test4.o
	$(CC) $(CCFLAGS) -o test4 test4.o

test5: test5.o
	$(CC) $(CCFLAGS) -o test5 test5.o

test6: test6.o
	$(CC) $(CCFLAGS) -o test6 test6.o

test7: test7.o
	$(CC) $(CCFLAGS) -o test7 test7.o

test8: test8.o
	$(CC) $(CCFLAGS) -o test8 test8.o

test9: test9.o
	$(CC) $(CCFLAGS) -o test9 test9.o

test10: test10.o
	$(CC) $(CCFLAGS) -o test10 test10.o

test11: test11.o
	$(CC) $(CCFLAGS) -o test11 test11.o

test12: test12.o
	$(CC) $(CCFLAGS) -o test12 test12.o

clean:
	rm -f *.o $(EXECS)

