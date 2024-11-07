package ru.example.recordbookbackend;

import CAdES.configuration.Configuration;
import ru.CryptoPro.CAdES.CAdESSignature;
import ru.CryptoPro.JCP.JCP;
import ru.CryptoPro.reprov.RevCheck;

import java.io.File;
import java.io.FileInputStream;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collections;

public class SignatureValidator {
    private static void validate(String[] args) throws Exception {
        Security.addProvider(new JCP());
        Security.addProvider(new RevCheck());

        System.setProperty("com.sun.security.enableCRLDP", "true");
        System.setProperty("com.ibm.security.enableCRLDP", "true");
        System.setProperty("ru.CryptoPro.reprov.enableAIAcaIssuers", "true");
        System.setProperty("com.sun.security.enableAIAcaIssuers", "true");


        File file = new File("hh.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        File signature = new File("hh.txt.p7s");
        FileInputStream signatureInputStream = new FileInputStream(signature);

        File cert = new File("root.cer");
        FileInputStream certInputStream = new FileInputStream(cert);
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        X509Certificate xcert = (X509Certificate) factory.generateCertificate(certInputStream);


        CAdESSignature cadesSignature = new CAdESSignature(signatureInputStream, fileInputStream, null);
        cadesSignature.verify(Collections.singleton(xcert));
        Configuration.printSignatureInfo(cadesSignature);

    }
}