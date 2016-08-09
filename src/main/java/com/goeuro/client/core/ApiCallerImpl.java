package com.goeuro.client.core;

import com.goeuro.client.exception.GoEuroAppException;
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
public class ApiCallerImpl implements ApiCaller{

    private static final Logger logger = Logger.getLogger(ApiCallerImpl.class.getName());

    private Properties urlConfig;

    private GsonBuilder builder;
    private Gson gson;


    public ApiCallerImpl(String configPath) {
        urlConfig =new Properties();
        try {
            urlConfig.load(new FileReader(configPath));
        } catch (IOException e) {
            throw new GoEuroAppException("Config file not found", e);
        }

        if (urlConfig.getProperty(ApiCaller.DEFUALT) == null ||
                urlConfig.getProperty(ApiCaller.DEFUALT).isEmpty()) {
            throw new GoEuroAppException("Config must contain default url. ex. defautlt=www.google.com");
        }
        builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        gson = builder.create();
    };

    @Override
    public String callApi(String name, Object[] params) throws MalformedURLException {
        String urlStr = urlConfig.getProperty(name);
        if (urlStr == null ||
                urlStr.isEmpty()) {
            throw new GoEuroAppException(MessageFormat.format("URL with name {0} not configured", name));
        }

        String formattedUrl = MessageFormat.format(urlStr, params);
        logger.log(Level.FINE, "Formatted URL" + formattedUrl);

        String response = null;
        try {
            response = getGetResponse(formattedUrl);
        } catch (MalformedURLException ex) {
            throw ex;
        } catch (IOException ex) {
            throw new GoEuroAppException("Unable to get URL response", ex);
        }

        return response;
    }

    @Override
    public String getApi(String name) {
        return urlConfig.getProperty(name);
    }

    public static String getGetResponse(String urlToRead) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        logger.log(Level.FINE, "URL Get:" + url.toString ());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}
