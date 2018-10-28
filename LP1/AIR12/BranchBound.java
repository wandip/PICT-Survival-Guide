public class BranchBound {

    int n = 8;

    boolean isSafe(int board[][], int row, int col, int slash[][], int backslash[][], boolean slashlookup[], boolean backslashlookup[], boolean rowlookup[]){
        if(rowlookup[row] || slashlookup[slash[row][col]] || backslashlookup[backslash[row][col]]){
            return false;
        }
        return true;
    }


    boolean calc(int board[][], int col, int slash[][], int backslash[][], boolean slashlookup[], boolean backslashlookup[], boolean rowlookup[]){
        if(col >= n){
            return true;
        }
        for(int i = 0; i < n; i++){
            if(isSafe(board, i, 0, slash, backslash, slashlookup, backslashlookup, rowlookup)){
                board[i][col] = 1;
                slashlookup[slash[i][col]] = true;
                backslashlookup[backslash[i][col]] = true;
                rowlookup[i] = true;

                if(calc(board, col+1, slash, backslash, slashlookup, backslashlookup, rowlookup)){
                    return true;
                }
                board[i][col] = 0;
                slashlookup[slash[i][col]] = false;
                backslashlookup[backslash[i][col]] = false;
                rowlookup[i] = false;
            }
        }
        return false;
    }

    void solve(){
        int board[][] = new int[n][n];
        int slash[][] = new int[n][n];
        int backslash[][] = new int[n][n];

        boolean rowlookup[] = new boolean[n];
        boolean slashlookup[] = new boolean[2*n-1];
        boolean backslashlookup[] = new boolean[2*n-1];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                slash[i][j] = i+j;
                backslash[i][j] = i-j+n-1;
            }
        }

        if(calc(board, 0, slash, backslash, slashlookup, backslashlookup, rowlookup)){
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    System.out.printf(board[i][j] + "\t");
                }
                System.out.println();
            }
        }
        else{
            System.out.println("Not Solvable");
        }
    }

    public static void main(String[] args) {
        BranchBound bb = new BranchBound();
        bb.solve();
    }
}
