package forms.model;

import demo.Address;

import java.io.Serializable;

public class GermanInsuranceObject implements Serializable {

    // this could include an acord object, an ibis object., a collection of objects like
    //  temp, misc, ai, acord & errors or whatever you like.

    private Name name = new Name();
    private Insured insured = new Insured();

    @Override
    public String toString() {
        return "GermanInsuranceObject{" +
                "name=" + name +
                ", insured=" + insured +
                '}';
    }

    class Name implements Serializable {
        public String first, middle, last;

        @Override
        public String toString() {
            return "Name{" +
                    "first='" + first + '\'' +
                    ", middle='" + middle + '\'' +
                    ", last='" + last + '\'' +
                    '}';
        }
    }

    class Insured implements Serializable {
        public String phone, email, age, occupation, cc;
        public boolean notifyMe = false;
        public Dwelling dwelling = new Dwelling();
        private Address address = new Address();
        private Integer deductible = 0;

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
    }

    class Dwelling implements Serializable {
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

}
