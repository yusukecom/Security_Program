import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class HibridEnc {
    public static void main(String[] args) throws Exception {

        // 公開鍵
        String PUBLIC_KEY_TEXT = "30 81 9F 30 0D 06 09 2A 86 48 86 F7 0D 01 01 01 05 00 "
                + "03 81 8D 00 30 81 89 02 81 81 00 C8 06 F9 09 AD AB 7C "
                + "AE 9B F0 C4 7B 65 AF 0D 2D AC B3 B3 3B C7 21 EA 3D CE "
                + "AD 53 DA 5A CC 28 50 5B A8 C6 F4 4E 05 2D 35 AB D9 C0 "
                + "BF AC 82 CE BB 5B 27 3A 60 92 D8 96 FF B1 C8 C4 6C 95 "
                + "00 2C B2 C7 31 B2 19 CF 18 58 CE 09 89 C1 04 06 C2 2F "
                + "B1 D7 54 83 3A AC A5 7E 22 CD 65 B1 B6 D5 C4 71 44 31 "
                + "55 94 D5 BD DC 74 92 D1 F2 75 CB 0B 1D 74 B8 B3 1E A5 "
                + "7F 87 B4 F5 98 15 1A C1 7B 38 25 16 05 02 03 01 00 01";

        // AES用初期値
        byte[] iv_data = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
        IvParameterSpec iv = new IvParameterSpec(iv_data);

        // 入力（ファイル名）
        String inputFilename = args[0];
        // 平文データ読み込み
        byte[] readBuffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis = new FileInputStream(inputFilename);
        int size;
        while ((size = fis.read(readBuffer)) != -1) {
            baos.write(readBuffer, 0, size);
        }
        fis.close();

        // SecureRandomを使って16byteの乱数(AES用の共有鍵)を生成
        byte[] b = new byte[16];
        SecureRandom r = new SecureRandom();
        r.nextBytes(b);
        Key key = new SecretKeySpec(b, "AES");

        // AESにてデータの暗号化
        byte[] enc_data = encrypt(baos.toByteArray(), key, iv);
        // AES暗号化データ(本文)のファイル出力
        FileOutputStream output = new FileOutputStream("enc1_" + inputFilename);
        output.write(enc_data);
        output.flush();
        output.close();

        // 共有鍵(b:バイト配列)をRSAにて暗号化
        byte[] enc_key = ENCRYPT(b, PUBLIC_KEY_TEXT);
        // RSA暗号化データ(共有鍵)のファイル出力
        FileOutputStream output2 = new FileOutputStream("enc2_" + inputFilename);
        output2.write(enc_key);
        output2.flush();
        output2.close();
    }

    // AES暗号処理
    private static byte[] encrypt(byte[] data, Key key, IvParameterSpec iv) throws Exception {
        // インスタンス生成
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 初期化：モード、共有鍵、初期値設定
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        // 暗号化
        return cipher.doFinal(data);
    }

    // RSA暗号化関数
    private static byte[] ENCRYPT(byte[] data, String PUBLIC_KEY_TEXT) throws Exception {
        // 公開鍵データをbyte配列に変換
        byte[] keyData = hexToByte(PUBLIC_KEY_TEXT);
        // 鍵空間に設定
        KeySpec keyspec = new X509EncodedKeySpec(keyData);
        // RSA用の鍵として設定
        KeyFactory keyfactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyfactory.generatePublic(keyspec);
        // RSA OAEPに設定
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        // 初期化設定（モード、公開鍵設定）
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 暗号化処理
        return cipher.doFinal(data);
    }

    // 16進Stringデータをbyte配列に変換する関数
    private static byte[] hexToByte(String hexString) {
        String[] hexArray = hexString.split(" ");
        byte[] bytes = new byte[hexArray.length];
        for (int i = 0; i < hexArray.length; i++) {
            String str = hexArray[i];
            bytes[i] = (byte) Integer.parseInt(str, 16);
        }
        return bytes;
    }
}