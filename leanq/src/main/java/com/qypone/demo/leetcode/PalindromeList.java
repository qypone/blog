package com.qypone.demo.leetcode;

/**
 * 给定一个链表的 头节点 head ，请判断其是否为回文链表。
 * 如果一个链表是回文，那么链表节点序列从前往后看和从后往前看是相同的。
 * 示例 1：
 */
public class PalindromeList {

  static ListNode first = null;
  static ListNode last = null;

  public static void main(String[] args) {
    int[] nums = {1,2,3,3,2,1};
    for (int i = 0; i < nums.length; i++) {
      construct(nums[i]);
    }

    boolean isPalindrome = new Solution().isPalindrome(first);
    System.out.println(isPalindrome);
  }

  static class Solution {
    public boolean isPalindrome(ListNode head) {

      ListNode nextNode = head;
      int index = 0;
      int[] nodeValues = new int[10000];
      while (nextNode != null) {
        nodeValues[index] = nextNode.val;
        nextNode = nextNode.next;
        index++;
      }

      for (int i = 0; i < index; i++) {
        if (nodeValues[i] != nodeValues[index-1 - i]) {
          return false;
        }
      }

      return true;
    }
  }

  public static class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }

  /**
   * 使用尾插法创建链表
   * @param value
   */
  public static void construct(int value) {
    if(first == null) {
      ListNode current = new ListNode(value);
      first = current;
      //只有一个节点的时候，最后一个节点也是第一个节点
      last = first;
    }else {
      ListNode current = new ListNode(value);
      //最后一个节点指向新节点
      last.next = current;
      //更新最后一个节点的位置
      last = current;
    }
  }
}
