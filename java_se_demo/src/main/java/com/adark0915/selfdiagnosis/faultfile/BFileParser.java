package com.adark0915.selfdiagnosis.faultfile;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Shelly on 2017-11-11.
 */
public class BFileParser{
    private static final Pattern SPLIT_PATTERN = Pattern.compile(";");

    private static final Pattern VAR_NAME = Pattern.compile("#(([\\w\\d.]+;\\s)+([\\w\\d]+))");

    public void doParseDescription(List<String> pDesStrings, BFile pBFile) throws Exception {
        // 描述
        pBFile.setDescription(pDesStrings.get(0));

        // 变量名
        String[] varNames = parseVarNames(pDesStrings.get(3));

        //变量实例数组
        FaultFile.Variable v = new FaultFile.Variable(varNames[0], "");
        List<FaultFile.Variable> vs = new ArrayList<>();
        vs.add(v);
        for (int i = 1; i < varNames.length; i++) {
            v = new FaultFile.Variable(varNames[i], "");
            vs.add(v);
        }

        FaultFile.Variable[] vars = new FaultFile.Variable[vs.size()];
        vs.toArray(vars);
        pBFile.setVariables(vars);
    }

    public void doParseData(String pDataLine, BFile pBFile) {
        pBFile.getData().add(SPLIT_PATTERN.split(pDataLine));
    }

    protected String[] parseVarNames(String pDesString) throws Exception {
        Matcher mVarName = VAR_NAME.matcher(pDesString);
        if (mVarName.find()) {
            try {
                return SPLIT_PATTERN.split(mVarName.group(1));
            } catch (Exception e) {
                throw new Exception("Parse var name error.", e);
            }
        } else {
            throw new ParseException("Cannot find var name.", 6);
        }
    }
}
