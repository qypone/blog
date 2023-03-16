package com.qypone.demo.leetcode;

/**
 * 给定一个n个元素有序的（升序）整型数组nums 和一个目标值target ，写一个函数搜索nums中的 target，如果目标值存在返回下标，否则返回 -1。
 * https://leetcode-cn.com/problems/binary-search
 */
public class BinarySearch {
  public static void main(String[] args) {
    int[] nums = {1, 0, 5};

    int search = new Solution().search(nums, 5);
    System.out.println(search);
  }


  static class Solution {
    public int search(int[] nums, int target) {
      return searchOne(nums, nums.length / 2, nums.length, target);
    }

    private int searchOne(int[] nums, int start_index, int end_index, int target) {
      if (start_index >= nums.length || end_index == 0) {
        return -1;
      }
      if (start_index == end_index && nums[start_index] != target) {
        return -1;
      }
      if (nums[start_index] == target) {
        return start_index;
      }
      if (nums[end_index - 1] == target) {
        return end_index - 1;
      }
      if (nums[start_index] > target) {
        return searchOne(nums, start_index / 2, start_index, target);
      } else if (nums[start_index] < target) {
        return searchOne(nums, start_index + 1, end_index, target);
      } else {
        return start_index;
      }
    }
  }

  /**
   * 官方题解
   */
  class Solution2 {
    public int search(int[] nums, int target) {
      int low = 0, high = nums.length - 1;
      while (low <= high) {
        int mid = (high - low) / 2 + low;
        int num = nums[mid];
        if (num == target) {
          return mid;
        } else if (num > target) {
          high = mid - 1;
        } else {
          low = mid + 1;
        }
      }
      return -1;
    }
  }
}

