
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class RepeatPractice {
    public static void main(String[] args) {
        System.out.println(maxCoins(new int[]{3, 1, 5, 8}));
    }




    class LRUCache {
        int cap;
        public LRUCache(int capacity) {
         cap = capacity;
        }
        LinkedHashMap<Integer,Integer> cache = new LinkedHashMap<>();
        public int get(int key) {
            if (!cache.containsKey(key)) return -1;
            Integer v = cache.remove(key);
            cache.put(key,v);
            return v;
        }
        public void put(int key, int value) {
         if (cache.containsKey(key)){
             Integer v = cache.remove(key);
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
    }

    /**
     * Your LRUCache object will be instantiated and called as such:
     * LRUCache obj = new LRUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */







    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        int sL = s.length();
        int pL = p.length();
        if (sL < pL) return res;
        int[] sA = new int[26];
        int[] pA = new int[26];
        for (int i = 0; i < pL; i++) {
            sA[s.charAt(i) - 'a']++;
            pA[p.charAt(i) - 'a']++;
        }
        if (Arrays.equals(sA,pA)) res.add(0);
        for (int i = pL; i < sL; i++) {
            sA[s.charAt(i - pL) - 'a']--;
            sA[s.charAt(i) - 'a']++;
            if (Arrays.equals(sA,pA)) res.add(i - pL + 1);
        }
        return res;
    }

    public static int maximalRectangle(char[][] matrix) {
        int res = 0;
        if (matrix.length == 0 || matrix[0].length == 0) return res;
        int row = matrix.length;
        int col = matrix[0].length;
        int[] arr = new int[col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == '1'){
                    arr[j] += 1;
                }else {
                    arr[j] = 0;
                }
            }
            int temp =  maximalRectangle2(arr);
            if (temp > res){
                res = temp;
            }
        }
        return res;
    }

    private static int maximalRectangle2(int[] arr) {
        int res = 0;
        if(arr.length == 0) return res;
        if (arr.length == 1) return arr[0];
        int[] newArr = new int[arr.length + 2];
        for (int i = 0; i < arr.length; i++) {
            newArr[i + 1] = arr[i];
        }
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < newArr.length; i++) {
            while (!stack.isEmpty() && newArr[stack.peek()] > newArr[i]){
                int pop = stack.pop();
                res = Math.max(res,(i - stack.peek() - 1 ) * newArr[pop]);
            }
            stack.push(i);
        }
        return res;
    }


    public static boolean wordBreak(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>();
        for (String string : wordDict) {
            set.add(string);
        }
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i < dp.length; i++) {
            for (int j = i - 1; j >= 0 ; j--) {
                dp[i] = dp[j] && check(s.substring(j,i),set);
                if (dp[i]) break;
            }
        }
        return dp[s.length()];
    }

    private static boolean check(String s, Set<String> set) {
        return set.contains(s);
    }

    public static int maxSubArray(int[] nums) {
        int max = nums[0];
        int pre = 0;
        for (int num : nums) {
            pre = Math.max(pre + num,num);
            max = Math.max(max,pre);
        }
        return max;
    }

    public  List<String> removeInvalidParentheses(String s) {
        int left = 0;
        int right = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left ++;
            }else if (s.charAt(i) == ')'){
                if (left > 0){
                    left--;
                }else {
                    right++;
                }
            }
        }
        List<String> list = new ArrayList<>();
        list.add(s);
        if (right == 0 && left == 0) return list;
        Set<String> set = new HashSet<>();
        Dfs(set,s,new StringBuilder(),0,left,right);
        return new ArrayList<>(set);
    }

    private void Dfs(Set<String> set, String s, StringBuilder sb, int i, int left, int right) {
        if (i == s.length()){
            if (left == 0 && right == 0 && isValid(sb.toString())){
                set.add(sb.toString());
            }
            return;
        }
        if (s.charAt(i) == '('){
            sb.append("(");
            Dfs(set,s,sb,i + 1, left,right);
            sb.deleteCharAt(sb.length() - 1);
            if (left > 0){
                Dfs(set,s,sb,i+1,left - 1,right);
            }
        }else if (s.charAt(i) == ')'){
            sb.append(")");
            Dfs(set,s,sb,i + 1, left,right);
            sb.deleteCharAt(sb.length() - 1);
            if (right > 0){
                Dfs(set,s,sb,i+1,left ,right - 1);
            }
        }else {
            sb.append(s.charAt(i));
            Dfs(set,s,sb,i + 1, left,right);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    private boolean isValid(String sb) {
        if (sb.isEmpty()) return true;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '(' ){
                stack.push(sb.charAt(i));
            }else if (sb.charAt(i) == ')'){
                if (!stack.isEmpty()){
                    stack.pop();
                }else return false;
            }
        }
        return stack.isEmpty();
    }

    public static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String,Integer> strToMap = new HashMap<>();
        int idx = 0;
        for (List<String> equation : equations) {
            for (String s : equation) {
                if (!strToMap.containsKey(s)){
                    idx++;
                    strToMap.put(s,idx - 1);
                }
            }
        }
        double[][] paths = new double[idx][idx];
        for (int i = 0; i < idx; i++) {
            paths[i][i] = 1;
        }
        int i = 0;
        for (List<String> equation : equations) {
            paths[strToMap.get(equation.get(0))][strToMap.get(equation.get(1))] = values[i];
            paths[strToMap.get(equation.get(1))][strToMap.get(equation.get(0))] = 1 / values[i];
            i++;
        }
        double[] res = new double[queries.size()];
        int count = 0;
        for (List<String> query : queries) {
            boolean[][] isVisited = new boolean[paths.length][paths.length];
            Integer a = strToMap.get(query.get(0));
            Integer b = strToMap.get(query.get(1));
            if (a == null || b == null){
                res[count] = -1.0;
                count++;
                continue;
            }
            double v =  calRes(a, b,paths,isVisited);
            if (v > 0){
                res[count] = v;
            }else {
                res[count] = -1.0;
            }
            count++;
        }
        return res;
    }
    private static double calRes(Integer a, Integer b, double[][] paths, boolean[][] isVisited) {
        if (paths[a][b] > 0) return paths[a][b];
       double res = 0;
        for (int i = 0; i < paths.length; i++) {
            if (paths[a][i] > 0 && !isVisited[a][i]){
                isVisited[a][i] = true;
                isVisited[i][a] = true;
               res = Math.max(res,paths[a][i] * calRes(i,b,paths,isVisited));
            }
        }
        return res;
    }


    public static String serialize(TreeNode root) {
        if (root == null) return "#";
        return root.val + "," + serialize(root.left) + "," + serialize(root.right);
    }

    public static TreeNode deserialize(String data) {
        String[] split = data.split(",");
        List<String> list = new ArrayList<>();
        for (String s : split) {
            list.add(s);
        }
        return reback(list);
    }

    private static TreeNode reback(List<String> list) {
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

    public static int leastInterval(char[] tasks, int n) {
        int[] nums = new int[26];
        for (char task : tasks) {
            nums[task - 'A'] += 1;
        }
        Arrays.sort(nums);
        int max = nums[25];
        int count = 1;
        for (int i = nums.length - 2; i >= 0 ; i--) {
            if (max == nums[i]){
                count++;
            }else break;
        }
        return Math.max(tasks.length,(max - 1) * (n + 1) + count);
    }

      static class TreeNode {
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
      }
    public static int rob(TreeNode root) {
        if (root == null) return 0;
        int[] res = steal(root);
        return Math.max(res[0],res[1]);
    }

    private static int[] steal(TreeNode root) {
        int[] left = new int[2];
        int[] right = new int[2];
        if (root.left != null) left = steal(root.left);
        if (root.right != null) right = steal(root.right);
        int[] dp = new int[2];
        dp[1] = left[0] + right[0] + root.val;
        dp[0] = Math.max(left[0] + right[0],Math.max(left[1]+right[1],Math.max(left[0]+right[1],left[1] + right[0])));
        return dp;
    }


    public static int minDistance(String word1, String word2) {
        int n = word1.length();
        int n2 = word2.length();
        int[][] dp = new int[n + 1][ n2 + 1];
        for (int i = 0; i < n + 1; i++) {
            dp[i][0] = i;
        }
        for (int i = 0; i < n2 + 1; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n2 + 1; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)){
                    dp[i][j] = dp[i - 1][ j - 1];
                }else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1],dp[i - 1][j]),dp[i][j - 1]) + 1;
                }
            }
        }
        return dp[n][n2];
    }


    public static int findDuplicate(int[] nums) {
        int slow = 0;
        int fast = 0;
        slow = nums[slow];
        fast = nums[nums[fast]];
        while (slow != fast){
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





    public static int maxProfit(int[] prices) {
        int n = prices.length;
        if (n < 2) return 0;
        int[][] dp = new int[n][3];
        dp[0][0] = 0;
        dp[0][1] -= prices[0];
        dp[0][2] = 0;
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0],dp[i - 1][1] + prices[i - 1]);
            dp[i][1] = Math.max(dp[i - 1][1],dp[i - 1][0] - prices[i]);
            dp[i][2] = dp[i - 1][1] + prices[i];
        }
        return Math.max(dp[n-1][0],dp[n-1][2]);
    }



    public static List<List<String>> groupAnagrams(String[] strs) {
        return new ArrayList<>(Arrays.stream(strs).collect(Collectors.groupingBy(
                  str -> {
                      char[] charArray = str.toCharArray();
                      Arrays.sort(charArray);
                      return new String(charArray);
                  }
                )).values());
    }


    public static int maxCoins(int[] nums) {
        int n = nums.length;
        int[] arr = new int[n + 2];
        arr[0] = 1;
        arr[arr.length - 1] = 1;
        for (int i = 0; i < nums.length; i++) {
            arr[i + 1] = nums[i];
        }
        int[][] dp = new int[n + 2][ n + 2];
        for (int len = 3; len <= n + 2; len++) {
            for (int l = 0; l + len - 1 < n + 2 ; l++) {
                int r = l + len - 1;
                for (int k = l + 1; k <= r - 1; k++) {
                    dp[l][r] = Math.max(dp[l][r],dp[l][k] + dp[k][r] + arr[k] * arr[l] * arr[r]);
                }
            }
        }
        return dp[0][n + 1];
    }

    public static int numTrees(int n) {
        if (n == 0) return 0;

        return count(n);
    }

    public static int count(int n){
        if (n == 0) return 1;
        if (n == 1) return 1;
        int total = 0;
        int i = 0;
        while (i < n){
            total += count(i) * count(n - 1 - i);
            i++;
        }
        return total;
    }

    public static int[] productExceptSelf(int[] nums) {
        int k = 1;
        int n = nums.length;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i]  = k;
            k *= nums[i];
        }
        k = 1;
        for (int i = n - 1; i >=0 ; i--) {
            res[i] *= k;
            k *= nums[i];
        }
        return res;
    }

    public static int findKthLargest(int[] nums, int k) {
     List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }
        return largerK(list,k);
    }

    private static int largerK(List<Integer> list, int k) {
        Random random = new Random();
        int cur = list.get(random.nextInt(list.size()-1));
        List<Integer> big = new ArrayList<>();
        List<Integer> small = new ArrayList<>();
        List<Integer> equal = new ArrayList<>();
        for (Integer i : list) {
            if (i == cur) equal.add(i);
            if (i < cur) small.add(i);
            if (i > cur) big.add(i);
        }
        if (k <= big.size()) return largerK(big,k);
        if (k > list.size() - small.size()) return largerK(small,k - list.size() + small.size());
        return cur;
    }


    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<Integer> state =  new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        int start = 0;
        backTrace(candidates,target,start,state,res);
        return res;
    }

     static void backTrace(int[] candidates, int target,int start,List<Integer> state,List<List<Integer>> res){
          if (target == 0){
              res.add(new ArrayList<>(state));
              return;
          }
        for (int i = start; i < candidates.length; i++) {
            start = i;
            if (target - candidates[i] < 0){
                break;
            }
            state.add(candidates[i]);
            backTrace(candidates,target - candidates[i],start,state,res);
            state.remove((Object)candidates[i]);
        }
    }

    public static int singleNumber(int[] nums) {
        int temp = nums[0];
        int n = nums.length;
        for (int i = 1; i < n; i++) {
            temp ^= nums[i];
        }
     return temp;
    }


      static  public class ListNode {
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

        public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            Stack<Integer> stack = new Stack<>();
            while (l1 != null){
                stack.push(l1.val);
                l1 = l1.next;
            }
            StringBuilder temp1 = new StringBuilder();
            while (!stack.isEmpty()){
                temp1.append(stack.pop().toString());
            }
            while (l2 != null){
                stack.push(l2.val);
                l2 = l2.next;
            }
            StringBuilder temp2 = new StringBuilder();
            while (!stack.isEmpty()){
                temp2.append(stack.pop().toString());
            }
            BigInteger i = new BigInteger(temp1.toString());
            BigInteger j = new BigInteger(temp2.toString());
            BigInteger res = i.add(j);
            String temp = String.valueOf(res);
            for (int k = 0; k < temp.length(); k++) {
                stack.push(Integer.valueOf(String.valueOf(temp.charAt(k))));
            }
            ListNode listNode = new ListNode(stack.pop());
            ListNode t = listNode;
            while (!stack.isEmpty()){
                t.next = new ListNode(stack.pop());
                t = t.next;
            }
            return listNode;
        }
    }

