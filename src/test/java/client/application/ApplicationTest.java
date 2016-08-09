package client.application;

import com.goeuro.client.application.GoEuroClientApp;
import org.junit.Test;

/**
 * Created by CDD-PC on 09/08/2016.
 */
public class ApplicationTest {

  public static void main (String[] args) {



  }

  @Test(expected = IllegalArgumentException.class)
  public void test_app_invalid_input_args_null() {
    GoEuroClientApp app = new GoEuroClientApp ();
    app.execute (null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_app_invalid_input_args_zero() {
    GoEuroClientApp app = new GoEuroClientApp ();
    app.execute (new String[1]);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_app_invalid_input_args_emty() {
    GoEuroClientApp app = new GoEuroClientApp ();
    app.execute (new String[]{""});
  }

  @Test()
  public void test_app() {
    GoEuroClientApp app = new GoEuroClientApp ();
    app.main (new String[]{"Berlin"});
  }

}
