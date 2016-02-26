package forms.model;

import com.google.common.collect.Sets;

import java.io.Serializable;
import java.util.Set;

public class Watercraft implements Serializable {
    private String type;
    private String value;
    private Set<AdditionalInterest> additionalInterests = Sets.newHashSet();

    public AdditionalInterest create() {
        return new AdditionalInterest();
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
