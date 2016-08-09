package com.goeuro.client.application;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.goeuro.client.core.ApiCaller;
import com.goeuro.client.core.ApiCallerImpl;
import com.goeuro.client.exception.GoEuroAppException;
import com.goeuro.client.model.CityLocation;
import com.goeuro.client.util.GoEuroUtil;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.io.IOUtils;

/**
 * Created by CDD-PC on 09/08/2016.
 */
public class GoEuroClientApp {

  private static final Logger logger = Logger.getLogger (GoEuroClientApp.class.getName ());

  private static final SimpleDateFormat OUTPUT_FILE_NAME_FORMAT = new SimpleDateFormat ("'Go_euro_client_api_log_'yyyyMMddhhmm'.csv'");

  public static void main (String[] args) {

    try {
      new GoEuroClientApp ().execute (args);
    } catch (Exception t) {
      t.printStackTrace ();
      logger.log (Level.SEVERE, t.getMessage ());
      throw t;
    }
  }

  public void execute (String args[]) {
    try {
      Logger global= LogManager.getLogManager().getLogger("");
      FileHandler fh = new FileHandler ("go_euro_client_api.log");
      fh.setFormatter (new SimpleFormatter ());
      global.addHandler (fh);
      global.setLevel (Level.WARNING);
      global.setUseParentHandlers(true);
    } catch (IOException e) {
      throw new GoEuroAppException("Error while configuring");
    }


    if (args == null || args.length == 0 || args[0] == null || args[0].isEmpty ()) {
      throw new IllegalArgumentException ("Please provide city name as argument : java -jar GoEuroTest.jar \"CITY_NAME\"");
    }

    String cityName = args[0];

    URL config = GoEuroClientApp.class.getClassLoader ()
      .getResource ("./go_euro_client_app.properties");

    if (config == null) {
      throw new GoEuroAppException ("go_euro_client_app.properties not found on classpath");
    }

    ApiCaller caller = new ApiCallerImpl (config.getPath ());
    String result = null;
    CityLocation[] locations = null;
    try {
      result = caller.callApi (ApiCaller.DEFUALT, new Object[] { cityName });
      logger.log (Level.FINE, result);

      // Parse
      try {
        locations = GoEuroUtil.dejsonizeCityLocatoinArray (result);
      } catch (JsonSyntaxException ex) {
        throw new GoEuroAppException ("Exeption while dejosonifying api resonse.", ex);
      }
      String csv = null;

      try {
        csv = GoEuroUtil.convertToCsv (new ArrayList<> (Arrays.asList (locations)),
          new String[] { "id", "name", "type", "geoPosition.latitude", "geoPosition.longitude" }); // Get fields from config Fields
      } catch (IllegalAccessException | NoSuchFieldException e) {
        throw new GoEuroAppException ("Error while converting api response to csv.", e);
      }


      try {
        FileWriter w = new FileWriter (OUTPUT_FILE_NAME_FORMAT.format (new Date ()));
        w.append (csv);
        w.flush ();
        w.close ();
      } catch (IOException e) {
        throw new GoEuroAppException ("Error while writting output to csv file", e);
      }

    } catch (MalformedURLException e) {
      throw new GoEuroAppException ("Invalid url configured in go_euro_client_app.properties ", e);
    }
  }
}
