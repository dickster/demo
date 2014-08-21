package com.brovada.easy.widgets;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class NameParserTest extends TestCase {

    @Test
    public void test_NameParser() throws ParseException {
        NameParser parser = new NameParser(new ByteArrayInputStream("".getBytes()));
        NameResult name = parser.parseName("mr derek w dick");
        print(name);
        name = parser.parseName("mr foo bar really long name and mrs his wife with an even longer name");
        print(name);
        name = parser.parseName("derek le van der hoerf-smith jones");
        print(name);
        name = parser.parseName("brad pitt");
        print(name);
        name = parser.parseName("mr. brad pitt");
        print(name);
        name = parser.parseName("mr. brad pitt + angelina jolie");
        print(name);
        name = parser.parseName("brad pitt + angelina jolie");
        print(name);
        name = parser.parseName("mr joe & mrs steve stevenson");
        print(name);
        name = parser.parseName("mister. joe & sue smith");
        print(name);
        name = parser.parseName("mrs. joe & ms cathy jones");
        print(name);
        name = parser.parseName("mr. \"derek w\" dick");
        print(name);
        name = parser.parseName("ms derek w dick");
        print(name);
        name = parser.parseName("ms derek w. dick");
        print(name);
        name = parser.parseName("sir derek w dick IX");
        print(name);
        name = parser.parseName("derek o'flaherty");
        print(name);
        name = parser.parseName("mr. joe derek-smith");
        print(name);
        name = parser.parseName("joe woo-hoo-toodle-loo jones");
        print(name);
        name = parser.parseName("doctor derek dick-smith");
        print(name);
        name = parser.parseName("derek \"the killer\" dick");
        print(name);
        name = parser.parseName("mr steve smith and ms cathy jones");
        print(name);
        name = parser.parseName("joe 'n cathy jones junior");
        print(name);
        name = parser.parseName("bill 'n bob r kansas III");
        print(name);
        name = parser.parseName("mr joe and ms cathy jones III");
        print(name);
        name = parser.parseName("sir richard & mrs dorothy jones");
        print(name);
        name = parser.parseName("george w. bush junior");
        print(name);
        name = parser.parseName("mr. george bush senior");
        print(name);
        name = parser.parseName("sir henry foobar iii");
        print(name);
        name = parser.parseName("prince");
        print(name);
        name = parser.parseName("the edge");
        print(name);
        name = parser.parseName("pele");
        print(name);
        name = parser.parseName("pele and prince");
        print(name);
        name = parser.parseName("pele and mr steve smith");
        print(name);
        name = parser.parseName("pele + mr steve smith");
        print(name);
        name = parser.parseName("sir pele and mr steve smith");
        print(name);
        name = parser.parseName("mr and mrs steve smith");
        print(name);
        name = parser.parseName("mrs and dr steve smith");
        print(name);
        name = parser.parseName("mr joe smith and mr steve smith");
        print(name);
        name = parser.parseName("mr joe smith iv & steve smith senior");
        print(name);
        name = parser.parseName("dr. franklin j washburn-lodge iii and joe \"bone crusher\" jones");
        print(name);
        name = parser.parseName("mr and ms steve smith");
        print(name);
    }

    private void print(NameResult name) {
        System.out.println("");
        System.out.print("  " +name + " " + (name.isTwoPeople() ? " (2)" : "" + (name.mayBeAmbiguous() ? " - ambigious" : "")));
        if (name.isTwoPeople()) {
            System.out.println(" merged ==>  : " + name.getName());
        } else {
            System.out.println("");
        }
    }


}
