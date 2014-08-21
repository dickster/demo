
package demo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author derek.dick
 */
public class GpsLocation implements Serializable {
    
    private BigDecimal latitude;
    private BigDecimal longitude;

    public GpsLocation() {
        this(0.0, 0.0);  // should use a better default??
    }

    public GpsLocation(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GpsLocation(Number latitude, Number longitude) {
        this.latitude = new BigDecimal(latitude.doubleValue());
        this.longitude = new BigDecimal(longitude.doubleValue());
    }

    public boolean isNear(GpsLocation location) {
        return false;   //TODO
    }
    
    public boolean isNear(GpsLocation location, Number threshold) {
        return false; //TODO
    }          
    
    public Double distanceFrom(GpsLocation location) {                                                                                                                                                                                                                                                               
        // do the haversine calculation stuff here...
        return 0.0;
    }

    public boolean isEmpty() {
        return latitude==null || longitude==null;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }
    
}
