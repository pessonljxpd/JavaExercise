package com.adark0915.file;

import java.io.File;

/**
 * Created by Shelly on 2017-10-18.
 */
public class file {

    private static final String FILE_PATH = "D:\\001\\";

    public static void main(String[] args){
        File file = new File(FILE_PATH);
        if (!file.exists() || file.isFile()){
            boolean mkdirs = file.mkdirs();
            System.out.println("mkdirs = "+mkdirs);
        }

        File file1 = new File(FILE_PATH + "新建文本文档.txt");

        File file2 = new File(FILE_PATH + "124.txt");

        boolean b = file1.renameTo(file2);
        System.out.println("renameTo = "+b);
    }
}
