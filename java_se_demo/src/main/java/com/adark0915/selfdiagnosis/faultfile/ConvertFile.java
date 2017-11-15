package com.adark0915.selfdiagnosis.faultfile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Shelly on 2017-11-11.
 */
public class ConvertFile extends FaultFile{
    public ConvertFile() {
        mData = new ArrayList<>();
    }

    @Override
    public FaultFile parseFaultFile(String pFileName) throws Exception {
        ConvertFileParser convertFileParser = new ConvertFileParser();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(pFileName);
            return parseConvertFile(convertFileParser, fis);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    public ConvertFile parseConvertFile(ConvertFileParser pFileParser, InputStream pStream) throws Exception {
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(pStream, Charset.forName("UTF-8"));

            br = new BufferedReader(isr);

            String line = null;
            ConvertFile file = new ConvertFile();
            List<String> desLines = new ArrayList<>();
            boolean dataBegin = false;
            int lineNo = 1;
            try {
                while ((line = br.readLine()) != null) {
                    if (lineNo == 1) {
                        desLines.add(line);
                    } else {
                        if (!dataBegin) {// 读取完描述信息，遇到第一行数据时
                            dataBegin = true;
                            pFileParser.doParseDescription(desLines, file);
                            desLines.clear();
                        }
                        pFileParser.doParseData(line, file);
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
