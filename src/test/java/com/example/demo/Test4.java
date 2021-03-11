package com.example.demo;

/**
 * @ClassName Test4
 * @Author Simon
 * @Date 2021/1/10 14:37
 * @Description
 */
public class Test4 {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public static  ListNode oddEvenList(ListNode head) {
        if(head==null){
            return  null;
        }
        ListNode  ou_init=head.next;
        ListNode ou=new ListNode(0);
        ListNode pre=head;
        ListNode temp=head;
        int i=1;
        while(temp!=null){
            if(i%2!=0){//奇数情况下
                pre=temp;
            }else{
                ou.next=temp;
                ou=ou.next;
                pre.next=temp.next;
            }
            if(temp.next==null){
                if(ou.next!=null){
                    ou.next=null;
                }
                break;
            }
            temp=temp.next;
            i++;
        }
        pre.next=ou_init;
        return head;

    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(5);
//        ListNode l6 = new ListNode(6);
        l1.next=l2;
        l2.next=l3;
        l3.next=l4;
        l4.next=l5;
//        l5.next=l6;
        ListNode res = oddEvenList(l1);
        while(res!=null){
            System.out.println(res.val);
            res = res.next;
        }
    }
}
