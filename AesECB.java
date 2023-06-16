import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AesECB {
public static void main(String[] args) throws Exception {
    // 入力（平文）
    byte[] data = args[0].getBytes();
    // 入力（共有鍵） genKey():乱数よりAES用の鍵を生成する自作の関数
    Key key = genKey();
    // 暗号化
    byte[] enc = encrypt(data, key);
    // 復号
    byte[] dec = decrypt(enc, key);
    // 文字列を連結
    StringBuffer stringBuffer = new StringBuffer();
    for (byte e : enc) {
    // 16進数に変換
    stringBuffer.append(String.format("%02x", e));
    }
    System.out.println("");
    System.out.println("");
    System.out.println("---------------------------------------------------");
    System.out.println("暗号文: "+stringBuffer.toString());
    System.out.println("");
    System.out.println("復号文: "+new String(dec));
    System.out.println("---------------------------------------------------");
    }
    
    private static Key genKey() {
        // SecureRandomを使って16byteの乱数を生成
        byte[] b = new byte[16];
        SecureRandom r = new SecureRandom();
        r.nextBytes(b);
        // 生成された乱数(byte配列)をAES用の鍵空間(object)に生成
        return new SecretKeySpec(b, "AES");
    }

    // 暗号処理
    private static byte[] encrypt(byte[] data, Key key) throws Exception {
        // インスタンス生成
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        // 初期化：モード、共有鍵
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 暗号化
        return cipher.doFinal(data);
    }

    // 復号処理
    private static byte[] decrypt(byte[] data, Key key) throws Exception {
        // インスタンス生成
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        // 初期化：モード、共有鍵、初期値設定
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 復号
        return cipher.doFinal(data);
    }
}