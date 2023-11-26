import java.util.Scanner;

public class BuleBridgeCup {
    public static void main(String[] args) {

    }



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
