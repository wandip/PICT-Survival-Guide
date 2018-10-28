public class BackTracking {

    int n = 8;

    boolean isSafe(int board[][], int row, int col){
        for(int i = 0; i < col; i++){
            if(board[row][i] == 1){
                return false;
            }
        }
        for(int i = row, j = col; i >= 0 && j >= 0; i--, j--){
            if(board[i][j] == 1){
                return false;
            }
        }
        for(int i = row, j = col; i < n && j >= 0; i++, j--){
            if(board[i][j] == 1){
                return false;
            }
        }
        return true;
    }

    boolean calc(int board[][], int col){
        if(col >= n){
            return true;
        }
        for(int i = 0; i < n; i++){
            if(isSafe(board, i, col)){
                board[i][col] = 1;
                if(calc(board, col+1)){
                    return true;
                }
                board[i][col] = 0;
            }
        }
        return false;
    }

    void solve(){
        int board[][] = new int[n][n];
        if(calc(board, 0)){
            for(int i = 0;i < n; i++){
                for(int j = 0; j < n; j++){
                    System.out.printf(board[i][j] + "\t");
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        BackTracking bt = new BackTracking();
        bt.solve();
    }
}
