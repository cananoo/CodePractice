import java.util.Arrays;

public class RandomPractice {
    public static void main(String[] args) {
        String maximumBinaryString = maximumBinaryString("01111001100000110010");
        System.out.println(maximumBinaryString);
    }


    public static String maximumBinaryString(String binary) {
        int k = binary.indexOf('0');
        if (k == -1) return binary;
        int count = 0;
        for (int i = k + 1; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') count++;
        }
        k = binary.length() - count - 1;
        char[] charArray = binary.toCharArray();
        Arrays.fill(charArray,'1');
        charArray[k] = '0';
        return String.valueOf(charArray);
    }


/*    public static String maximumBinaryString(String binary) {
        String binaryString = Integer.toBinaryString(findSolution(binary, getInt(binary)));;
        if (binaryString.length() < binary.length()) {
            String res = "";
            for (int i = 0; i < binary.length() - binaryString.length(); i++) {
                res = "0" + binaryString;
            }
            binaryString = res;
        }
        return binaryString;
    }

    public static int getInt(String binary) {
        int res = 0;
        for (int i = binary.length() - 1; i >= 0; i--) {
            if (binary.charAt(i) == '1') {
                res += Math.pow(2, binary.length() - i - 1);
            }
        }
        return res;
    }

    private static int findSolution(String binary, int max) {
        for (int i = 0; i < binary.length() - 1; i++) {
            if (binary.charAt(i) == '0' && binary.charAt(i + 1) == '0') {
                String newString = binary.substring(0, i) + "1" + binary.substring(i+1,binary.length());
                int temp = getInt(newString);
                if (temp > max) {
                    max = temp;
                }
                max = findSolution(newString, max);
            }
            if (binary.charAt(i) == '1' && binary.charAt(i + 1) == '0') {
                String newString  = "";
                if (i + 2 < binary.length()) {
                    newString = binary.substring(0, i) + "0" + "1" + binary.substring(i+2,binary.length());
                }else {
                    newString = binary.substring(0, i) + "0" + "1" ;
                }
                int temp = getInt(newString);
                if (temp > max) {
                    max = temp;
                }
                max = findSolution(newString, max);
            }
        }

        return max;
    }*/
}
