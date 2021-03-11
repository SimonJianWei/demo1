package com.example.demo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/*
 * 二叉树广度优先遍历（breath—first—search）
 */
public class Test3 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        HashSet<TreeNode> hash = new HashSet<TreeNode>();
        HashSet<TreeNode> set = new HashSet<TreeNode>();
        LinkedList<TreeNode> search = new LinkedList<TreeNode>();
        List<List<Integer>> res = new LinkedList<List<Integer>>();
        List<Integer> temp = new LinkedList<Integer>();
        TreeNode next = root;
        TreeNode p = root;
        //获取最右边界节点 数据集合
        while (p != null) {
            set.add(p);
            p = p.right != null ? p.right : p.left;
        }
        //广度优先遍历 bfs
        while (next != null) {
            //process 数据
            set.add(next);
            temp.add(next.val);
            //层次遍历边界条件
            if (set.contains(next)) {
                res.add(temp);
                temp = new LinkedList<Integer>();
            }
            //获取子元素存入队列
            TreeNode left = getChild(hash, next.left);
            TreeNode right = getChild(hash, next.right);
            if (left != null) {
                search.offer(left);
            }
            if (right != null) {
                search.offer(right);
            }
            next = search.poll();
        }
        return res;
    }

    //去重
    public TreeNode getChild(HashSet<TreeNode> hash, TreeNode root) {
        return hash.contains(root) || root == null ? null : root;
    }

    public int minDepth(TreeNode root) {
        LinkedList<TreeNode> search = new LinkedList<TreeNode>();
        int leve = 1;
        if (root == null) {
            return 0;
        }
        search.offer(root);
        while (!search.isEmpty()) {
            int size = search.size();
            for (int i = 0; i < size; i++) {
                TreeNode next = search.poll();
                if (next.left != null) {
                    search.offer(next.left);
                } else if (next.right != null) {
                    search.offer(next.right);
                } else {
                    return leve;
                }
            }
            leve++;
        }
        return leve;
    }

    public static void main(String[] args) {
        Test3 test3 = new Test3();
        TreeNode t1 = test3.new TreeNode(1);
        TreeNode t2 = test3.new TreeNode(2);
        TreeNode t3 = test3.new TreeNode(3);
        TreeNode t4 = test3.new TreeNode(4);
        TreeNode t5 = test3.new TreeNode(5);
        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t2.right = t5;
        test3.minDepth(t1);

    }
}
