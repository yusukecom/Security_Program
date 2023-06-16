import java.math.BigInteger;

public class GCDMax {
    public static void main(String[] args) {
        // 入力
        BigInteger a = new BigInteger(args[0]);
        BigInteger b = new BigInteger(args[1]);
        // 出力
        System.out.println(a.gcd(b));
    }
}