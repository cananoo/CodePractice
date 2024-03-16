package Interview;

import java.math.BigDecimal;

import java.util.Scanner;
    public class MainMT2 {
        public static void main(String[] args) {
            Scanner scan = new Scanner(System.in);
            int n = scan.nextInt();
            long q = scan.nextInt();
            BigDecimal[] arr = new BigDecimal[n];
            for (int i = 0; i < n; i++) {
                BigDecimal bigDecimal = new BigDecimal(scan.nextInt());
                arr[i] = bigDecimal;
            }
            double count = Math.pow(2, (int) q);
            for (int i = 0; i < q; i++) {
                int idx = scan.nextInt() - 1;
                arr[idx] =  arr[idx].divide(new BigDecimal(2));
            }
            BigDecimal res =  new BigDecimal(0);
            for (int i = 0; i < n; i++) {
               res = res.add(arr[i]);
            }
            BigDecimal m = new BigDecimal(Math.pow(10,9) + 7);
            BigDecimal multiply = res.multiply(new BigDecimal(count));
            BigDecimal[] bigDecimals = multiply.divideAndRemainder(m);
            System.out.println((bigDecimals[1].longValue()));
        }
    }

/*
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        long n = scan.nextInt();
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += scan.nextInt();
        }
        long minus = scan.nextInt() + scan.nextInt();
        if (minus < sum){
            System.out.println(sum - minus);
        }
    }
}*/


/*
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String s = scan.nextLine();
        int up = 0;
        int down = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) > 'Z'){
                down++;
            }else {
                up++;
            }
        }
        int res = Integer.MAX_VALUE;
        if (s.charAt(0) > 'Z'){
            // 首字母小写
            // ①第一种
            res = Math.min(res,up);
            // ②
            res = Math.min(res,down + 1);
            // ③
            res = Math.min(res,up + 1);

        }else {
            // 首字母大写
            // ①第一种
            res = Math.min(res,up + 1);
            // ②
            res = Math.min(res,down);
            // ③
            res = Math.min(res,up);
        }
        System.out.println(res);
    }
}*/


/*
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        long q = scan.nextInt();
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scan.nextInt();
        }
        for (int i = 0; i < q; i++) {
            int idx = scan.nextInt() - 1;
            for (int j = 0; j < n; j++) {
                if (j != idx){
                    arr[j] *= 2;
                }
            }
        }
        long res = 0;
        for (int i = 0; i < n; i++) {
            res += arr[i];
        }
        System.out.println((long)(res % (Math.pow(10,9) + 7)));
    }
}*/


/*
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] ints = new int[n];
        for (int i = 0; i < n; i++) {
            ints[i] = scan.nextInt();
        }
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int[] subArr = Arrays.copyOfRange(ints,i,j + 1);
                Map<Integer,Integer> map = new HashMap<>();
                for (int k = 0; k < subArr.length; k++) {
                    if (!map.containsKey(subArr[k])){
                        map.put(subArr[k],1);
                    }else {
                        map.put(subArr[k],map.get(subArr[k]) + 1);
                    }
                }
                int max = 0;
                int key = Integer.MAX_VALUE;
                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    Integer temp = entry.getValue();
                    Integer tempKey = entry.getKey();
                    if (temp > max){
                        max = temp;
                        key = tempKey;
                    }else if (temp == max && key !=Integer.MAX_VALUE && key >tempKey){
                        key = tempKey;
                    }
                }
                sum += key;
            }
        }
        System.out.println(sum);
    }
}*/


/*
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scan.nextInt();
        }
        for (int i = 0; i < n; i++) {
            int[] ints = Arrays.copyOf(arr, arr.length);
            ints[i] -= 2 * ints[i];
            if (i == n - 1){
                System.out.print(findRes(ints));
            }else System.out.print(findRes(ints) + " ");
        }
    }

    private static int findRes(int[] ints) {
        int res = 0;
        for (int i = 0; i < ints.length; i++) {
            for (int j = i + 1; j < ints.length; j++) {
                if (ints[i] > ints[j]) res++;
            }
        }
        return res;
    }
}*/
