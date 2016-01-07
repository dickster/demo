package demo;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

public class Name implements Serializable {
    String salutation;
    List<String> first = Lists.newArrayList();
    List<String> middle = Lists.newArrayList();
    List<String> last = Lists.newArrayList();
    String relation;  //junior, senior, III etc...

    List<String> names = Lists.newArrayList();

    public Name() {
    }

    public Name(String first, String last) {
        this.first.add(first);
        this.last.add(last);
    }

    @Override
    public String toString() {
        // CAVEAT : important not to change this. used by wicket models.
        Iterable<String> nonBlankNames = Iterables.filter(Lists.newArrayList(getSalutation(), getFirst(), getMiddle(), getLast(), getRelation()), new Predicate<String>() {
            @Override public boolean apply(String s) {
                return StringUtils.isNotBlank(s);
            }
        });
        return Joiner.on(" ").skipNulls().join(nonBlankNames);
    }


    public Name withFirstName(String name) {
        this.first = Lists.newArrayList(name);
        return this;
    }

    public Name withLastName(String name) {
        this.last= Lists.newArrayList(name);
        return this;
    }

    public Name withMiddleName(String name) {
        this.middle = Lists.newArrayList(name);
        return this;
    }

    private String unquoted(String name) {
        return name.replace("\"", "");
    }

    public String getFirst() {
        return toString(first);
    }

    public void setFirst(String name) {
        this.first = Lists.newArrayList(name);
    }

    public void setLast(String name) {
        this.last = Lists.newArrayList(name);
    }

    public void setMiddle(String name) {
        this.middle = Lists.newArrayList(name);
    }

    public String getMiddle() {
        return toString(middle);
    }

    public String getLast() {
        return toString(last);
    }

    public String getSalutation() {
        return salutation;
    }

    private String toString(List<String> name) {
        if (name==null) return "";
        String text = Joiner.on(" ").skipNulls().join(name);
        return (name.size()>1) ?
                "\"" + text + "\"" :
                text;
    }

    private boolean isAmbiguous(List<String> name) {
        return name!=null && name.size()>1;
    }

    public Name setSalutation(String salutation) {
        this.salutation = salutation;
        return this;
    }

    public Name addFirst(String name) {
        first.add(unquoted(name));
        return this;
    }

    public Name addMiddle(String name) {
        middle.add(unquoted(name));
        return this;
    }

    public Name addLast(String name) {
        last.add(unquoted(name));
        return this;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Name merge(Name other) {
        if (!StringUtils.isBlank(salutation) && !StringUtils.isBlank(other.salutation)) {
            salutation += "/" + other.salutation;
        }
        if (getLast().isEmpty()) {
            last = other.last;
        } else {
            last.addAll(other.last);
        }
        if (first.isEmpty()) {
            first = other.first;
        } else {
            first.addAll(other.first);
        }
        if (StringUtils.isBlank(relation)) {
            relation = other.relation;
        }
        return this;
    }

    public boolean isAmbiguous() {
        // note the definition of ambiguous is itself ambiguous.
        // if you have two last names then it *could* be ambigous.
        // e.g. john steven dick peterson.   dick could be his last name or middle name.
        // this will most likely be parsed as john/steven/dick peterson when it could be interpreted as
        //  john/steven dick/peterson.
        return isAmbiguous(first) || isAmbiguous(middle) || isAmbiguous(last);
    }

    public void endParse() {
        if (names.size()==0) {
            return;
        }
        first.add(names.get(0));
        if (names.size()==1) {
            return;
        } else if (names.size()==2) {
            last.add(names.get(1));
            return;
        }
        if (names.size()==3) {
            if (isProbablyMiddleName(names.get(1))) {
                middle.add(names.get(1));
                last.addAll(names.subList(2, names.size()));
            } else {
                last.addAll(names.subList(1, names.size()));
            }
        } else {
            last.addAll(names.subList(1, names.size()));
        }
    }

    public void add(String name) {
        names.add(name);
    }

    public boolean isProbablyMiddleName(String name) {
        // this business logic should be refactored out into a spring bean service.
        // if nameService.isProbablyNotAMiddleName(name)  name.addLast(name); else name.addMiddle(name);
        Preconditions.checkArgument(StringUtils.isNotBlank(name));
        if (name.length()==1||(name.length()==2&&name.endsWith("."))) {
            return true;
        }
        // lots more to put here from different cultures.
        List<String> notMiddleNames = Lists.newArrayList("van", "le", "la", "di", "mac", "bin", "binti", "de", "o'", "der", "den", "l'");
        return !notMiddleNames.contains(name.toLowerCase());
    }



}
