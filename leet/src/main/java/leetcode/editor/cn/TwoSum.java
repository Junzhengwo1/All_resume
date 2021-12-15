//给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那 两个 整数，并返回它们的数组下标。 
//
// 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。 
//
// 你可以按任意顺序返回答案。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [2,7,11,15], target = 9
//输出：[0,1]
//解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
// 
//
// 示例 2： 
//
// 
//输入：nums = [3,2,4], target = 6
//输出：[1,2]
// 
//
// 示例 3： 
//
// 
//输入：nums = [3,3], target = 6
//输出：[0,1]
// 
//
// 
//
// 提示： 
//
// 
// 2 <= nums.length <= 104 
// -109 <= nums[i] <= 109 
// -109 <= target <= 109 
// 只会存在一个有效答案 
// 
//
// 进阶：你可以想出一个时间复杂度小于 O(n2) 的算法吗？ 
// Related Topics 数组 哈希表 
// 👍 12798 👎 0

package leetcode.editor.cn;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//Java：两数之和
public class TwoSum{
    public static void main(String[] args) {
        Solution solution = new Solution();
        // TO TEST
        int[] a ={11,15,2,7};
        int b = 9;
        int[] ints = solution.twoSum(a, b);
        int[] ints1 = solution.twoSumForHash(a, b);
        System.out.println(Arrays.toString(ints1));
        System.out.println(Arrays.toString(ints));


    }


    //leetcode submit region begin(Prohibit modification and deletion)
    static class Solution {

        /**
         * 循环思想
         */
        public int[] twoSum(int[] nums, int target) {

            for (int i = 0; i < nums.length; i++) {
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[i] + nums[j] == target) {
                        return new int[]{i, j};
                    }
                }
            }
            throw new IllegalArgumentException("No two sum solution");
        }

        /**
         * 对比思想
         *
         * @param nums
         * @param target
         * @return
         */
        public int[] twoSumForHash(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                map.put(nums[i],i);
            }
            for (int j=0 ; j<nums.length ; j++) {
                int temp = target - nums[j];
                if(map.containsKey(temp) && map.get(temp)!=j){
                    return new int[] {j,map.get(temp)};
                }
            }
            throw new IllegalArgumentException("No two sum solution");
        }


    }
//leetcode submit region end(Prohibit modification and deletion)

}