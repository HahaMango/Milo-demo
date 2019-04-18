package demo.certificate;

import java.io.File;
import java.security.KeyPair;
import java.security.cert.X509Certificate;

public interface CerLoad {
    public X509Certificate getCer(File cerfile);

    public KeyPair getKeyPair(File pkfile);
}
