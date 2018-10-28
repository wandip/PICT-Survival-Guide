#include "stdio.h"
#include <cuda.h>

__global__
void matmul(int *da, int *db, int *dc, int n){
    int idx = threadIdx.x;
    int idy = threadIdx.y;
    int sum = 0;
    for(int i = 0; i < n; i++){
        sum += da[idy*n + i] * db[i*n + idx];
    }
    dc[idy * n +idx] = sum;
}

int main(){
    int n = 3;
    int a[n][n], b[n][n], c[n][n];
    int *da, *db, *dc;

    for(int i = 0;i < n; i++){
        for(int j = 0;j < n; j++){
            a[i][j] = i;
            b[i][j] = j;
            c[i][j] = 0;
        }
    }

    cudaMalloc((void **)&da, n*n*sizeof(int));
    cudaMalloc((void **)&db, n*n*sizeof(int));
    cudaMalloc((void **)&dc, n*n*sizeof(int));

    cudaMemcpy(da, a, sizeof(a), cudaMemcpyHostToDevice);
    cudaMemcpy(db, b, sizeof(b), cudaMemcpyHostToDevice);

    dim3 blockDim(n, n);
    matmul<<<1, blockDim>>>(da, db, dc, n);

    cudaMemcpy(&c, dc, sizeof(c), cudaMemcpyDeviceToHost);

    for(int i = 0;i < n; i++){
        for(int j = 0;j < n; j++){
            printf(" %d", c[i][j]);
        }
    }

}
