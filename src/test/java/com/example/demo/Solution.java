package com.example.demo;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @ClassName Soltion
 * @Author Simon
 * @Date 2021/1/3 9:45
 * @Description
 */
public  class Solution {
    public boolean isValidSudoku(char[][] board) {
        if(board==null|| board.length<3){
            return  true;
        }
        HashMap row=new HashMap<String,HashSet<Character>>();
        HashMap col=new HashMap<String,HashSet<Character>>();
        HashMap data=new HashMap<String,HashSet<Character>>();
        return  judge(board,row,col,data,0);

    }
    //i为行   BFS
    public  boolean  judge(char[][] board,HashMap row,HashMap col,HashMap data ,int i){
        boolean  flag=true;
        if(i>=board.length){
            return  flag;
        }
        int y=(i+1)%3==0?(i+1)/3:(i+1)/3+1;//行
        //遍历每一列
        for(int j=0;j<board[i].length;j++){
            int x=(j+1)%3==0?(j+1)/3:(j+1)/3+1;//列
            int d=(y-1)*(board.length/3)+x; //第几个9宫格块
            HashSet<Character> record=data.containsKey(d)?(HashSet<Character>)data.get(d):new HashSet<Character>();
            HashSet<Character>rows=row.containsKey(i)?(HashSet<Character>)row.get(i):new HashSet<Character>();
            HashSet<Character>cols=col.containsKey(j)?(HashSet<Character>)col.get(j):new HashSet<Character>();
            if(rows.contains(board[i][j])&&board[i][j]!='.'){
                return  false;
            }
            if(cols.contains(board[i][j])&&board[i][j]!='.'){
                return false;
            }
            if(record.contains(board[i][j])&&board[i][j]!='.'){
                return  false;
            }
            record.add(board[i][j]);
            rows.add(board[i][j]);
            cols.add(board[i][j]);
            data.put(d,record);
            row.put(i,rows);
            col.put(j,cols);
        }
        flag=judge(board,row,col,data,i+1);
        return flag;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        char[][] board = {{'8','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}};
        boolean validSudoku = solution.isValidSudoku(board);
        System.out.println(validSudoku);

    }
}