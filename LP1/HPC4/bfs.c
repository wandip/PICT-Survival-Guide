#include<iostream>
#include<math.h>
#include<mpi.h>
#include<stdlib.h>
using namespace std;
void print(int arr[], int len,int rank){
    cout<<"Depth "<<rank<<": ";
    for(int i =0;i<len;i++)
    cout<<arr[i]<<" ";
    cout<<endl;
}

int main(int argc, char *argv[]){
    int rank, sizes, size, sum, i;
    sizes = pow(2,5);
    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);
    MPI_Status status;
    if(rank==0){
        int arr[sizes];
        for(int i =0;i<sizes;i++){
            arr[i] = i+1;
        }
        int i = 1;
        int sum =0;
        for(int id =1;id<size;id++){
            MPI_Send(&i, 1, MPI_INT, id, 2000, MPI_COMM_WORLD);
            MPI_Send(&arr[sum], i, MPI_INT, id, 2000, MPI_COMM_WORLD);
            sum +=i;
            i<<=1;
        }
    }
    else
    {
        int recvarr[sizes],  len;
        MPI_Recv(&len, 1, MPI_INT, 0, 2000, MPI_COMM_WORLD, &status);
        MPI_Recv(&recvarr, len, MPI_INT, 0, 2000, MPI_COMM_WORLD, &status);
        print(recvarr, len,rank);
    }
    MPI_Finalize();
}
