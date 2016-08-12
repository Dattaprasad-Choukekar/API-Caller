package com.goeuro.client.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.goeuro.client.core.ApiCallerImpl;
import com.goeuro.client.model.CityLocation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by CDD-PC on 08/08/2016.
 */
public class GoEuroUtil {

  private static final Logger logger = Logger.getLogger (GoEuroUtil.class.getName ());
  private static final String SEPARATOR = ",";

  private static GsonBuilder builder;
  private static  Gson gson;

  static {
    builder = new GsonBuilder();
    builder.setPrettyPrinting().serializeNulls();
    gson = builder.create();
  }

  public static String convertToCsv (Collection<CityLocation> obj, String[] fieldNames) throws NoSuchFieldException, IllegalAccessException {
    //TODO use annatattions instead of field names
    if (fieldNames == null || fieldNames.length == 0) {
      return "";
    }
    StringBuffer result = new StringBuffer ();
    Iterator<CityLocation> it = obj.iterator ();
    while (it.hasNext ()) {
      CityLocation o = it.next ();
      StringBuffer sb = new StringBuffer ();
      for (int i = 0; i < fieldNames.length; i++) {
        String currFieldName = fieldNames[i];
        if (currFieldName == null || currFieldName.length () == 0)
          continue;

        Field field = null;
        Object value = null;
        if ((currFieldName).startsWith ("geoPosition.")) {
          currFieldName = currFieldName.replace ("geoPosition.", "");
          field = o.getGeoPosition ().getClass ().getDeclaredField (currFieldName);
          field.setAccessible (true);
          value = field.get (o.getGeoPosition ());
        } else {
          field = o.getClass ().getDeclaredField (currFieldName);
          field.setAccessible (true);
          value = field.get (o);
        }

        sb.append (StringEscapeUtils
          .escapeCsv(value.toString ()));

        if (i != (fieldNames.length - 1)){
          sb.append (SEPARATOR);
        }

      }
      result.append (sb);
      if (it.hasNext ()) {
        result.append (System.lineSeparator());
      }
    }
    return  result.toString ();
  }

  public static CityLocation[] dejsonizeCityLocatoinArray(String content) {
    return gson.fromJson (content, CityLocation[].class);
  }

  public static String getGetResponse (String urlToRead) throws IOException {
    StringBuilder result = new StringBuilder ();
    URL url = new URL (urlToRead);
    logger.log (Level.FINE, "URL Get:" + url.toString ());
    HttpURLConnection conn = (HttpURLConnection) url.openConnection ();
    conn.setRequestMethod ("GET");
    conn.setReadTimeout (5000);
    BufferedReader rd = new BufferedReader (new InputStreamReader (conn.getInputStream ()));
    String line;
    while ((line = rd.readLine ()) != null) {
      result.append (line);
    }
    rd.close ();
    return result.toString ();
  }

  //  public static String convertToCsv (Collection<Object> obj, String[] fieldNames) throws NoSuchFieldException, IllegalAccessException {
//    //TODO use annatattions instead of field names
//    if (fieldNames == null || fieldNames.length == 0) {
//      return "";
//    }
//    StringBuffer result = new StringBuffer ();
//    Iterator<Object> it = obj.iterator ();
//    while (it.hasNext ()) {
//      Object o = it.next ();
//      StringBuffer sb = new StringBuffer ();
//      for (int i = 0; i < fieldNames.length; i++) {
//        if (fieldNames[i] == null || fieldNames[i].length () == 0)
//          continue;
//
//
//        Field field = o.getClass ().getDeclaredField (fieldNames[i]);
//        field.setAccessible (true);
//        Object value = field.get (o);
//        sb.append (StringEscapeUtils
//          .escapeCsv(value.toString ()));
//        if (i != (fieldNames.length - 1)){
//          sb.append (SEPARATOR);
//        }
//
//      }
//      result.append (sb);
//      if (it.hasNext ()) {
//        result.append (System.lineSeparator());
//      }
//    }
//    return  result.toString ();
//  }

}
