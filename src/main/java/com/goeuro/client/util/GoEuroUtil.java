package com.goeuro.client.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by CDD-PC on 08/08/2016.
 */
public class GoEuroUtil {

  private static final String SEPARATOR = ",";

  public static String convertToCsv (Collection<Object> obj, String[] fieldNames) throws NoSuchFieldException, IllegalAccessException {
    StringBuffer result = new StringBuffer ();
    Iterator<Object> it = obj.iterator ();
    while (it.hasNext ()) {
      Object o = it.next ();
      StringBuffer sb = new StringBuffer ();
      for (int i = 0; i < fieldNames.length; i++) {
        Field field = o.getClass ().getDeclaredField (fieldNames[i]);
        field.setAccessible (true);
        Object value = field.get (o);
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

}
