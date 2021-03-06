package com.goeuro.client.core;

import com.goeuro.client.exception.GoEuroAppException;
import com.goeuro.client.util.GoEuroUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sun.nio.cs.StreamDecoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by datta on 07/08/2016.
 */
public class ApiCallerImpl implements ApiCaller {

  private static final Logger logger = Logger.getLogger (ApiCallerImpl.class.getName ());

  private Properties urlConfig;

  private GsonBuilder builder;

  public ApiCallerImpl (String configPath) {
    urlConfig = new Properties ();
    try {
      urlConfig.load (new FileReader (configPath));
    } catch (IOException e) {
      throw new GoEuroAppException ("Config file not found", e);
    }

    if (urlConfig.getProperty (ApiCaller.DEFUALT) == null ||
      urlConfig.getProperty (ApiCaller.DEFUALT).isEmpty ()) {
      throw new GoEuroAppException ("Config must contain default url. ex. defautlt=www.google.com");
    }
    builder = new GsonBuilder ();
    builder.setPrettyPrinting ().serializeNulls ();
  }

  ;

  public ApiCallerImpl (String key, String url) {
    urlConfig = new Properties ();
    urlConfig.put (key, url);
    builder = new GsonBuilder ();
    builder.setPrettyPrinting ().serializeNulls ();
  }

  ;

  @Override
  public String callApi (String name, Object[] params) throws MalformedURLException {
    String urlStr = urlConfig.getProperty (name);
    if (urlStr == null ||
      urlStr.isEmpty ()) {
      throw new GoEuroAppException (MessageFormat.format ("URL with name {0} not configured", name));
    }

    String formattedUrl = MessageFormat.format (urlStr, params);
    logger.log (Level.FINE, "Formatted URL" + formattedUrl);
    System.out.println (formattedUrl);
    String response = null;
    try {
      response = GoEuroUtil.getGetResponse (formattedUrl);
    } catch (MalformedURLException ex) {
      throw ex;
    } catch (IOException ex) {
      throw new GoEuroAppException ("Unable to get URL response", ex);
    }

    return response;
  }

  @Override
  public String getApiByName (String name) {
    return urlConfig.getProperty (name);
  }


}
