package forms.model;

import com.google.common.base.Joiner;

import java.io.Serializable;

public class Name implements Serializable {
    public String first ="derek";
    public String middle = "william";
    public String last = "dick";
    public String salutation = "Mr.";

    public Name() {
        System.out.println("name created by section panel.");
    }

    Name(String first, String last) {
        this(first, null, last);
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
