package com.pfl.lib_common.exception;

/**
 * Created by Administrator on 2017/12/18 0018.
 */

public class NoNetworkException extends RuntimeException {

    private int cede;

    public NoNetworkException(String message) {
        super(message);
    }

    public NoNetworkException(String message, int code) {
        super(message);
        this.cede = code;
    }
}

