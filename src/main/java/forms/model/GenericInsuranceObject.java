package forms.model;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import demo.Address;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class GenericInsuranceObject implements Serializable {

    // this could include an acord object, an ibis object., a collection of objects like
    //  temp, misc, ai, acord & errors or whatever you like.

    private List<Name> names = Lists.newArrayList(new Name("derek","william", "dick"));
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
        public String salutation;

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
        private String country;
        private Integer accidents;
        private String drinks;
        private String salutation;
        private Boolean smokes;
        public String phone, email, occupation, cc;
        public Integer age;
        public boolean notifyMe = false;
        public Dwelling dwelling = new Dwelling();
        private Address address = new Address();
        private String address2;
        private Integer deductible = 0;
        private Contact contact = new Contact();
        private String driversLicense;


        public String getCountry() {
            return country;
        }

        @Override
        public String toString() {
            return "Insured{" +
                    "address='" + address + '\'' +
                    ", email='" + email + '\'' +
                    ", age='" + age + '\'' +
                    ", occupation='" + occupation + '\'' +
                    ", cc='" + cc + '\'' +
                    ", dwelling=" + dwelling +
                    '}';
        }

        public Integer getAge() {
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
        public Integer year;

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

}
