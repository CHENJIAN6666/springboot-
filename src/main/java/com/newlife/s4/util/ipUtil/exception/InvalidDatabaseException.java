package com.newlife.s4.util.ipUtil.exception;

import java.io.IOException;

/**
 * 描述:
 *
 * @author withqianqian@163.com
 * @create 2018-11-09 16:20
 */
public class InvalidDatabaseException extends IOException {
    private static final long serialVersionUID = 7818375828106090155L;

    public InvalidDatabaseException(String message) {
        super(message);
    }
}
