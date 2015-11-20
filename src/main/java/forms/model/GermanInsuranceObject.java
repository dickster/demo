package forms.model;

import java.io.Serializable;

public class GermanInsuranceObject implements Serializable {

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
        public String address, email, age, occupation, cc;
        public Dwelling dwelling = new Dwelling();

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
        public String pool, roofType = "";


        @Override
        public String toString() {
            return "Dwelling{" +
                    "pool='" + pool + '\'' +
                    ", roofType='" + roofType + '\'' +
                    '}';
        }
    }

}
