package com.adark0915.selfdiagnosis.faultfile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EBFileParser_V1GW {
	private static final Pattern ms_SplitPattern = Pattern.compile(";");

	private static final Pattern ms_DatePattern = Pattern.compile("eb\\d{6,6}_\\d{4,4}_\\d{1,3}", Pattern.CASE_INSENSITIVE);

	private static final SimpleDateFormat ms_DateFormat = new SimpleDateFormat("yyMMdd_HHmm", Locale.ENGLISH);

	private static final Pattern ms_ErrorNoPattern = Pattern.compile("--Error No.:(\\d+)");

	private static final Pattern ms_VarNo = Pattern.compile("--variable number:((\\d+;)+)");

	private static final Pattern ms_VarName = Pattern.compile("#(([\\w\\d]+;)+([\\w\\d]+))");

	public void doParseDescription(List<String> pDesStrings, EBFile pEbFile) throws Exception {
		pEbFile.setCreateDate(parseDate(pDesStrings.get(0)));

		// 描述
		pEbFile.setDescription(pDesStrings.get(0));

		// 故障号
		pEbFile.setErrorNo(parseErrorNo(pDesStrings.get(2)));

		// 变量号
		String[] varNos = parseVarNos(pDesStrings.get(3));

		// 变量名
		String[] varNames = parseVarNames(pDesStrings.get(5));

		//变量实例数组
		EBFile.Variable v = new EBFile.Variable(varNames[0], 0);
		List<EBFile.Variable> vs = new ArrayList<>();
		vs.add(v);
		for (int i = 0; i < varNos.length; i++) {
			v = new EBFile.Variable(varNames[i + 1], Long.parseLong(varNos[i]));
			vs.add(v);
		}

		EBFile.Variable[] vars = new EBFile.Variable[vs.size()];
		vs.toArray(vars);
		pEbFile.setVariables(vars);
	}

	protected Date parseDate(String pDesString) throws ParseException {
		Matcher mDate = ms_DatePattern.matcher(pDesString);
		if (mDate.find()) {
			String date = mDate.group().substring(2);
			return ms_DateFormat.parse(date);
		} else {
			throw new ParseException("Cannot find date.", 1);
		}
	}

	protected long parseErrorNo(String pDesString) throws Exception {
		Matcher mErrorNo = ms_ErrorNoPattern.matcher(pDesString);
		if (mErrorNo.find()) {
			try {
				return Long.parseLong(mErrorNo.group(1));
			} catch (Exception e) {
				throw new Exception("Parse error no error.", e);
			}
		} else {
			throw new ParseException("Cannot find error no.", 3);
		}
	}

	protected String[] parseVarNos(String pDesString) throws Exception {
		Matcher mVarNo = ms_VarNo.matcher(pDesString);
		if (mVarNo.find()) {
			try {
				return ms_SplitPattern.split(mVarNo.group(1));
			} catch (Exception e) {
				throw new Exception("Parse var no error.", e);
			}
		} else {
			throw new ParseException("Cannot find var no.", 5);
		}
	}

	protected String[] parseVarNames(String pDesString) throws Exception {
		Matcher mVarName = ms_VarName.matcher(pDesString);
		if (mVarName.find()) {
			try {
				return ms_SplitPattern.split(mVarName.group(1));
			} catch (Exception e) {
				throw new Exception("Parse var name error.", e);
			}
		} else {
			throw new ParseException("Cannot find var name.", 6);
		}
	}

	public void doParseData(String pDataLine, EBFile pEbFile) {
		pEbFile.getData().add(ms_SplitPattern.split(pDataLine));
	}

}
