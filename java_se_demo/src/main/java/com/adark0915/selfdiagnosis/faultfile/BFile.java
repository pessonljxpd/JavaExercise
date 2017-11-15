package com.adark0915.selfdiagnosis.faultfile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Shelly on 2017-11-11.
 */
public class BFile extends FaultFile {

    public BFile() {
        mData = new ArrayList<>();
    }

    @Override
    public FaultFile parseFaultFile(String pFileName) throws Exception {
        BFileParser mBFileParser = new BFileParser();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(pFileName);
            return parseBFile(mBFileParser, fis);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    public BFile parseBFile(BFileParser pBFileParser, InputStream pStream) throws Exception {
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(pStream, Charset.forName("ASCII"));

            br = new BufferedReader(isr);

            String line = null;
            BFile file = new BFile();
            List<String> desLines = new ArrayList<>();
            boolean dataBegin = false;
            int lineNo = 1;
            try {
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("#")) {
                        desLines.add(line);
                        dataBegin = false;
                    } else {
                        if (!dataBegin) {// 读取完描述信息，遇到第一行数据时
                            dataBegin = true;
                            pBFileParser.doParseDescription(desLines, file);
                            desLines.clear();
                        }
                        pBFileParser.doParseData(line, file);
                    }
                    lineNo++;
                }
                return file;
            } catch (Exception e) {
                String msg = String.format(Locale.ENGLISH, "Parse file error. at line:'%d';content:'%s'.", lineNo, line);
                throw new IOException(msg, e);
            }
        } finally {
            if (isr != null) {
                isr.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }
}
