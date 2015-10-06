sl: library
	gcc main.c -L. -l sl -o sl
	chmod +x ./sl

library: sorted-list.o
	ar rcs libsl.a sorted-list.o

sorted-list.o:
	gcc -c sorted-list.c -o sorted-list.o

clean:
	rm ./*.o
	rm ./sl
	rm ./libsl.a