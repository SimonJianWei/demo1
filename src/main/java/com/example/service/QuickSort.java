package com.example.service;

import java.util.Arrays;

/**
 * @ClassName QuickSort
 * @Author Simon
 * @Date 2021/1/11 15:01
 * @Description 快速排序
 * 1）设置两个变量i、j，排序开始的时候：i=0，j=N-1；
 * 2）以第一个数组元素作为关键数据，赋值给key，即key=A[0];
 * 3）从j开始向前搜索，即由后开始向前搜索(j--)，找到第一个小于key的值A[j]，将A[j]和A[i]互换；
 * 4）从i开始向后搜索，即由前开始向后搜索(i++)，找到第一个大于key的A[i]，将A[i]和A[j]互换；
 * 5）重复第3、4步，直到i=j； (3,4步中，没找到符合条件的值，即3中A[j]不小于key,4中A[i]不大于key的时候改变j、i的值，使得j=j-1，i=i+1，直至找到为止。找到符合条件的值，进行交换的时候i， j指针位置不变。另外，i==j这一过程一定正好是i+或j-完成的时候，此时令循环结束）。
 *
 */

public class QuickSort {
    /**
     *分治思想  递归将中间数排好 慢慢向两边扩散 直到两边序列为一停止
     * @param arr
     */
    public  void quickSort(int[] arr,int left,int right) {
        if(arr==null||left<0){
            return;
        }
        int dp;
        if(left<right){
            dp = partition(arr, left, right);
            quickSort(arr, left, dp - 1);
            quickSort(arr, dp + 1, right);
        }

    }
    //分区交换  填报空位 先从右 后从左(因为第一个数是pivot 右边计较放左边空位 防止覆盖元数据)
    public  int   partition(int []arr, int left, int right){
        int temp = arr[left];
        while(left<right){
            //从右至左
            while(right>left&&arr[right]>temp){
                right--;
            }
            //从左至右
            arr[left++]=arr[right];
            while(left<right&&arr[left]<temp){
                left++;
            }
            arr[right--]=arr[left];
        }
        arr[left] = right;
        return  left;
    }


    public static void main(String[] args){
        QuickSort sort = new QuickSort();
        int []array= new int[] {23,45,17,11,13,89,72,26,3,17,11,13};;
        sort.equals(array);
        Arrays.stream(array).boxed().forEach(System.out::print);
    }
}
