package forms.model;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import demo.Address;

import java.io.Serializable;
import java.util.List;

public class GermanInsuranceObject implements Serializable {

    // this could include an acord object, an ibis object., a collection of objects like
    //  temp, misc, ai, acord & errors or whatever you like.

    private List<Name> names = Lists.newArrayList(new Name("derek","william", "dick"));
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
