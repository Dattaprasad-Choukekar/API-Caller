package com.goeuro.client.exception;

import java.io.IOException;

/**
 * Created by datta on 07/08/2016.
 */
public class GoEuroAppException extends RuntimeException {

    public GoEuroAppException(String s, Exception e) {
        super(s, e);
    }

    public GoEuroAppException(String s) {
        super(s);
    }

    public GoEuroAppException(Exception e) {
        super(e);
    }

}
