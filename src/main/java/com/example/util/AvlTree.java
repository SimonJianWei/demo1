package com.example.util;

import java.util.LinkedList;
import java.util.Queue;

public class AvlTree{
    private AvlNode root;

    /**
     * 初始化平衡二叉树
     * @param val 键值
     */
    public AvlTree(int val){
        this.root = new AvlNode(val);
    }

    public AvlTree(){
        this(1);
    }

    public AvlNode getRoot(){return root;}

    private static class AvlNode{
        AvlNode parent;
        AvlNode left;
        AvlNode right;

        int val;

        public AvlNode(int val) {
            this.val = val;
        }
    }

    /**
     * 左子树过深，进行右旋操作
     * @param cur 失衡节点，平衡因子绝对值大于1的那个节点
     */
    public void rightRotate(AvlNode cur){
        if(cur != null){
            //右旋有关的节点包括失衡节点本身，它的父节点，左孩子以及左孩子的右孩子
            AvlNode leftChild = cur.left;
            AvlNode parentNode = cur.parent;
            AvlNode rightGrandson = leftChild.right;

            //左孩子代替失衡节点位置
            leftChild.parent = parentNode;
            if(parentNode != null){
                if(parentNode.left == cur)
                    parentNode.left = leftChild;
                else
                    parentNode.right = leftChild;
            }else {
                //父节点为空则左孩子成为根节点
                this.root = leftChild;
            }
            //重新设置失衡节点和它的左孩子的指针指向
            leftChild.right = cur;
            cur.parent = leftChild;

            //失衡节点的左孩子的右子树 成为 失衡节点的左子树
            cur.left = rightGrandson;
            if(rightGrandson != null)
                rightGrandson.parent = cur;
        }
    }

    /**
     * 右子树过深，进行左旋操作
     * @param cur 失衡节点，平衡因子绝对值大于1的那个节点
     */
    public void leftRotate(AvlNode cur){
        if(cur != null){
            //一次左旋涉及到的节点包括失衡节点本身，它的父节点，右孩子以及右孩子的左孩子
            AvlNode rightChild = cur.right;
            AvlNode parentNode = cur.parent;
            AvlNode leftGrandson = rightChild.left;

            //将失衡节点的右孩子的位置调整到失衡节点的位置
            rightChild.parent = parentNode;
            if(parentNode != null){
                if(parentNode.left == cur)
                    parentNode.left = rightChild;
                else
                    parentNode.right = rightChild;
            }else {
                this.root = rightChild;
            }
            //重现调整指向
            rightChild.left = cur;
            cur.parent = rightChild;

            //处理右孩子的左孩子
            cur.right = leftGrandson;
            if(leftGrandson != null)
                leftGrandson.parent = cur;
        }
    }

    /**
     * 获得当前节点的深度
     * @param cur 当前节点
     * @return 深度
     */
    private int getDepth(AvlNode cur){
        if(cur == null)
            return 0;
        int left = getDepth(cur.left);
        int right = getDepth(cur.right);
        return left > right ? left + 1 : right + 1;
    }

    /**
     * 计算平衡因子
     * @param cur 节点
     * @return 平衡因子大小
     */
    private int calBalance(AvlNode cur){
        if(cur == null)
            return 0;
        int leftDepth;
        int rightDepth;
        if(cur.left == null)
            leftDepth = 0;
        else
            leftDepth = getDepth(cur.left);
        if(cur.right == null)
            rightDepth = 0;
        else
            rightDepth = getDepth(cur.right);
        return leftDepth - rightDepth;
    }

    /**
     * 平衡二叉树新增节点
     * @param root 根节点
     * @param val 键值
     */
    public void insert(AvlNode root, int val){
        //插入节点
        if(root.val > val){
            if(root.left != null)
                insert(root.left, val);
            else {
                root.left = new AvlNode(val);
                root.left.parent = root;
            }
        }else {
            if(root.right != null)
                insert(root.right, val);
            else {
                root.right = new AvlNode(val);
                root.right.parent = root;
            }
        }

        //调整二叉树
        rebuild(root);
    }

