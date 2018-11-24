package com.hbase.common;

import java.io.IOException;

/**
 * @author : Guzh
 * @since : 2018/11/20
 */
public class HbaseConfig {



    // 关闭连接
    public static void close() {
        if (HconnectionFactory.connection != null) {
            try {
                HconnectionFactory.connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
