package demo;

import java.io.Serializable;


public class Insured implements Serializable {
    Address address = new Address();
    VIN vin = new VIN(2013, "Ford", "Mustang", "123fcyA");
    String name;

    public Insured() {
        this.name = "New Insured";
    }

    public Insured(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}