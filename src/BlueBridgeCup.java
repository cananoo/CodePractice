import java.util.*;

public class BlueBridgeCup {

    public static void main(String args[]) {
        int[] weight = {1, 4, 3};//物品的重量
        int[] value = {1500,3000,2000};//物品的价值
        int capacity = 4;//背包的容量
        int n = value.length;//物品的个数
        System.out.println(knapsack(n, capacity, weight, value));
        System.out.println(fullKnapsack(n,capacity,weight,value));
    }





    // 0 - 1 背包问题
    public static int knapsack(int weightSize, int bagWeight, int weights[], int values[]) {
        int[] dp = new int[bagWeight + 1];
        for (int i = 0; i < weightSize; i++) {
            for (int j = bagWeight; j >= weights[i] ; j--) {
                dp[j] = Math.max(dp[j],dp[j - weights[i]] + values[i]);
            }
        }
        return dp[bagWeight];
    }
    // 完全背包
    public static int fullKnapsack(int weightSize, int bagWeight, int weights[], int values[]) {
        int[] dp = new int[bagWeight + 1];
        for (int i = 0; i < weightSize; i++) {
            for (int j = weights[i]; j <= bagWeight ; j++) {
                dp[j] = Math.max(dp[j],dp[j - weights[i]] + values[i]);
            }
        }
        return dp[bagWeight];
    }



    //全排列
    //① 无重复项
    private static void permutation(int[] arr, int n) {
     int len = arr.length;
     if (n >= len - 1){
         for (int i : arr) {
             System.out.print(i);
         }
         System.out.println();
     }else {
         for (int i = n; i < arr.length; i++) {
             {int temp = arr[n];arr[n] = arr[i]; arr[i] = temp ;}
             permutation(arr,n + 1);
             {int temp = arr[n];arr[n] = arr[i]; arr[i] = temp ;}
         }
     }
    }


    //② 有重复项 (只增加一个List，防止重复被换)
    private static void permutationWithRepetitiveElement(int[] arr, int n) {
        int num = arr.length;
        List<Integer> list = new ArrayList<>();
        if (n >= num - 1) {
            for (int i : arr) {
                System.out.print(i);
            }
            System.out.println();
        }else {
            for (int i = n; i < arr.length ; i++) {
                if (!list.contains(arr[i])){
                    list.add(arr[i]);
                    {int temp = arr[n]; arr[n] = arr[i];arr[i] = temp;}
                    permutationWithRepetitiveElement(arr,n + 1);
                    {int temp = arr[n]; arr[n] = arr[i];arr[i] = temp;}
                }
            }
        }

    }




    //分治 (汉诺塔操作数)
    public static int hanoiTower(int num){
        if (num == 1) return 1;
        int res = 0;
        // 将上num - 1 从 A移动到C
        res += hanoiTower(num - 1);
        // 将最下面已从A移动到B
        res += hanoiTower(1);
        // 将C的盘子移动到B
        res += hanoiTower(num - 1);

        return res;
    }


