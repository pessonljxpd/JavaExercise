package com.adark0915.format;

/**
 * Created by Shelly on 2017-8-23.
 */
public class DecimalFormatDemo {

    public static void main(String[] args) {
//        DecimalFormat df = new DecimalFormat("0.000");
//
//        double d1 = 12312312313.23456;
//        double d2 = 00.000000000000000000000000000012321;
//        double d3 = 2.0;
//
//        System.out.println(df.format(d1));
//        System.out.println(df.format(d2));
//        System.out.println(df.format(d3));

//        System.out.println(UUID.randomUUID());

        String respStr = "*&GETARRAYSTRING:WTG04;103/2500;ZBFGC;20160406;2500&3000_E_V170630B;Sinoma59.5_90_Res_GW_2500_1706;192.168.151.4.1.1;;2500_E_GW_V_GL;;;com_2500_standard_V2.2;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;&GETARRAYSTRING";
        if (respStr.startsWith("*&")) {
            respStr = respStr.replaceAll("[\\*]??&[a-zA-Z]+:?", "");
            System.out.println(respStr);
        }


    }
}
