#include <stdio.h>
#include <stdio.h>
#include <cuda.h>

__global__
void vectmul(int *da, int *db, int *dc, int n){
    int tid = threadIdx.x;
    int sum = 0;
    for(int i = 0; i < n; i++){
        sum += da[tid*n + i] * db[i];
    }

    dc[tid] = sum;
}

int main(){
    int n = 3;
    int a[n][n], b[n], c[n];
    int *da, *db, *dc;

    for(int i = 0; i < n; i++){
        for(int j = 0; j < n; j++){
            a[i][j] = i;
        }
        b[i] = i;
        c[i] = 0;
    }

    cudaMalloc((void **)&da, n*n*sizeof(int));
    cudaMalloc((void **)&db, n*sizeof(int));
    cudaMalloc((void **)&dc, n*sizeof(int));

    cudaMemcpy(da, a, sizeof(a), cudaMemcpyHostToDevice);
    cudaMemcpy(db, b, sizeof(b), cudaMemcpyHostToDevice);

    vectmul<<<1, n>>>(da, db, dc, n);

    cudaMemcpy(&c, dc, sizeof(c), cudaMemcpyDeviceToHost);

    for(int i = 0; i < n; i++){
        printf("%d ", c[i]);
    }
}
