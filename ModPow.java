import java.math.BigInteger;

public class ModPow {
    public static void main(String[] args) {
        // 入力
        BigInteger a = new BigInteger(args[0]);
        BigInteger x = new BigInteger(args[1]);
        BigInteger n = new BigInteger(args[2]);
        // 出力
        System.out.println(a.modPow(x, n));
    }
}