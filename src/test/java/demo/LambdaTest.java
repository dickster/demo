package demo;

import ch.lambdaj.function.closure.Closure1;
import ch.lambdaj.function.closure.Closure2;
import ch.lambdaj.function.matcher.Predicate;
import ch.lambdaj.group.Group;
import com.google.common.collect.Lists;
import junit.framework.TestCase;
import org.hamcrest.Matcher;

import java.util.Collections;
import java.util.List;

import static ch.lambdaj.Lambda.*;
import static ch.lambdaj.collection.LambdaCollections.with;
import static org.hamcrest.Matchers.greaterThan;


public class LambdaTest extends TestCase {

    List<Person> people = Lists.newArrayList(
            new Person("john", 32),
            new Person("cathy", 56),
            new Person("bill", 93),
            new Person("sally", 18),
            new Person("steve", 18)
    );

    public void test_sort() {
        List<String> oldPeople = with(people)
                .retain(having(on(Person.class).getAge(), greaterThan(28)))
                .extract(on(Person.class).getName())
                .sort(on(String.class));

        System.out.println(oldPeople);
    }

    public void test_sum() {
        int age = sum(people, on(Person.class).getAge());
        System.out.println(age);

        List<Person> oldPeople = with(people)
                .retain(having(on(Person.class).getAge(), greaterThan(28)));
        age = sum(oldPeople, on(Person.class).getAge());
        System.out.println(age);
    }

    public void test_group() {
        Group<Person> group = group(people, by(on(Person.class).getVin().getYear()));
        System.out.println(group.findGroup("1918").findAll());
    }

    public void test_filter() {
        Matcher<Person> fiveLetterName = new Predicate<Person>() {
            public boolean apply(Person item) {
                return item.getName().length()==5;
            }
        };

        List<Person> fiveLetterNames = filter(fiveLetterName, people);
        System.out.println(fiveLetterNames);
    }

    public void test_foreach() {
        forEach(people).setAge(55);
        System.out.println(people);
    }

    public Integer validate(Person a, Person b) {
        System.out.println("validating " + a + " " + b);
        return a.getAge()+b.getAge();
    }

    public void test_closure() {
        Closure2<Person, Person> ageAdder = closure(Person.class, Person.class); {
            of(this).validate(var(Person.class), var(Person.class));
        }
        Integer result = (Integer)ageAdder.apply(people.get(0), people.get(1));
        System.out.println(result);
        Closure1<Person> ageAdder2 = ageAdder.curry2(people.get(2));
        result = (Integer)ageAdder2.apply(people.get(0));
        System.out.println(result);
    }




    public class Person {
        String name;
        Integer age;
        VIN vin;

        Person(String name, Integer age) {
            this.name = name;
            this.age = age;
            vin = new VIN(1900+age, name, "model", name+age );
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public VIN getVin() {
            return vin;
        }

        public void setVin(VIN vin) {
            this.vin = vin;
        }

        @Override
        public String toString() {
            return name + '(' + age + ')';
        }
    }
}
