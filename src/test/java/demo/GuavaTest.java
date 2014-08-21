package demo;

import com.google.common.collect.BoundType;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.reflect.TypeToken;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class GuavaTest extends TestCase {

    public void test_reflection() {
        List<Address> addresses = Lists.newArrayList();
        List<VIN> vins = Lists.newArrayList();
        Class<?> p = TypeToken.of(StringList.class).resolveType(List.class.getTypeParameters()[0]).getRawType();
        System.out.println(p);
        p = TypeToken.of(addresses.getClass()).resolveType(addresses.getClass().getTypeParameters()[0]).getRawType();
        System.out.println(p);
        p = TypeToken.of(NumberList.class).resolveType(List.class.getTypeParameters()[0]).getRawType();
        System.out.println(p);

        Iterable<Integer> lowScores = Iterables.filter(Lists.newArrayList(34, 44, 12, 53, 7594, 3746, -45), Range.lessThan(100));

        Range<Integer> someScores = Range.closed(0, 42);
        for(int sc : ContiguousSet.create(someScores, DiscreteDomain.integers())) {
            System.out.println(sc);
        }
        Range<Integer> integerRange = Range.downTo(3, BoundType.CLOSED);
    }

    public class StringList extends ArrayList<String> {
    }

    public class NumberList extends ArrayList<Number> {
    }



}
