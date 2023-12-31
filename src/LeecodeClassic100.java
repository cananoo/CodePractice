

import java.util.*;

public class LeecodeClassic100 {

    public static void main(String[] args) {
        System.out.println(coinChange(new int[]{1,2,5},11));
    }

    /**
     * Coin Change
     * @param coins 硬币数组
     * @param amount 金额
     * @return 最少硬币数         (动态规划)
     */
    public static int coinChange(int[] coins, int amount) {
        if (coins.length == 0) return -1;

        int[] amountToCoins = new int[amount + 1];
        amountToCoins[0] = 0;
        for (int i = 1; i < amountToCoins.length; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                if (i - coins[j] >= 0 && amountToCoins[i - coins[j]] < min){
                    min = amountToCoins[i - coins[j]] + 1;
                }
            }
            amountToCoins[i] = min;
        }
        return amountToCoins[amount] == Integer.MAX_VALUE ? -1 : amountToCoins[amount];
    }

    /**
     * Burst Balloons
     * @param nums 气球数组
     * @return 最大硬币数
     */
    public static int maxCoins2(int[] nums) {
      int n = nums.length;
      int[] arr = new int[nums.length + 2];
      arr[0] = 1;
      arr[n + 1] = 1;
        for (int i = 1; i < arr.length-1; i++) {
            arr[i] = nums[i - 1];
        }
      int[][] dp = new int[n+2][n+2];
        // 长度
        for (int len = 3; len <= n + 2; len++) {
            // 左右区间
            for (int l = 0; l + len - 1  <= n + 1; l++) {
                int r  = l + len - 1;
                for (int k = l + 1; k <= r - 1 ; k++) {
                    dp[l][r] =  Math.max(dp[l][r],dp[l][k] + dp[k][r] + arr[l] * arr[k] * arr[r]);
                }
            }
        }
        return  dp[0][n + 1];
    }



    // 超时
    public static int maxCoins(int[] nums) {
     int n = nums.length;
       int max = 0;
        for (int i = 0; i < n; i++) {
            int count = 0;
            int left = 1;
            int right = 1;
             if (i - 1 >=0 ) left = nums[i - 1];
             if (i + 1 < n) right = nums[i + 1];
             count += left * nums[i] * right;
             int[] arr = new int[n - 1];
            for (int j = 0; j < i; j++) {
                arr[j] = nums [j];
            }
            for (int j = i + 1 ; j < nums.length ; j++) {
                arr[j - 1] = nums[j];
            }
          count +=  maxCoins(arr);
            if (count > max) max = count;
        }
        return max;
    }


    /**
     * Best Time to Buy and Sell Stock with Cooldown
     * @param prices 股票价格数组
     * @return 最大利润
     */

    public static int maxProfit4(int[] prices) {
        if (prices.length < 2) return 0;
        int n = prices.length;
        int[][] dp = new int[n][3];
        // 不是因为今天卖掉不持股拥有的现金
        dp[0][0] = 0;
        // 持股后拥有的现金
        dp[0][1] = -prices[0];
        // 因为今天卖掉后不持股所拥有的现金
        dp[0][2] = 0;
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0],dp[i - 1 ][2]);
            dp[i][1] = Math.max(dp[i - 1][1],dp[i - 1 ][0] - prices[i]);
            dp[i][2] = dp[i - 1 ][1] + prices[i];
        }
        return  Math.max(dp[n-1][0],dp[n-1][2]);
    }


    // 加一个记忆Map 标记在某天买能够得到的最大收益 超时 208/210
    static Map<Integer,Integer> memory = new HashMap<>();
    public static int maxProfit3(int[] prices) {
        if (prices.length < 2) return 0;
        if (memory.get(prices.length) != null) {
            return memory.get(prices.length);
        }
        // 卖和冻结是一个连续操作
        int res = 0;
        int max = 0;
        for (int i = 0; i < prices.length; i++) {
            res -= prices[i];
            int temp = res;
            for (int j = i + 1; j < prices.length; j++) {
                res += prices[j];
                // 卖了之后冻结一天 ，说明如果后面还剩三天则可以再买
                if (prices.length - j > 3) {
                        res += maxProfit3(Arrays.copyOfRange(prices, j + 2, prices.length));
                }
                if (res > max) max = res;
                // 回溯-- 不在这天卖
                res = temp;
            }
            memory.put(prices.length,max);
            res = 0;
        }
        return max;
    }

    /**
     * Remove Invalid Parentheses
     * @param s  字符串
     * @return 有效的括号字符串列表
     */
    public static List<String> removeInvalidParentheses2(String s) {
        // 先统计需要删除的左括号数和右括号数
        int left = 0;
        int right = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') left++;
            if (s.charAt(i) == ')') {
                if (left > 0) {
                    left --;
                }else right++;
            }
        }

        List<String> list = new ArrayList<>();
        list.add(s);
        if (left == right && left == 0) return list;

        Set<String> set = new HashSet<>();
        toDfs(set,s,new StringBuilder(),0,left,right);
        return new ArrayList<>(set);
    }

    private static void toDfs(Set<String> set, String s,StringBuilder sb, int i, int left, int right) {
    if (i == s.length()) {
        if ( left == 0 && right == 0 && isValidStyle(sb.toString())) {
        set.add(sb.toString());
    }
    return;
    }
    if (s.charAt(i) == '('){
        // 要这个括号
        sb.append("(");
        toDfs(set,s,sb,i+1,left,right);
        sb.deleteCharAt(sb.length()-1);
        //不要这个括号
        if (left > 0){
            toDfs(set,s,sb,i+1,left-1,right);
        }
    }else if (s.charAt(i) == ')'){
        // 要这个括号
        sb.append(")");
        toDfs(set,s,sb,i+1,left,right);
        sb.deleteCharAt(sb.length()-1);
        //不要这个括号
           if (right > 0){
               toDfs(set,s,sb,i+1,left,right-1);
           }
    }else {
        sb.append(s.charAt(i));
        toDfs(set,s,sb,i+1,left,right);
        sb.deleteCharAt(sb.length()-1);
    }
    }


    // 超时
    static Set<String> validList = new HashSet<>();
    public static List<String> removeInvalidParentheses(String s) {
        List<String> tempList = new ArrayList<>();
        if (isValidStyle(s)) return Arrays.asList(s);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '(' && s.charAt(i) != ')'){
                continue;
            }
            String temp = s.substring(0,i) + s.substring(i+1,s.length());
            if (isValidStyle(temp)) {
                tempList.add(temp);
                validList.add(temp);
            }
        }
        if (tempList.isEmpty()){
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != '(' && s.charAt(i) != ')'){
                    continue;
                }
                String temp = s.substring(0,i) + s.substring(i+1,s.length());
                removeInvalidParentheses(temp);
            }
        }
        int max = 0;
        for (String string : validList) {
            if (string.length() > max) max = string.length();
        }
        for (Iterator<String> iterator = validList.iterator(); iterator.hasNext(); ) {
            String next =  iterator.next();
            if (next.length() < max) iterator.remove();
        }
        List<String> res = new ArrayList<>(validList);
        if (res.isEmpty()){
            res.add("");
        }
        return res;
    }

    private  static boolean isValidStyle(String temp) {
        if (!temp.contains( "(") && !temp.contains(")")) return true;
        if (temp.length() == 0) return true;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) == '('){
                stack.push(temp.charAt(i));
            }else if (temp.charAt(i) == ')'){
                if (!stack.isEmpty()){
                    stack.pop();
                }else {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    /**
     * Longest Increasing Subsequence
     * @param nums 整数数组
     * @return 最长递增子序列的长度
     * @ 解法  用一个Map记录从每个索引开始的最长子序列 递归即可
     */
     static  Map<Integer,Integer> lengthOfLISMap = new HashMap<>();
    public static int lengthOfLIS(int[] nums) {
      transfer(nums);
      int max = 1;
        for (Integer value : lengthOfLISMap.values()) {
            max = Math.max(max,value);
        }
        return max;
    }
    public  static  int transfer(int[] nums){
        if (nums.length == 1) {
            lengthOfLISMap.put(1,1);
            return 1;
        }
        int res = 1;
        lengthOfLIS(Arrays.copyOfRange(nums, 1,nums.length));
        int count = 1;
        for (int i = 1 ; i < nums.length; i++ ) {
            if (nums[nums.length - i] > nums[0]){
                count += lengthOfLISMap.get(i);
            }
            if ( count > res)  res = count;
            count = 1;
        }
        lengthOfLISMap.put(nums.length,res);
        return res;
    }

    /**
     * Serialize and Deserialize Binary Tree
     */
    String res = "";
    public static String serialize2(TreeNode root) {
        // 先序遍历 null 用 # 代替
        if (root == null) return "#";
        return root.val  + "," + serialize2(root.left)+ "," + serialize2(root.right);
    }

    public static TreeNode deserialize2(String data) {
          String[] split = data.split(",");
            List<String> list = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                list.add(split[i]);
            }
            return reback(list);
    }
    public static TreeNode reback(List<String> list){
        if (list.get(0).equals("#")){
            list.remove(0);
            return null;
        }
        TreeNode root = new TreeNode(Integer.valueOf(list.get(0)));
        list.remove(0);
        root.left = reback(list);
        root.right = reback(list);
        return root;
    }

    // 复杂解法
    // 获取一棵树的最大深度
    public static int depth(TreeNode node){
        if (node == null ) return 0;
        return Math.max(depth(node.left)+1,depth(node.right)+1);
    }

    // 根据树的高度补全二叉树为完全二叉树
    public  static TreeNode complimentTree(TreeNode node,int depth,int count){
        if (depth == count) return node;
        if (node.left == null && count + 1 <= depth){
            node.left = new TreeNode(-1001);
        }
        if (node.right == null && count + 1 <= depth){
            node.right = new TreeNode(-1001);
        }
        complimentTree(node.left,depth,count+1);
        complimentTree(node.right,depth,count+1);
      return node;
    }
    public static Map<Integer,List<Integer>> gatherSameLevelToMap(TreeNode root,int count,Map<Integer,List<Integer>> sMap) {
        if (root == null ) return null;
        List<Integer> list = sMap.get(count);
        if (list == null)  {
            list = new ArrayList<>();
            sMap.put(count,list);
        }
        list.add(root.val);
        gatherSameLevelToMap(root.left,count+1,sMap);
        gatherSameLevelToMap(root.right,count+1,sMap);

        return sMap;
    }

    public static String serialize(TreeNode root) {
        if (root == null) return null;
         root = complimentTree(root, depth(root), 1);
        Map<Integer, List<Integer>> integerListMap = gatherSameLevelToMap(root, 0, new HashMap<>());
        String serilizedString = "";
        for (int i = 0; i < integerListMap.size(); i++) {
            List<Integer> list = integerListMap.get(i);
            if (i != integerListMap.size() - 1){
                String string = list.toString();
                serilizedString += string.substring(1, string.length()-1) + ", ";
            }else {
                String string = list.toString();
                serilizedString += string.substring(1, string.length()-1);
            }
        }
        return serilizedString;
    }
    public static TreeNode deserialize(String data) {
        if (data == null) return null;
        String[] split = data.split(", ");
        List<TreeNode> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals("-1001")){
                list.add(null);
            }else {
                list.add(new TreeNode(Integer.valueOf(split[i])));
            }
        }
        // 根据 list 构造二叉树
        for (int i = 0; i < list.size(); i++) {
             if (list.get(i) != null){
                 if (i * 2 + 1 < list.size()) list.get(i).left = list.get( i * 2 + 1);

                 if (i * 2 + 2 < list.size()) list.get(i).right = list.get( i * 2 + 2);
             }
        }
        return list.get(0);
    }

    /**
     * Find the Duplicate Number
     * @param nums 整数数组
     * @return 重复的数
     */
        //https://zhuanlan.zhihu.com/p/102298178 妙！
        public static int findDuplicate2(int[] nums) {
            int slow = 0;
            int fast = 0;
            slow = nums[slow];
            fast = nums[nums[fast]];
            while (slow != fast) {
                slow = nums[slow];
                fast = nums[nums[fast]];
            }
            fast = 0;
            while (slow != fast){
                slow = nums[slow];
                fast = nums[fast];
            }
            return slow;
        }


    // 超时 54/58
    public static int findDuplicate(int[] nums) {
        int n = nums.length - 1;
        int res = 0;
        for (int i = 0; i < n; i++) {
            int temp = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (temp == nums[j]) {
                    res = temp;
                    break;
                }
            }
        }
        return res;
    }

    /**
     * move zeros
     * @param nums 整数数组
     */
    public static void moveZeroes(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length ; i++){
            if (nums[i] != 0) {
                list.add(nums[i]);
                nums[i] = 0;
            }
        }
        for (int i = 0; i < list.size(); i++) {
            nums[i] = list.get(i);
        }
    }

    /**
     * Perfect Squares
     * @param n 整数
     * @return 最少的完全平方数的个数
     */
    // 记忆Map
   static Map<Integer,Integer> numSquraresMap = new HashMap<>();
   static Map<Integer,Boolean> mapToSquare = new HashMap<>();
    public static int numSquares(int n) {
        if (mapToSquare.get(n) != null && mapToSquare.get(n)) return 1;
        if (isPerfectSquare(n)) {
            mapToSquare.put(n,true);
            return  1;
        }
        int res = Integer.MAX_VALUE;
        // 循环次数
        int num =  n % 2 == 0 ? n/2 : n/2 + 1 ;
        for (int i = 1; i <= num; i++) {
            int p1 = 0;
            int p2 = 0;
            if (numSquraresMap.containsKey(i) ) {
                p1 = numSquraresMap.get(i);
            }else {
                p1 =  numSquares(i);
                numSquraresMap.put(i, p1);
            }
            if (numSquraresMap.containsKey(n - i)) {
                p2 = numSquraresMap.get(n - i);
            }else {
                p2 =  numSquares(n - i);
                numSquraresMap.put(n - i, p2);
            }
            res = Math.min(res,p1 + p2);
        }
        numSquraresMap.put(n,res);
        return res;
    }
    //判断一个数是否为完全平方数
    public static boolean isPerfectSquare(int n){
        double pow = Math.pow(n, 0.5);
        double floor = Math.floor(pow);
        return  Math.pow(floor,2) == n;
    }

    /**
     * Search a 2D Matrix II
     * @param matrix  二维整数数组
     * @param target    整数
     * @return  是否存在
     */
    public static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0) return false;
        int raw = matrix[0].length;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < raw; j++) {
                if (matrix[i][j] == target) return true;
                if (matrix[i][j] > target) raw = j;
            }
            if (raw == 0) break;
        }
        return false;
    }



    /**
     * Sliding Window Maximum
     * @param nums   整数数组
     * @param k     正整数
     * @return 滑动窗口最大值数组
     *
     * 思路:  使用暴力法，当滑动窗口滑出去的左侧元素为最大值时，无法找到新的滑动窗口的最大值，只能逐个遍历，找到新的最大值.
     *       故可改用双向队列 -- 不断记录滑动时最大的元素列表 --按递减的方式存储 --每轮只需要取列表首元素即可（提前是元素索引未出窗口，否则换下一个）
     */
    public static int[] maxSlidingWindow2(int[] nums, int k) {
        if (nums == null || nums.length <2) return nums;
        LinkedList<Integer> queue = new LinkedList<>();
        int[] res = new int[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            if (queue.isEmpty() || nums[i] <=  nums[queue.peekLast()]) {
                queue.add(i);
            }else {
                while (!queue.isEmpty() && nums[queue.peekLast()] < nums[i]) {
                    queue.pollLast();
                }
                queue.add(i);
            }
            // 判断首位是否还在滑动窗口内
            if (queue.peek() < i - k + 1) queue.poll();
            // 滑动窗口形成
            if (i + 1 >= k){
                res[i + 1 - k] = nums[queue.peek()];
            }
        }
      return  res;
    }

    // 超时(49/51)
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length - k + 1;
       int[] res = new int[n];
       int max = Integer.MIN_VALUE;
        for (int i = 0; i < k; i++) {
            if (nums[i] > max) max=nums[i];
        }
        res[0] = max;
        for (int i = 1; i < n ; i++) {
            if (nums[i-1]<max && max >= nums[i+k-1]){
                res[i] = max;
                continue;
            }
            if (nums[i-1]<max && max < nums[i+k-1]){
                res[i] = nums[i+k-1];
                max =  nums[i+k-1];
                continue;
            }
            max = Integer.MIN_VALUE;
            for (int j = i; j < i+k; j++) {
                if (max < nums[j]) max = nums[j];
            }
            res[i] = max;
        }
        return res;
    }


    /**
     * Product of Array Except Self
     * @param nums 整数数组
     * @return 除自身外数组的乘积       (开始没有思路--看了题解茅塞顿开) -- 要求O（n）--不能使用除法
     */
    public int[] productExceptSelf(int[] nums) {
        int k = 1;
        int[] res = new int[nums.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = k;
            k *= nums[i];
        }
        k  = 1;
        for (int i = res.length - 1; i >= 0; i--) {
            res[i] *= k;
            k *= nums[i];
        }
return res;
    }

    /**
     * Lowest Common Ancestor of a Binary Tree
     * @param root 二叉树根节点
     * @param p 二叉树节点
     * @param q 二叉树节点
     * @return 二叉树最近公共祖先节点
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> pPath = new ArrayList<>();
        List<TreeNode> qPath = new ArrayList<>();
        findPath(root, p, pPath);
        findPath(root, q, qPath);
        for (int i = 0; i < pPath.size(); i++) {
            for (int j = 0; j < qPath.size(); j++) {
                if (pPath.get(i) == qPath.get(j)) {
                    return pPath.get(i);
                }
            }
        }
        return null;
    }
    public static Boolean findPath(TreeNode root ,TreeNode target,List<TreeNode> path){
        if (root == null ) return false;
        if (root.val == target.val) {
            path.add(root);
            return true;
        }
        if (findPath(root.left,target,path)) {
            path.add(root);
            return true;
        }
        if (findPath(root.right,target,path)) {
            path.add(root);
            return true;
        }
        return false;
    }








    /**
     * Palindrome Linked List
     * @param head 链表头节点
     * @return 是否是回文链表
     */
    public static boolean isPalindrome(ListNode head) {
        Stack<Integer> stack = new Stack();
        ListNode temp = head;
        while (temp != null){
            stack.push(temp.val);
            temp = temp.next;
        }
        while (head!=null){

          if (head.val != stack.pop()) return false;
            head = head.next;
        }
        return  true;
    }


    /**
     * Invert Binary Tree
     * @param root 二叉树根节点
     * @return 反转后的二叉树根节点
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        invertTree(root.left);
        invertTree(root.right);
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.left = right;
        root.right = left;
        return root;
    }


    /**
     * Maximal Square
     * @param matrix  二维字符数组
     * @return 最大正方形面积    (动态规划)
     */
    public static int maximalSquare2(char[][] matrix) {
        int maxArea = 0;
        int row = matrix.length;
        int col = matrix[0].length;
        if (row == 0) return maxArea;
        int[][] dp  = new int[row + 1][col + 1];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col ; j++) {
                if (matrix[i][j] == '1'){
                 dp[i + 1][j + 1]  = Math.min(Math.min(dp[i][j+1],dp[i+1][j]),dp[i][j]) + 1 ;
                 maxArea = Math.max(maxArea, dp[i+1][j+1] * dp[i+1][j+1]);

                }
            }
        }
        return  maxArea;
    }
    //(暴力超时.. 76/78)
    public static int maximalSquare(char[][] matrix) {
        int maxArea = 0;
       int row = matrix.length;
       if (row == 0) return maxArea;
       int column = matrix[0].length;
       int max = Math.min(row,column);
       // 正方形宽度
       for (int i = max; i > 0; i--) {
           // 纵向移动
             int moveToDown = row - i + 1;
           // 横向移动
             int moveToRight = column - i + 1;
           for (int j = 0; j < moveToDown; j++) {
               for (int k = 0; k < moveToRight; k++) {
                   boolean flag = true;

                   for (int l = j; l < i + j; l++) {
                       for (int m = k; m < k + i; m++) {
                           if (matrix[l][m] == '0') {
                               flag = false;
                               break;
                           }
                       }
                       if (!flag) break;
                   }
                   if (flag){
                       maxArea = i * i;
                       break;
                   }
               }

               if (maxArea != 0) break;
           }
           if (maxArea != 0) break;
       }
       return maxArea;
    }

    /**
     *  Kth Largest Element in an Array
     * @param nums  整数数组
     * @param k     正整数
     * @return 第k大的数          时间复杂度要求O(n) --思想 ：快速选择
     */
    public static int findKthLargest(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }
        return largest(list,k);
    }

    public static  int largest(List<Integer>list,int k){
        Random random = new Random();
        int incur = list.get(random.nextInt(list.size()));
        List<Integer> big = new ArrayList<>();
        List<Integer> small = new ArrayList<>();
        List<Integer> equal = new ArrayList<>();
        for (Integer i : list) {
            if (i < incur) small.add(i);
            if (i > incur) big.add(i);
            if (i == incur) equal.add(i);
        }
        if (k <= big.size()) return largest(big,k);
        if (list.size() - small.size() < k) return  largest(small,k - list.size() + small.size());
        return  incur;
    }


    /**
     * Implement Trie (Prefix Tree)
     */
    static class Trie {
        Map<String,Boolean> map = new HashMap<>();
        public Trie() {

        }

        public void insert(String word) {
         map.put(word,true);
        }

        public boolean search(String word) {
         if (map.containsKey(word)) return true;

         return false;
        }

        public boolean startsWith(String prefix) {
            boolean flag = false;
            for (String string : map.keySet()) {
                 if (string.startsWith(prefix)) flag = true;
            }
            return flag;
        }
    }

    /**
     * Course Schedule
     * @param numCourses  课程数
     * @param prerequisites 课程关系数组
     * @return 是否可以完成课程
     */
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        Set<Integer> finish = new HashSet<>();
        List<Integer> havaPre = new ArrayList<>();
        while (true){
            for (int i = 0; i < numCourses; i++) {
                finish.add(i);
            }
            for (int i = 0; i < prerequisites.length; i++) {
                if (prerequisites[i][0] != -1) havaPre.add(prerequisites[i][0]);
            }
            finish.removeAll(havaPre);
            boolean flag = false;
            for (int i = 0; i < prerequisites.length; i++) {
                if (finish.contains(prerequisites[i][1]) && prerequisites[i][0] != -1){
                    prerequisites[i][0] = -1;
                    flag = true;
                }
            }
            if (!flag) break;
            finish.clear();
            havaPre.clear();
        }
        return finish.size() == numCourses;
    }

    /**
     * Reverse Linked List
     * @param head  链表头节点
     * @return  反转后的链表头节点
     */
    public static ListNode reverseList(ListNode head) {
           if (head == null){
               return null;
           }
           List<ListNode> list = new ArrayList<>();
           while (head != null){
                list.add(head);
                head = head.next;
           }
        for (int i = list.size() - 1; i > 0; i--) {
            list.get(i).next = list.get(i - 1);
        }
         list.get(0).next = null;
        return list.get(list.size()-1);
    }

    /**
     * Number of Islands
     * @param grid  二维字符数组
     * @return 岛屿数量 (递归秒了)
     */
    public static int numIslands(char[][] grid) {
        if (grid.length == 0){
            return 0;
        }
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1'){
                    setIsland(grid,i,j);
                    count++;
                }
            }
        }
        return count;
    }
    public static void  setIsland(char[][] grid,int x,int y){
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length){
            return;
        }
        if (grid[x][y] == '1'){
            grid[x][y] = '0';
        }else {
            return;
        }
        setIsland(grid,x-1,y);
        setIsland(grid,x+1,y);
        setIsland(grid,x,y+1);
        setIsland(grid,x,y-1);
    }

    /**
     * House Robber
     * @param nums 整数数组
     * @return 最大金额
     */
    // 加一个记忆Map，记录数组长度为n时的最大值，避免重复计算 (自底向上)
    static Map<Integer,Integer> memoryMap = new HashMap<>();
    public static int rob2(int[] nums) {
        int n = nums.length;
        int max = 0;
        if (n == 1) {
            return nums[0];
        }
        for (int i = 0; i < n; i++) {
            int start = nums[i];
            int temp = 0;
            if (i + 2 < n){
                if (memoryMap.containsKey(n - i - 2)){
                    temp = memoryMap.get(n - i - 2);
                }else {
                    int[] ints = Arrays.copyOfRange(nums,i + 2,n);
                    temp = rob2(ints);
                    memoryMap.put(n - i - 2,temp);
                }
            }
            start += temp;
            if (start > max) max = start;
        }
        return max;
    }
    // （直接递归超时）
    public static int rob(int[] nums) {
        int n = nums.length;
        int max = 0;
        if (n == 1) {
            return nums[0];
        }
        for (int i = 0; i < n; i++) {
            int start = nums[i];
            int temp = 0;
            if (i + 2 < n){
                int[] ints = Arrays.copyOfRange(nums,i + 2,n);
                temp = rob(ints);
            }
            start += temp;
            if (start > max) max = start;
        }
        return max;
}

    /**
     * Majority Element
     * @param nums 整数数组
     * @return 众数
     */
    public static int majorityElement(int[] nums) {
      int n  = nums.length;
      Map<Integer,Integer> map = new HashMap<>();
        for (int num: nums) {
            if (map.containsKey(num)){
                map.put(num,map.get(num) + 1);
            }else {
                map.put(num,1);
            }
        }
        int isMajority = n / 2;
        int majority = Integer.MIN_VALUE;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
              if (entry.getValue() > isMajority){
                  majority = entry.getKey();
                  break;
              }
        }
      return majority;
    }

    /**
     * 相交链表
     * @param headA  链表A头节点
     * @param headB 链表B头节点
     * @return 相交节点
     */
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        List<ListNode> list = new ArrayList<>();
        while (headA != null){
            list.add(headA);
            headA = headA.next;
        }
        while (headB != null){
            if (list.contains(headB)) return headB;
            headB = headB.next;
        }
        return null;
    }



    /**
     * 最小栈
     */
    static class MinStack {
        private List<Integer> minList;
        private int[] stack ;
        private int count;
        public MinStack() {
            minList = new ArrayList<>();
            stack = new int[30000];
            count = 0;
        }

        public void push(int val) {
           stack[count++] = val;
           minList.add(val);
           minList.sort((o1, o2) -> {
               if (o1 > o2) return 1;
               if (o1 < o2) return -1;
               return 0;
           });
        }

        public void pop() {
            int val = stack[--count];
            stack[count] = 0;
            minList.remove((Object)val);
        }

        public int top() {
           return stack[count - 1];
        }

        public int getMin() {
         return minList.get(0);
        }
    }


    /**
     * 乘积最大子数组
     * @param nums  整数数组
     * @return 乘积最大子数组的乘积 (暴力竟然过了..第一次)
     */
    public static int maxProduct(int[] nums) {
        int max = Integer.MIN_VALUE;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int temp = nums[i];
            max = Math.max(max, temp);
            for (int j = i + 1; j < n; j++) {
                temp = temp * nums[j];
                max = Math.max(max, temp);
            }
        }
        return max;
    }

    /**
     * 排序链表
     * @param head  链表头节点
     * @return 排序后的链表
     */
    public static ListNode sortList(ListNode head) {
        if (head == null){
            return null;
        }
          List<Integer> list = new ArrayList<>();
          while (head != null){
              list.add(head.val);
              head = head.next;
          }
          list.sort(((o1, o2) -> {
              if (o1 > o2) return 1;
              if (o1 < o2) return -1;
              return 0;
          }));
          head = new ListNode();
          ListNode temp = head;
        for (int i = 0; i < list.size(); i++) {
            temp.val = list.get(i);
            if (i == list.size() - 1) break;
            temp.next = new ListNode();
            temp = temp.next;
        }
          return head;
    }



    /**
     * LRU 缓存 (利用LickedHashMap -- 哈希表和双向链表的结合 解决)
     */
    static class LRUCache2 {
        int cap;
        LinkedHashMap<Integer,Integer> cache = new LinkedHashMap<>();
        public LRUCache2(int capacity) {
           cap = capacity;
        }
        public int get(int key) {
            if (!cache.containsKey(key)){
                return  -1;
            }
           makeRecently(key);
            return cache.get(key);
        }
        public void put(int key, int value) {
            if (cache.containsKey(key)){
                makeRecently(key);
                cache.put(key,value);
                return;
            }
            if (cap > cache.size()){
                cache.put(key,value);
                return;
            }
         cache.remove(cache.keySet().iterator().next());
            cache.put(key,value);
        }

        public void makeRecently(int key) {
            Integer k = cache.remove(key);
            cache.put(key,k);
        }
    }


    //(超时)
   static class LRUCache {
       private  Map<Integer,String> map = new HashMap<>();
       private int count ;
       private int num;
        public LRUCache(int capacity) {
         count = capacity;
        }
        public int get(int key) {

         if (!map.containsKey(key))
            return -1;

         put(key,Integer.parseInt(map.get(key).substring(6,String.valueOf(map.get(key)).length())));
         return Integer.parseInt(map.get(key).substring(6,String.valueOf(map.get(key)).length()));
        }
        public void put(int key, int value) {
          if (map.containsKey(key)){
              String str = String.valueOf(num);
              int n = str.length();
              for (int i = 0; i < 6 - n; i++) {
                  str = "0" + str;
              }
              map.put(key,str + value);
              num++;
              return;
          }
          if (count > 0){
              String str = String.valueOf(num);
              int n = str.length();
              for (int i = 0; i < 6 - n; i++) {
                  str = "0" + str;
              }
              map.put(key,str + value);
              num++;
              count--;
          }else {
              int LRU_Key = Integer.MIN_VALUE ;
              long temp = Long.MAX_VALUE;
              for (Map.Entry<Integer,String> entry: map.entrySet()) {
                  long change = temp;
                  temp = Math.min(Long.parseLong(entry.getValue().substring(0, 6)),temp);
                  if (change != temp){
                      LRU_Key = entry.getKey();
                  }
              }
              map.remove(LRU_Key);
              String str = String.valueOf(num);
              int n = str.length();
              for (int i = 0; i < 6 - n; i++) {
                  str = "0" + str;
              }
              map.put(key,str + value);
              num++;
          }
        }
    }

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */




    /**
     * Linked List Cycle Ⅱ*
     * @param head 链表头节点
     * @return 环的入口节点 （和前一题一个思路）
     */
    public static ListNode detectCycle(ListNode head) {
        if (head == null) return null;
        List<String> list = new ArrayList<>();
        ListNode temp = head;
        ListNode res = null;
        while (temp != null){
            if (!list.contains(temp.toString())){
                list.add(temp.toString());
                temp = temp.next;
            }else {
                res = temp;
                break;
            }
        }
        return res;
    }

    /**
     * Linked List Cycle
     * @param head 链表头节点
     * @return 是否有环     (遍历看指针是否重复)
     */
    public static boolean hasCycle(ListNode head) {
        if (head == null) return false;
     List<String> list = new ArrayList<>();
     ListNode temp = head;
       boolean res = false;
       while (temp != null){
           if (!list.contains(temp.toString())){
               list.add(temp.toString());
               temp = temp.next;
           }else {
               res = true;
               break;
           }
        }
       return res;
    }


    /**
     * Word Break
     * @param s  字符串
     * @param wordDict 字典
     * @return 是否可以拆分       (动态规划思想)
     */
       /*
        动态规划算法，dp[i]表示s前i个字符能否拆分
        转移方程：dp[j] = dp[i] && check(s[i+1, j]);
        check(s[i+1, j])就是判断i+1到j这一段字符是否能够拆分
        其实，调整遍历顺序，这等价于s[i+1, j]是否是wordDict中的元素
        这个举个例子就很容易理解。
        假如wordDict=["apple", "pen", "code"],s = "applepencode";
        dp[8] = dp[5] + check("pen")
        翻译一下：前八位能否拆分取决于前五位能否拆分，加上五到八位是否属于字典
        （注意：i的顺序是从j-1 -> 0哦~
    */
    public static boolean wordBreak2(String s, List<String> wordDict) {
      Set<String> set = new HashSet<>();
        for (String str : wordDict) {
            set.add(str);
        }
        Boolean[] dp = new Boolean[s.length()+1];
        dp[0] = true;
        for (int j = 1; j < s.length() + 1; j++) {
            for (int i = j-1; i >=0 ; i--) {
                dp[j] = dp[i] && check(s.substring(i,j),set);
                if (dp[j]) break;
            }
        }
        return dp[s.length()];
    }
    private static boolean check(String s, Set<String> set){
        return set.contains(s);
    }


    // 纯递归，时间复杂度过高
        public static boolean wordBreak(String s, List<String> wordDict) {
             Map<String,Integer> map = new HashMap<>();
            for (String str : wordDict) {
                map.put(str,1);
            }
            if (map.containsKey(s)){
                return true;
            }
            return wordBreakHelp(s,map);
        }
    private static boolean wordBreakHelp(String s, Map<String,Integer> map) {
        boolean flag = false;
        int n = s.length();
        List<String> strs = new ArrayList<>();
        String word = "";
        for (int i = 0; i < n ; i++) {
            word += s.charAt(i);
            if (map.containsKey(word)){
                strs.add(word);
            }
        }
        for (int i = 0; i < strs.size(); i++) {
            String newStr = s.substring(strs.get(i).length());
            boolean b = wordBreakHelp(newStr, map);
            if (b){
                flag = true;
                break;
            }
        }
        return flag;
    }



    /**
     * 只出现一次的数字
     * @param nums  整数数组
     * @return 只出现一次的数字
     */
    //O(n)  -- 异或
    public static int singleNumber2(int[] nums) {
        int ans = nums[0];
        if (nums.length > 1) {
            for (int i = 1; i < nums.length; i++) {
                ans = ans ^ nums[i];
            }
        }
        return ans;
    }

    //O(nlogn)
    public static int singleNumber(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int res = Integer.MIN_VALUE;
        if (nums.length == 1){
            return nums[0];
        }
        for (int i = 0; i < n - 1; i+=2) {
             if (nums[i] != nums[i+1]){
                 res = nums[i];
                 break;
             }
             if (i + 2 >= n - 1 &&  n%2 != 0 ){
                 res = nums[n-1];
             }
        }
          return res;
    }

    /**
     * 最长连续序列
     * @param nums 无序整数数组
     * @return 最长连续序列长度
     */
    public static int longestConsecutive(int[] nums) {
        if (nums.length == 0){
            return 0;
        }
        Arrays.sort(nums);
        int max = 0;
        int count = 1;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] + 1 == nums[i + 1]) {
                count++;
            }else if (nums[i]  == nums[i + 1] ){
                continue;
            } else {
                if (count > max){
                    max = count;
                }
                count = 1;
            }
        }
     return Math.max(max,count);
    }


    /**
     * Binary Tree Maximum Path Sum
     * @param root 二叉树根节点
     * @return 二叉树最大路径和
     */
