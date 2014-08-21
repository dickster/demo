package com.brovada.easy.widgets;

import com.google.code.geocoder.model.GeocodeResponse;
import junit.framework.TestCase;
import org.junit.Test;

public class AddressConverterTest extends TestCase {

    @Test
    public void test_GeoCoder() {
        AddressUtils utils = new AddressUtils() {
            @Override protected void nop() {
                // NOTE : this is put in because Google puts a threshold on # of API requests you can make.
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        GeocodeResponse geocode = utils.getGeocode("6 saskatoon drive");
        boolean same = false;
        assertTrue(utils.compare("6 sskatoon, Toronto", "  6 SASKatoon drive, Etobicoke"));
        assertTrue(utils.compare("6 sskatoon, Toronto", "  6 SASKatoon drive, Etobicoke"));
        assertFalse(utils.compareWithThreshold("m9p2e7, Toronto", "  6 SASKatoon drive, Etobicoke", .00001));
        assertTrue(utils.compareWithThreshold("m9p-2e7, Toronto", "  6 SASKatoon drive, Canada", .5));
        assertTrue(utils.compare("6 saskatoon, Toronto", "  6 SASKatoon drive, Etobicoke"));
        assertTrue(utils.compare("6 saskatoon, m9p 2e7", "    6Saskatoon Dr"));
        assertTrue(utils.compare("6 siskatoon, Toronto", "  6 SASKatoon drive, Etobicoke"));
        assertTrue(utils.compare("6 sskatoon, Toronto", "  6 SASKatoon drive, Etobicoke"));
        assertTrue(utils.compare("6 sskatoon, Toronto", "  6 SASKatoon drive, Etobicoke"));
        assertTrue(utils.compare("6 saskatoon DR. m9p   2E7", " 6 SASKATOON DRIVE, CANADA"));
    }

}
