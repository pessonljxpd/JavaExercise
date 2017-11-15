package com.adark0915.selfdiagnosis.expression;

import java.util.List;

/**
 * Created by Shelly on 2017-11-14.
 */
public class Expression {
    private String name;
    private int level;
    private String sonId;
    private String andOr;
    private List<Expression> children;

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int pLevel) {
        level = pLevel;
    }

    public String getSonId() {
        return sonId;
    }

    public void setSonId(String pSonId) {
        sonId = pSonId;
    }

    public String getAndOr() {
        return andOr;
    }

    public void setAndOr(String pAndOr) {
        andOr = pAndOr;
    }

    public List<Expression> getChildren() {
        return children;
    }

    public void setChildren(List<Expression> pChildren) {
        children = pChildren;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", sonId='" + sonId + '\'' +
                ", andOr='" + andOr + '\'' +
                ", children=" + children +
                '}';
    }
}
