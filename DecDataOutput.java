import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;

public class DecDataOutput {
public static void main(String[] args) throws Exception {
    // 入力（ファイル名）
    String inputFilename = args[0];
    // 出力ファイル名
    String outputFilename = "dec_"+ inputFilename;
    // 入力（共有鍵, 初期値）
    byte[] key_data = { -1, 1, 0, -1, 1, 0, -1, 1, 0, -1, 1, 0, -1, 1, 0, -1};
    byte[] iv_data = {-1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    // AES用の共有鍵及び初期値設定
    Key key = new SecretKeySpec(key_data, "AES");
    IvParameterSpec iv = new IvParameterSpec(iv_data);
    // 暗号化データ読み込み
    byte[] readBuffer = new byte[1024];
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    FileInputStream fis = new FileInputStream(inputFilename);
    int size;
    while ((size = fis.read(readBuffer)) != -1) {
    baos.write(readBuffer, 0, size);
    }
    fis.close();
    // 復号
    byte[] dec = decrypt(baos.toByteArray(), key, iv);
    // 復号データのファイル出力
    FileOutputStream output = new FileOutputStream(outputFilename);
    output.write(dec);
    output.flush();
    output.close();
    }

    // 復号処理
    private static byte[] decrypt(byte[] data, Key key, IvParameterSpec iv) throws Exception {
    // インスタンス生成
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    // 初期化：モード、共有鍵、初期値設定
    cipher.init(Cipher.DECRYPT_MODE, key, iv);
    // 復号
    return cipher.doFinal(data);
    }
}