//返回子树路径中最大的前缀和(如果将树的路径从上往下看的话，那么前缀和即为以当前节点向下连续的节点之和
    int count = Integer.MIN_VALUE;
    public  int maxPathSum(TreeNode root) {
         dfs(root);
         return count;
    }
    private  int dfs(TreeNode root){
        if (root == null){
            return 0;
        }
        int left = dfs(root.left);
        int right = dfs(root.right);
        count = Math.max(count,root.val);
        count = Math.max(count,root.val + left);
        count = Math.max(count,root.val + right);
        count = Math.max(count,root.val + left + right);
        return  Math.max(0,Math.max(left,right)) +root.val;
    }


    /**
     * Best Time to Buy and Sell Stock
     * @param prices  股票价格数组
     * @return 最大利润    (滑动窗口解题--只需要遍历一遍)
     */
    public static int maxProfit(int[] prices) {
        int n = prices.length;
        int max = 0;
        int left = 0;
            for (int j = left; j < n ; j++) {
                if (prices[j] < prices[left]){
                    left = j;
                }else if (prices[j] > prices[left] && prices[j] - prices[left] >max){
                    max = prices[j] - prices[left];
                }
            }
        return  max;
    }

    //(暴力超时)
    public static int maxProfit2(int[] prices) {
        int n = prices.length;
         int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (prices[j] - prices[i] > 0 && prices[j] - prices[i] > max) {
                    max = prices[j] - prices[i];
                }
            }
        }
