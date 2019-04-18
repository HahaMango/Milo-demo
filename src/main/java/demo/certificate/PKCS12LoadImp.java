package demo.certificate;

import org.eclipse.milo.opcua.stack.core.util.SelfSignedCertificateBuilder;
import org.eclipse.milo.opcua.stack.core.util.SelfSignedCertificateGenerator;

import java.io.*;
import java.security.*;
import java.security.cert.X509Certificate;

public class PKCS12LoadImp implements CerLoad {

    private X509Certificate certificate = null;
    private KeyPair keyPair = null;

    private String CLIENT_ALIAS = "cerTest";
    private char[] PASSWORD = "opc.ua".toCharArray();

    private void load(File cerfile){
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            if (!cerfile.exists()) {
                keyStore.load(null, PASSWORD);

                KeyPair keyPair = SelfSignedCertificateGenerator.generateRsaKeyPair(2048);

                SelfSignedCertificateBuilder builder = new SelfSignedCertificateBuilder(keyPair)
                        .setCommonName("Eclipse Milo Example Client")
                        .setOrganization("digitalpetri")
                        .setOrganizationalUnit("dev")
                        .setLocalityName("Folsom")
                        .setStateName("CA")
                        .setCountryCode("US")
                        .setApplicationUri("urn:eclipse:milo:examples:client")
                        .addDnsName("localhost")
                        .addIpAddress("127.0.0.1");

                X509Certificate certificate = builder.build();

                keyStore.setKeyEntry(CLIENT_ALIAS, keyPair.getPrivate(), PASSWORD, new X509Certificate[]{certificate});
                try (OutputStream out = new FileOutputStream(cerfile)) {
                    keyStore.store(out, PASSWORD);
                }
            } else {
                try (InputStream in = new FileInputStream(cerfile)) {
                    keyStore.load(in, PASSWORD);
                }
            }

            Key serverPrivateKey = keyStore.getKey(CLIENT_ALIAS, PASSWORD);
            if (serverPrivateKey instanceof PrivateKey) {
                certificate = (X509Certificate) keyStore.getCertificate(CLIENT_ALIAS);
                PublicKey serverPublicKey = certificate.getPublicKey();
                keyPair = new KeyPair(serverPublicKey, (PrivateKey) serverPrivateKey);
            }
        }catch (Exception e){
            System.out.println("发生异常");
        }
    }

    @Override
    public X509Certificate getCer(File cerfile) {
        if(certificate==null)
            load(cerfile);
        return certificate;
    }

    @Override
    public KeyPair getKeyPair(File pkfile) {
        if(keyPair !=null)
            load(pkfile);
        return keyPair;
    }
}