    // 二分查找 非递归
    public static int findIdx(int[] arr, int target) {
        int left = 0;
        int right = arr.length -1;

        while(left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] == target) {
                return mid;
            }else if (arr[mid] > target) {
                right = mid - 1;
            }else {
                left = mid + 1;
            }
        }
        return -1;
    }




    /**
     * 七段码
     */
    public static Set<Set<Character>> sevenSegments(Map<Character, List<Character>> map,Character character){
        Set<Set<Character>> sets = new HashSet<>();
        Set<Character> init = new HashSet<>();
        init.add(character);
        sets.add(init);
        List<Character> list = new ArrayList<>();
        for (Character c : map.get(character)) {
            list.add(c);
        }
        // 获取下层序列
        for (Character c : list) {
            Set<Set<Character>>    sets1 = sevenSegments(map, c);
            for (Set<Character> characterSet : sets1) {
                characterSet.add(character);
                sets.add(characterSet);
            }
        }

        return sets;
    }
    /**
     *   public static void main(String[] args) {
     *
     *         Map<Character, List<Character>> map = new HashMap<>();
     *         map.put('a',Arrays.asList('b','f'));
     *         map.put('b',Arrays.asList('c','g'));
     *         map.put('c',Arrays.asList('d','g'));
     *         map.put('d',Arrays.asList('e'));
     *         map.put('e',Arrays.asList('f','g'));
     *         map.put('f',Arrays.asList('g'));
     *         map.put('g',Arrays.asList());
     *
     *         Set<Set<Character>> sets = new HashSet<>();
     *         for (Character c : map.keySet()) {
     *             Set<Set<Character>> sets1 = sevenSegments(map, c);
     *                sets.addAll(sets1);
     *         }
     *
     *         // 同层合并
     *         Character[] chars = new Character[]{'a','b','c','d','e','f','g'};
     *         for (int i = chars.length-1; i >= 0 ; i--) {
     *             List<Character> characters = map.get(chars[i]);
     *              if (characters.size() == 2) {
     *                  Set<Set<Character>> temp1 = new HashSet<>();
     *                  Character ch  = chars[i];
     *                  // 查找包含字符1的所有序列
     *                  for (Set<Character> set : sets) {
     *                      if (set.contains(ch)) {
     *                          Set<Character> newtemp = new HashSet<>(set);
     *                          temp1.add(newtemp);
     *                      }
     *                  }
     *                  Set<Set<Character>> temp2 = new HashSet<>();
     *                  Character ch2  = characters.get(0);
     *                  // 查找包含字符2的所有序列
     *                  for (Set<Character> set : sets) {
     *                      if (set.contains(ch2)) {
     *                          Set<Character> newtemp = new HashSet<>(set);
     *                          temp2.add(newtemp);
     *                      }
     *                  }
     *
     *                  Set<Set<Character>> temp3 = new HashSet<>();
     *                  Character ch3  = characters.get(1);
     *                  // 查找包含字符2的所有序列
     *                  for (Set<Character> set : sets) {
     *                      if (set.contains(ch3)) {
     *                          Set<Character> newtemp = new HashSet<>(set);
     *                          temp3.add(newtemp);
     *                      }
     *                  }
     *
     *                  // 合并子序列
     *                  for (Set<Character> set : temp1) {
     *                      for (Set<Character> characterSet : temp2) {
     *                              Set<Character> newtemp = new HashSet<>(set);
     *                              Set<Character> newtemp2 = new HashSet<>(characterSet);
     *                              newtemp.addAll(newtemp2);
     *                              sets.add(newtemp);
     *                      }
     *                  }
     *                  for (Set<Character> set : temp1) {
     *                      for (Set<Character> characterSet : temp3) {
     *                          Set<Character> newtemp = new HashSet<>(set);
     *                          Set<Character> newtemp3 = new HashSet<>(characterSet);
     *                          newtemp.addAll(newtemp3);
     *                          sets.add(newtemp);
     *                      }
     *                  }
     *              }
     *         }
     *         System.out.println(sets);
     *         System.out.println(sets.size());
     *     }
     */


    /**
 *蛇形填数
 */
public void fillNumbers(){
    int res = 0;
    int last = 0;
    int count =1;
    for (int i = 0; i < 39; i++) {
        if (i == 38) {
            last = (count/2)+1;
            break;
        }
        res += count++;
        System.out.println(res);
    }
    System.out.println(res + last);
}


    /**
     * 门牌制作
     */
    public void doorCard(){
        int count = 0;
        for (int i = 1; i <= 2020; i++) {
            String str = String.valueOf(i);
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(j) == '2') {
                    count++;
                }
            }
        }
        System.out.println(count);
    }


    /**
     * 包子凑数
     */
    public static void bun() {
        boolean[] res = new boolean[10001];
        res[0] = true;
        int[] ai = new int[100];
        Scanner scan = new Scanner(System.in);
        int num = scan.nextInt();
        for (int i = 0; i < num; i++) {
            ai[i] = scan.nextInt();
        }
        // 如果所有的数都互质，说明无法凑出的数是有限的，否则是无限的
        int g = ai[0];
        for (int i = 1; i < ai.length; i++) {
            if (ai[i] == 0) break;
            g = gcd(g,ai[i]);
        }
        if (g != 1){
            System.out.println("INF");
            return;
        }
        // 一维dp寻找不可凑数
        for (int i = 0; i < ai.length; i++) {
               dp(res,ai[i]);
        }
        //统计凑不出数
        int count = 0;
        for (int w = 1; w < res.length; w++) {
            if (res[w] == false) count++;
        }
        System.out.println(count);

        }

        // 一维dp
        public static boolean dp(boolean[] arr,int ai){
              boolean flag = false;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == true && ai + i < arr.length  &&  arr[i + ai] == false){
                    arr[i + ai] = true;
                    flag = true;
                }
            }
            if (flag == false) return flag;
            // 如果没有状态改变 ，结束递归
            return dp(arr, ai);
        }

         // 求gcd
        public static int gcd(int a,int b){
             if (a < b){
                 int temp = a;
                 a = b;
                 b = temp;
             }
            if (a % b == 0) return b;
           return gcd(b,a%b);
        }
}
