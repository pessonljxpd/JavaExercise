package com.adark0915;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shelly on 2017-10-24.
 */
public class TestEqualsMethod {

    public static void main(String[] args){

        List<String> a = new ArrayList<>();
        List<String> b = new ArrayList<>();


        a.add("abc");
        a.add("def");
        a.add("ghi");

        b.add("abc");
        b.add("def");
        b.add("ghi");

        System.out.println("[==] : "+(a==b));

        System.out.println("[equals()] : "+ a.equals(b));

    }

}