    public boolean delete(int val){
        boolean flag;//标识变量
        AvlNode temp = root;
        AvlNode delNode = null;//删除节点

        //找到删除节点的位置
        while (temp != null){
            if(temp.val == val){
                delNode = temp;
                break;
            }
            else if(temp.val > val)
                temp = temp.left;
            else
                temp = temp.right;
        }

        //删除节点后需要调整的节点位置
        AvlNode bal = null;

        if(delNode == null)
            return false;
        AvlNode parent = delNode.parent;
        boolean isLeft = true;
        if(parent != null && delNode == parent.right)
            isLeft = false;
        //删除节点为叶子节点
        if(delNode.left == null && delNode.right == null){
            //若不存在父节点，则删除节点为根节点
            if(parent == null)
                root = null;
            else {
                if(isLeft)
                    parent.left = null;
                else
                    parent.right = null;
            }
            bal = parent;
            delNode = null;
            flag = true;
        }//删除节点有一个孩子节点
        else if((delNode.left != null && delNode.right == null) || (delNode.left == null && delNode.right != null)){
            if(delNode.left != null){
                if(parent == null){
                    root = delNode.left;
                } else {
                    if(isLeft)
                        parent.left = delNode.left;
                    else
                        parent.right = delNode.left;
                }
                delNode.left.parent = parent;
            }
            if(delNode.right != null){
                if(parent == null){
                    root = delNode.right;
                }else {
                    if(isLeft)
                        parent.left = delNode.right;
                    else
                        parent.right = delNode.right;
                }
                delNode.right.parent = parent;
            }
            bal = parent;
            delNode = null;
            flag = true;
        }//删除节点左右孩子都不为空，找到直接后继节点，删除此节点，将它的值赋给删除节点即可
        else {
            //获得直接后继节点
            AvlNode processor = getDirectPostNode(delNode);
            int tempVal = processor.val;
            boolean delete = delete(tempVal);
            if(delete){
                delNode.val = tempVal;
            }
            bal = processor;
            processor = null;
            flag = true;
        }

        //对二叉树进行再调整
        if(flag)
            rebuild(bal);

        return flag;
    }

    /**
     * 重新调整二叉树
     * @param root 新插入节点的父亲节点
     */
    private void rebuild(AvlNode root){
        //向上回溯找到最近可能破坏平衡的节点
        while (root != null){
            int balance = calBalance(root);//此时root指向插入节点的父节点

            //左子树高，准备右旋
            if(balance > 1){
                //此时右孙较深，先左旋
                if(calBalance(root.left) == -1) {
                    leftRotate(root.left);
                }
                rightRotate(root);
            }

            //右子树高，准备左旋
            if(balance < -1){
                //此时左孙较深，应先右旋调整
                if(calBalance(root.right) == 1) {
                    rightRotate(root.right);
                }
                leftRotate(root);
            }
            root = root.parent;
        }
    }
    /**
     * 获得直接后继节点
     * @param cur 当前节点
     * @return 直接后继节点
     */
    private AvlNode getDirectPostNode(AvlNode cur){
        //当前节点的右子树不为空，则直接后继节点一定存在右子树上
        if(cur.right != null){
            AvlNode right = cur.right;
            while (right.left != null){
                right = right.left;
            }
            return right;
        }
        //当前节点的右子树为空，若当前节点是父亲节点的左孩子，则
        //父亲节点是它的直接后继节点，若当前节点是父亲节点的右孩子，则
        //一直向上回溯到它的某个祖先节点是某个节点的左子树即可
        AvlNode parent = cur.parent;
        while (parent != null && (cur == parent.right)){
            cur = parent;
            parent = parent.parent;
        }
        return parent;
    }

    /**
     * 依次打印每一层节点
     */
    public void travel(){
        Queue<AvlNode> queue = new LinkedList<>();
        queue.offer(this.root);
        int i = 1;
        while (!queue.isEmpty()){
            System.out.println("这是二叉树第" + i + "层");
            int count = 0;
            int size = queue.size();
            while (count < size && !queue.isEmpty()){
                AvlNode out = queue.poll();
                System.out.print(out.val + " ");
                if(out.left != null)
                    queue.offer(out.left);
                if(out.right != null)
                    queue.offer(out.right);
                count++;
            }
            i++;
            System.out.println();
        }
    }

    /**
     * 中序遍历二叉树
     * @param root 根节点
     */
    public void inOrder(AvlNode root){
        if(root != null){
            inOrder(root.left);
            System.out.print(root.val + " ");
            inOrder(root.right);
        }
    }

    //测试主函数
    public static void main(String[] args) {
        AvlTree avlTree = new AvlTree(1);
        System.out.println("---------新增节点开始---------");
        avlTree.insert(avlTree.getRoot(), 2);
        avlTree.insert(avlTree.getRoot(), 5);
        avlTree.insert(avlTree.getRoot(), 3);
        avlTree.insert(avlTree.getRoot(), 6);
        avlTree.insert(avlTree.getRoot(), 4);
        avlTree.insert(avlTree.getRoot(), 7);
        System.out.println("---------新增节点结束---------");
        avlTree.travel();
        avlTree.inOrder(avlTree.getRoot());
        avlTree.delete(3);
        System.out.println("----------------------");
        avlTree.travel();
        avlTree.inOrder(avlTree.getRoot());
    }
}