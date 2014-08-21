package demo;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.util.Collection;
import java.util.Stack;

public class NameResult extends Stack<Name> {

    public Name getName() {
        return merge();
    }

    public Collection<Name> getNames() {
        return this;
    }

    private final Name merge() {
        Preconditions.checkState(size()==1 || size()==2, "can't merge if there are no names or too many names : " + size());
        if (size()==1) {
            return get(0);
        }
        return get(0).merge(get(1));
    }

    public boolean isTwoPeople() {
        return size()==2;
    }

    public boolean mayBeAmbiguous() {
        Iterable<Name> ambiguous = Iterables.filter(this, new Predicate<Name>() {
            @Override public boolean apply(Name name) {
                return name.isAmbiguous();
            }
        });
        return ambiguous.iterator().hasNext();
    }
}
