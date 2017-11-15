package com.adark0915.selfdiagnosis.faultfile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EBFile {
	public static class Variable {
		private String m_Name;

		private long m_No;

		public String getName() {
			return m_Name;
		}

		public long getNo() {
			return m_No;
		}

		public Variable(String pName, long pNo) {
			m_Name = pName;
			m_No = pNo;
		}

		@Override
		public String toString() {
			return "Variable{" +
					"m_Name='" + m_Name + '\'' +
					", m_No=" + m_No +
					'}';
		}
	}

	private long m_ErrorNo;

	private String m_Description;
	
	private Date m_CreateDate;

	private Variable[] m_Variables;

	private List<String[]> m_Data;

	public long getErrorNo() {
		return m_ErrorNo;
	}

	public void setErrorNo(long pErrorNo) {
		this.m_ErrorNo = pErrorNo;
	}

	public String getDescription() {
		return m_Description;
	}

	public void setDescription(String pDescription) {
		this.m_Description = pDescription;
	}

	public Date getCreateDate() {
		return m_CreateDate;
	}

	public void setCreateDate(Date pCreateDate) {
		this.m_CreateDate = pCreateDate;
	}

	public List<String[]> getData() {
		return m_Data;
	}

	public Variable[] getVariables() {
		return m_Variables;
	}

	public void setVariables(Variable[] pVariables) {
		m_Variables = pVariables;
	}

	private EBFile() {
		m_Data = new ArrayList<>();
	}

	private static EBFileParser_V1GW m_EBFileParser = new EBFileParser_V1GW();
	
	
	public static EBFile parseEbFile(String pFileName) throws Exception {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(pFileName);
			return parseEbFile(fis);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}

	public static EBFile parseEbFile(InputStream pStream) throws Exception {
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {

			isr = new InputStreamReader(pStream, Charset.forName("ASCII"));

			br = new BufferedReader(isr);

			String line = null;
			EBFile file = new EBFile();
			List<String> desLines = new ArrayList<>();
			boolean dataBegin = false;
			int lineNo = 1;
			try {
				while ((line = br.readLine()) != null) {
					if (line.startsWith("#") && !dataBegin) {
						desLines.add(line);
					} else {
						if (!dataBegin) {// 读取完描述信息，遇到第一行数据时
							dataBegin = true;
							m_EBFileParser.doParseDescription(desLines, file);
							desLines.clear();
						}
						m_EBFileParser.doParseData(line, file);
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
