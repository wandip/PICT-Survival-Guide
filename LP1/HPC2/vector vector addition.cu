#include <stdio.h>
#include <cuda.h>
#include <stdlib.h>

__global__ void vectadd(int *da, int *db, int *dc){
    int tid = threadIdx.x + blockIdx.x * blockDim.x;
    dc[tid] = da[tid] + db[tid];
}

int main(){
    int n = 3;
    int a[n], b[n], c[n];
    int *da, *db, *dc;

    for(int i = 0; i < n; i++){
        a[i] = i;
        b[i] = i;
        c[i] = 0;
    }

    cudaMalloc((void **)&da, n*sizeof(int));
    cudaMalloc((void **)&db, n*sizeof(int));
    cudaMalloc((void **)&dc, n*sizeof(int));

    cudaMemcpy(da, a, sizeof(a), cudaMemcpyHostToDevice);
    cudaMemcpy(db, b, sizeof(b), cudaMemcpyHostToDevice);

    vectadd<<<n, 1>>>(da, db, dc);

    cudaMemcpy(&c, dc, sizeof(c), cudaMemcpyDeviceToHost);

    for(int i = 0; i < n; i++){
        printf("%d ", c[i]);
    }
}
