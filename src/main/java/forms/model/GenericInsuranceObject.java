package forms.model;

import com.google.common.base.Joiner;
import demo.Address;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class GenericInsuranceObject implements Serializable {

    // this could include an acord object, an ibis object., a collection of objects like
    //  temp, misc, ai, acord & errors or whatever you like.

    private Errors errors = new Errors();
    private Name name = new Name();
    private Insured insured = new Insured();
    private Vehicle vehicle = new Vehicle();
    private Payment payment = new Payment();

    @Override
    public String toString() {
        return "GenericInsuranceObject{" +
                "name=" + name +
                ", insured=" + insured +
                ", vehicle=" + vehicle +
                '}';
    }

    public Insured getInsured() {
        return insured;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Name getName() {
        return name;
    }

    public Errors getErrors() {
        return errors;
    }

    public GenericInsuranceObject clearErrors() {
        errors = new Errors();
        return this;
    }

    public void invalid() {
        this.getInsured().country = "Mexico";
    }

    public Payment getPayment() { return payment; }

    public class Payment implements Serializable {
        public Integer cc;
        private Integer securityCode;
        public String expiry;
        public String method;
        public String frequency;
        public BigDecimal total = new BigDecimal(1932.34);
    }

    public class Name implements Serializable {
        public String first ="derek";
        public String middle = "william";
        public String last = "dick";
        public String salutation = "Mr.";

        Name() {
        }

        Name(String first, String middle, String last) {
            this.first = first;
            this.middle = middle;
            this.last = last;
        }

        @Override
        public String toString() {
           return Joiner.on(" ").skipNulls().join(first, middle, last);
        }
    }

    public class Insured implements Serializable {
        public String country;
        public int accidents = 2;
        public String drinks = "coke";
        public String salutation;
        public boolean smokes = true;
        public String phone, occupation, cc="123123123";
        public int age = 55;
        public boolean notifyMe = false;
        public Dwelling dwelling = new Dwelling();
        public Address address = new Address();
        public String address2;
        public int deductible = 0;
        public Contact contact = new Contact();
        public String driversLicense;

        public String getCountry() {
            return country;
        }

        @Override
        public String toString() {
            return "Insured{" +
                    "country='" + country + '\'' +
                    ", accidents=" + accidents +
                    ", drinks='" + drinks + '\'' +
                    ", salutation='" + salutation + '\'' +
                    ", smokes=" + smokes +
                    ", phone='" + phone + '\'' +
                    ", occupation='" + occupation + '\'' +
                    ", cc='" + cc + '\'' +
                    ", age=" + age +
                    ", notifyMe=" + notifyMe +
                    ", dwelling=" + dwelling +
                    ", address=" + address +
                    ", address2='" + address2 + '\'' +
                    ", deductible=" + deductible +
                    ", contact=" + contact +
                    ", driversLicense='" + driversLicense + '\'' +
                    '}';
        }

        public int getAge() {
            return age;
        }
    }

    public class Contact implements Serializable {
        public String email;
        public String phone;
        public String website;
    }

    public class Dwelling implements Serializable {
        public String roofType = "";
        public boolean pool;

        @Override
        public String toString() {
            return "Dwelling{" +
                    "pool='" + pool + '\'' +
                    ", roofType='" + roofType + '\'' +
                    '}';
        }
    }

    public class Vehicle implements Serializable {
        public String type = "Buick";
        public int year = 1978;

        @Override
        public String toString() {
            return "Vehicle{" +
                    "type='" + type + '\'' +
                    '}';
        }

        public String getType() {
            return type;
        }
    }

    public class Errors extends ArrayList<String> implements Serializable {
        @Override
        public boolean add(String s) {
            return super.add(s);
        }

        public String getHtml() {
            return Joiner.on("<br>").skipNulls().join(this);
        }
    }

}
