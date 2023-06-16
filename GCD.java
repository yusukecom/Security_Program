import java.math.BigInteger;

public class GCD {
    public static void main(String[] args) {
        // 入力
        BigInteger a = new BigInteger(args[0]);
        BigInteger b = new BigInteger(args[1]);
        // 定数
        BigInteger zero = BigInteger.ZERO;
        BigInteger one = BigInteger.ONE;
        BigInteger two = BigInteger.valueOf(2);
        // GCDの計算
        // [STEP 1]
        BigInteger g = one;
        if (a.compareTo(b) < 0) {
            BigInteger t = a;
            a = b;
            b = t;
        }
        // [STEP 2]
        for (BigInteger n = two; n.compareTo(b) <= 0; n = n.add(one)) {
            // [STEP 2-1]
            while (a.remainder(n).equals(zero) && b.remainder(n).equals(zero)) {
                // [STEP 2-1-1]
                g = n.multiply(g);
                a = a.divide(n);
                b = b.divide(n);
            }
        }
        // 出力
        System.out.println(g);
    }
}