package com.kite.algorithm;

/**
 * 八皇后的问题
 */
public class NQueen {


    public static void main(String[] args) {
        int[] result = new int[4];

        NQueen queen=new NQueen();
        long start=System.currentTimeMillis();
        queen.calQueens(0,result,result.length);
        System.out.println("耗时 "+(System.currentTimeMillis()-start));
    }

    public void calQueens(int row, int[] result, int n) { // 调用方式：calQueens(0);
        if (row == n) { // 8个棋子都放置好了，打印结果
            printQueens(result,n);
            return; // 8行棋子都放好了，已经没法再往下递归了，所以就return
        }
        for (int column = 0; column < n; ++column) {
            // 每一行都有8中放法
            if (isOk(row, column,result,n)) {
                // 有些放法不满足要求
                result[row] = column; // 第row行的棋子放到了column列
                calQueens(row + 1,result,n); // 考察下一行
            }
        }
    }

    private boolean isOk(int row, int column,int[] result,int n) {
        //判断row行column列放置是否合适
        int leftup = column - 1, rightup = column + 1;
        for (int i = row - 1; i >= 0; --i) { // 逐行往上考察每一行
            if (result[i] == column) return false; // 第i行的column列有棋子吗？
            if (leftup >= 0) { // 考察左上对角线：第i行leftup列有棋子吗？
                if (result[i] == leftup) return false;
            }
            if (rightup < n) { // 考察右上对角线：第i行rightup列有棋子吗？
                if (result[i] == rightup) return false;
            }
            --leftup;
            ++rightup;
        }
        return true;
    }


    private void printQueens(int[] result,int n) { // 打印出一个二维矩阵
        for (int row = 0; row < n; ++row) {
            for (int column = 0; column < n; ++column) {
                if (result[row] == column) System.out.print("Q ");
                else System.out.print("* ");
            }
            System.out.println();
        }
        System.out.println();
    }


}
