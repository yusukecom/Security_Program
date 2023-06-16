import java.math.BigInteger;

public class CRT{
    public static void main(String[] args) {
        // 入力
        BigInteger a = new BigInteger(args[0]);
        BigInteger p = new BigInteger(args[1]);
        BigInteger b = new BigInteger(args[2]);
        BigInteger q = new BigInteger(args[3]);

        //modinverse
        BigInteger m = q.modInverse(p);

        //modsub
        BigInteger c = a.subtract(b).mod(p);

        //modmul
        BigInteger d = m.multiply(c).mod(p);

        //BigIntegerMul
        BigInteger e = d.multiply(q);

        // BigIntegerAdd
        BigInteger x = e.add(b);

        System.out.println("x = "+ x);

    }
}
