
import java.math.BigInteger;
import java.util.*;

public class RepeatPractice {
    public static void main(String[] args) {

        System.out.println(findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));

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

