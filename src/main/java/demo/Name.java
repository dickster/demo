package demo;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Stack;

public class Name implements Serializable {
    List<String> salutation = Lists.newArrayList();
    List<String> first = Lists.newArrayList();
    List<String> middle = Lists.newArrayList();
    List<String> last = Lists.newArrayList();
    List<String> relations = Lists.newArrayList();  //junior, senior, III etc...
    List<String> titles = Lists.newArrayList();  //PHd MD B.Sc etc...
    List<String> names = Lists.newArrayList();
    Stack<String> prefixes = new Stack<String>();
    List<String> nickNames = Lists.newArrayList();
    private boolean inverse = false;

    public Name(String john, String doe) {
        addFirst(john);
        addLast(doe);
    }

    public Name() {
    }

//    @Override
//    public String toString() {
//        // CAVEAT : important not to change this. used by wicket models.
//        Iterable<String> nonBlankNames = Iterables.filter(Lists.newArrayList(getSalutation(), getFirst(), getMiddle(), getLast(), getRelation()), new Predicate<String>() {
//            @Override public boolean apply(String s) {
//                return StringUtils.isNotBlank(s);
//            }
//        });
//        return Joiner.on(" ").skipNulls().join(nonBlankNames);
//    }



    private String getRelation() {
        return relations.toString();
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

    public String getMiddle() {
        return toString(middle);
    }

    public String getLast() {
        return toString(last);
    }

    public String getTitles() {
        return toString(titles);
    }

    public String getSalutation() {
        return salutation.toString();
    }

    private String toString(List<String> name) {
        String text = Joiner.on(" ").skipNulls().join(name);
        return (name.size()>1) ?
                "\"" + text + "\"" :
                text;
    }

    private boolean isAmbiguous(List<String> name) {
        return name!=null && name.size()>1;
    }

    public Name addSalutation(String salutation) {
        this.salutation.add(salutation);
        System.out.println("salutation added " + salutation);
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

    public List<String> getRelations() {
        return relations;
    }

    public void addRelation(String relation) {
        this.relations.add(relation);
    }

    public Name merge(Name other) {
        // e.g. Mr and Mrs John Smith will yield...
        //      Mr John Smith,  Mrs John Smith.
        // Mr John and Mrs Sue Smith will yield...
        //      Mr John Smith,  Mrs Sue Smith.
        if (first.isEmpty()) {
            first.addAll(other.first);
        }
        if (getLast().isEmpty()) {
            last.addAll(other.last);
        }
        return this;
    }

    public boolean isAmbiguous() {
        // note the definition of ambiguous is itself ambiguous.
        // if you have two last names then it *could* be ambiguous.
        // e.g. john steven dick peterson.   dick could be his last name or middle name.
        // this will most likely be parsed as john/steven/dick peterson when it could be interpreted as
        //  john/steven dick/peterson.
        return isAmbiguous(first) || isAmbiguous(middle) || isAmbiguous(last);
    }

    public void endParse() {
        if (inverse) {
            String lastName = names.get(0);
            names.remove(0);
            names.add(lastName);
        }

        if (names.size()==0) {
            return;
        }
        first.add(names.get(0));
        if (names.size()==1) {
            return;
        }
        else if (names.size()==2) {
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
            System.out.println("THIS NAME IS AMBIGUOUS? assuming a middle name of " + names.get(1));
            middle.add(names.get(1));
            last.addAll(names.subList(2, names.size()));
        }
    }

    public void add(String name) {
        // TODO : add ambiguity check. if name = "O" could be prefix?
        // alternatives add(name);
        if (!prefixes.isEmpty()) {
            names.add(prefixes.pop() + " " + name);
        }
        else {
            names.add(name);
        }
    }

    public boolean isProbablyMiddleName(String name) {
        // this business logic should be refactored out into a spring bean service.
        // if nameService.isProbablyNotAMiddleName(name)  name.addLast(name); else name.addMiddle(name);
        Preconditions.checkArgument(StringUtils.isNotBlank(name));
        if (name.length()==1||(name.length()==2&&name.endsWith("."))) {
            return true;
        }
        // lots more to put here from different cultures.
        // TODO :
        List<String> notMiddleNames = Lists.newArrayList("van", "le", "la", "di", "mac", "bin", "binti", "de", "o'", "der", "den", "l'");
        return !notMiddleNames.contains(name.toLowerCase());
    }

    public void pushPrefix(String prefix) {
        prefixes.push(prefix);
    }

    public void addNickName(String name) {
        this.nickNames.add(name.substring(1, name.length() - 1));
    }

    public void addTitle(String name) {
        this.titles.add(name);
    }

    public void setInverse(boolean inverse) {
        this.inverse = inverse;
    }


    @Override
    public String toString() {
        return toDebugString();
        //return getFirst() + " " + getLast();
    }

    public String toDebugString() {
        StringBuilder builder = new StringBuilder();
        if (!salutation.isEmpty()) builder.append("  salutation :" + salutation);
        if (!first.isEmpty()) builder.append("  first :" + first);
        if (!middle.isEmpty()) builder.append("  middle :" + middle);
        if (!nickNames.isEmpty()) builder.append("  nickNames :" + nickNames);
        if (!last.isEmpty()) builder.append("  last :" + last);
        if (!relations.isEmpty()) builder.append("  relations :" + relations);
        if (!titles.isEmpty()) builder.append("  titles:" + titles);

        return builder.toString();
    }

}
