package com.will.tgchannel;

public class test {
    // public static void main(String[] args) {
    //     ListNode l1;
    //     Stack s = new Stack<>();
    //     while(l1.next != null){
    //         s.push(l1.val);
    //         l1 = l1.next;
    //     }
    //     List list = new ArrayList<>();
    //     for(var i = 0; i < s.size(); i++){
    //         list.add(s.pop());
    //     }
    //     int xxx = Integer.parseInt(list.toString());
    // }
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    // class Solution {
    //     public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    //     Stack s = new Stack<>();
    //     while(l1.next != null){
    //         s.push(l1.val);
    //         l1 = l1.next;
    //     }
    //     Stack s2 = new Stack<>();
    //     while(l2.next != null){
    //         s2.push(l2.val);
    //         l2 = l2.next;
    //     }
    //     List list1 = new ArrayList<>();
    //     for(var i = 0; i < s1.size(); i++){
    //         list.add(s.pop());
    //     }
        
    //     int result1 = Integer.parseInt(list.toString());
    //     List list2 = new ArrayList<>();
    //     for(var i = 0; i < s2.size(); i++){
    //         list2.add(s.pop());
    //     }
    //     int result2 = Integer.parseInt(list2.toString());
    //     return result1 + result2;
    //     }
    // }
}
