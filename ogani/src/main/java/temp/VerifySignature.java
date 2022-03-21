package temp;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

public class VerifySignature {

    public static void main(String[] args) {
        try {
            FileInputStream keyfis = new FileInputStream("C:\\temp\\public.txt");
            byte[] encKey = new byte[keyfis.available()];
            keyfis.read(encKey);
            keyfis.close();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
            KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
            PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);

            FileInputStream sigfis = new FileInputStream("C:\\temp\\signature.txt");
            byte[] sigToVerify = new byte[sigfis.available()];
            sigfis.read(sigToVerify);
            sigfis.close();

            Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
            sig.initVerify(publicKey);
            FileInputStream datafis = new FileInputStream("C:\\temp\\1.png");
            BufferedInputStream bufin = new BufferedInputStream(datafis);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufin.read(buffer)) != -1) {
                sig.update(buffer, 0, len);
            }
            ;

            bufin.close();

            boolean verifies = sig.verify(sigToVerify);
            System.out.println("RESULT " + verifies);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
