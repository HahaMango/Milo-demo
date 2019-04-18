package demo.certificate;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class DerPemLoadImp implements CerLoad{

    private X509Certificate certificate = null;
    private KeyPair keyPair = null;

    private void load(File cerifle,File pkfile){
        try {
            if(cerifle !=null) {
                FileInputStream in = new FileInputStream(cerifle);
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                certificate = (X509Certificate) cf.generateCertificate(in);
            }

            if(pkfile !=null) {
                FileInputStream privatekeyfile = new FileInputStream(pkfile);
                Security.addProvider(new BouncyCastleProvider());
                PEMParser pemParser = new PEMParser(new InputStreamReader(privatekeyfile));
                Object object = pemParser.readObject();
                PEMDecryptorProvider pemDecryptorProvider = new JcePEMDecryptorProviderBuilder().build("opc.ua".toCharArray());
                JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
                keyPair = converter.getKeyPair(((PEMEncryptedKeyPair) object).decryptKeyPair(pemDecryptorProvider));
            }
        } catch (IOException e) {
            System.out.println("证书文件不存在");
        }catch (CertificateException e){
            System.out.println("证书发生异常");
        }
    }

    @Override
    public X509Certificate getCer(File cerfile) {
        if(certificate==null)
            load(cerfile,null);
        return certificate;
    }

    @Override
    public KeyPair getKeyPair(File pkfile) {
        if(keyPair==null)
            load(null,pkfile);
        return keyPair;
    }
}
