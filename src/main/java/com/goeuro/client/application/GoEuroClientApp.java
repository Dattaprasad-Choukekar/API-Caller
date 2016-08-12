package com.goeuro.client.application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

/**
 * Created by CDD-PC on 09/08/2016.
 */
public class GoEuroClientApp {

  private static final Logger logger = Logger.getLogger (GoEuroClientApp.class.getName ());

  private static final SimpleDateFormat OUTPUT_FILE_NAME_FORMAT = new SimpleDateFormat ("yyyyMMddhhmm'.csv'");

  public static void main (String[] args) {

    try {
      new GoEuroClientApp ().execute (args);
    } catch (Exception t) {
      t.printStackTrace ();
      logger.log (Level.SEVERE, t.getMessage (), t);
      throw t;
    }
  }

  public void execute (String args[]) {

    // Setup Logging
    try {
      Logger global = LogManager.getLogManager ().getLogger ("");
      FileHandler fh = new FileHandler ("go_euro_client_api.log", true);
      fh.setFormatter (new SimpleFormatter ());
      global.addHandler (fh);
      global.setLevel (Level.WARNING);
    } catch (IOException e) {
      throw new GoEuroAppException ("Error while configuring", e);
    }

    // Validate input
    if (args == null || args.length == 0 || args[0] == null || args[0].trim ().isEmpty ()) {
      throw new IllegalArgumentException ("Please provide city name as argument : java -jar GoEuroTest.jar \"CITY_NAME\"");
    }

    String cityName = args[0];
    System.out.println ("City Name :" + cityName);
    /*URL config = GoEuroClientApp.class.getClassLoader ()
      .getResource ("./go_euro_client_app.properties");

    if (config == null) {
      throw new GoEuroAppException ("go_euro_client_app.properties not found on classpath");
    }

    ApiCaller caller = new ApiCallerImpl (config.getPath ());*/

    ApiCaller caller = new ApiCallerImpl (ApiCaller.DEFUALT, "http://api.goeuro.com/api/v2/position/suggest/en/{0}");
    String result = null;
    CityLocation[] locations = null;
    try {
      // Get API result
      result = caller.callApi (ApiCaller.DEFUALT, new Object[] { cityName });

      if (result.isEmpty () || result.equals ("[]")) {
        System.out.println ("Empty result. Nothing to write.");
        return;
      }
      logger.log (Level.FINE, result);

      // Parse result
      try {
        locations = GoEuroUtil.dejsonizeCityLocatoinArray (result);
      } catch (JsonSyntaxException ex) {
        throw new GoEuroAppException ("Exeption while dejosonifying api resonse.", ex);
      }

      // COnvert to CSV
      String csv = null;
      try {
        csv = GoEuroUtil.convertToCsv (new ArrayList<> (Arrays.asList (locations)),
          new String[] { "id", "name", "type", "geoPosition.latitude", "geoPosition.longitude" }); //TODO Get fields from config file
      } catch (IllegalAccessException | NoSuchFieldException e) {
        throw new GoEuroAppException ("Error while converting api response to csv.", e);
      }

      //Write to file
      String op = OUTPUT_FILE_NAME_FORMAT.format (new Date ());
      writeToFile (cityName + "_" + op, csv);

    } catch (MalformedURLException e) {
      throw new GoEuroAppException ("Invalid url configured in go_euro_client_app.properties ", e);
    }
  }

  private File writeToFile (String fileName, String csv) {

    File opF = new File (fileName);
    try (FileWriter w = new FileWriter (opF)) {
      w.append (csv);
      w.flush ();
    } catch (IOException e) {
      throw new GoEuroAppException ("Error while writting output to csv file", e);
    }
    System.out.println ("Output csv location: " + opF);
    return opF;
  }
}
