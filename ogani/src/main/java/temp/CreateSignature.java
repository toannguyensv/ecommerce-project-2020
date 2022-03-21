package temp;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;

public class CreateSignature {

    public static void main(String[] args) {
        try{
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA","SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG","SUN");
            keyGen.initialize(1024,random);
            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();

            Signature dsa = Signature.getInstance("SHA1withDSA","SUN");
            dsa.initSign(priv);
            FileInputStream fis = new FileInputStream("C:\\temp\\1.png");
            BufferedInputStream bufin = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufin.read(buffer)) != -1){
                dsa.update(buffer,0,len);
            };
            bufin.close();
            byte[] realSig = dsa.sign();

            //save signature
            FileOutputStream sigfos = new FileOutputStream("C:\\temp\\signature.txt");
            sigfos.write(realSig);
            sigfos.close();

            //save public key
            byte[] key = pub.getEncoded();
            FileOutputStream keyfos = new FileOutputStream("C:\\temp\\public.txt");
            keyfos.write(key);
            keyfos.close();
        } catch (Exception e){
                System.err.println(e.toString());
        }
    }

}
