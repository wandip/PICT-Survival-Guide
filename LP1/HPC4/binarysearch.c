#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>
#define N 1024

int a[1024];
int b[1024];

int BS(int a[], int x, int l, int r){
    while(r >= l){
        int m = (l+r)/2;
        if(a[m] == x){
            return m;
        }
        else if(a[m] > x){
            r = m-1;
        }
        else{
            l = m+1;
        }
    }
    return -1;
}

int main(int argc,char** argv){

    int myid, no_proc, key, noofelements, rnoofelements, index, foundat = -1, foundindex = -1, start;

    MPI_Status status;

    MPI_Init(&argc, &argv);

    MPI_Comm_rank(MPI_COMM_WORLD, &myid);
    MPI_Comm_size(MPI_COMM_WORLD, &no_proc);

	//printf("%d\n", myid);

    if(myid == 0){

        printf("Enter key:- \n");
		//key = 520;
        scanf("%d",&key);

        for(int i = 0; i < N; i++){
            a[i] = i;
        }

        for(int i = 1; i < no_proc; i++){
            start =( N / no_proc ) * i;

            noofelements = N / no_proc;
			//printf("start %d\n", noofelements);
            MPI_Send(&noofelements, 1, MPI_INT, i, 2001, MPI_COMM_WORLD);
            MPI_Send(&a[start], noofelements, MPI_INT, i, 2001, MPI_COMM_WORLD);
            MPI_Send(&key, 1, MPI_INT, i, 2001, MPI_COMM_WORLD);
        }

        index = BS(a, key, 0, noofelements-1);

        if(index !=-1){
            foundindex = index;
            foundat = 0;
        }

        for(int i = 1; i < no_proc; i++){
            MPI_Recv(&index, 1, MPI_INT, i, 2002, MPI_COMM_WORLD, &status);
            if(index != -1){
                foundindex = index + (i*noofelements);
                foundat = i;
            }
        }

        printf("%d %d", foundindex, foundat);

    }
    else{

        MPI_Recv(&rnoofelements, 1, MPI_INT, 0, 2001, MPI_COMM_WORLD, &status);
        MPI_Recv(&b, rnoofelements, MPI_INT, 0, 2001, MPI_COMM_WORLD, &status);
        MPI_Recv(&key, 1, MPI_INT, 0, 2001, MPI_COMM_WORLD, &status);

        index = BS(b, key, 0, rnoofelements);

        MPI_Send(&index, 1, MPI_INT, 0, 2002, MPI_COMM_WORLD);
    }
	MPI_Finalize();
}
