package com.adark0915.jsoup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestSplit {

    public static void main(String[] args) {
        String str = "background-color:#b07219;";
        String REGEX = "(:#)([a-f][0-9]+)";
        Pattern compile = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = compile.matcher(str);
        while (matcher.find()){
            System.out.println("args = [" + matcher.group(2) + "]");
        }
//        int indexOf = str.indexOf(":");
//        String substring = str.substring(indexOf + 1, indexOf + 8);
//        System.out.println(substring);

//        String count = "26 stasad1frs 3toda1y";
//        String complier = "(\\d+)(\\D+)";
//        Pattern p = Pattern.compile(complier);
//        Matcher m = p.matcher(count);
//        System.out.println("groupCount = [" + m.groupCount() + "]");
//        while (m.find()) {
//            System.out.println("args = [" + m.group(2) + "]");
//        }
    }

}
