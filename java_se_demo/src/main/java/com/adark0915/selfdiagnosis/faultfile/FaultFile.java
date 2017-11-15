package com.adark0915.selfdiagnosis.faultfile;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Shelly on 2017-11-11.
 */
public abstract class FaultFile {
    private String mDescription;
    private Variable[] mVariables;
    protected List<String[]> mData;

    public Variable[] getVariables() {
        return mVariables;
    }

    public void setVariables(Variable[] pVariables) {
        mVariables = pVariables;
    }

    public List<String[]> getData() {
        return mData;
    }

    public void setData(List<String[]> pData) {
        mData = pData;
    }


    public abstract FaultFile parseFaultFile(String pFileName) throws Exception;

    @Override
    public String toString() {
        return "FaultFile{" +
                "mDescription='" + mDescription + '\'' +
                ", mVariables=" + Arrays.toString(mVariables) +
                ", mData=" + mData +
                '}';
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String pDescription) {
        mDescription = pDescription;
    }

    public static class Variable {
        private String mName;

        private String mComment;

        public String getName() {
            return mName;
        }

        public String getComment() {
            return mComment;
        }

        public Variable(String pName, String pComment) {
            mName = pName;
            mComment = pComment;
        }

        @Override
        public String toString() {
            return "Variable{" +
                    "mName='" + mName + '\'' +
                    ", mComment=" + mComment +
                    '}';
        }
    }
}
