
typedef struct mementry{
	struct mementry* prev;
	struct mementry* succ;
	int isFree;
	unsigned int size;
}mementry;
