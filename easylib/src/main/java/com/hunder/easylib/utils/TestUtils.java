package com.hunder.easylib.utils;

import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * Created by hp on 2019/6/12.
 */

public class TestUtils {

    public static void test() {
        try {
            CertificateFactory.getInstance("");
            System.currentTimeMillis();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
    }

}
