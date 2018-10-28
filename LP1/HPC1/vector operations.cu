#include <stdio.h>
#include <math.h>
#include <cuda.h>

__global__
void addn(int *da, int n){
    int idx = threadIdx.x;
    int steps = 1;
    while(n > 1){
        if(idx < n/2){
            int first = idx*2*steps;
            int second = first + steps;
            da[first] += da[second];
        }
        steps *= 2;
        n /= 2;
    }

}

__global__
void minn(int *da, int n){
    int idx = threadIdx.x;
    int steps = 1;
    while(n > 1){
        if(idx < n/2){
            int first = idx*2*steps;
            int second = first + steps;
            if(da[first] > da[second]){
                da[first] = da[second];
            }
        }
        steps *= 2;
        n /= 2;
    }

}

__global__
void maxn(int *da, int n){
    int idx = threadIdx.x;
    int steps = 1;
    while(n > 1){
        if(idx < n/2){
            int first = idx*2*steps;
            int second = first + steps;
            if(da[first] < da[second]){
                da[first] = da[second];
            }
        }
        steps *= 2;
        n /= 2;
    }

}

__global__
void stdn(int *da, int n, int add){
    int idx = threadIdx.x;
    int steps = 1;
    da[idx] = pow((da[idx] - add/n), 2);
    da[idx+8] = pow((da[idx+8] - add/n), 2);
    while(n > 1){
        if(idx < n/2){
            int first = idx*2*steps;
            int second = first + steps;
            da[first] += da[second];
        }
        steps *= 2;
        n /= 2;
    }

}


int main(){
    int n = 16;
    int a[n];
    for(int i = 0; i < n; i++){
        a[i] = i;
    }

    int *da;
    cudaMalloc((void **)&da, n*sizeof(int));

    int add;
    cudaMemcpy(da, a, sizeof(a), cudaMemcpyHostToDevice);
    addn<<<1, n/2>>>(da, n);
    cudaMemcpy(&add, da, sizeof(int), cudaMemcpyDeviceToHost);
    printf("%d ", add);

    int mini;
    cudaMemcpy(da, a, sizeof(a), cudaMemcpyHostToDevice);
    minn<<<1, n/2>>>(da, n);
    cudaMemcpy(&mini, da, sizeof(int), cudaMemcpyDeviceToHost);
    printf("%d ", mini);

    int maxi;
    cudaMemcpy(da, a, sizeof(a), cudaMemcpyHostToDevice);
    maxn<<<1, n/2>>>(da, n);
    cudaMemcpy(&maxi, da, sizeof(int), cudaMemcpyDeviceToHost);
    printf("%d ", maxi);

    printf("%d ", add/n);

    int std;
    cudaMemcpy(da, a, sizeof(a), cudaMemcpyHostToDevice);
    stdn<<<1, n/2>>>(da, n, add);
    cudaMemcpy(&std, da, sizeof(int), cudaMemcpyDeviceToHost);
    std = std/n;
    std = sqrt(std);
    printf("%d", std);
}
