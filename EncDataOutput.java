import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;

public class EncDataOutput {
public static void main(String[] args) throws Exception {
    // 入力（ファイル名）
    String inputFilename = args[0];
    // 出力ファイル名
    String outputFilename = "enc_"+ inputFilename;
    // 入力（共有鍵, 初期値）
    byte[] key_data = {-1,1,0,-1,1,0,-1,1,0,-1,1,0,-1,1,0,-1};
    byte[] iv_data = {-1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
    // AES用の共有鍵及び初期値設定
    Key key = new SecretKeySpec(key_data, "AES");
    IvParameterSpec iv = new IvParameterSpec(iv_data);
    // 平文データ読み込み
    byte[] readBuffer = new byte[1024];
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    FileInputStream fis = new FileInputStream(inputFilename);
    int size;
    while ((size = fis.read(readBuffer)) != -1) {
    baos.write(readBuffer, 0, size);
    }
    fis.close();
    // 暗号化
    byte[] enc = encrypt(baos.toByteArray(), key, iv);
    // 暗号化データのファイル出力
    FileOutputStream output = new FileOutputStream(outputFilename);
    output.write(enc);
    output.flush();
    output.close();
    }

    // 暗号処理
    private static byte[] encrypt(byte[] data, Key key, IvParameterSpec iv) throws Exception {
    // インスタンス生成
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    // 初期化：モード、共有鍵、初期値設定
    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
    // 暗号化
    return cipher.doFinal(data);
    }
}
