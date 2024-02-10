import java.math.BigInteger;
import java.util.Stack;

public class RepeatPractice {
    public static void main(String[] args) {
   // input  l1 : [9]

        ListNode l1 = new ListNode(9);
        // l2 : [1,9,9,9,9,9,9,9,9,9]

        ListNode l2 = new ListNode(1,new ListNode(9,new ListNode(9,new ListNode(9,new ListNode(9,new ListNode(9,new ListNode(9,new ListNode(9,new ListNode(9,new ListNode(9))))))))));

        ListNode res = addTwoNumbers(l1,l2);
        while (res != null){
            System.out.println(res.val);
            res = res.next;
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

