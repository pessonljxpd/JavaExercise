package com.adark0915.zip;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Shelly on 2017-10-17.
 */
public class ZipTest {

    public static void main(String[] args){
        File file = new File("D:\\abc");

        try {
            File destFile = new File("D:\\abc\\1231241231.zip");
            ZipUtils.zipFiles(Arrays.asList(file.listFiles()), destFile);
        } catch (IOException pE) {
            pE.printStackTrace();
        }
    }
}
