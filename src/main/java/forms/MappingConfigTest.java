package forms;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;


public class MappingConfigTest extends TestCase {

    MappingConfig<Source,Dest> config;
    private Dest dest;
    private Source source;

    @Before
    public void setup() {
        config = new MappingConfig<Source, Dest>();
    }

    @Test
    public void test_Simple() {
        source = new Source();
        dest = new Dest();
        config.addMapping("s1.s1A", "d1.d1xxx");
        config.addMapping("s1.s1b", "d1.x");
        config.addMapping("hello", "d1.y");
        config.addMapping("foo", "d2.destFoo");
        config.mapFromAcordToImpl(source, dest);
        System.out.println(dest);
    }


    class Source {
        S1 s1;
        S2 s2;
        String hello = "hello";
        Object foo = "foo";

    }

    class S1 {
        String s1A = "s1A";
        String s1b = "s1b";
    }

    class S2 {
        String s2A = "s2A";
        String s2b = "s2b";
    }

    class Dest {
        D1 d1;
        D2 d2;
    }

    class D1 {
        String d1XXX;
        String x,y,z;
    }

    class D2 {
        String destHello;
        Object destFoo;
    }

}



