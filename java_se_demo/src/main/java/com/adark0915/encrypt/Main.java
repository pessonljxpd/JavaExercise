package com.adark0915.encrypt;

import java.io.UnsupportedEncodingException;

/**
 * Created by Shelly on 2017-9-12.
 */
public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String urlPath = "http://blog.csdn.net/clj198606061111/article/details/39722781";
        String md5ToString = EncryptUtils.encryptMD5ToString(urlPath);
        System.out.println("encryptMD5ToString: "+md5ToString);
    }
}
