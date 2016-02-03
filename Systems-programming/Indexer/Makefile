indexer: library
	gcc indexerMain.c -L. -l indexer -o indexer
	chmod +x ./indexer

library: indexerTk.o indexer.o
	ar rcs libindexer.a indexerTk.o indexer.o

indexerTk.o:
	gcc -c indexerTk.c -o indexerTk.o

indexer.o:
	gcc -c indexer.c -o indexer.o

clean:
	rm ./*.o
	rm ./indexer
	rm ./libindexer.a