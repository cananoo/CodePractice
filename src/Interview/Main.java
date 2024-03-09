package Interview;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int q = in.nextInt();
        int[][] arr = new int[n][n];
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            arr[a - 1][b - 1] = 1;
            arr[b - 1][a - 1] = 1;
        }
        for (int i = 0; i < q; i++) {
            int op = in.nextInt();
            int a = in.nextInt();
            int b = in.nextInt();
            if (op == 1){
                arr[a - 1][b - 1] = 0;
                arr[b - 1][a - 1] = 0;
            }else if (op == 2){
                if (findPath(arr,a,b)){
                    System.out.println("Yes");
                }else {
                    System.out.println("No");
                }
            }
        }
    }

    private static boolean findPath(int[][] arr, int a, int b) {
        if (arr[a - 1][b - 1] == 1) return true;
        for (int i = a; i < arr.length; i++) {
            if (arr[a - 1][i] == 1 && findPath(arr,i+1,b)) return true;
        }
        return false;
    }
}

/*
Scanner in  = new Scanner(System.in);
int n = in.nextInt();
int q = in.nextInt();
        in.nextLine();
int zero = 0;
int sum = 0;
        for (int i = 0; i < n; i++) {
int record = in.nextInt();
            if (record != 0){
sum += record;
            }else {
zero++;
        }
        }
        for (int i = 0; i < q; i++) {
        in.nextLine();
int a = in.nextInt();
int b = in.nextInt();
            System.out.print(sum + (zero * a) + " ");
        System.out.println(sum + (zero * b) );
        }*/


/*
public class Main {
    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        int n = in.nextInt();
        char[][] arr = new char[n][n];
        in.nextLine();
        for (int i = 0; i < n; i++) {
            String s = in.nextLine();
            for (int j = 0; j < n; j++) {
                arr[i][j] = s.charAt(j);
            }
        }
        System.out.println(0);
        for (int i = 2; i < n + 1; i++) {
            int res = 0;
            int one = 0;
            int zero = 0;
            int recur = n - i + 1;
            for (int j = 0; j < recur; j++) {
                for (int k = 0; k < recur; k++) {
                    for (int l = 0; l < i; l++) {
                        for (int m = 0; m < i; m++) {
                            if (arr[j + l][k + m] == '1') {
                                one++;
                            }else zero++;
                        }
                    }
                    if (zero == one) res++;
                    one = 0;
                    zero = 0;
                }
            }
            System.out.println(res);
            res = 0;
        }
    }
}*/

/*
public class Main {
    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
        }
        int temp = 1;
        for (int i : arr) {
            temp *= i;
        }
        int res = 0;
        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < n - i + 1; j++) {
                int[] ints = Arrays.copyOfRange(arr, j, j + i);
                int tempNum = temp;
                for (int anInt : ints) {
                    tempNum /= anInt;
                }
                int zero = 0;
                int devide = 10;
                while (tempNum % devide == 0){
                    zero++;
                    devide *= 10;
                }
                if (zero == k) res++;
            }
        }
        System.out.println(res);
    }
}*/
