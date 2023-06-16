import java.math.BigInteger;

public class GCDCo {
    public static void main(String[] args) {
        // 入力
        BigInteger a_ = new BigInteger(args[0]);
        BigInteger b_ = new BigInteger(args[1]);
        BigInteger a = a_;
        BigInteger b = b_;
        BigInteger g = BigInteger.ONE;
        // BigIntegerメソッドの速度計測
        // 処理前の時刻を取得
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 5000000; i++) {
            a = a_;
            b = b_;
            g = a.gcd(b);
        }
        // 処理後の時刻を取得
        long endTime = System.currentTimeMillis();
        System.out.println("BigIntegerメソッド: gcd=" + g + " 処理時間：" + (endTime - startTime) + " ms");
        // ユークリッド互除法の速度計測
        // 処理前の時刻を取得
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 5000000; i++) {
            a = a_;
            b = b_;
            // [STEP 1]
            for (BigInteger r = a.remainder(b); !r.equals(BigInteger.ZERO); r = a.remainder(b)) {
                // [STEP 1-1]
                a = b;
                b = r;
            }
            // [STEP 2]
            g = b;
        }
        // 処理後の時刻を取得
        endTime = System.currentTimeMillis();
        System.out.println("ユークリッド互除法: gcd=" + g + " 処理時間：" + (endTime - startTime) + " ms");
    }
}