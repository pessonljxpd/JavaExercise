package com.adark0915.net;

import java.io.IOException;

/**
 * Created by Shelly on 2017-10-25.
 */
public class TestNet {

    public static void main(String[] args){
        boolean networkEnable = isNetworkEnable();
        System.out.println("NetworkEnable = "+networkEnable);
    }

    public static boolean isNetworkEnable() {
        boolean enable = false;
        try {
            String ip = "www.baidu.com";// ping一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
            // ping的状态
            int status = p.waitFor();
            if (status == 0) { //ping的状态码返回 0 表示正常。
                enable = true;
            } else {
                enable = false;
            }
        } catch (InterruptedException pE) {
            pE.printStackTrace();
        } catch (IOException pE) {
            pE.printStackTrace();
        }

        return enable;
    }
}
