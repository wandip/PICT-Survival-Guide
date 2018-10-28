public class AlphaBetaPruning {
    int min = -1000;
    int max = 1000;

    int minmax(int depth, int nodeno, int height, boolean ismaximization, int score[], int alpha, int beta){
        //System.out.printf(String.valueOf(score[0]));
        if(depth == height){
            return score[nodeno];
        }

        else if(ismaximization){
            int bestval = min;
            for(int i = 0; i < 2; i++){
                int value = minmax(depth+1, nodeno*2+i, height, false, score, alpha, beta);
                bestval = Math.max(value, bestval);
                alpha = Math.max(bestval, alpha);
                if(beta <= alpha){
                    break;
                }
            }
            return bestval;
        }

        else{
            int bestval = max;
            for(int i = 0; i < 2; i++){
                int value = minmax(depth+1, nodeno*2+i, height, true, score, alpha, beta);
                bestval = Math.min(value, bestval);
                beta = Math.min(bestval, beta);
                if(beta <= alpha){
                    break;
                }
            }
            return bestval;
        }
    }

    public static void main(String[] args) {

        int a[] = {3, 5, 6, 9, 1, 2, 0, -1};

        int height = (int) Math.ceil(Math.log(a.length) / Math.log(2));

        AlphaBetaPruning alphaBetaPruning = new AlphaBetaPruning();

        int result = alphaBetaPruning.minmax(0 ,0,height, true, a, alphaBetaPruning.min, alphaBetaPruning.max);

        System.out.printf(String.valueOf(result));
    }
}