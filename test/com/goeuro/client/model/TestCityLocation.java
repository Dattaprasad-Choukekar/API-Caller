package com.goeuro.client.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by datta on 07/08/2016.
 */
public class TestCityLocation {

    private Gson gson;

    @Before
    public void setUp() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        gson = builder.create();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void test_jsonify_city_location() throws IOException {
        CityLocation l = new CityLocation();
        l.setId(379727);
        l.setKey(null);
        l.setName("Paris");
        l.setFullName("Paris, France");
        l.setIataAirportCode(null);
        l.setType("location");
        l.setCountry("France");
        l.setLocationId(11914);
        l.setInEurope(true);
        l.setCountryCode("FR");
        l.setCoreCountry(true);
        l.setDistance(null);
        GeoPosition p = new GeoPosition();
        p.setLatitude(48.85341f);
        p.setLongitude(2.3488f);
        l.setGeoPosition(p);
        try (InputStream s1 = new ByteArrayInputStream(gson.toJson(l).getBytes());
             InputStream s2 = getClass().getResourceAsStream("TestCityLocation_test_jsonify_city_location");){
            System.out.println(gson.toJson(l));

            Assert.assertTrue(IOUtils.contentEquals(new InputStreamReader(s1, StandardCharsets.UTF_8),
                    new InputStreamReader(s2, StandardCharsets.UTF_8)));

        }
    }


    @Test
    public void test_dejsonify_city_location() throws IOException {
        try (InputStream s1 = getClass().getResourceAsStream("TestCityLocation_test_dejsonify_city_location");) {
            CityLocation c = gson.fromJson(new InputStreamReader(s1, StandardCharsets.UTF_8), CityLocation.class);
            Assert.assertEquals(c.getId(), 376217);
            Assert.assertEquals(c.getKey(), null);
            Assert.assertEquals(c.getName(), "Berlin");
            Assert.assertEquals(c.getFullName(), "Berlin, Germany");
            Assert.assertEquals(c.getIataAirportCode(), null);
            Assert.assertEquals(c.getType(), "location");
            Assert.assertEquals(c.getCountry(), "Germany");
            Assert.assertEquals(c.getLocationId(), 8384);
            Assert.assertEquals(c.isInEurope(), true);
            Assert.assertEquals(c.getCountryCode(), "DE");
            Assert.assertEquals(c.isCoreCountry(), true);
            Assert.assertEquals(c.getDistance(), null);
        }
    }

}