package ru.example.recordbookbackend.service;

import CAdES.configuration.Configuration;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.CryptoPro.CAdES.CAdESSignature;
import ru.CryptoPro.JCP.JCP;
import ru.CryptoPro.reprov.RevCheck;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
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

    public CAdESSignature validate(byte[] file, byte[] signature, InputStream cert) throws Exception {

//        File file = new File("hh.txt");
//        FileInputStream fileInputStream = new FileInputStream(file);
//
//        File signature = new File("hh.txt.p7s");
//        FileInputStream signatureInputStream = new FileInputStream(signature);

//        File cert = new File("root.cer");
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        X509Certificate xcert = (X509Certificate) factory.generateCertificate(cert);


        CAdESSignature cadesSignature = new CAdESSignature(signature, file, null);
        cadesSignature.verify(Collections.singleton(xcert));
        Configuration.printSignatureInfo(cadesSignature);
        return cadesSignature;

    }
}