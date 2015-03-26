/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package demo;

import java.io.Serializable;

public class Address implements Serializable {

    private String streetAddress = "";
    private String city = "";
    private String province = "";
    private String country = "";
    private String postalCode = "";
    private GpsLocation gpsLocation = new GpsLocation();
    private String formattedAddress;

    public Address() {

    }

    public Address(Address a) {
        this.streetAddress = a.streetAddress;
        this.city = a.city;
        this.province = a.province;
        this.country = a.country;
        this.postalCode = a.postalCode;
        this.gpsLocation = a.gpsLocation;
        this.formattedAddress = a.formattedAddress;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public GpsLocation getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(GpsLocation gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
}
