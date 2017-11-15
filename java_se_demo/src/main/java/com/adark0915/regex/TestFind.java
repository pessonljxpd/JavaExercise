package com.adark0915.regex;

/**
 * Created by Shelly on 2017-10-25.
 */
public class TestFind {
    public static void main(String[] args) {
        String content = "[123] 1. 截至目前为止，[1213213]最长域名后缀 .cancerresearch";
//        Pattern pattern = Pattern.compile("\\[\\d+\\]");
//
//        Matcher matcher = pattern.matcher(content);
//        if (matcher.find()) {
//            String group = matcher.group();
//            System.out.println(group);
//        }

        String code = "";
        int start = content.indexOf("[");
        int end = content.indexOf("]");
        code = content.substring(start+1, end );
        System.out.println("code = "+code);

    }
}
