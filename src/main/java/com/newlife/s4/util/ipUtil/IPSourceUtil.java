package com.newlife.s4.util.ipUtil;

import com.newlife.s4.util.ipUtil.exception.IPFormatException;
import com.newlife.s4.util.ipUtil.exception.InvalidDatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 描述:
 *
 * @author withqianqian@163.com
 * @create 2018-11-09 17:24
 */
public class IPSourceUtil {
    private static final Logger logger = LoggerFactory.getLogger(IPSourceUtil.class);
    static District db = null;
    private static synchronized District getDb() {
        if (db == null) {
            try {
                db = new District("/data/ip_lib/ipiptest.ipdb");
                logger.info("#######################初始化 ip 库成功!");
            } catch (IOException e) {
                //e.printStackTrace();
                logger.error("初始化 ip 库失败");
            }

        }
        return db;
    }

    public static String[] getAddr(String ip) throws IPFormatException, IOException {
        return getDb().find(ip, "CN");
    }

}
