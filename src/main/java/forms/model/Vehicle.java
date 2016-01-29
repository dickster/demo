package forms.model;

import com.google.common.base.Joiner;

import java.io.Serializable;
import java.util.UUID;

public class Vehicle implements Serializable {
    public String type;
    public String year;
    public String model;
    public String color;
    public String vin;

    public Vehicle() {
    }

    public Vehicle(String type, String year, String model) {
        this.type = type;
        this.year = year;
        this.model = model;
        this.color = "white";
    }

    public Vehicle withTestData() {
        this.vin = UUID.randomUUID().toString().substring(0,8);
        this.color = "white";
        this.year = (1970 + (int)(Math.random()*55)) + "";
        this.model = "Ford";
        return this;
    }

    @Override
    public String toString() {
        return Joiner.on(" ").skipNulls().join(year, type, model);
    }

    public String getType() {
        return type;
    }
}

