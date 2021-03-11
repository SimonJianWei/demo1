package com.example.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Practice {

    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     */
    public static int[] test1(int[] nums, int target) {
//        int [] data=new int[2];
//        shiro:
//        for (int i = 0; i < nums.length; i++) {
//            for (int j = i + 1; j < nums.length; j++) {
//                if ( nums[j] + nums[i] == target) {
//                    data[0] = i;
//                    data[1] = j;
//                    break shiro;
//                }
//            }
//        }

        int[] indexs = new int[2];

        // 建立k-v ，一一对应的哈希表
        HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (hash.containsKey(nums[i])) {
                indexs[0] = i;
                indexs[1] = hash.get(nums[i]);
                return indexs;
            }
            // 将数据存入 key为补数 ，value为下标
            hash.put(target - nums[i], i);
        }
        return indexs;
    }

    /**
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     */

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    //遍历链表
    public static List loopChain(ListNode l1) {
        List list = new LinkedList();
        list.add(l1.val);
        if (l1.next != null) {
            ListNode next = l1.next;
            list.add(next.val);
            while (next.next != null) {
                next = next.next;
                list.add(next.val);
            }
        }
        return list;
    }

    public void test3(ListNode head)    {

    }
        public static void main(String[]args){

        }
}