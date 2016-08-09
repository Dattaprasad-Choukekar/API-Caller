package client.util;

import java.util.ArrayList;
import java.util.List;

import com.goeuro.client.model.CityLocation;
import com.goeuro.client.model.GeoPosition;
import com.goeuro.client.util.GoEuroUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by CDD-PC on 08/08/2016.
 */
public class GoEuroUtilTest {

  private CityLocation location;

  @Before
  public void setUp() {
    location = new CityLocation();
    location.setId(379727);
    location.setKey(null);
    location.setName("Paris");
    location.setFullName("Paris, France");
    location.setIataAirportCode(null);
    location.setType("location");
    location.setCountry("France");
    location.setLocationId(11914);
    location.setInEurope(true);
    location.setCountryCode("FR");
    location.setCoreCountry(true);
    location.setDistance(null);
    GeoPosition p = new GeoPosition();
    p.setLatitude(48.85341f);
    p.setLongitude(2.3488f);
    location.setGeoPosition(p);
  }

  @After
  public void tearDown() {

  }


  @Test
  public void test_convert_to_csv() throws NoSuchFieldException, IllegalAccessException {
    List<CityLocation> c = new ArrayList<CityLocation> ();
    c.add (location);
    String r =  GoEuroUtil.convertToCsv (c, new String[]{"name", "fullName"});
    Assert.assertTrue (r != null && !r.isEmpty ());
    Assert.assertEquals ("Paris,\"Paris, France\"", r);
  }

  @Test
  public void test_convert_to_csv_multiple_objects() throws NoSuchFieldException, IllegalAccessException {
    List<CityLocation> c = new ArrayList<CityLocation> ();
    c.add (location);
    c.add (location);
    c.add (location);
    String r =  GoEuroUtil.convertToCsv (c, new String[]{"name", "fullName"});
    Assert.assertTrue (r != null && !r.isEmpty ());
    Assert.assertEquals ("Paris,\"Paris, France\"" + System.lineSeparator() +
      "Paris,\"Paris, France\"" + System.lineSeparator() +
      "Paris,\"Paris, France\"", r);
  }


  @Test(expected = NoSuchFieldException.class)
  public void test_convert_to_csv_unexpected_field() throws NoSuchFieldException, IllegalAccessException {
    List<CityLocation> c = new ArrayList<CityLocation> ();
    c.add (location);
    c.add (location);
    c.add (location);
    String r =  GoEuroUtil.convertToCsv (c, new String[]{"name", "fullName", "ddddddd"});
    Assert.assertTrue (r != null && !r.isEmpty ());
    Assert.assertEquals ("Paris,\"Paris, France\"" + System.lineSeparator() +
      "Paris,\"Paris, France\"" + System.lineSeparator() +
      "Paris,\"Paris, France\"", r);
  }
}
