package forms.model;

import com.google.common.base.Joiner;

import java.io.Serializable;

public class Name implements Serializable {
    public String first ="";
    public String middle ;
    public String last;
    public String salutation = "Mr.";

    public Name() {
    }

    Name(String first, String last) {
        this(first, null, last);
    }

    Name(String first, String middle, String last) {
        this.first = first;
        this.middle = middle;
        this.last = last;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    @Override
    public String toString() {
        return Joiner.on(" ").skipNulls().join(first, middle, last);
    }
}
