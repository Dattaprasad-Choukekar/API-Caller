package com.goeuro.client.application;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.goeuro.client.core.ApiCaller;
import com.goeuro.client.core.ApiCallerImpl;
import com.goeuro.client.exception.GoEuroAppException;
import org.apache.commons.io.IOUtils;

/**
 * Created by CDD-PC on 09/08/2016.
 */
public class GoEuroClientApp {

  private static final Logger logger = Logger.getLogger(GoEuroClientApp.class.getName());

  public static void main (String[] args) {

    if (args == null || args.length == 0 || args[0] == null || args[0].isEmpty ()) {
      throw new IllegalArgumentException ("Please provide City name"); //TODO GOod help message
    }

    String cityName = args[0];

    URL config = GoEuroClientApp.class.getClassLoader()
      .getResource("go_euro_client_app.properties");

    if (config == null) {
      throw new GoEuroAppException ("go_euro_client_app.properties not found on classpath");
    }

    ApiCaller caller = new ApiCallerImpl (config.getFile ());
    String result = null;
    try {
      result = caller.callApi (ApiCaller.DEFUALT, new Object[]{"en", cityName}); // TODO locale is fixed for the moment
      logger.log (Level.FINE, result);


      // Parse

     // write to file
      List res = IOUtils.readLines (new StringReader (result));





    } catch (MalformedURLException e) {
      e.printStackTrace ();
      throw new GoEuroAppException ("Invalid url configured in go_euro_client_app.properties ", e);
    } catch (IOException e) {
      new GoEuroAppException (e); //TODO
    }

  }
}
