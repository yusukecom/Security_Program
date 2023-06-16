import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
import java.io.FileOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class HibridDec {
    public static void main(String[] args) throws Exception {

        // 秘密鍵
        String PRIVATE_KEY_TEXT = "30 82 02 78 02 01 00 30 0D 06 09 2A 86 48 86 F7 0D 01 "
                + "01 01 05 00 04 82 02 62 30 82 02 5E 02 01 00 02 81 81 "
                + "00 C8 06 F9 09 AD AB 7C AE 9B F0 C4 7B 65 AF 0D 2D AC "
                + "B3 B3 3B C7 21 EA 3D CE AD 53 DA 5A CC 28 50 5B A8 C6 "
                + "F4 4E 05 2D 35 AB D9 C0 BF AC 82 CE BB 5B 27 3A 60 92 "
                + "D8 96 FF B1 C8 C4 6C 95 00 2C B2 C7 31 B2 19 CF 18 58 "
                + "CE 09 89 C1 04 06 C2 2F B1 D7 54 83 3A AC A5 7E 22 CD "
                + "65 B1 B6 D5 C4 71 44 31 55 94 D5 BD DC 74 92 D1 F2 75 "
                + "CB 0B 1D 74 B8 B3 1E A5 7F 87 B4 F5 98 15 1A C1 7B 38 "
                + "25 16 05 02 03 01 00 01 02 81 80 49 7B A6 49 93 EF 87 "
                + "E2 6D 8F 49 DF 3B 3F CF CB 93 C2 80 79 D9 71 F0 27 BC "
                + "A1 98 48 83 4A B5 14 B0 57 94 9F 73 7A 1B 5D B8 40 4A "
                + "BB 1F 98 FE 71 7D CF 2F 77 02 FB 66 9D 90 A6 7C E2 96 "
                + "EA 75 80 A0 E8 FA 4C BA 60 DB 6C A8 DC B9 5A B6 B3 A8 "
                + "1E 5C 5A E8 AD 50 3D 92 26 D8 32 6F 98 44 64 94 5A 1F "
                + "B3 28 3D 8C 97 05 43 07 80 45 A7 1F 99 C0 75 21 40 1E "
                + "27 DE 01 E2 02 DC E2 D0 33 12 11 51 11 02 41 00 FA 48 "
                + "7E 36 E8 F9 6C 95 2D 57 2C 70 DC 4B 56 10 50 EF A9 85 "
                + "A5 1C 4F 44 94 4C 9D F5 D9 A2 C4 51 5F 54 D7 13 D3 A0 "
                + "10 41 2B 74 13 5C C8 44 FF BA 94 13 A7 63 50 96 97 68 "
                + "FB 22 DE EF 2F D4 23 93 02 41 00 CC 98 9C EC 14 1F A0 "
                + "69 FB 46 14 38 25 97 24 F9 E6 C2 06 EA 91 44 2B B7 5D "
                + "CA 18 F4 DF C0 F3 2E BF 51 57 09 05 06 66 2B 26 9B 81 "
                + "B2 B9 6A 57 51 35 E9 70 F2 5C B2 C3 04 F6 C6 CA 9F BE "
                + "43 8F 07 02 41 00 DC CD 01 94 3D BA 76 21 B1 2B 5B C8 "
                + "81 80 70 FF D9 F7 65 2D C3 39 13 71 64 07 A7 BF 51 EE "
                + "37 95 B6 2D A9 C5 13 08 FD EE 10 80 C9 E8 2B C2 3B 7D "
                + "85 CF 44 F7 E8 0B C2 AD DA 08 AC 76 85 52 78 C3 02 41 "
                + "00 C5 80 D9 93 28 45 F3 93 FB 86 06 04 C1 7C EB AB F6 "
                + "2F FD 7F 38 E6 47 11 47 0F CE 11 AD 62 55 1F 1E 7F 05 "
                + "F6 E6 0B EC 5A E1 75 22 BA 06 35 7A BF 21 BD 0D 54 59 "
                + "5A 13 DA D9 E2 C3 3D 7B ED 39 C7 02 41 00 F1 12 D6 B7 "
                + "8B C7 E7 AE 08 F3 EA 12 84 DB E3 62 FE 41 8B B3 F5 CE "
                + "71 ED 47 D9 57 DF 93 ED D5 7C 72 FB 75 8D DC BB 34 1F "
                + "9B 78 DC 8D 1B 5C 6D FD 37 F7 4E 95 2B D4 B0 28 1A 29 " + "EC EC 9C 0C 53 45";

        // AES用初期値
        byte[] iv_data = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
        IvParameterSpec iv = new IvParameterSpec(iv_data);

        // 入力（ファイル名）
        String inputFilename1 = args[0];
        String inputFilename2 = args[1];
        // 暗号化データ(本文)読み込み
        byte[] readBuffer1 = new byte[1024];
        ByteArrayOutputStream enc_data = new ByteArrayOutputStream();
        FileInputStream fis1 = new FileInputStream(inputFilename1);
        int size1;
        while ((size1 = fis1.read(readBuffer1)) != -1) {
            enc_data.write(readBuffer1, 0, size1);
        }
        fis1.close();
        // 暗号化データ(共有鍵)読み込み
        byte[] readBuffer2 = new byte[1024];
        ByteArrayOutputStream enc_key = new ByteArrayOutputStream();
        FileInputStream fis2 = new FileInputStream(inputFilename2);
        int size2;
        while ((size2 = fis2.read(readBuffer2)) != -1) {
            enc_key.write(readBuffer2, 0, size2);
        }
        fis2.close();

        // 共有鍵をRSAにて復号
        byte[] dec_key = DECRYPT(enc_key.toByteArray(), PRIVATE_KEY_TEXT);
        Key key = new SecretKeySpec(dec_key, "AES");

        // AESにて暗号化データ(本文)の復号
        byte[] data = decrypt(enc_data.toByteArray(), key, iv);
        // 復号データのファイル出力
        FileOutputStream output = new FileOutputStream("dec_" + inputFilename1);
        output.write(data);
        output.flush();
        output.close();
    }

    // AES復号処理
    private static byte[] decrypt(byte[] data, Key key, IvParameterSpec iv) throws Exception {
        // インスタンス生成
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 初期化：モード、共有鍵、初期値設定
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        // 復号
        return cipher.doFinal(data);
    }

    // RSA暗号復号関数
    public static byte[] DECRYPT(byte[] data, String PRIVATE_KEY_TEXT) throws Exception {
        // 秘密鍵データをbyte配列に変換
        byte[] keyData = hexToByte(PRIVATE_KEY_TEXT);
        // 鍵空間に設定
        KeySpec keyspec = new PKCS8EncodedKeySpec(keyData);
        // RSA用の鍵として設定
        KeyFactory keyfactory = KeyFactory.getInstance("RSA");
        Key privateKey = keyfactory.generatePrivate(keyspec);
        // RSA OAEPに設定
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        // 初期化設定（モード、秘密鍵設定）
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
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
