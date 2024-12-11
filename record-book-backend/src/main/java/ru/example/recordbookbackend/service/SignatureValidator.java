package ru.example.recordbookbackend.service;

import CAdES.configuration.Configuration;
import jakarta.annotation.PostConstruct;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.util.Selector;
import org.springframework.stereotype.Service;
import ru.CryptoPro.CAdES.CAdESSignature;
import ru.CryptoPro.JCP.JCP;
import ru.CryptoPro.reprov.RevCheck;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
public class SignatureValidator {

    @PostConstruct
    public void init() {
        Security.addProvider(new JCP());
        Security.addProvider(new RevCheck());

        System.setProperty("com.sun.security.enableCRLDP", "true");
        System.setProperty("com.ibm.security.enableCRLDP", "true");
        System.setProperty("ru.CryptoPro.reprov.enableAIAcaIssuers", "true");
        System.setProperty("com.sun.security.enableAIAcaIssuers", "true");
    }

    public CAdESSignature validate(byte[] file, byte[] signature) throws Exception {

//        File file = new File("hh.txt");
//        FileInputStream fileInputStream = new FileInputStream(file);
//
//        File signature = new File("hh.txt.p7s");
//        FileInputStream signatureInputStream = new FileInputStream(signature);

//        File cert = new File("root.cer");


        CAdESSignature cadesSignature = new CAdESSignature(signature, file, null);
        Collection<X509CertificateHolder> allCerts = cadesSignature.getCertificateStore().getMatches(new AllCertificatesSelector());
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        ArrayList<X509Certificate> result = new ArrayList<>();
        for (X509CertificateHolder c : allCerts) {
            result.add((X509Certificate) factory.generateCertificate(new ByteArrayInputStream(c.getEncoded())));
        }

        cadesSignature.verify(result);
        Configuration.printSignatureInfo(cadesSignature);
        return cadesSignature;

    }

    public static class AllCertificatesSelector implements Selector {
        public AllCertificatesSelector() {
        }

        public boolean match(Object var1) {
            return true;
        }

        public Object clone() {
            return this;
        }
    }
}