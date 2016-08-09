package com.goeuro.client.core;

import java.net.MalformedURLException;

/**
 * Created by datta on 07/08/2016.
 */
public interface ApiCaller {
    String DEFUALT = "default";
    String callApi(String name, Object[] params) throws MalformedURLException;
    String getApi(String name);
}
