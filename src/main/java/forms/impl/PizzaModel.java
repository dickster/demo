package forms.impl;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PizzaModel implements Serializable {

    public List<Pizza> pizzas = Lists.newArrayList(
            new Pizza("mamas favourite", 34.99 ),
            new Pizza("vulcano", 25.0 ),
            new Pizza("hawaiian", 25.0 )
    );

    public List<String> toppings;
    public String size;
    public String city;
    public String tip;

    public class Pizza implements Serializable {
        public String desc;
        public BigDecimal price;

        public Pizza(String s, double price) {
            this.desc = s;
            this.price = new BigDecimal(price+"");
        }
    }
}


