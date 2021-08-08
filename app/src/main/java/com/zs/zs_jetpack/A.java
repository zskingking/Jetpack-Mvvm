package com.zs.zs_jetpack;

import java.util.Map;

/**
 * @author zs
 * @date 2021/8/6
 */
class A {


    public static void sort(){
        quickSort(a,0,a.length - 1);
    }

    public static int[] a = {5,1,7,2,9,4};
    private static void quickSort(int[] nums, int start, int end) {
        if (start >= end) return;

        int left = start, right = end;
        int standard = nums[left];

        while (left < right) {
            while (left < right && nums[right] >= standard) {
                right--;
            }
            nums[left] = nums[right];
            while (left < right && nums[left] <= standard) {
                left++;
            }
            nums[right] = nums[left];
        }
        //走到这左右指针发生碰撞，并且值可覆盖(原值已经挪到其他位置)
        nums[left] = standard;
        quickSort(nums,start, left-1);
        quickSort(nums,left+1, end);
    }
}
