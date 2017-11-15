package com.adark0915.selfdiagnosis.faultfile;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Shelly on 2017-11-13.
 */
public class ConvertFileParser {
    private static final Pattern SPLIT_PATTERN = Pattern.compile("\\t");

    private static final Pattern VAR_NAME = Pattern.compile("#(([\\w\\d.]+;\\s)+([\\w\\d]+))");

    public void doParseDescription(List<String> pDesStrings, ConvertFile pConvertFile) throws Exception {
        // 变量名
        String[] varNames = parseVarNames(pDesStrings.get(0));

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
        pConvertFile.setVariables(vars);
    }

    public void doParseData(String pDataLine, ConvertFile pConvertFile) {
        pConvertFile.getData().add(SPLIT_PATTERN.split(pDataLine));
    }

    protected String[] parseVarNames(String pDesString) throws Exception {
        return SPLIT_PATTERN.split(pDesString);
    }
}
