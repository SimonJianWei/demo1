package com.example.demo;

import java.util.*;

public class Test {
    //队列实现栈
    class MyStack {

        private LinkedList<Integer> stack;
        /** Initialize your data structure here. */
        public MyStack() {
            this.stack=new LinkedList<Integer> ();
        }

        /** Push element x onto stack. */
        public void push(int x) {
            stack.addFirst(x);
        }

        /** Removes the element on top of the stack and returns that element. */
        public int pop() {
            return  stack.pollLast();
        }

        /** Get the top element. */
        public int top() {
            return stack.peekLast();
        }

        /** Returns whether the stack is empty. */
        public boolean empty() {
            return stack.size()==0?true:false;
        }
    }

    public static   List<List<Integer>> test2(int []nums ){
        List<List<Integer>> res=new LinkedList<List<Integer>>();
        HashSet<Integer> hash=new HashSet<Integer>();
        //选择排序
        for(int i=0;i<nums.length-1;i++){
            int min=i;
            for(int j=i+1;j<nums.length;j++){
                if(nums[j]<nums[min]){
                    min=j;
                }
            }
            int temp =nums[min];
            nums[min]=nums[i];
            nums[i]=temp;
        }

        for(int i=0;i<nums.length-2;i++){
            if((i>0)&&nums[i]==nums[i-1]){
                continue;
            }
            int x=nums.length-1;
            int j=i+1;
            while(j<x){
                int s=nums[i]+nums[j]+nums[x];
                if(s>0){
                    x-=1;
                    continue;
                }else if(s<0){
                    j+=1;
                    continue;
                }else {
                    List<Integer> temp=new LinkedList<Integer>();
                    temp.add(nums[i]);
                    temp.add(nums[j]);
                    temp.add(nums[x]);
                    res.add(temp);
                    while( (j<x)&&nums[j]==nums[j+1]){
                        j+=1;
                    }
                    while( (j<x)&&nums[x]==nums[x-1]){
                        x-=1;
                    }
                    j+=1;
                    x-=1;
                    // x=nums.length-1;
                }
            }

        }
        return res;

    }

    public static void main(String[] args) {
//        Test test = new Test();
//        MyStack stack = test.new MyStack();
//        stack.push(1);
//        stack.push(2);
//        System.out.println(stack.top());
//        System.out.println(stack.pop());
        //插入排序
//        int nums[]={-1, 0, 1, 2, -1, -4};
        int nums[]={3,0,-2,-1,1,2};
//        int nums[]={1,-1,-1,0};
//         for(int i=1;i<nums.length;i++){
//             int j=i;
//             while (j>0){
//                 if(nums[j]>nums[j-1]){
//                     int temp=nums[j];
//                     nums[j]=nums[j-1];
//                     nums[j-1]=temp;
//                 }else{
//                    break;
//                 }
//                 j--;
//             }
//         }
        List<List<Integer>> list=Test.test2(nums);
        Arrays.sort(nums);
        Arrays.stream(nums).forEach(System.out::println);
        System.out.println(list.toString());
    }
}
