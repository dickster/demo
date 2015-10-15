package forms;


import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class TransformationTest extends TestCase {

    @Before
    public void setup() {

    }

    @Test
    public void test_chain() {
        Transformation x = PredefinedTransformation.UPPERCASE;
        SuffixTransformation y = new SuffixTransformation(".foo").caseSensitive();
        Transformation<String> chain = new ChainedTransformation<String>(x,y);

        String result = chain.transform("hello");
        System.out.println(result);

        result = chain.transform("HeLLo??");
        System.out.println(result);

        result = chain.inverseTransform("abc");
        System.out.println(result);
        result = chain.inverseTransform("ABC");
        System.out.println(result);
        result = chain.inverseTransform("abc.Foo");
        System.out.println(result);
        result = chain.inverseTransform("abc.foo");
        System.out.println(result);
        result = chain.inverseTransform("ABC.foo");
        System.out.println(result);


        result = chain.transform(null);
        System.out.println(result);

        result = chain.transform("");
        System.out.println(result);

        y.skipNulls().skipBlanks();
        result = chain.transform(null);
        System.out.println(result);
        result = chain.transform("");
        System.out.println(result);
        result = chain.inverseTransform(null);
        System.out.println(result);

    }
}
