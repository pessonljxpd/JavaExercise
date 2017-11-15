package com.adark0915.selfdiagnosis;

import com.adark0915.selfdiagnosis.faultfile.BFile;
import com.adark0915.selfdiagnosis.faultfile.ConvertFile;
import com.adark0915.selfdiagnosis.faultfile.EBFile;
import com.adark0915.selfdiagnosis.faultfile.FaultFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shelly on 2017-11-11.
 */
public class TestFaultFileParser {

    public void testEBParser() {
        try {
            EBFile ebFile = EBFile.parseEbFile("Eb130608_1759_433.txt");
            Date createDate = ebFile.getCreateDate();
            String description = ebFile.getDescription();
            long errorNo = ebFile.getErrorNo();
            System.out.println("Description: " + description + "\r\n" + "ErrorNo: " + errorNo + "\r\n" + "Create Date: " + createDate);
            EBFile.Variable[] variables = ebFile.getVariables();
            for (int i = 0; i < variables.length; i++) {
                System.out.println(variables[i].toString());
            }
            for (String[] values : ebFile.getData()) {
                Map<String, String> kvs = new HashMap<>();

                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    String key = ebFile.getVariables()[i].getName();
                    kvs.put(key, value);
                }

                System.out.println(kvs.toString());
            }
        } catch (Exception pE) {
            pE.printStackTrace();
        }
    }

    public void testBFileParser() {
        BFile bFile = new BFile();
        try {
            bFile = (BFile) bFile.parseFaultFile("b171019_1559_1181_bak.txt");
            String description = bFile.getDescription();
//            System.out.println("Description: " + description);
            FaultFile.Variable[] variables = bFile.getVariables();
            System.out.println(variables.length);
            for (int i = 0; i < variables.length; i++) {
//                System.out.println(variables[i].toString());
            }
            for (int i = 0; i < bFile.getData().size(); i++) {
                String[] values = bFile.getData().get(i);
                Map<String, String> kvs = new HashMap<>();

                for (int j = 0; j < values.length; j++) {
                    String value = values[j];
                    String key = bFile.getVariables()[j].getName();
                    kvs.put(key, value);
                }
                System.out.println(kvs.toString());
            }
        } catch (Exception pE) {
            pE.printStackTrace();
        }
    }

    public void testConvertFileParser() {
        ConvertFile convertFile = new ConvertFile();
        try {
            convertFile = (ConvertFile) convertFile.parseFaultFile("1U1FaultData.txt");
            FaultFile.Variable[] variables = convertFile.getVariables();
            System.out.println("variables.length = " + variables.length);
            for (int i = 0; i < variables.length; i++) {
                System.out.println(variables[i]);
            }
            for (int i = convertFile.getData().size() - 1; i >= 0; i--) {
                String[] values = convertFile.getData().get(i);
                Map<String, String> kvs = new HashMap<>();

                for (int i1 = 0; i1 < values.length; i1++) {
                    String value = values[i1];
                    String key = convertFile.getVariables()[i1].getName();
                    kvs.put(key, value);
                }
                System.out.println(kvs.toString());
            }
        } catch (Exception pE) {
            pE.printStackTrace();
        }
    }

}