return max;
    }


    /**
     * Flatten Binary Tree to Linked List
     * @param root  二叉树根节点  (递归很容易解决)
     */
    public static void flatten(TreeNode root) {
        if(root == null) {
            return;
        }
        flatten(root.left);
        flatten(root.right);

        if (root.left == null){
            return;
        }
        if (root.right == null){
            root.right = root.left;
            root.left = null;
        }else {
            TreeNode temp =  root.left;
            while (true){
                if (temp.right == null){
                    temp.right = root.right;
                    break;
                }else {
                    temp = temp.right;
                }
            }
            root.right = root.left;
            root.left = null;
        }
    }



    /**
     *   Construct Binary Tree from Preorder and Inorder Traversal
     * @param preorder 前序遍历结果
     * @param inorder 中序遍历结果
     * @return 二叉树根节点         （递归直接解决--根据前序和中序的位置关系解决）
     */
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        if ( preorder.length == 0 || inorder.length == 0){
            return null;
        }
        TreeNode root = new TreeNode(preorder[0]);
        int index = -1;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == preorder[0]){
                index = i;
            }
        }
        if (index != 0 && index!= -1){
            int[] newPreoder = Arrays.copyOfRange(preorder, 1, index + 1);
            int[] newInorder = Arrays.copyOfRange(inorder, 0, index);
            TreeNode leftRoot = buildTree(newPreoder, newInorder);
            root.left = leftRoot;
        }
        if (index != inorder.length-1 && index != -1){
            int[] newPreoder = Arrays.copyOfRange(preorder, index + 1, preorder.length);
            int[] newInorder = Arrays.copyOfRange(inorder, index + 1 , inorder.length);
            TreeNode RightRoot = buildTree(newPreoder, newInorder);
            root.right = RightRoot;
        }
      return  root;
    }


    /**
     * Maximum Depth of Binary Tree
     * @param root  二叉树根节点
     * @return 二叉树最大深度 (递归秒了)
     */
    public static int maxDepth(TreeNode root) {
     return    count(root,0);
    }
    public static int count(TreeNode root,int count) {
        if (root == null){
            return  count;
        }
        ++count;
       int left = count(root.left,count);
       int right = count(root.right,count);
        return Math.max(left,right);
    }



    /**
     * Binary Tree Level Order Traversal
     * @param root 二叉树根节点
     * @return 二叉树层序遍历结果        (map秒了)
     */

    public static List<List<Integer>> levelOrder(TreeNode root) {
        Map<Integer,List<Integer>> map = new HashMap<>();
        level(root,map,0);
        List<List<Integer>> list = new ArrayList<>();
        for (List<Integer> li : map.values() ) {
            list.add(li);
        }
        return list;
    }
    public static void level(TreeNode root,Map<Integer,List<Integer>> map,int count) {
        if (root == null){
            return ;
        }
        if (map.get(count) == null){
            List<Integer> list = new ArrayList<>();
            list.add(root.val);
            map.put(count,list);
        }else {
            map.get(count).add(root.val);
        }
        ++count;
        level(root.left,map,count);
        level(root.right,map,count);
    }





    /**
     * 对称二叉树
     * @param root 二叉树根节点
     * @return 是否为对称二叉树 (暴力...)
     */
    public static boolean isSymmetric(TreeNode root) {
        ArrayList<Integer> integers = mid1(root.left,-1500);
        ArrayList<Integer> integers1 = mid2(root.right,-1500);

        if (integers.size() != integers1.size()){
            return  false;
        }

        for (int i = 0; i < integers.size(); i++) {
            if (!Objects.equals(integers.get(i), integers1.get(i))){
                return false;
            }
        }
     return true;
    }
    // 中序遍历
    public static ArrayList<Integer> mid1(TreeNode root,int count){
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null){
            list.add(count);
            return list;
        }
        list.addAll(mid1(root.left,++count));
        list.add(root.val);
        list.addAll(mid1(root.right,++count));
        return list;
    }
    public static ArrayList<Integer> mid2(TreeNode root,int count){
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null){
            list.add(count);
        return list;
        }
        list.addAll(mid2(root.right,++count));
        list.add(root.val);
        list.addAll(mid2(root.left,++count));
        return list;
    }



    /**
     * 验证二叉搜索树
     * @param root 二叉树根节点
     * @return 是否为二叉搜索树
     */
    long temp = Long.MIN_VALUE;
    public  boolean isValidBST(TreeNode root) {
        if (root == null){
            return true;
        }
        if (!isValidBST(root.left)){
            return false;
        }
        if (temp >= root.val){
            return false;
        }else {
            temp =root.val;
        }
        if (!isValidBST(root.right)) {
            return false;
        }
        return true;
    }






    /**
     *  不同的二叉搜索树
     * @param n 节点数
     * @return 二叉搜索树的个数
     */
    public static int numTrees(int n) {
        if (n == 0){
            return 0;
        }
        return  count(n);

    }
    public static int count(int n){
        int total = 0;
        if (n == 0){
            return 1;
        }
        if (n == 1){
            return  1;
        }
        total += count(n-1);
        int i = 1;
        while (i < n){
            total += count(i)*count(n-1-i);
            i++;
        }
        return  total;
    }


    /**
     *  二叉树的中序遍历
     * @param root 二叉树根节点
     * @return 中序遍历结果
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null){
            return list;
        }
        list.addAll(inorderTraversal(root.left));


        list.add(root.val);

        list.addAll(inorderTraversal(root.right));
        return list;
    }

      static class  TreeNode {
     int val;
     TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }

          @Override
          public String toString() {
              return "TreeNode{" +
                      "val=" + val +
                      ", left=" + left +
                      ", right=" + right +
                      '}';
          }
      }

    /**
     *  最大矩形
     * @param matrix 二维字符数组
     * @return 最大矩形面积 (没思路，竟然也是利用单调栈法....)
     */
    public static int maximalRectangle(char[][] matrix) {
        int res = 0;
        if (matrix.length == 0 || matrix[0].length == 0){
            return res;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        //构造高度数组,每层叠加高度
        int[] arr = new int[col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == '1'){
                    arr[j] += 1;
                }else {
                    arr[j] = 0;
                }
            }
            if (largestRectangleArea2(arr) > res){
                res = largestRectangleArea2(arr);
            }
        }
        return  res;
    }


     /**
     * 最大矩形
     * @param heights 高度数组
     * @return 最大矩形面积
     */

     //单调栈法(妙啊！！ -- 顾名思义就是栈中元素始终递增)
     public static int largestRectangleArea2(int[] heights) {
          int res = 0;
         if (heights.length == 0){
             return res;
         }
         if (heights.length == 1){
             return heights[0];
         }
         int[] newHeights = new int[heights.length+2];
         for (int i = 1; i < heights.length + 1; i++) {
             newHeights[i] = heights[i-1];
         }
         Stack<Integer> stack = new Stack<>();
         for (int i = 0; i < newHeights.length; i++) {
             while (!stack.isEmpty() && newHeights[stack.peek()] > newHeights[i]){
                 int pop = stack.pop();
                 res = Math.max(res,(i - stack.peek() - 1) * newHeights[pop] );
             }
             stack.push(i);
         }
         return  res;
        }

    //超时了 呜呜呜
    public static int largestRectangleArea(int[] heights) {
        if (heights.length == 0){
            return 0;
        }
        int cur = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < heights.length ; i++) {
            if (heights[i] < min){
                min = heights[i];
                cur = i;
            }
        }
        int max = min * heights.length;
        // 向右找
        int[] right = Arrays.copyOfRange(heights, cur+1, heights.length);
        int rightMax = largestRectangleArea(right);
        if (max < rightMax) {
            max = rightMax;
        }
        //向左找
        int[] left = Arrays.copyOfRange(heights, 0, cur);
        int leftMax = largestRectangleArea(left);
        if (max < leftMax) {
            max = leftMax;
        }

        return max;
    }


    /**
     *  单词搜索
     * @param board 二维字符数组
     * @param word 单词
     * @return 是否存在
     */
    public static boolean exist(char[][] board, String word) {
        boolean[][] boar  = new boolean[board.length][board[0].length];
        for (boolean[] arr: boar) {
            Arrays.fill(arr,true);
        }
        char c = word.charAt(0);
         class XY{
             int x;
             int y;

             public XY(int x, int y) {
                 this.x = x;
                 this.y = y;
             }
         }
         List<XY> xys = new ArrayList<>();
        for (int i = 0; i < board.length ; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (c == board[i][j]){
                   xys.add(new XY(i,j));
                }
            }
        }
        boolean flag = false;
        for (XY xy : xys) {
            if (canGo(boar,board,xy.x,xy.y,word.length(),word)){
                flag = true;
                break;
            }
        }
        return  flag;
    }
    public static boolean canGo(boolean[][] board,char[][] boar,int x,int y,int count,String word){

        if (x >=board.length || y >= board[0].length){
            return false;
        }

        if (word.length() == 1 && boar[x][y] == word.charAt(0)){
            return true;
        }

        if (x - 1 >= 0 && board[x - 1][y] && boar[x - 1][y] == word.charAt(word.length() - count + 1) ){
            board[x][y] = false;
            count--;
            if (count == 1){
                return true;
            }
           if (canGo(board,boar,x-1,y,count,word)){
               return true;
           }else {
               board[x][y] = true;
               count++;
           }
        }

        if (x + 1 < board.length && board[x + 1][y]  && boar[x + 1][y] == word.charAt(word.length() - count + 1)){
            board[x][y] = false;
            count--;
            if (count == 1){
                return true;
            }
            if (canGo(board,boar,x+1,y,count,word)){
                return true;
            }else {
                board[x][y] = true;
                count++;
            }
        }

        if (y + 1 < board[0].length && board[x][y + 1] && boar[x][y + 1] == word.charAt(word.length() - count + 1)){
            board[x][y] = false;
            count--;
            if (count == 1){
                return true;
            }
            if (canGo(board,boar,x,y+1,count,word)){
                return true;
            }else {
                board[x][y] = true;
                count++;
            }
        }

        if (y - 1 >= 0 && board[x][y - 1]  && boar[x][y - 1] == word.charAt(word.length() - count + 1)){
            board[x][y] = false;
            count--;
            if (count == 1){
                return true;
            }
            if (canGo(board,boar,x,y-1,count,word)){
                return true;
            }else {
                board[x][y] = true;
                count++;
            }
        }

        return false;
    }



    /**
     * 子集
     * @param nums 无重复元素的整数数组
     * @return 所有不同的子集
     */
    public static List<List<Integer>> subsets(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> empty = new ArrayList<>();
        res.add(empty);
        int n = nums.length;
        if (nums.length == 0 ){
            return res;
        }
        List<List<Integer>> first = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            List<Integer> temp = new ArrayList<>();
            temp.add(nums[i]);
            first.add(temp);
        }
        for (int i = 0; i < nums.length; i++) {
            List<List<Integer>> list = subByN(nums, first, i);
            for (int j = 0; j < list.size(); j++) {
                res.add(list.get(j));
            }
            first = new ArrayList<>();
            for (List<Integer> li: list ) {
                List<Integer> ff = new ArrayList<>();
                for (int j = 0; j < li.size(); j++) {
                    ff.add(li.get(j));
                }
                first.add(ff);
            }
        }
        return res;
    }
    public static List<List<Integer>> subByN(int[] nums,List<List<Integer>> list,int n){
        if (n == 0) {
            return list;
        }
        List<List<Integer>> res =  new ArrayList<>();
        for (int i = 0; i < list.size()-1; i++) {
            for (int j = 0; j <nums.length ; j++) {
                if (list.get(i).get(list.get(i).size()-1) < nums[j] ){
                    List<Integer> temp = new ArrayList<>();
                    temp.addAll(list.get(i));
                    temp.add(nums[j]);
                    res.add(temp);
                }
            }
        }
    return res;
    }


    /**
     * 最小覆盖子串
     * @param s 字符串
     * @param t 子串
     * @return 最小覆盖子串
     */

    // 滑动窗口
    public static String minWindow3(String s, String t) {
        if (s == null || t == null || s.length() < t.length()){
            return "";
        }
        Map<Character,Integer> map = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            map.put(t.charAt(i),map.getOrDefault(t.charAt(i),0)+1);
        }
        char[] chars = s.toCharArray();

        int start = 0;
        int end = 0;
        for (int left = 0 , right =0 ,result = s.length(); right < chars.length; right++) {
            if (map.containsKey(chars[right])){
                map.put(chars[right],map.get(chars[right])-1);
            }

            while (Collections.max(map.values()) == 0) {
                if (map.containsKey(chars[left])){
                    map.put(chars[left],map.get(chars[left])+1);
                }

                if (result >= right - left + 1) {
                    result = right - left + 1 ;
                    start = left;
                    end  = right + 1;
                }
                left ++;
            }

        }

        return s.substring(start,end);
    }

    /**
     * 又超时了呜呜
     */
    public static String minWindow2(String s, String t) {
        if (s.length() < t.length()){
            return "";
        }
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < t.length(); i++) {
            list.add(t.charAt(i));
        }
        int min = 1000000;
        String minStr = "";
        for (int i = 0; i < s.length(); i++) {
            List<Character> slit = new ArrayList<>();
            for (int j = i; j < s.length(); j++) {
                 slit.add(s.charAt(j));
                 if (slit.size() < t.length()) {
                     continue;
                 }
                boolean flag = true;
                ArrayList<Character> backUp = new ArrayList<>();
                for (int k = 0; k < list.size(); k++) {
                    if (slit.contains(list.get(k))){
                        slit.remove(list.get(k));
                        backUp.add(list.get(k));
                    }else {
                        slit.addAll(backUp);
                        flag = false;
                       break;
                    }
                }
                if (flag && min > s.substring(i, j + 1).length() ){
                    minStr = s.substring(i, j + 1);
                    min = minStr.length();
                    break;
                }
            }
        }
        return minStr;
    }

    public static String minWindow(String s, String t) {
        if (s.length() < t.length()){
            return "";
        }
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < t.length(); i++) {
            list.add(t.charAt(i));
        }
        int min = 1000000;
        String minStr = "";
        for (int i = 0; i < s.length(); i++) {
            for (int j = i+t.length()-1; j < s.length(); j++) {
                String string = s.substring(i, j + 1);
                ArrayList<Object> copyList = new ArrayList<>();
                for (int k = 0; k < string.length(); k++) {
                    copyList.add(string.charAt(k));
                }
                boolean flag = true;
                for (int k = 0; k < list.size(); k++) {
                    if (copyList.contains(list.get(k))){
                        copyList.remove(list.get(k));
                    }else {
                        flag = false;
                        break;
                    }
                }
                if (flag && string.length() < min) {
                    min = string.length();
                    minStr = string;
                }
            }
        }
        return minStr ;
    }

    /**
     * 颜色分类
     * @param nums  颜色数组
     */
    public static void sortColors(int[] nums) {
        for (int i = 0; i < nums.length-1; i++) {
            for (int j = 1; j < nums.length-i; j++) {
                if (nums[j] < nums[j-1]) {
                    int temp = nums[j];
                    nums[j] = nums[j-1];
                    nums[j-1] = temp;
                }
            }
        }

    }

    /**
     *  编辑距离
     * @param word1 字符串1
     * @param word2 字符串2
     * @return  最小操作数
     */
    public static int minDistance(String word1, String word2) {
     int n = word1.length();
     int n2 = word2.length();
     int[][] arr = new int[n+1][n2+1];

        for (int i = 0; i < arr.length; i++) {
            arr[i][0] = i;
        }
        for (int i = 0; i < arr[0].length; i++) {
            arr[0][i] = i;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j < arr[i].length; j++) {
                if (word1.charAt(i-1) == word2.charAt(j-1)){
                      arr[i][j] = arr[i-1][j-1];
                }else {
                    arr[i][j] = Math.min(Math.min(arr[i-1][j-1],arr[i-1][j]),arr[i][j-1])+1;
                }
            }
        }
     return  arr[n][n2];
    }


    /**
     *  爬楼梯
     * @param n  楼梯数
     * @return 有多少种方法爬到楼顶
     */
    static Map<Integer,Integer> map = new HashMap<>();
    public static int climbStairs(int n) {
        if (n == 1){
            return 1;
        }
        if (n == 2){
            return 2;
        }
        if (!map.containsKey(n)){
            map.put(n,climbStairs(n-1)+climbStairs(n-2));
        }
        return map.get(n);
    }

    /**
     * 最小路径和
     * @param grid  二维数组
     * @return 最小路径和
     */
    public static int minPathSum2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int left = -1;
                int up = -1;
                if (j-1 >=0){
                    left = grid[i][j-1];
                }
                if (i-1 >=0){
                    up = grid[i-1][j];
                }
                int plus = 0;
                if (left == -1){
                    plus = up;
                }
                if (up == -1){
                    plus = left;
                }
                if (left ==-1 && up ==-1){
                    plus = 0;
                }
                if (left!= -1 && up != -1){
                    plus = left > up ? up : left;
                }

                grid[i][j] = plus + grid[i][j];
            }
        }
        return grid[m-1][n-1];
    }

    //(递归正确但超时)
    public static int minPathSum(int[][] grid) {
        if (grid.length == 1 && grid[0].length == 1){
            return grid[0][0];
        }
        int  min = 0;
        //初始化
        min = min + grid[0][0];
        int  min1 = -1;
        int  min2 = -1;
        //向右
          if (grid[0].length > 1){
              int[][] ne = new int[grid.length][grid[0].length-1];
              for (int i = 0; i < ne.length; i++) {
                  ne[i] =  Arrays.copyOfRange(grid[i],1,grid[i].length);
              }
             min1 = min + minPathSum(ne);
          }
        //向下
       if (grid.length > 1){
           int[][] nf = new int[grid.length-1][grid[0].length];
           for (int i = 0; i < nf.length; i++) {
               nf[i] =  grid[i+1];
           }
           min2 = min +  minPathSum(nf);
       }
       if (min1 == -1){
           return min2;
       }
      if (min2 == -1) {
          return  min1;
      }
        return min1 > min2 ? min2 : min1;
    }


    /**、
     *  不同路径
     * @param m 列数
     * @param n 行数
     * @return 不同路径数
     */
    //（妙,动态规划，每个点代表到此点的路线数）
    public static int uniquePaths2(int m, int n) {
      int[][] arr = new int[m][n];
        for (int i = 0; i < arr.length; i++) {
            arr[i][0] = 1;
        }
        for (int j = 0; j < arr[0].length; j++) {
            arr[0][j] = 1;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j < arr[0].length; j++) {
                arr[i][j] = arr[i-1][j] + arr[i][j-1];
            }
        }
        return arr[m-1][n-1];
    }


    //（优雅但超时）
    public static int uniquePaths(int m, int n) {
        int count = 0;
        if (1 == m && 1 == n){
            count++;
        }
     //向右
        if (1+1 < m ){
            count += uniquePaths(m-1,n);
                }
        if (1+1 == m) {
           count++;
        }

     //向下
        if (1+1 < n){
            count += uniquePaths(m,n-1);
        }
        if (1+1 == n) {
            count++;
        }
        return count;
    }




    /**
     * 合并区间
     * @param intervals 二维整数数组
     * @return  合并后的数组
     */
        public static int[][] merge(int[][] intervals) {
            //排序，区间首位进行排序
            Arrays.sort(intervals, (o1, o2) -> {
                if (o1[0] > o2[0]){
                    return 1;
                }else if (o1[0] < o2[0]){
                    return -1;
                }else {
                    return 0;
                }
            });
            List<int[]> list = new ArrayList<>();
            for (int i = 0; i < intervals.length; i++) {
                int[] temp = new int[]{-1,-1};
                int cons = i;
                temp[0] = intervals[cons][0];
                temp[1] = intervals[cons][1];
                while (i+1 < intervals.length && intervals[i][1] >= intervals[i+1][0]){
                    if (intervals[i][1] >= intervals[i+1][1]){
                        while (i+1 < intervals.length && temp[1] >= intervals[i+1][1]){
                            i++;
                            continue;
                        }
                        if (i+1 < intervals.length && temp[1] >= intervals[i+1][0]){
                            temp[1] = intervals[i+1][1];
                            i++;
                        }else {
                            break;
                        }
                    }else {
                        temp[1] = intervals[i+1][1];i++;
                    }
                }
                list.add(temp);
            }
            int[][] res = new int[list.size()][2];
            for (int i = 0; i < list.size(); i++) {
                res[i] = list.get(i);
            }


            return res;
        }

    /**
     * 跳跃游戏
     * @param nums 非负整数数组
     * @return 是否能够到达最后一个位置
     */
    public static boolean canJump2(int[] nums) {
        boolean isContainZero = false;
        List<Integer> zeros = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0){
                isContainZero = true;
                zeros.add(i);
            }
        }
        if (!isContainZero){
            return true;
        }
        boolean canJump = true;
        if (nums[0]==0 && nums.length >1){
            return false;
        }
        for (int i = 0; i < zeros.size(); i++) {
            for (int j = zeros.get(i)-1; j >= 0; j--) {
                if (nums[j]+j > zeros.get(i)){
                    break;
                }
                if (j ==0 && zeros.get(i)!=nums.length-1){
                    canJump = false;
                }
            }
            if (!canJump){
                break;
            }
        }
        return  canJump;
    }
    //(递归超时)
    public static boolean canJump(int[] nums) {
      int jump = nums[0];
        if (jump >= nums.length-1){
         return  true;
        }
        if (jump == 0){
            return  false;
        }
        boolean isTrue =  false;
        for (int i = 1; i <=jump; i++) {
            int[] ints = Arrays.copyOfRange(nums, i, nums.length);
            isTrue = canJump(ints);
            if (isTrue){
                break;
            }
        }
      return isTrue;
    }



    /**
     * 最大子数组和
     * @param nums  整数数组
     * @return 最大子数组和
     */
    public static int maxSubArray2(int[] nums) {
        int max = nums[0] ;
        int pre = 0;
        for (int num:
             nums) {
             pre = Math.max(pre + num, num);
             max = Math.max(pre,max);
        }
return max;
    }

    //(暴力超时 O(n²))
    public static int maxSubArray(int[] nums) {
        int n = nums.length;
        int max = -999999999;
        for (int i = 0; i < n; i++) {
            int temp = 0;
            for (int j = i; j < n; j++) {
                    temp = temp +nums[j];
                if (temp > max){
                    max = temp;
                }
            }

        }
return  max;
    }


    /**
     * 字母异位词分组
     * @param strs 字符串数组
     * @return  字母异位词分组后的数组
     */
    public static List<List<String>> groupAnagrams2(String[] strs) {
        Map<String,List<String>> map = new HashMap<>();
        int n = strs.length;
        for (int i = 0; i < n; i++) {
            String change = change(strs[i]);
            if (map.containsKey(change)){
                map.get(change).add(strs[i]);
            }else {
              List lis = new ArrayList<>();
              lis.add(strs[i]);
                map.put(change,lis);
            }
        }
        return  new ArrayList<>(map.values());
    }
    public  static String change(String str){
        char[] charArray = str.toCharArray();
         Arrays.sort(charArray);
        return  String.valueOf(charArray);
    }


    //暴力超时
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String,List<Character>> map = new HashMap<>();
        int n = strs.length;
            List<Character> ff = new ArrayList<>();
            String str1 = strs[0];
            for (int i = 0; i < str1.length(); i++) {
                ff.add(str1.charAt(i));
            }
            map.put(strs[0],ff);
        for (int i = 1; i < n; i++) {
            int count = 0;
            for ( Map.Entry<String, List<Character>> entry: map.entrySet()) {
                  count++;
                List<Character> w = entry.getValue();
                w.sort((o1, o2) -> {
                    if (o1>o2){
                        return  1;
                    }else if (o1< o2){
                        return -1;
                    }else {
                        return 0;
                    }
                });
                char[] charArray = strs[i].toCharArray();
                Arrays.sort(charArray);
                if (w.size() == charArray.length){
                    boolean flag = true;
                    for (int j = 0; j < w.size(); j++) {
                        if (w.get(j)!=charArray[j]){
                            flag = false;
                            continue;
                        }
                    }
                    if (flag){
                        break;
                    }else if (count == map.entrySet().size()){
                        List<Character> list = new ArrayList<>();
                        for (Character ch:
                             charArray) {
                            list.add(ch);
                        }
                        list.sort((o1, o2) -> {
                            if (o1>o2){
                                return  1;
                            }else if (o1< o2){
                                return -1;
                            }else {
                                return 0;
                            }
                        });
                        map.put(strs[i],list);
                        break;
                    }
                }else if (count == map.entrySet().size() ){
                    List<Character> list = new ArrayList<>();
                    for (Character ch:
                            charArray) {
                        list.add(ch);
                    }
                    list.sort((o1, o2) -> {
                        if (o1>o2){
                            return  1;
                        }else if (o1< o2){
                            return -1;
                        }else {
                            return 0;
                        }
                    });
                        map.put(strs[i],list);
                        break;
                }
            }
        }
        Set<String> strings = map.keySet();
        Map<String,List<String>> map2 = new HashMap<>();
        for (String str:
             strings) {
            map2.put(str,new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            for ( Map.Entry<String, List<Character>> entry: map.entrySet()) {
                List<Character> w = entry.getValue();
                char[] charArray = strs[i].toCharArray();
                Arrays.sort(charArray);
                if (w.size()!=charArray.length){
                    continue;
                }else {
                  boolean flag = true;
                    for (int j = 0; j < w.size(); j++) {
                        if (w.get(j) != charArray[j]) {
                            flag = false;
                            continue;
                        }
                    }
                    if (flag){
                        map2.get(entry.getKey()).add(strs[i]);
                    }else {
                        continue;
                    }
                }
            }
        }
        Set<Map.Entry<String, List<String>>> entries = map2.entrySet();
        List<List<String>> res = new ArrayList<>();
        for ( Map.Entry<String,List<String>> w: entries) {
            res.add(w.getValue());
        }
        return res;
    }



    /**
     *  旋转图像
     * @param matrix 二维数组
     */
    public static void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n/2; i++) {
            for (int j = n-i-1; j > i; j--) {
                int temp ;
                temp = matrix[i][j];
                matrix[i][j] = matrix[n-1-j][i];
                matrix[n-1-j][i] = temp;

                temp = matrix[n-1-i-j+i][i];
                matrix[n-1-j][i] = matrix[n-1-i][n-1-j];
                matrix[n-1-i][n-1-j] = temp;

                temp = matrix[n-1-i][n-1-j];
                matrix[n-1-i][n-1-j] = matrix[j][n-1-i];
                matrix[j][n-1-i] = temp;
               }
            }
        }


    /**
     * 全排列
     * @param nums 无重复数字的数组
     * @return 所有不同的全排列    (按排列公式)
     */
    public static List<List<Integer>> permute(int[] nums) {
       if (nums.length == 0){
           return  new ArrayList<>();
       }

       if (nums.length == 1) {
           return findpermute(nums);
       }
        List<List<Integer>> permute = findpermute(nums);
        permute.remove(0);

        return permute;
    }
    public static List<List<Integer>> findpermute(int[] nums) {
            int n = nums.length;
            List<List<Integer>> res = new ArrayList<>();
            List<Integer> list = new ArrayList<>();
            for (int num: nums) {
                list.add(num);
            }
            res.add(list);
            if (nums.length > 1){
                List<List<Integer>> permute = findpermute(Arrays.copyOfRange(nums, 1, n));
                if (permute.size()!=1){
                    permute.remove(0);
                }
                for (List<Integer> listt: permute) {
                    List<Integer> newlist = new ArrayList<>();
                    newlist.add(nums[0]);
                    newlist.addAll(listt);
                    res.add(newlist);
                }
            }else {
                return res;
            }
            for (int i = 1; i < nums.length; i++) {
                int temp;
                temp = nums[i];
                nums[i]=nums[0];
                nums[0] = temp;
                if (nums.length > 1){
                    List<List<Integer>> permute2 = findpermute(Arrays.copyOfRange(nums, 1, n));
                    if (permute2.size()!=1){
                        permute2.remove(0);
                    }
                    for (List<Integer> listt: permute2) {
                        List<Integer> newlist = new ArrayList<>();
                        newlist.add(nums[0]);
                        newlist.addAll(listt);
                        res.add(newlist);
                    }
                }
                else {
                    return res;
                }
            }
            return res;
        }





    /**
     *接雨水
     * @param height  高度数组
     * @return 能接多少雨水
     */
    public static int trap(int[] height) {
        int  n = height.length;
        int res = 0;
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < n-1; i++) {
            boolean flag = false;
            for (int j = i+1; j < n; j++) {
                if (height[i] <= height[j]){
                    if (height[i] == height[j]){
                        map.put(i,j);
                    }
                    int temp = i+1;
                    while (temp<j){
                        res+=height[i]-height[temp];
                        temp++;
                    }
                    i = temp-1;
                    flag =true;
                    break;
                }
            }
            if (!flag){
                break;
            }
        }

        for (int i = n-1; i >0; i--) {
            boolean flag = false;
            for (int j = i-1; j >=0; j--) {
                if (height[i] <= height[j]){
                    if (height[i] == height[j] && (map.containsKey(j) || map.containsKey(i))){
                      i = j+1;
                      flag =true;
                      break;
                    }
                    int temp = i-1;
                    while (temp>j){
                        res+=height[i]-height[temp];
                        temp--;
                    }
                    i = temp+1;
                    flag =true;
                    break;
                }
            }
            if (!flag){
                break;
            }
        }

        return res;
    }



    /**
     * 组合总和
     * @param candidates 无重复元素的数组
     * @param target 目标值
     * @return 所有和为 target 的组合         (递归超时)
     */
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> state = new ArrayList<>();
        int start = 0;
        FindRes(candidates,target,state,res,start);
        return res;
    }
    static void  FindRes(int[] candidates, int target,List<Integer> state, List<List<Integer>> res,int start){
        if ( target == 0){
            res.add(new ArrayList<>(state));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > target){
                break;
            }
            state.add(candidates[i]);
            FindRes(candidates,target - candidates[i],state,res,i);
            state.remove(state.size()-1);
        }
    }

    /**
     *
     *public static List<List<Integer>> combinationSum(int[] candidates, int target) {
     *         List<List<Integer>> list  = new ArrayList<>();
     *         if (target < 2 ){
     *             return  list;
     *         }
     *         for (int i = 0; i < candidates.length; i++) {
     *             if (target == candidates[i]) {
     *                 List<Integer> temp = new ArrayList<>();
     *                 temp.add(candidates[i]);
     *                     list.add(temp);
     *                 break;
     *             }
     *         }
     *         for (int i = 2; i <=(target/2); i++) {
     *             //左
     *                 List<List<Integer>> list1 = combinationSum(candidates, i);
     *             //右
     *             List<List<Integer>> list2 ;
     *             if (target - i >=2){
     *                  list2 = combinationSum(candidates, target - i);
     *             }else {
     *                  list2 = new ArrayList<>();
     *             }
     *             if (list1.isEmpty() || list2.isEmpty()){
     *                 continue;
     *             }
     *               //合并
     *             for (int j = 0; j < list1.size(); j++) {
     *                 for (int k = 0; k < list2.size(); k++) {
     *                     List<Integer> temp = new ArrayList<>();
     *                     temp.addAll(list1.get(j));
     *                     temp.addAll(list2.get(k));
     *                     temp.sort((o1, o2) -> {
     *                         if (o1 >o2){
     *                             return 1;
     *                         }else if ( o1 < o2){
     *                             return -1;
     *                         }else {
     *                             return  0;
     *                         }
     *                     });
     *                     if (!list.contains(temp)){
     *                         list.add(temp);
     *                     }
     *                 }
     *             }
     *         }
     *         return  list;
     *     }
     */



    /**
     * 在排序数组中查找元素的第一个和最后一个位置
     * @param nums 有序数组
     * @param target 目标值
     * @return 目标值的索引
     */
    public static int[] searchRange(int[] nums, int target) {
        int[] res = new int[]{-1,-1};
        if (nums == null || nums.length ==0){
            return  res;
        }
        if (target > nums[nums.length-1]){
            return res;
        }
       int start = 0;
       int end = nums.length;
       while (start <= end){
           int mid = (start+end)/2;
           if (nums[mid] == target ){
               if (mid-1 < 0 || nums[mid]>nums[mid-1]){
                   res[0] = mid;
               }else {
                   start = 0;
                   end = mid -1;
                   int temp = mid;
                   while (start<=end){
                       temp = (start+end)/2;
                       if (nums[temp] == target && (temp-1 < 0 || nums[temp]>nums[temp-1]) ){
                           res[0] =temp;
                           break;
                       }else {
                           if (nums[temp] == target){
                               end =temp-1;
                           }else {
                               start = temp + 1;
                           }
                       }

                   }
               }
               if (mid+1 > nums.length-1 || nums[mid]<nums[mid+1]){
                   res[1] = mid;
                   break;
               }else {
                   start = mid+1;
                   end = nums.length-1;
                   int temp = mid;
                   while (start<=end){
                       temp = (start+end)/2;
                       if (nums[temp] == target && (temp+1 > nums.length-1 || nums[temp]<nums[temp+1]) ){
                           res[1] =temp;
                           break;
                       }else {
                           if (nums[temp] == target){
                               start =temp+1;
                           }else {
                               end = temp - 1;
                           }

                       }

                   }
                   break;
               }
           }else if (nums[mid] < target){
               start = mid+1;
           }else {
               end = mid-1;
           }
       }
      return res;
    }

    /**
     *  搜索旋转排序数组
     * @param nums 旋转后的数组
     * @param target 目标值
     * @return 目标值的索引
     */
    public static int search(int[] nums, int target) {
        int key = findKey(nums);
        int start;
        int end;
      if (key!=-1){
         if (target > nums[0]){
             start = 1;
             end = key ;
         }else if (target < nums[0]){
             start = key+1;
             end = nums.length-1;
         }else {
             return 0;
         }
     }else {
          start = 0;
          end = nums.length-1;
      }
       while (start<=end){
           int mid = (start + end)/2;
           if (nums[mid] == target){
               return  mid;
           }else if (nums[mid] > target){
               end = mid-1;
           }else {
               start = mid+1;
           }
       }
       return -1;
    }
  public static int findKey(int[] nums) {
      int start = 0;
      int end = nums.length - 1;
      while (start <= end) {
          int mid = (start + end) / 2;
          if (nums[mid] >= nums[0]) {
              if (mid+1<nums.length&&nums[mid] > nums[mid + 1]) {
                 return mid;
              } else {
                  start = mid + 1;
              }
          } else if (nums[mid] < nums[0]) {
              if (mid-1 >0 &&nums[mid] < nums[mid - 1]) {
                  return mid-1;
              } else {
                  end = mid -1;
              }
          }
      }
      return -1;
  }

    /**
     * 最长有效括号
     * @param s 字符串
     * @return 最长有效括号的长度
     */
        public static int longestValidParentheses(String s) {
            int num = s.length();
            int max = 0;
            int temp = 0;
            int count = 0;
            for (int i = 0; i < num-1; i+=(temp==0?1:temp)) {
                temp = 0;
                for (int j = i; j < num; j++) {
                    if (s.charAt(j) == '('){
                        count++;
                    }else {
                          count--;
                          }

                    if (count <0){
                        break;
                    }
                    if (count == 0){
                        temp = j-i+1;
                    }
                }
                count = 0;
                max = temp > max ? temp : max;
            }
            return max;
        }


    /**
     * 下一个排列
     * @param nums 数组
     */
    public static void nextPermutation(int[] nums) {
        boolean flag = false;
        int temp = -1;
        int res =  -1;
        int flace = -1;
        int place = -1;
        for (int i = nums.length-1; i >=0; i--) {
            if (temp <= nums[i]){
                temp = nums[i];
            }else if(temp!= -1 ){
                flag = true;
                 res = nums[i];
                 flace = i;
                break;
            }
        }

        if (!flag){
            SortNumsByIndx(nums,0);
        }else {
            for (int i = nums.length-1; i > 0; i--) {
                if (nums[i]> res){
                    place = i;
                    temp = nums[flace];
                    nums[flace] = nums[place];
                    nums[place] = temp;
                    SortNumsByIndx(nums,flace+1);
                    break;
                }

            }

        }

    }
    //将一个数组的指定索引到数组尾进行升序排序
    public static int[] SortNumsByIndx(int[] nums,int index){
        if (index < 0 || index >=nums.length ){
            return  null;
        }
        int n = nums.length - 1;
        for (int i = index; i < nums.length-1; i++) {
            int temp;
            for (int j = index+1; j <=n; j++) {
                if (nums[j] < nums[j-1]){
                    temp = nums[j];
                    nums[j] = nums[j-1];
                    nums[j-1] = temp;
                }
            }
            n--;
        }
        return  nums;
    }


    /**
     * 合并 K 个升序链表
     * @param lists  链表数组          (和前前题思路一致)
     * @return 合并后的链表
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0){
            return null;
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < lists.length; i++) {
            while (lists[i]!= null){
                list.add(lists[i].val);
                lists[i] = lists[i].next;
            }
        }
        list.sort((o1, o2) -> {
            if (o1 > o2) {
                return 1;
            }else  if (o1 < o2){
                return -1;
            }else return  0;
        });
        ListNode head = new ListNode();
        if (list.size() == 0){
            return null;
        }else {
            head.val =list.get(0);
        }
        ListNode listNode = head;
        for (int i = 1; i < list.size(); i++) {
            listNode.next = new ListNode();
            listNode = listNode.next;
            listNode.val =list.get(i);
        }

        return head;
    }


    /**
     * 括号生成
     * @param n  括号对数
     * @return 所有有效的括号组合
     */
    public static  List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<>();
          if (n ==1){
              list.add("()");
              return list;
          }
        List<String> list2 = generateParenthesis(n - 1);
        int size = list2.size();
        for (int i = 0; i < size; i++) {
            if (!list.contains("()"+list2.get(i))){
                list.add("()"+list2.get(i));
            }
            String temp = "";
            for (int j = 0; j < list2.get(i).length(); j++) {
                if (list2.get(i).charAt(j) == '('){
                    temp +="(";
                }else {
                   String temp2 = temp;
                   temp += ")";
                    temp2+="()";
                    for (int k = j; k < list2.get(i).length(); k++) {
                        temp2+=list2.get(i).charAt(k);
                    }
                    if (!list.contains(temp2)){
                        list.add(temp2);
                    }
                }
            }
        }
        return list;
    }




    /**
     * 合并两个有序链表
     * @param list1  链表1
     * @param list2 链表2
     * @return 合并后的链表
     */
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
      if (list1==null){
          return list2;
      }
      if (list2==null){
          return list1;
      }
      List<Integer> list = new ArrayList<>();
      while (list1!=null){
          list.add(list1.val);
          list1 = list1.next;
      }
        while (list2!=null){
            list.add(list2.val);
            list2 = list2.next;
        }
        list.sort((o1, o2) -> {
            if (o1>o2){
                return 1;
            }else if (o1 < o2){
                return  -1;
            }else {
                return 0;
            }
        });
      int n = list.size();
      ListNode head  = new ListNode();
      ListNode temp  = head;
        for (int i = 0; i < n; i++) {
            temp.val = list.get(0);
            list.remove(0);
            if (i == n-1){
                break;
            }
            temp.next = new ListNode();
            temp = temp.next;
        }


      return  head;
    }

    /**
     *  有效的括号
     * @param s  字符串
     * @return 是否有效
     */
    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        int n = s.length();
        if (n % 2 != 0){
            return  false;
        }
        for (int i = 0; i < n; i++) {
            if (s.charAt(i)=='(' || s.charAt(i)=='{' || s.charAt(i)=='['){
                stack.push(s.charAt(i));
            }else {
                if (stack.isEmpty())
                    return false;
                Character pop = stack.pop();
                if (!( pop == '(' && s.charAt(i)== ')' || pop == '[' && s.charAt(i)== ']' || pop == '{' && s.charAt(i)== '}' )){
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }













  static   class ListNode {
      int val;
     ListNode next;
     ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }

    /**
     * 删除链表的倒数第 N 个结点
     * @param head 头节点
     * @param n 从后往前数第n个
     * @return 删除后的链表
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int temp  = 1;
        ListNode node = head;

        while (node.next!=null){
            temp++;
            node = node.next;
        }
        //正数第几个
        int res = temp - n + 1 ;

        temp = 0;
        node = head;
        for (int i = 1; i < res-1; i++) {
            node = node.next;
        }
        if (head.next == null){
            return  null;
        }
        if (res == 1){
            head = head.next;
        }
        if (node.next.next==null){
            node.next = null;
        }else {
            node.next = node.next.next;
        }
        return head;
    }











    /**
     * 电话号码的字母组合
     * @param digits  一个仅包含数字 2-9 的字符串
     * @return 返回所有它能表示的字母组合
     */
    public static List<String> letterCombinations(String digits) {
        int n = digits.length();
        int out = 0;
        List<Character> charlist = new ArrayList<>();
        List<String> templist = new ArrayList<>();
        if (n == 0){
            return templist;
        }
        for (int i = 0; i < n; i++) {
            int c = Integer.parseInt(String.valueOf(digits.charAt(i)));
          out = c > 7 ? 1 : 0;
            if (c == 9 || c == 7){
                int start = (c-2)*3+97+out;
                charlist.add((char)start);
                charlist.add((char)(start+1));
                charlist.add((char)(start+2));
                charlist.add((char)(start+3));
            }else {
                int start = (c-2)*3+97+out;
                charlist.add((char)start);
                charlist.add((char)(start+1));
                charlist.add((char)(start+2));
            }
        }
        int cur = 0;
        while (cur < n){
            if (templist.isEmpty()){
                templist.add(String.valueOf(charlist.get(0)));
                templist.add(String.valueOf(charlist.get(1)));
                templist.add(String.valueOf(charlist.get(2)));
                if (charlist.get(0)=='w' || charlist.get(0)=='p' ){
                    templist.add(String.valueOf(charlist.get(3)));
                    charlist.remove(0);
                }
                charlist.remove(0);
                charlist.remove(0);
                charlist.remove(0);
                continue;
            }
            int tsize = templist.size();
            for (int i = 0; i < tsize; i++) {
                if (charlist.size() == 0){
                    return templist;
                }
                if ((charlist.get(0)=='w' || charlist.get(0)=='p' )){
                    if (!templist.contains(templist.get(0) + charlist.get(3))){
                        templist.add(templist.get(0) + charlist.get(3));
                    }
                }
                if (!templist.contains(templist.get(0) + charlist.get(0))){
                    templist.add(templist.get(0) + charlist.get(0));
                }
                if (!templist.contains(templist.get(0) + charlist.get(1))){
                    templist.add(templist.get(0) + charlist.get(1));
                }
                if (!templist.contains(templist.get(0) + charlist.get(2))){
                    templist.add(templist.get(0) + charlist.get(2));
                }
                templist.remove(0);
            }
            cur++;
            if ((charlist.get(0)=='w' || charlist.get(0)=='p')){
                charlist.remove(0);
            }
            charlist.remove(0);
            charlist.remove(0);
            charlist.remove(0);
        }
      return templist;
    }



    /**
     * 三数之和
     * @param nums 整数数组
     * @return 三元组序列
     *
     * 如果用三个循环，时间复杂度太高了
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
         if (nums[n-1] <0 || (nums[n-1]==0 && ( nums[n-2] != 0 || nums[n-3] != 0) )){
             return list;
         }
        if (nums[0] >0 || (nums[0]==0 && ( nums[1] != 0 || nums[2] != 0) )){
            return list;
        }
             int i = 0;
            while (nums[i]<0){
                i++;
            }
            if (nums[i] ==0 && nums[i+1] ==0 && nums[i+2] == 0 ){
                List<Integer> list1 = Arrays.asList(0, 0, 0);
                list.add(list1);
            }
        for (int j = 0; j < i; j++) {
            int cur = i;
            int cur2 = n - 1;
            if (j+1 < i && nums[j] == nums[j+1]){
                continue;
            }
            while (cur < cur2){
               if ( nums[j] + nums[cur] + nums[cur2] == 0){
                   List<Integer> res = new ArrayList<>();
                   if (cur - 1 >=i &&nums[cur] == nums[cur-1]){
                       cur2--;
                       cur++;
                       continue;
                   }
                   res.add( nums[j]);
                   res.add(nums[cur]);
                   res.add( nums[cur2]);
                   list.add(res);
                   cur2--;
                   cur++;
               } else if( nums[j] + nums[cur] + nums[cur2] > 0){
                   cur2--;
               }else {
                   cur++;
               }
            }
        }
        for (int j = n-1; j >= i; j--) {
            int cur = 0;
            int cur2 = i - 1;
            if (j-1 >= i && nums[j] == nums[j-1]){
                continue;
            }
            while (cur < cur2){
                if ( nums[j] + nums[cur] + nums[cur2] == 0){
                    List<Integer> res = new ArrayList<>();
                    if (cur - 1 >=0 &&nums[cur] == nums[cur-1]){
                        cur2--;
                        cur++;
                        continue;
                    }
                    res.add( nums[j]);
                    res.add(nums[cur]);
                    res.add( nums[cur2]);
                    list.add(res);
                    cur++;
                    cur2--;
                } else if(nums[j] + nums[cur] + nums[cur2]  < 0){
                    cur++;
                }else {
                    cur2--;
                }
            }
        }
        return list;
    }




    /**
     *  盛最多水的容器
     * @param height  围栏数组
     * @return    所盛的容积 (时间过长，无法通过)
     * public static int maxArea(int[] height) {
     *            int n  = height.length;
     *            int max = 0;
     *         for (int i = 0; i < n; i++) {
     *             for (int j = i+1; j < n; j++) {
     *                 int min = Math.min(height[i],height[j]);
     *                 int res = min * (j - i);
     *                 if (res>max){
     *                     max = res;
     *                 }
     *             }
     *         }
     *         return max;
     *     }
     */


    /**
     * 改进 (盛最多水的容器) 双指针算法
      */
  public static int maxArea(int[] height) {
         int i = 0;
         int j = height.length-1;
         int res = 0;
         while (i < j){
             res = height[i] < height[j] ? Math.max(res,(j-i)*height[i++]) : Math.max(res,(j-i)*height[j--]);
         }
         return res;
          }




    /**
     *  正则表达式匹配
     * @param s 字符串
     * @param p 正则
     * @return 是否匹配
     */
    public static  boolean isMatch(String s, String p) {
        int n = s.length() + 1;
        int m = p.length() + 1;
        //构建状态矩阵
        boolean[][] st = new boolean[n][m];
        //初始化状态矩阵
        st[0][0] = true;
        for (int i = 1; i < m; i++) {
            if (p.charAt(i - 1) == '*' && (st[0][i - 2] )) {
                st[0][i] = true;
            }else {
                st[0][i] = false;
            }
        }
        for (int i = 1; i < n; i++) {
            st[i][0] = false;
        }
        //根据状态转移规则构建
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                // 如果当前位置有 *
                if (p.charAt(j - 1) == '*' && (st[i][j - 2] || st[i - 1][j] && (p.charAt(j - 2) == '.' || s.charAt(i - 1) == p.charAt(j - 2)))) {
                    st[i][j] = true;
                } else if (st[i - 1][j - 1] == true && (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.')) {
                    // 如果当前位置没有 *
                    st[i][j] = true;
                } else {
                    st[i][j] = false;
                }
            }

        }
        return st[n - 1][m - 1];
    }



    /**
     * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。 (暴力但还行 35%)
     * @param s 字符串
     * @param numRows 行数
     * @return  从左往右逐行读取，产生出一个新的字符串
     */
    public static String convert(String s, int numRows) {
        int n = s.length();
        if (numRows == 1){
            return s;
        }
        String res = "";
        boolean flag = false;
        int temp = numRows;
        for (int i = 0; i <numRows; i++) {
            int j = i;
            int isOver = (temp-1)*2;
            while (j < n){
                res += s.charAt(j);
                int temp2 = (numRows-1)*2-isOver;
                if (isOver ==(numRows-1)*2){
                    j += (temp-1)*2;
                }else if (isOver<(numRows-1)*2){
                    j += isOver;
                    isOver = temp2;
                }
            }
            temp -=1;
            if (flag ==true){
                break;
            }
            if (temp == 1){
                temp = numRows;
                //到了最后一行
                flag = true;
            }
        }
        return  res;
    }

    /**
     * 最大回文子串
     * @param s 字符串
     * @return 最大回文子串
     */
    public static String longestPalindrome(String s) {
        int len = s.length();
        //从最大数往最小数进行验证
        for (int i = len; i > 0; i--) {
            //每轮数的滑动次数
            for (int j = 0; j < len-i+1; j++) {
               if (isPalindrome(s.substring(0+j,i+j))){
                   return  s.substring(0+j,i+j);
               }else {
                   continue;
               }
            }
        }
           return null;
    }

    /**
     * 是否为回文字符串
     * @param s 字符串
     * @return  is or not
     */
    public static boolean isPalindrome(String s){
        int len = s.length();
        for (int i = 0; i <=(len/2-1); i++) {
            if (s.charAt(i) == s.charAt(len-i-1)){
                continue;
            }else {
                return false;
            }
        }
        return true;
    }


    /**
     * 寻找两个正序数组的中位数
     * @param nums1 数组1
     * @param nums2 数组2
     * @return 中位数
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        ArrayList<Integer> arr = new ArrayList<>();
        int len1 = nums1.length;
        int len2 = nums2.length;
        int mid = (len1 + len2)%2==1 ? (len1+len2)/2 : -1;
        for (int i = 0; i < len1; i++) {
            arr.add(nums1[i]);
        }
        for (int i = 0; i < len2; i++) {
            arr.add(nums2[i]);
        }
        arr.sort((o1, o2) -> {
            if (o1 > o2){
                return 1;
            }else if (o1<o2) {
                return -1;
            }else {
                return 0;
            }
        });
        Object[] array = arr.toArray();
        if (mid != -1){
            return Double.valueOf((int)array[mid]);
        }else {
            return  ((double)(int)array[(len1+len2)/2]+(double)(int)array[((len1+len2)/2)-1])/2;
        }

    }


    /**
     * 寻找一个字符串的最大相同子串 (暴力解法)
     * @param s 字符串
     * @return 子串
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s.equals("")){
            return 0;
        }
        int length = s.length();
        int count = 0;
        int max = 1;
        Map<String,Integer>  key = new HashMap<>();
        for (int j = 0; j < length; j++) {
            for (int i = j; i < length; i++) {
                if (key.containsKey(String.valueOf(s.charAt(i)))) {
                    if (count > max){
                        max = count;
                    }
                    count = 0;
                    key.clear();
                  break;
                }else {
                    key.put(String.valueOf(s.charAt(i)),1);
                    count +=1;
                }
            }
        }
        System.out.println(max);
        return max;
    }
}