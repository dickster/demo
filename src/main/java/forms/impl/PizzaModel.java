package forms.impl;

import java.io.Serializable;
import java.util.List;

public class PizzaModel implements Serializable {

    public List<String> toppings;
    public String size;
    public String city;
    public String tip;
}
