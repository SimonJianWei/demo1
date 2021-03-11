package com.example.service;

import java.util.LinkedList;

/**
 * @ClassName ShuDu
 * @Author Simon
 * @Date 2021/1/8 15:48
 * @Description  数独问题  leetcode 37
 */
public class ShuDu {

    private boolean[][] row;

    /**
     * @param board 九宫格数据
     * @description leetcode
     */
    public void solveSudoku(char[][] board) {
        LinkedList<int[][]> result = new LinkedList<int[][]>();
        boolean[][] col = new boolean[9][9];  //记录某列，某位数字是否已经被摆放
        boolean[][] row = new boolean[9][9];  //记录某行，某位数字是否已经被摆放
        boolean[][] block = new boolean[9][9]; // 记录某 3x3 宫格内，某位数字是否已经被摆放   第几块九宫格
        //提前把数据录入数组中
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    row[i][num] = true;
                    col[j][num] = true;
                    int digest = i / 3 * 3 + j / 3;
                    block[digest][num] = true;
                }
            }
        }
        dfs(result, board, row, col, block, 0, 0);
    }

    /**
     * 回溯遍历
     *
     * @param res
     * @param board
     * @param row
     * @param col
     * @param block
     * @param i
     * @param j
     * @return
     */
    public Boolean dfs(LinkedList<int[][]> res, char[][] board, boolean[][] row, boolean[][] col, boolean[][] block, int i, int j) {
        //寻找空数据
        while (board[i][j] != '.') {
            if (++j >= 9) {
                i++;
                j = 0;
            }
            if (i >= 9) {
                return true;
            }
        }
        //填数据
        for (int num = 0; num < 9; num++) {
            int blockIndex = i / 3 * 3 + j / 3;
            boolean flag = block[blockIndex][num] || row[i][num] || col[j][num];
            if (!flag) {
                block[blockIndex][num] = true;
                row[i][num] = true;
                col[j][num] = true;
                board[i][j] = (char) (num + '1');
                if (dfs(res, board, row, col, block, i, j)) {
                    return true;
                } else {
                    // 回溯
                    row[i][num] = false;
                    col[j][num] = false;
                    block[blockIndex][num] = false;
                    board[i][j] = '.';
                }
            }
        }

        return false;
    }

    private void printBoard(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        ShuDu shudu = new ShuDu();
        shudu.solveSudoku(board);
        shudu.printBoard(board);
    }

}