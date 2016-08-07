package com.goeuro.client.core;

import com.goeuro.client.exception.GoEuroAppException;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by datta on 07/08/2016.
 */
public class TestApiCallerImpl {

    @Test(expected=GoEuroAppException.class)
   public void test_invalid_config_file() {
        ApiCaller c = new ApiCallerImpl("abcd");
    }


    @Test()
    public void test_valid_config_file() {
        URL u = getClass().getResource("TestApiCallerImpl_valid_config_file");
        ApiCaller c = new ApiCallerImpl(u.getFile());
        Assert.assertEquals("http://api.goeuro.com/api/v2/position/suggest/en/Berlin",
                c.getApi(ApiCaller.DEFUALT));
    }

    @Test(expected=GoEuroAppException.class)
    public void test_no_default_url_in_config_file() {
        URL u = getClass().getResource("TestApiCallerImpl_test_no_default_url_in_config_file");
        ApiCaller c = new ApiCallerImpl(u.getFile());
    }

    @Test(expected=GoEuroAppException.class)
    public void test_call_undefined_url() throws MalformedURLException {
        URL u = getClass().getResource("TestApiCallerImpl_valid_config_file");
        ApiCaller c = new ApiCallerImpl(u.getFile());
        c.callApi("doesnotexist", null);
    }

    @Test()
    public void test_call_malformed_url() {
        URL u = getClass().getResource("TestApiCallerImpl_valid_config_file");
        try {
            ApiCaller c = new ApiCallerImpl(u.getFile());
            c.callApi("malformedurl", null);
        } catch (MalformedURLException ex) {
            return;
        }
        Assert.fail("Excpected Malformed URL Exception");
    }

    @Test()
    public void test_api_response() {
        URL u = getClass().getResource("TestApiCallerImpl_valid_config_file");
        String response = null;
        try {
            ApiCaller c = new ApiCallerImpl(u.getFile());
            response = c.callApi(ApiCaller.DEFUALT, new Object[]{"Berlin"});
        } catch (MalformedURLException ex) {
            return;
        }
        Assert.assertTrue(response!= null && !response.isEmpty());
    }
}
