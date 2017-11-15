package com.adark0915.selfdiagnosis;

import com.adark0915.selfdiagnosis.expression.Expression;
import com.google.gson.Gson;

/**
 * Created by Shelly on 2017-11-14.
 */
public class TestExpressionParser {
    public void parseExpression(String pJSON){
        Gson gson = new Gson();
        Expression expression = gson.fromJson(pJSON, Expression.class);
        System.out.println(expression.toString());
    }
}
