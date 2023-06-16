import java.math.BigInteger;

public class GCD2 {
    public static void main(String[] args) {
        // 入力
        BigInteger a = new BigInteger(args[0]);
        BigInteger b = new BigInteger(args[1]);
        // [STEP 1]
        for (BigInteger r = a.remainder(b); !r.equals(BigInteger.ZERO); r = a.remainder(b)) {
            // [STEP 1-1]
            a = b;
            b = r;
        }
        // [STEP 2]
        System.out.println(b);
    }
}
