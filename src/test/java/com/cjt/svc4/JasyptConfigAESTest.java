package com.cjt.svc4;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.junit.jupiter.api.Test;

public class JasyptConfigAESTest {
    public String key = "@www7523!@";

    @Test
    void stringEncryptor() {
        String url = null;
        String userName = "milkt_dev_user";
        String password = "QvPb_DL!RSo$(3tu@sbNM";
        String redisPassword = null;

        System.out.println("En_url : " + jasyptEncoding(url));
        System.out.println("En_username : " + jasyptEncoding(userName));
        System.out.println("En_password : " + jasyptEncoding(password));
        System.out.println("En_redisPassword : " + jasyptEncoding(redisPassword));
    }

    @Test
    void stringDecryptor() {
        String url = null;
        String userName = "VmKyy97l03FEH5Q6v83sfwaBQcOR2/QjOGu2SxEULRrIl0hI5geEKTXb+heywIRX";
        String password = "mtYc6kXmKd+lC3sk3eeTpMJST3WZUvIuvnY6nwXDtsCq+7fGXOKNGoED2LBH0wYbE+a0GivbCx5RoUnYeiV6wA==";
        String redisPassword = null;

        System.out.println("De_url : " + jasyptDecoding(url));
        System.out.println("De_username : " + jasyptDecoding(userName));
        System.out.println("De_password : " + jasyptDecoding(password));
        System.out.println("De_redisPassword : " + jasyptDecoding(redisPassword));
    }

    public String jasyptEncoding(String value) {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        pbeEnc.setPassword(key);
        pbeEnc.setIvGenerator(new RandomIvGenerator());
        return pbeEnc.encrypt(value);
    }

    public String jasyptDecoding(String value) {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        pbeEnc.setPassword(key);
        pbeEnc.setIvGenerator(new RandomIvGenerator());
        return pbeEnc.decrypt(value);
    }
}