package com.example.demo;

public class Linked {
      public class ListNode {
          int val;
          ListNode next;

          ListNode(int x) {
              val = x;
          }
      }

    class Solution {
        public ListNode deleteDuplicates(ListNode head) {
            //防止初始队列为空 造成空指针
            if(head==null|| head.next==null){
                return head;
            }
            //快慢指针
            ListNode slow=head;
            ListNode fast=head.next;
            while(fast!=null){
                if(slow.val==fast.val){
                    slow.next=fast.next;
                    slow=slow.next;
                    if(fast.next!=null){
                        fast=fast.next.next;
                    }else{
                        fast=null;
                    }
                    continue;
                }
                slow=slow.next;
                fast=fast.next;
            }
            return head;
        }
    }


    public static void main(String[] args) {
        Linked linked = new Linked();
        ListNode l1=linked.new ListNode(1);
        ListNode l2=linked.new ListNode(2);
        ListNode l3=linked.new ListNode(2);
        ListNode l4=linked.new ListNode(3);
        ListNode l5=linked.new ListNode(4);
        ListNode l6=linked.new ListNode(4);
        l1.next=l2;
        l2.next=l3;
        l3.next=l4;
        l4.next=l5;
        l5.next=l6;
        Solution solution = linked.new Solution();
        ListNode head=solution.deleteDuplicates(l1);
        while(head!=null){
            System.out.println(head.val);
            head=head.next;
        }

    }
}
