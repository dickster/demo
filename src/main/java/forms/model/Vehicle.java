package forms.model;

import com.google.common.base.Joiner;

import java.io.Serializable;

public class Vehicle implements Serializable {
    public String type;
    public String year;
    public String model;
    public String color;

    public Vehicle() {
    }

    public Vehicle(String type, String year, String model) {
        this.type = type;
        this.year = year;
        this.model = model;
        this.color = "white";
    }

    @Override
    public String toString() {
        return Joiner.on(" ").skipNulls().join(year, type, model);
    }

    public String getType() {
        return type;
    }
}

