import java.math.BigInteger;

public class BigIntegerMul {
    public static void main(String[] args) {
        // 入力
        BigInteger a = new BigInteger(args[0]);
        BigInteger b = new BigInteger(args[1]);
        // 掛け算
        BigInteger c = a.add(b);
        BigInteger d = a.subtract(b);
        BigInteger e = a.multiply(b);
        BigInteger f = a.divide(b);
        
        /*
         * 出力 BigIngegerでは大きな値を扱える
         */
        System.out.println(c);
        System.out.println(d);
        System.out.println(e);
        System.out.println(f);
        
    }
}